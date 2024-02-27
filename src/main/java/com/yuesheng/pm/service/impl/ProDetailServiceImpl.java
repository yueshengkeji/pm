package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProDetailMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author XiaoSong
 * @date 2017/2/10
 */
@Service("ProDetailService")
public class ProDetailServiceImpl implements ProDetailService {

    @Autowired
    private ProDetailMapper proDetailMapper;

    @Autowired
    private ProPutDetailService proPutDetailService;

    @Autowired
    private ProDetailPayService proDetailPayService;

    @Autowired
    private ProDetailMoneyService proDetailMoneyService;
    @Autowired
    private ProDetailOweService oweService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addDetail(ProDetail detail, Staff staff) {
        Map<String, Object> result = new HashMap<>(16);
        if (detail.getYear() == null || "".equals(detail.getYear().trim())) {
//            设置单据年份
            detail.setYear(DateFormat.getYear());
        }
        /*
         * 判断单据+年份是否存在，存在则不创建，直接返回通知客户端
         */
        result.put("detail", detail);
        if (companyExist(detail.getCompany().getId(), null, detail.getYear(), detail.getCompanyBelong()) != null) {
            result.put("status", "-1");
            result.put("msg", "该单位今年已经添加");
            return result;
        }

        /*
         * 判断单位是否为null
         */
        if (detail.getCompany() == null || detail.getCompany().getId() == null) {
            Company company = new Company();
            company.setId("");
            detail.setCompany(company);
        }
//        设置制单人
        detail.setStaff(staff);
//        设置制单日期
        detail.setDate(DateFormat.getDateTime());
        detail.setId(UUID.randomUUID().toString());
//        设置单据编号
        detail.setSeries(detail.getComName() + detail.getYear());

        proDetailMapper.addDetail(detail);
        /*
         * 自动生成一个账面欠款单据
         */
        ProDetailMoney money = new ProDetailMoney();
        money.setId(UUID.randomUUID().toString());
        money.setMainId(detail.getId());
        money.setMoney(0.0);
        money.setDate(DateFormat.getDateTime());
        money.setSeries("默认科目");
        money.setRemark("");
        proDetailMoneyService.addMoney(money);
        result.put("status", "1");
        result.put("msg", "添加成功");
        return result;
    }

    @Override
    public int updateRemark(String remark, String id) {
        return proDetailMapper.updateRemark(remark, id);
    }

    @Override
    public int updateSaleName(String saleName, String id) {
        return proDetailMapper.updateSaleName(saleName, id);
    }

    @Override
    public int updateSaleTel(String tel, String id) {
        return proDetailMapper.updateSaleTel(tel, id);
    }

    @Override
    public int updateTax(Double tax, String id) {
        return proDetailMapper.updateTax(tax, id);
    }

    @Override
    public int updateSex(int sexType, String id) {
        return proDetailMapper.updateSex(sexType, id);
    }

    @Override
    public int updateYearOwe(Double money, String id) {
        return proDetailMapper.updateYearOwe(money, id);
    }

    @Override
    public int updatePaper(Double money, String id) {
        return proDetailMapper.updatePaper(money, id);
    }

    @Override
    public int updateEndOwe(String id) {
//        获取采购金额
        Map<String, BigDecimal> proPut = proPutDetailService.getMoneySumByMain(id);
//        获取已付款金额
        Map<String, BigDecimal> pay = proDetailPayService.getMoneySumByMain(id);
//        获取年初欠款
        Double money = getYearMoney(id);
//        采购金额
        BigDecimal proMoney = null;
        try {
            proMoney = proPut.get("pro_money");
        } catch (NullPointerException e) {
            proMoney = new BigDecimal(0);
        }
//        付款金额
        BigDecimal payMoney = null;
        try {
            payMoney = pay.get("pay_money");
        } catch (NullPointerException e) {
            payMoney = new BigDecimal(0);
        }
        /*
          开始计算
          期末欠款 = 年初欠款 + 今年采购 - 今年已付款；
         */
        money = ((money != null ? money : 0) + (proMoney != null ? proMoney.doubleValue() : 0.0)) - (payMoney != null ? payMoney.doubleValue() : 0);
        return proDetailMapper.updateEndOwe(money, id);
    }

    public Double getYearMoney(String id) {
        return proDetailMapper.getYearMoney(id);
    }

    @Override
    public int updateLastMsg(String lastDate, String staffId) {
        return proDetailMapper.updateLastMsg(lastDate, staffId);
    }

    @Override
    public int deleteDetail(String id) {
        return proDetailMapper.deleteDetail(id);
    }

    @Override
    public List<ProDetail> getDetailByYear(String year) {
        return proDetailMapper.getDetailByYear(year);
    }

    @Override
    public String companyExist(String companyId, String companyName, String year, Integer companyBelong) {
        PageHelper.startPage(1,1);
        return proDetailMapper.companyExist(companyId, companyName, year, companyBelong);
    }

    @Override
    public int updateDetail(ProDetail detail) {
        return proDetailMapper.updateDetail(detail);
    }

    @Override
    public ProDetail getDetailById(String id) {
        return proDetailMapper.getDetailById(id);
    }

    @Override
    public String companyExistDetailId(String companyId, String companyName, String newYear, Integer companyBelong) {
        PageHelper.startPage(1,1);
        return proDetailMapper.companyExistDetailId(companyId, companyName, newYear, companyBelong);
    }

    @Override
    public List<ProDetail> getDetailByYear(Map<String, Object> params, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,false);
        return proDetailMapper.getDetailByParam(params);
    }

    @Override
    public Integer getDetailByYear(Map<String, Object> params) {
        return proDetailMapper.getDetailCountByParam(params);
    }

    @Override
    public List<ProDetail> getDetailByCompany(String companyId, String year) {
        return proDetailMapper.getDetailByCompany(companyId, year);
    }

    @Override
    public ProDetail getDetailByCompany(String companyId, String substring, String companyBelong) {
        PageHelper.startPage(1,1);
        return proDetailMapper.getDetailByCompanyBelong(companyId, substring, companyBelong);
    }

    @Override
    public String getCompanyType(String projectType) {
        if (projectType.equals("U0HCGCKL")) {
            projectType = "2";
        } else {
            projectType = "1";
        }
        return projectType;
    }

    @Override
    public int updateDate(ProDetail nowDetail) {
        return proDetailMapper.updateDate(nowDetail);
    }

    @Override
    public String getCompanyTypeByPro(Procurement procurement) {
        if (StringUtils.equals(procurement.getPm01326(), "1")) {
            return "2";
        }
        return "1";
    }

    @Override
    public ProDetail addDetailByProcurement(Procurement procurement) {
        ProDetail pd = new ProDetail();
        Company company = procurement.getCompany();
        pd.setCompany(company);
        pd.setComName(company.getName());
        pd.setSaleName(company.getRelationP());
        pd.setTel(company.getTel());
        pd.setTax(procurement.getTax());
        pd.setSeries(company.getName());
        if ("1".equals(procurement.getPm01326())) {
            pd.setCompanyBelong(2);
        } else {
            pd.setCompanyBelong(1);
        }
        pd.setStaff(procurement.getStaff());
        pd.setType(0);
        pd.setYear(DateUtil.getYear());
        Map<String, Object> result = addDetail(pd, procurement.getStaff());
        if (result.get("status").equals("1")) {
            return null;
        } else {
            return pd;
        }
    }

    @Override
    public List<ProDetail> getDetailListByYear(Map<String, Object> params) {
        return proDetailMapper.getDetailByParamV2(params);
    }

    @Override
    public List<ProDetail> getDetailByYearV2(Map<String, Object> params) {
        return proDetailMapper.getDetailByParamV2(params);
    }

    @Override
    public int updateMoney(ProDetail detail) {
        return proDetailMapper.updateMoney(detail);
    }

    @Override
    public int updateYearMoney(String id, Double proMoney) {
        ProDetail pd = new ProDetail();
        pd.setId(id);
        pd.setYearPro(proMoney);
        return proDetailMapper.updateYearPro(pd);
    }

    @Override
    public int updatePutMoney(String id, Double putMoney) {
        ProDetail pd = new ProDetail();
        pd.setId(id);
        pd.setPutMoney(putMoney);
        return proDetailMapper.updatePutMoney(pd);
    }

    @Override
    public int updateProAndPutMoney(String id, Double proMoney, Double putMoney) {
        ProDetail pd = new ProDetail();
        pd.setId(id);
        pd.setPutMoney(putMoney);
        pd.setYearPro(proMoney);
        return proDetailMapper.updateProAndPutMoney(pd);
    }

    @Override
    public int updatePayAndBillMoney(String id, Double payMoney, Double billMoney) {
        ProDetail pd = new ProDetail();
        pd.setId(id);
        pd.setBillMoney(billMoney);
        pd.setYetMoney(payMoney);
        return proDetailMapper.updatePayAndBillMoney(pd);
    }

    @Override
    public int updateTotalMoneys(String year) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("year", StringUtils.isBlank(year) ? DateUtil.getYear() : year);
        PageHelper.startPage(1, 100, "series desc");
        Page<ProDetail> details = (Page<ProDetail>) getDetailByYearV2(params);
        int pages = details.getPages();
        int count = 0;
        for (int i = 2; i <= pages; i++) {
            count += details.size();
            updateCount(details);
            PageHelper.startPage(i, 100, "series desc");
            details = (Page<ProDetail>) getDetailByYearV2(params);
        }
        if (details.size() > 0) {
            updateCount(details);
            count += details.size();
        }
//        LogManager.getLogger("mylog").info("更新对账单行数：" + count);
        System.out.println("更新对账单行数：" + count);
        return count;
    }

    private void updateCount(Page<ProDetail> details) {
        details.forEach(item -> {

            //更新付款统计
            proDetailPayService.updateMainPayMoney(item.getId());

            //更新入库统计
            ProPutForDetail ppfd = new ProPutForDetail();
            ppfd.setMainId(item.getId());
            proPutDetailService.updateMainDetail(ppfd);

            //更新会计科目
            ProDetailMoney money = new ProDetailMoney();
            money.setMainId(item.getId());
            proDetailMoneyService.updateDetailSubject(money);

            //更新年初欠款/欠票金额
            ProDetailOwe oweMoney = oweService.getOweByDate(null, item.getId(), 1);
            if (!Objects.isNull(oweMoney)) {
                item.setYearOwe(oweMoney.getOweMoney());
            }
            oweMoney = oweService.getOweByDate(null, item.getId(), 0);
            if (!Objects.isNull(oweMoney)) {
                item.setYearBillFinance(oweMoney.getOweMoney());
            }
            updateYearOweAndBill(item);
        });
    }

    @Override
    public int updateYearOweAndBill(ProDetail item) {
        return proDetailMapper.updateYearOweAndBill(item);
    }

    @Override
    public int updateSubjects(String id, String subSeries) {
        return proDetailMapper.updateSubjects(id, subSeries);
    }
}
