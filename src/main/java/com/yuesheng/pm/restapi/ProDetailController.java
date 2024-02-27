package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NetRequestUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by XiaoSong on 2017/2/10 采购明细.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
@Controller
@RequestMapping("/managent")
public class ProDetailController {
    /**
     * 主单据服务
     */
    @Autowired
    private ProDetailService proDetailService;
    /**
     * 采购入库服务
     */
    @Autowired
    private ProPutDetailService proPutDetailService;
    /**
     * 采购付款明细服务
     */
    @Autowired
    private ProDetailPayService proDetailPayService;
    /**
     * 欠款、欠票明细
     */
    @Autowired
    private ProDetailOweService proDetailOweService;
    /**
     * 账面欠款服务
     */
    @Autowired
    private ProDetailMoneyService proDetailMoneyService;
    /**
     * 采购订单材料服务
     */
    @Autowired
    private ProcurementMaterService procurementMaterService;
    /**
     * 合同付款明细
     */
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private ProcurementService procurementService;

    /**
     * 添加主单据
     *
     * @param detail  单据对象
     * @param session session对象
     * @return {}
     */
    @RequestMapping(value = "/addProDetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDetail(@RequestBody ProDetail detail, HttpSession session) {
        return addDetail(detail, (Staff) session.getAttribute(Constant.SESSION_KEY));
    }

    private Map<String, Object> addDetail(@RequestBody ProDetail detail, Staff staff) {
        Map<String, Object> result = proDetailService.addDetail(detail, staff);
        return result;
    }

    /**
     * 判断供应单位本年是否添加过采购详细单据，null为未添加过
     *
     * @param companyId     单位id
     * @param year          年份
     * @param companyBelong 单据对应的公司内部单位{1=单位1，2=单位2，3=单位3，4=单位4，5=单位5}
     * @return {null="未添加过"}
     */
    @RequestMapping("/proDetailComExist")
    @ResponseBody
    public String companyExist(String companyId, String year, Integer companyBelong) {
        if (StringUtils.isBlank(year)) {
            year = DateFormat.getYear();
        }
        return proDetailService.companyExist(companyId, null, year, companyBelong);
    }

    /**
     * 根据年份获取采购明细集合
     *
     * @param year 年份
     * @return
     */
    @RequestMapping("/getProDetails")
    @ResponseBody
    public List<ProDetail> getDetails(String year) {
        List<ProDetail> details = proDetailService.getDetailByYear(year);
        fillProDetailOwe(details);
        return details;
    }

    private void fillProDetailOwe(final List<ProDetail> details) {
        for (ProDetail detail : details) {
            detail.setMoneyOwe(proDetailOweService.getOweByDate(null, detail.getId(), 1));
            detail.setBillOwe(proDetailOweService.getOweByDate(null, detail.getId(), 0));
        }
    }

    /**
     * 获取采购对账单主单据集合
     *
     * @param year       年份
     * @param pageNumber 数据下标
     * @param pageSize   数据大小
     * @param searchText 检索字符串
     * @param type       单位类型
     * @return
     */
    @RequestMapping("/getProDetailsByParam")
    @ResponseBody
    public Map<String, Object> getDetails(String year,
                                          Integer pageNumber,
                                          Integer pageSize,
                                          String searchText,
                                          String type) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", ("".equals(searchText) ? null : searchText));
        params.put("year", year);
        params.put("type", ("all".equals(type) ? null : type));
        List<ProDetail> proDetails = proDetailService.getDetailByYear(params,pageNumber, pageSize);
        Integer total = proDetailService.getDetailByYear(params);
        fillProDetailOwe(proDetails);
        params.clear();
        params.put("rows", proDetails);
        params.put("total", total);
        return params;
    }

    /**
     * update new year order by date for prev year order
     *
     * @return
     */
    @RequestMapping("/updateSort")
    @ResponseBody
    public String updateSort() {
        List<ProDetail> prevDetails = proDetailService.getDetailByYear("2020");
        List<ProDetail> nowDetails = proDetailService.getDetailByYear("2021");
        for (int i = 0; i < prevDetails.size(); i++) {
            ProDetail prevDetail = prevDetails.get(i);
            for (int x = 0; x < nowDetails.size(); x++) {
                ProDetail nowDetail = nowDetails.get(x);
                if (prevDetail.getComName().equals(nowDetail.getComName())
                        && prevDetail.getCompanyBelong() == nowDetail.getCompanyBelong()) {
                    nowDetail.setDate(prevDetail.getDate().replace("2020", "2021"));
                    proDetailService.updateDate(nowDetail);
                    break;
                }
            }
        }
        return "ok";
    }

    /**
     * 获取采购部使用-采购付款与到票情况
     *
     * @param proDetailId 付款与入库单据明细id（pro_detail_c)主键
     * @return
     */
    @RequestMapping("/getProDetailDpList")
    @ResponseBody
    public List<ProDetailDP> getProDetailDp(String proDetailId) {
        return proPutDetailService.getDetailDp(proDetailId);
    }

    /**
     * 添加采购付款与到票情况
     *
     * @param proDetailDp 采购付款与到票信息
     * @return id=null代表添加失败
     */
    @RequestMapping("/insertDetailDp")
    @ResponseBody
    public ProDetailDP insertDetailDp(ProDetailDP proDetailDp) {
        if (proDetailDp.getId() == null || "".equals(proDetailDp.getId())) {
            proDetailDp.setId(UUID.randomUUID().toString());
        }
        if (proPutDetailService.insertProDetailDp(proDetailDp) != 1) {
            proDetailDp.setId(null);
        }
        return proDetailDp;
    }

    /**
     * 更新采购付款与到票情况
     *
     * @param proDetailDp
     * @return
     */
    @RequestMapping("/updateProDetailDp")
    @ResponseBody
    public ProDetailDP updateDetailDp(ProDetailDP proDetailDp, HttpSession httpSession) {
        //id不存在，新增
        if (proDetailDp.getId() == null || "".equals(proDetailDp.getId())) {
            return insertDetailDp(proDetailDp);
        }
        ProDetailDP pdp = proPutDetailService.getProDetailDp(proDetailDp.getId());
        if (pdp == null) {
            //该条记录不存在，新增
            return insertDetailDp(proDetailDp);
        }
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        if ("d17f2b20-da2d-47d2-b328-86d45b9757b6".equals(staff.getId())) {
            if (proPutDetailService.updateProDetailDp(proDetailDp) != 1) {
                //修改失败
                proDetailDp.setId("-2");
                return proDetailDp;
            }
        } else {
            //无修改权限，请使用领导账号进行修改
            proDetailDp.setId("-1");
            return proDetailDp;
        }
        return proDetailDp;
    }

    /**
     * 同步数据
     *
     * @param year 需要同步的年份，如果传入2017，则同步2017到2018
     * @return
     */
    @RequestMapping("/asyncProDetails")
    @ResponseBody
    public Map<String, Object> asyncProDetails(String year, HttpSession hs) {
        return asyncProDetails(year, (Staff) hs.getAttribute(Constant.SESSION_KEY));
    }

    public Map<String, Object> asyncProDetails(String year, Staff staff) {
        List<ProDetail> pdList = getDetails(year);
        ProDetailOwe pdw;
        ProDetailOwe pdo;
        Map<String, Object> result = new HashMap<>(16);
        String newYear;
        try {
            newYear = (Integer.parseInt(year) + 1) + "";
        } catch (NumberFormatException e) {
            result.put("state", "-1");
            result.put("msg", "指定的年份不正确");
            return result;
        }
        ProDetail newPd;
        Double proMoney;
        for (ProDetail pd : pdList) {
            newPd = pd.clone();
            newPd.setYear(newYear);
//            欠款 = 期初金额 + 采购金额 - 本年已付款金额
            pdw = getOweByDate(pd.getId(), 1);
//            欠票 = 期初欠票 + 采购金额 - 本年已收票金额
            pdo = getOweByDate(pd.getId(), 0);
            proMoney = pd.getYearPro();
            proMoney = proMoney == null ? 0.0 : proMoney;
            if (pdw == null) {
                pdw = new ProDetailOwe();
                pdw.setOweMoney(0.0);
            }
            if (pdo == null) {
                pdo = new ProDetailOwe();
                pdo.setOweMoney(0.0);
            }
            if (pd.getYetMoney() == null) {
                pd.setYetMoney(0.0);
            }

            newPd.setYearOwe(pdw.getOweMoney() + proMoney - pd.getYetMoney());
            if(pd.getBillMoney() == null){
                pd.setBillMoney(0.0);
            }
            newPd.setYearBillFinance(pdo.getOweMoney() + proMoney - pd.getBillMoney());
            result = addDetail(newPd, staff);
            if ("-1".equals(result.get("status"))) {
//                存在，更新数据≈
                newPd = proDetailService.getDetailById(proDetailService.companyExistDetailId(pd.getCompany().getId(), null, newYear, pd.getCompanyBelong()));
                newPd.setYearOwe(pdw.getOweMoney() + proMoney - pd.getYetMoney());
                newPd.setYearBillFinance(pdo.getOweMoney() + proMoney - pd.getBillMoney());
            }

            if (newPd != null) {

                //欠款添加  proDetail.moneyOwe.oweMoney + proDetail.yearPro - proDetail.yetMoney
                pdw.setType(1);
                pdw.setMainId(newPd.getId());
                pdw.setDate(DateFormat.getDate());
                pdw.setOweMoney(newPd.getYearOwe());
                addOwe(pdw, 1, staff);

                //欠票添加  proDetail.billOwe.oweMoney + proDetail.yearPro - proDetail.billMoney
                pdo.setType(0);
                pdo.setMainId(newPd.getId());
                pdo.setDate(DateFormat.getDate());
                if (pd.getBillMoney() == null) {
                    pd.setBillMoney(0.0);
                }
                pdo.setOweMoney(newPd.getYearBillFinance());
                addOwe(pdo, 0, staff);

//                账面欠款copy ProDetailMoney
                List<ProDetailMoney> pdmList = proDetailMoneyService.getMoneyByMainId(pd.getId());
//                删除之前的
                proDetailMoneyService.deleteByMain(newPd.getId());
                for (ProDetailMoney pdm : pdmList) {
                    pdm.setMainId(newPd.getId());
                    pdm.setId(UUID.randomUUID().toString());
//                    添加到数据库
                    proDetailMoneyService.addMoney(pdm);
                }
            }
        }
        return result;
    }

    /**
     * 获取采购与入库明细
     *
     * @param mainId 主单据id
     * @param start  获取明细的开始时间
     * @param end    截止时间
     * @return
     */
    @RequestMapping("/getPutDetailByDate")
    @ResponseBody
    public List<ProPutForDetail> getPutDetail(String mainId, String start, String end) {
        Map<String, Object> params = new HashMap(16);
        params.put("mainId", mainId);
        params.put("start", start);
        if (!Objects.isNull(end)) {
            end = DateUtil.format(DateUtil.rollDay(DateUtil.parse(end, "yyyy-MM-dd"), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE);
            params.put("end", end);
        }
        List<ProPutForDetail> proPutForDetails = proPutDetailService.getDetailByDate(params);
        return proPutForDetails;
    }

    /**
     * 通过主单据id获取所有采购入库明细集合
     *
     * @param mainId 主单据id
     * @return
     */
    @RequestMapping("/getPutDetailByMain")
    @ResponseBody
    public List<ProPutForDetail> getPutDetail(String mainId) {
        return proPutDetailService.getDetailAndDpByMain(mainId);
    }

    /**
     * 添加采购入库明细
     *
     * @param detail  明细对象
     * @param session
     * @return
     */
    @RequestMapping("/addPutDetail")
    @ResponseBody
    public ProPutForDetail addPutDetail(@RequestBody ProPutForDetail detail, HttpSession session) {
        if (addPutDetail(session, detail)) {
            return detail;
        }
        return null;
    }

    /**
     * 添加采购明细集合
     *
     * @param details 明细集合JSON字符串
     * @param session
     * @return
     */
    @RequestMapping("/addPutDetailList")
    @ResponseBody
    public List<ProPutForDetail> addPutDetailList(@RequestBody String details, HttpSession session) {
        List<ProPutForDetail> temp = JSONObject.parseArray(details, ProPutForDetail.class);
        for (ProPutForDetail detail : temp) {
            if (addPutDetail(session, detail)) {
                return temp;
            }
        }
        return null;
    }

    private boolean addPutDetail(HttpSession session, ProPutForDetail detail) {
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        return proPutDetailService.addDetail(detail, staff);
    }

    /**
     * 添加采购付款明细
     *
     * @param pay     付款单
     * @param session
     * @return
     */
    @RequestMapping("/addPayDetail")
    @ResponseBody
    public ProDetailPay addPayDetail(@RequestBody ProDetailPay pay, HttpSession session) {
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        if (pay.getMainId() != null) {
//            if ("总经办".equals(staff.getSection().getName())) {     //财务部、总经办人员方可添加
            pay.setId(UUID.randomUUID().toString());
//            设置添加时间
            pay.setDate(DateFormat.getDate());
            pay.setStaff(staff);
            proDetailPayService.addPay(pay);
//            }
        }
        return pay;
    }

    /**
     * 更新主单据信息
     *
     * @param detail  主单据对象
     * @param session
     * @return
     */
    @RequestMapping("/updateProMainDetail")
    @ResponseBody
    public int updateProMainDetail(@RequestBody ProDetail detail, HttpSession session) {
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        Staff source = proDetailService.getDetailById(detail.getId()).getStaff();
//        if (staff.getId().equals(source.getId())) {
        detail.setLastDate(DateFormat.getDate());
        detail.setLastStaff(staff);
        return proDetailService.updateDetail(detail);
//        } else {
//            return -1;
//        }
    }

    /**
     * 获取采购明细对象
     *
     * @param id 采购明细id
     * @return
     */
    @RequestMapping("/getProDetailById")
    @ResponseBody
    public ProDetail getProDetailById(String id) {
        return proDetailService.getDetailById(id);
    }

    /**
     * 获取采购付款明细集合
     *
     * @param mainId 采购单主对象id
     * @return
     */
    @RequestMapping("/getPayDetails")
    @ResponseBody
    public List<ProDetailPay> getPayDetails(String mainId) {
        return proDetailPayService.getPayDetails(mainId);
    }

    /**
     * 修改采购明细
     */
    @PostMapping("updateProPutDetail")
    @ResponseBody
    public ProPutForDetail updatePutDetail(@RequestBody ProPutForDetail detail, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proPutDetailService.updateDetail(detail);
        proPutDetailService.updateLastMsg(DateFormat.getDate(), staff, detail.getId());
        return detail;
    }

    /**
     * 修改采购入库明细单对象
     *
     * @param detail 明细对象
     * @param type   修改类型（0=项目，1|2=采购时间，default:入库金额）
     * @return
     */
    @RequestMapping("/updateProDetail/{type}")
    @ResponseBody
    public int updateProDetail(@RequestBody ProPutForDetail detail,
                               @PathVariable int type,
                               HttpSession session) {
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        switch (type) {
            case 0:
//                修改项目
//                ProDetail pd = proDetailService.getDetailById(detail.getMainId());
                if (staff.getId().equals("d17f2b20-da2d-47d2-b328-86d45b9757b6")) {
                    setProject(detail);
                    proPutDetailService.updateProject(detail);
                } else {
                    return -1;
                }
                break;
            case 1:
            case 2:
                /*
                 * 修改采购时间
                 * 采购金额
                 * */
                if ("d17f2b20-da2d-47d2-b328-86d45b9757b6".equals(staff.getId())) {
                    proPutDetailService.updateProMoney(detail);
                } else {
                    return -1;
                }
                break;
            case 9:
//                更新备注
                proPutDetailService.updateRemark(detail);
                break;
            default:    //入库金额
                proPutDetailService.updatePutMoney(detail);
                break;
        }
        proPutDetailService.updateLastMsg(DateFormat.getDate(), (Staff) session.getAttribute(Constant.SESSION_KEY), detail.getId());
        return 1;
    }

    private void setProject(ProPutForDetail detail) {
        if (detail.getProject() == null || detail.getProject().getId() == null) {
            Project project = new Project();
            project.setId("");
            detail.setProject(project);
        }
    }

    /**
     * 采购入库表主键
     *
     * @param proDetailId 采购/入库明细id
     * @param companyId   单位id
     * @return
     */
    @RequestMapping("/getProDetailDpByPayment")
    @ResponseBody
    public List<ProDetailDP> getProDetailDpByPayment(String proDetailId, String companyId) {
        ProPutForDetail proPutForDetail = proPutDetailService.getDetail(proDetailId);
        List<ProDetailDP> dps = null;
        if (proPutForDetail != null && proPutForDetail.getProject().getId() != null) {
            dps = paymentDetailService.getProDetailDp(companyId, proPutForDetail.getProject().getId());
        }
        return dps;
    }

    /**
     * 修改采购付款明细对象
     *
     * @param pay     付款单据对象
     * @param type    修改字段类型（0|1=付款情况，2|3=收票情况，default=no）
     * @param session
     * @return
     */
    @RequestMapping("/updatePayDetail/{type}")
    @ResponseBody
    public int updatePayDetail(@RequestBody ProDetailPay pay, @PathVariable int type, HttpSession session) {
        switch (type) {
            case 0:
            case 1:
//                更新付款情况
                proDetailPayService.updatePayMoney(pay);
                break;
            case 2:
            case 3:
//                更新收票情况
                proDetailPayService.updateBillMoney(pay);
                break;
            default:
                //其他忽略
                break;
        }
        ProDetailPay temp = proDetailPayService.getPay(pay.getId());
        if (temp != null && (temp.getPayMoney() == null || temp.getPayMoney() == 0)
                && (temp.getBillMoney() == null || temp.getBillMoney() == 0)) {
            proDetailPayService.deletePay(pay.getId());
        }
        //更新最后修改信息
        proDetailPayService.updateLastMsg(DateFormat.getDate(), (Staff) session.getAttribute(Constant.SESSION_KEY), pay.getId());
        return 1;
    }

    /**
     * 获取欠款、欠票明细对象
     * //@param date   时间
     *
     * @param type   类型
     * @param mainId 主键id
     * @return
     */
    @RequestMapping("/getProOweByDate")
    @ResponseBody
    public ProDetailOwe getOweByDate(String mainId, int type) {
        return proDetailOweService.getOweByDate(null, mainId, type);
    }

    /**
     * 添加欠款、欠票明细
     *
     * @param owe     欠款欠票对象
     * @param type    添加类型
     * @param session
     * @return
     */
    @RequestMapping("/addProOwe/{type}")
    @ResponseBody
    public ProDetailOwe addOwe(@RequestBody ProDetailOwe owe, @PathVariable int type, HttpSession session) {
        addOwe(owe, type, (Staff) session.getAttribute(Constant.SESSION_KEY));
        return owe;
    }

    private ProDetailOwe addOwe(ProDetailOwe owe, int type, Staff staff) {
        if (owe.getMainId() != null) {
            ProDetailOwe temp = proDetailOweService.getOweByDate(owe.getDate(), owe.getMainId(), owe.getType());
            if (temp == null) {
//                不存在，可以添加
                owe.setId(UUID.randomUUID().toString());
                owe.setDate(DateFormat.getDate());
                owe.setStaff(staff);
                proDetailOweService.addOwe(owe);
            } else {
                /*
                 * 已存在，修改金额
                 * 修改为最新的金额（当前要添加的金额为最新）
                 * */
                temp.setOweMoney(owe.getOweMoney());
//                设置修改人
                temp.setStaff(staff);
                temp.setDate(DateFormat.getDate());
                proDetailOweService.updateMoney(temp);
            }
        }
        return owe;
    }

    /**
     * 更新欠款，欠票信息
     *
     * @param owe  欠款欠票对象
     * @param type 类型
     * @return
     */
    @RequestMapping("/updateProOwe/{type}")
    @ResponseBody
    public int updateProOwe(@RequestBody ProDetailOwe owe, @PathVariable int type, HttpSession session) {
        owe.setStaff((Staff) session.getAttribute(Constant.SESSION_KEY));
        return proDetailOweService.updateMoney(owe);
    }

    /*
        账面欠款信息
     */

    /**
     * 获取（会计科目集合）账面欠款集合
     *
     * @param mainId 主单据id
     * @return
     */
    @RequestMapping("/getLeaveOwe")
    @ResponseBody
    public List<ProDetailMoney> getLeaveOweMoneys(String mainId) {
        return proDetailMoneyService.getMoneyByMainId(mainId);
    }

    /**
     * @param money 会计科目
     * @return
     */
    @RequestMapping("/updateLeaveSeries")
    @ResponseBody
    public ProDetailMoney updateLeaveSeries(@RequestBody ProDetailMoney money) {
        proDetailMoneyService.update(money);
        return money;
    }

    /**
     * 添加会计科目（账面欠款集合）
     *
     * @param money 会计科目
     * @return
     */
    @RequestMapping("/addProDetailMoney")
    @ResponseBody
    public ProDetailMoney addProDetailMoney(@RequestBody ProDetailMoney money) {
        if (money.getMainId() != null) {
            money.setDate(DateFormat.getDateTime());
            money.setId(UUID.randomUUID().toString());
            proDetailMoneyService.addMoney(money);
        }
        return money;
    }

    /**
     * 获取采购金额
     *
     * @param companyId 单位id
     * @param start     开始时间
     * @param end       结束时间
     * @return
     */
    @RequestMapping("/getProcurementMoney")
    @ResponseBody
    public List<ProPutForDetail> getProcurementMoney(String companyId, String start, String end) {
        Map<String, Object> params = new HashMap(16);
        ;
        params.put("companyId", companyId);
        params.put("start", start);
        params.put("end", end);
        return procurementMaterService.getProMoneyByCompany(params);
    }

    /**
     * 获取登记的采购金额|入库金额合计
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param type  0=采购金额，1=入库金额,2=合同付款金额
     * @return
     */
    @RequestMapping("/getProByMonth")
    @ResponseBody
    public Map<String, Object> getProByMonth(String start, String end, String type) {
        Map<String, Object> result = new HashMap<>(16);
        Double d = null;
        if ("2".equals(type)) {
//            合同付款
            result.put("start", start);
            result.put("end", end);
            result.put("state", "1");
            result.put("type", type);
            d = paymentDetailService.getPayMoneyByDate(result);
        } else {
//            采购金额 | 入库金额
            try {
                if (StringUtils.isNotBlank(end)) {
                    end = DateUtil.format(DateUtil.rollDay(DateUtil.parse(end, "yyyy-MM-dd"), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE);
                }
                d = proPutDetailService.getProMoneyByDate(start, end, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        result.put("type", type);
        result.put("start", start);
        result.put("end", end);
        result.put("money", d);
        return result;
    }

    /**
     * 获取用友T3收支数据
     *
     * @param leaveNumber 会计科目
     * @param year        数据年份
     * @return 数据集合
     */
    @RequestMapping("/getYuData")
    @ResponseBody
    public String getYuData(String leaveNumber, String year) {
        LinkedHashMap param = new LinkedHashMap<>();
        param.put("leaveNumber", leaveNumber);
        param.put("year", year);
        return NetRequestUtil.sendGetRequest("http://192.168.2.254:8082/queryYuPayBillData", param);
    }

    /**
     * 获取用友T3收支数据
     *
     * @param date  凭证号
     * @param year  数据年份
     * @param money 查询金额
     * @param type  查询类型{0=付款，1=收票}
     * @return 数据集合
     */
    @RequestMapping("/getYuRemark")
    @ResponseBody
    public String getYuData(String date, String year, Double money, String type) {
        LinkedHashMap param = new LinkedHashMap<>();
        param.put("date", date);
        param.put("year", year);
        param.put("money", String.valueOf(money));
        param.put("type", type);
        return NetRequestUtil.sendGetRequest("http://192.168.2.254:8082/queryYuPayBillData/getRemark", param);
    }
}
