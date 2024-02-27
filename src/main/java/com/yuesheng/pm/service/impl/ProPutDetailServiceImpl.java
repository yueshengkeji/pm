package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProPutDetailMapper;
import com.yuesheng.pm.model.ProPutDetailCount;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 96339 on 2017/2/11.
 *
 * @author XiaoSong
 * @date 2017/02/11
 */
@Service("proPutDetailService")
public class ProPutDetailServiceImpl implements ProPutDetailService {
    @Autowired
    private ProPutDetailMapper proPutDetailMapper;
    @Autowired
    @Lazy
    private ProDetailService proDetailService;
    @Autowired
    private ProDetailDpService proDetailDpService;
    @Autowired
    @Lazy
    private ProcurementService procurementService;
    @Autowired
    private ProcurementMaterService materService;

    @Override
    public void addDetail(ProPutForDetail proPutForDetail) {
        if (StringUtils.isBlank(proPutForDetail.getProjectName())) {
            proPutForDetail.setProjectName("");
        }
        if (Objects.isNull(proPutForDetail.getProject())) {
            proPutForDetail.setProject(new Project());
        }
//        添加采购入库明细
        proPutDetailMapper.addDetail(proPutForDetail);
//        更新主单据期末欠款
        proDetailService.updateEndOwe(proPutForDetail.getMainId());
        updateMainDetail(proPutForDetail);
    }

    @Override
    public ProPutForDetail updateDetail(ProPutForDetail detail) {
        if (StringUtils.isBlank(detail.getProjectName())) {
            detail.setProjectName("");
        }
        if (Objects.isNull(detail.getProject())) {
            Project p = new Project();
            p.setName("");
            p.setId("");
            detail.setProject(p);
        }
        int row = proPutDetailMapper.updateDetail(detail);
        ProPutForDetail result = updateMainDetail(detail);
        return result;
    }

    @Override
    public ProPutForDetail updateMainDetail(ProPutForDetail detail) {
        if (!Objects.isNull(detail) && StringUtils.isNotBlank(detail.getMainId())) {
            Double proMoney = getProMoneySumByMain(detail.getMainId());
            Double putMoney = getPutMoneySumByMain(detail.getMainId());
            proDetailService.updateProAndPutMoney(detail.getMainId(), proMoney, putMoney);
            ProPutForDetail result = new ProPutForDetail();
            result.setProMoney(proMoney);
            result.setPutMoney(putMoney);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public int updateProMoney(ProPutForDetail detail) {
        int rows = proPutDetailMapper.updateProMoney(detail);
        updateMainDetail(detail);
        //更新主单据期末欠款
        rows += proDetailService.updateEndOwe(detail.getMainId());
        return rows;
    }

    @Override
    public int updatePutMoney(ProPutForDetail detail) {
        int row = proPutDetailMapper.updatePutMoney(detail);
        updateMainDetail(detail);
        return row;
    }

    @Override
    public int updateProject(ProPutForDetail detail) {
        return proPutDetailMapper.updateProject(detail);
    }

    @Override
    public int updateLastMsg(String lastDate, Staff lastStaff, String id) {
        return proPutDetailMapper.updateLastMsg(lastDate, lastStaff, id);
    }

    @Override
    public int deleteDetail(String id) {
        ProPutForDetail pdd = getDetail(id);
        if (!Objects.isNull(pdd)) {
            int row = proPutDetailMapper.deleteDetail(id);
            updateMainDetail(pdd);
            return row;
        } else {
            return 0;
        }
    }

    @Override
    public int deleteDetailForMain(String mainId) {
        int row = proPutDetailMapper.deleteDetailForMain(mainId);
        ProPutForDetail pdd = new ProPutForDetail();
        pdd.setMainId(mainId);
        updateMainDetail(pdd);
        return row;
    }

    @Override
    public List<ProPutForDetail> getDetailByMain(String mainId) {
        return proPutDetailMapper.getDetailListByMain(mainId);
    }

    @Override
    public ProPutForDetail getDetail(String id) {
        return proPutDetailMapper.getDetail(id);
    }

    @Override
    public List<ProPutForDetail> getDetailByMain(Map<String, Object> params) {
        return proPutDetailMapper.getDetailByDate(params);
    }

    @Override
    public Double getProMoneySumByMain(String mainId) {
        return proPutDetailMapper.getProMoneySumByMain(mainId);
    }

    @Override
    public Double getPutMoneySumByMain(String mainId) {
        return proPutDetailMapper.getPutMoneySumByMain(mainId);
    }

    @Override
    public Map<String, BigDecimal> getMoneySumByMain(String mainId) {
        return proPutDetailMapper.getMoneySumByMain(mainId);
    }

    @Override
    public Double getProMoneyByDate(String start, String end, String type) {
        return proPutDetailMapper.getProMoneyByDate(start, end, type);
    }

    @Override
    public List<ProDetailDP> getDetailDp(String proDetailId) {
        return proDetailDpService.getDetailDp(proDetailId);
    }

    @Override
    public int insertProDetailDp(ProDetailDP detailDp) {
        return proDetailDpService.insertProDetailDp(detailDp);
    }

    @Override
    public int updateProDetailDp(ProDetailDP proDetailDp) {
        return proDetailDpService.updateProDetailDp(proDetailDp);
    }

    @Override
    public Map<String, BigDecimal> getMoneysByPayAndDp(String proDetailId) {
        return proDetailDpService.getMoneyByPayAndDp(proDetailId);
    }

    @Override
    public ProDetailDP getProDetailDp(String id) {
        return proDetailDpService.getProDetailDp(id);
    }

    @Override
    public boolean addDetail(ProPutForDetail detail, Staff staff) {
        if (detail.getMainId() != null && !"".equals(detail.getMainId())) {
            // if ("采购部".equals(staff.getSection().getName())) {
//                只能由采购部添加该明细单据
//                ProDetail pd = proDetailService.getDetailById(detail.getMainId());
//                if (staff.getId().equals(pd.getStaff().getId())) {
            setProject(detail);
            detail.setStaff(staff);
            detail.setId(UUID.randomUUID().toString());
            detail.setDate(DateFormat.getDateTime());
            addDetail(detail);
            return true;
            // }
        }
        return false;
    }

    @Override
    public void deleteByProId(String proId) {
        List<ProPutForDetail> pddList = proPutDetailMapper.getProDetailByProId(proId, null);
        proPutDetailMapper.deleteByProId(proId);
        pddList.forEach(this::updateMainDetail);
    }

    @Override
    public int updateRemark(ProPutForDetail detail) {
        return proPutDetailMapper.updateRemark(detail);
    }

    @Override
    public List<ProPutForDetail> getProDetailBYProId(String proId, String detailId) {
        return proPutDetailMapper.getProDetailByProId(proId, detailId);
    }

    @Override
    public ProPutForDetail getDetailByMain(String id, String projectId) {
        PageHelper.startPage(1,1);
        return proPutDetailMapper.getDetailByMain(id, projectId);
    }

    @Override
    public List<ProDetailDP> getDpByParam(Map<String, Object> param,Integer pageNum,Integer pageSize) {
        return proDetailDpService.getDpByParam(param,pageNum,pageSize);
    }

    @Override
    public Integer getDpCount(Map<String, Object> result) {
        return proDetailDpService.getDpCount(result);
    }

    @Override
    public int deleteByPutId(String putId) {
        List<ProPutForDetail> ppfd = proPutDetailMapper.getProDetailByPutId(putId);
        int row = proPutDetailMapper.deleteByPutId(putId);
        ppfd.forEach(this::updateMainDetail);
        return row;
    }

    @Override
    public List<ProPutForDetail> getProDetailByPutId(String putId) {
        return proPutDetailMapper.getProDetailByPutId(putId);
    }

    @Override
    public Double getProMoneySumByMain(String mainId, String endTime) {
        return proPutDetailMapper.getProMoneySumByMainDate(mainId, endTime);
    }

    @Override
    public int deleteBySeries(String mainId, String proDate) {
        int row = proPutDetailMapper.deleteBySeries(mainId, proDate);
        ProPutForDetail ppfd = new ProPutForDetail();
        ppfd.setMainId(mainId);
        updateMainDetail(ppfd);
        return row;
    }

    @Override
    public List<ProPutForDetail> getBySeries(String contractSeries, String proceedsSeries) {
        return proPutDetailMapper.getBySeries(contractSeries, proceedsSeries);
    }

    @Override
    public ProPutForDetail getByContractDetailId(String contractId, String detailId) {
        return proPutDetailMapper.getByContractDetailId(contractId, detailId);
    }

    @Override
    public int deleteByContractDetailId(String contractId, String detailId) {
        ProPutForDetail ppfd = proPutDetailMapper.getByContractDetailId(contractId, detailId);
        int row = proPutDetailMapper.deleteByContractDetailId(contractId, detailId);
        updateMainDetail(ppfd);
        return row;
    }

    @Override
    public int deleteByContract(String contractId) {
        int row = proPutDetailMapper.deleteByContract(contractId);
        ProPutForDetail pdd = new ProPutForDetail();
        pdd.setMainId(contractId);
        updateMainDetail(pdd);
        return row;
    }

    @Override
    public List<ProPutForDetail> getDetailAndDpByMain(String detailId) {
        List<ProPutForDetail> proPutForDetails = getDetailByMain(detailId);
        Map<String, BigDecimal> moneysResult;
        for (ProPutForDetail proPutForDetail : proPutForDetails) {
            moneysResult = getMoneysByPayAndDp(proPutForDetail.getId());
            setProPayAndDp(moneysResult, proPutForDetail);
        }
        return proPutForDetails;
    }

    @Override
    public void setProPayAndDp(final Map<String, BigDecimal> moneysResult,
                               final ProPutForDetail proPutForDetail) {
        /*
         * 采购登记付款总数
         */
        try {
            proPutForDetail.setPayMoneys(moneysResult.get("payMoneys").doubleValue());
        } catch (NullPointerException e) {
            proPutForDetail.setPayMoneys(0.0);
        }
        /*
         * 采购登记到票总数
         */
        try {
            proPutForDetail.setDpMoneys(moneysResult.get("dpMoneys").doubleValue());
        } catch (NullPointerException e) {
            proPutForDetail.setDpMoneys(0.0);
        }
    }

    @Override
    public List<ProPutForDetail> getDetailByDate(Map<String, Object> params) {
        return getDetailByDate(params, true);
    }

    @Override
    public List<ProPutForDetail> getDetailByDate(Map<String, Object> params, boolean loadProMoney) {
        List<ProPutForDetail> proPutForDetails = getDetailByMain(params);
        Map<String, BigDecimal> moneysResult;

        for (ProPutForDetail proPutForDetail : proPutForDetails) {
            moneysResult = getMoneysByPayAndDp(proPutForDetail.getId());
            if (moneysResult != null) {
                setProPayAndDp(moneysResult, proPutForDetail);
            }
            if (loadProMoney) {
                if (proPutForDetail.getProId() != null) {
                    String p = procurementService.getProSaleMoney(proPutForDetail.getProId());
                    if (StringUtils.isBlank(p) || StringUtils.equals(p, "0")) {
                        Double money = materService.getProMoneyByProId(proPutForDetail.getProId());
                        proPutForDetail.setProjectName(proPutForDetail.getProjectName() + "-" + new DecimalFormat("######0.00").format(money));
                    } else {
                        proPutForDetail.setProjectName(proPutForDetail.getProjectName() + "-" + p);
                    }
                }
            }
        }

        return proPutForDetails;
    }

    @Override
    public List<ProPutForDetail> getDetail(HashMap<String, Object> param) {
        return proPutDetailMapper.getDetailAll(param);
    }

    @Override
    public ProPutDetailCount getDetailCount(HashMap<String, Object> param) {
        return this.proPutDetailMapper.getDetailCount(param);
    }

    @Override
    public Double getProMoneySum(String year) {
        return this.proPutDetailMapper.getProMoneySum(year);
    }

    @Override
    public Double getPutMoneySum(String year) {
        return this.proPutDetailMapper.getPutMoneySum(year);
    }

    private void setProject(ProPutForDetail detail) {
        if (detail.getProject() == null || detail.getProject().getId() == null) {
            Project project = new Project();
            project.setId("");
            project.setName("");
            detail.setProject(project);
        } else {
            detail.setProjectName(detail.getProject().getName());
        }
    }
}
