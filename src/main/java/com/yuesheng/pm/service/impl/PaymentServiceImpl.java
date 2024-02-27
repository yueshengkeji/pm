package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.PaymentMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by 96339 on 2016/12/27.
 * 合同付款主单据服务
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    @Lazy
    private PaymentDetailService paymentDetailService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ProDetailService proDetailService;
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    @Lazy
    private ContractService contractService;


    @Override
    public Payment getDetailById(String id) {
        return paymentMapper.getDetailById(id);
    }

    @Override
    public Payment getPaymentById(String id) {
        return paymentMapper.getPaymentById(id);
    }

    @Override
    public List<Payment> getPaymentByCompany(String companyId, String startDate, String endDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("company", companyId);
        params.put("start", startDate);
        params.put("end", endDate);
        return paymentMapper.getPaymentByCompany(params);
    }

    @Override
    public List<PaymentType> getTypes() {
        return paymentMapper.getTypes();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRealityPay(Payment payment, Staff staff) {
        payment.setRealityId(UUID.randomUUID().toString());
        payment.setSeries("");
        payment.setDate(DateFormat.getDate());      //制单时间
        payment.setPayDate(payment.getDate());      //付款时间
        payment.setStaff(staff);     //设置制单人
        paymentMapper.addRealityPay(payment);
        RealityPay pay = null;
        for (PaymentDetail detail : payment.getDetails()) {
            detail.setStaff(staff);
            detail.setDate(payment.getDate());
            detail.setCompany(payment.getCompany());
            detail.setSeries("");
            pay = new RealityPay();
            /*
            设置实际付款明细
            1.实际付款主表id
            2.自身id
            3.合同付款明细对象
             */
            pay.setRealityId(payment.getRealityId());
            pay.setId(UUID.randomUUID().toString());
            pay.setDetail(detail);
            paymentDetailService.addRealityDetail(pay);
        }
    }

    @Override
    public void approve(String realityId, int state, String staffId, String date) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("realityId", realityId);
        params.put("state", state);
        params.put("staffId", staffId);
        params.put("date", date);
        paymentMapper.approve(params);
    }

    @Override
    public void updatePayMoney(Payment payment) {
        for (PaymentDetail detail : payment.getDetails()) {
            detail.setRatify(detail.getApplyMoney());       //目前默认申请金额为实际付款金额
            paymentDetailService.updatePayMoney(detail);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPayment(Payment payment) throws SQLException {
        Payment pay = new Payment();
        pay.setId(payment.getId());
        for (PaymentDetail detail : payment.getDetails()) {
            detail.setPayment(pay);
            detail.setDate(payment.getDate());
            detail.setCompany(payment.getCompany());
            detail.setStaff(payment.getStaff());
            detail.setLastUpdate(payment.getDate());
            detail.setLastUpdateStaff(payment.getStaff());
            detail.setId(UUID.randomUUID().toString());
            detail.setApplyDate(detail.getDate());
            try {
                if (StringUtils.isBlank(detail.getProjectId()) && detail.getContract().getProjects() != null) {
                    detail.setProjectId(detail.getContract().getProjects().get(0).getId());
                }
            } catch (Exception e) {
                //无项目
                detail.setProjectId("");
            }
            if (Objects.isNull(detail.getyMoney())) {
                detail.setyMoney(detail.getContract().getYetPay());
            }
            paymentDetailService.addDetail(detail);
            payment.setPaymentType(detail.getPaymentType());
        }
        //付款类型不为NUll才添加
        if (payment.getPaymentType() != null) {
            payment.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            if (Objects.isNull(payment.getRemark())) {
                payment.setRemark("");
            }
            paymentMapper.addPayment(payment);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public int updatePayment(Payment payment) {
        return paymentMapper.updatePayment(payment);
    }

    @Override
    public List<Payment> getPaymentList(Map<String, Object> params) {
        return paymentMapper.getPaymentList(params);
    }

    @Override
    public int getPaymentTotal(Map<String, Object> params) {
        return paymentMapper.getPaymentTotal(params);
    }

    @Override
    public List<Payment> getPaymentByContract(String contractId) {
        return paymentMapper.getPaymentByContract(contractId);
    }

    @Override
    public void delete(String id) {
        delete(id, true);
    }

    @Override
    @Transactional
    public void delete(String id, boolean deleteFiles) {
        //获取合同付款附件
        if (deleteFiles) {
            attachService.deleteAttachByModuleId(id, "sdpd064");
        }
        List<PaymentDetail> details = paymentDetailService.getDetailByPayId(id);
        paymentDetailService.deleteByPayment(id);
        for (PaymentDetail detail : details) {
            Contract c = detail.getContract();
            if (!Objects.isNull(c)) {
                Double am =  (c.getApplyMoney() - detail.getApplyMoney());
                if(am < 0.0){
                    am = 0.0;
                }
                contractService.updateApplyMoney(c.getId(),am);
            }
        }
        flowMessageService.deleteMessage(id);
        paymentMapper.delete(id);
    }

    @Override
    public Payment addPayment(Payment pay, String[] attachs, boolean autoFlow, String flowId) {
        //        id不存在时才生成id
        if (pay.getId() == null || "".equals(pay.getId())) {
            pay.setId(UUID.randomUUID().toString());
        }
        pay.setDate(DateFormat.getDate());
        pay.setRate(1);
        try {
//            添加付款到数据库
            addPayment(pay);
            if (pay.getCompany().getName() == null) {
                pay.setCompany(companyService.getCompanyById(pay.getCompany().getId()));
            }
            Attach attach;
            /*
             * 添加附件参数
             * put1:附件表名称
             * put2:字段1名称
             * put3:字段2名称
             * put4:模块主键id
             * put5:附件id
             */
            HashMap<String, String> params = new HashMap<>(16);
            params.put("table", "sdpd064FJ");
            params.put("field", "pd064FJ01");
            params.put("field2", "pd064FJ02");
            params.put("moduleId", pay.getId());

            if (attachs != null) {
                for (String a : attachs) {
                    params.put("id", a);
                    List<Attach> attaches = attachService.getAttachByRelation(params);
                    if (attaches.isEmpty() && a != null && !"".equals(a)) {
                        attachService.addAttachRelation(params);
                    }
                }
            }
        } catch (Exception e) {      //添加失败
            return null;
        }
        pay = getPaymentById(pay.getId());
        pay.setDetails(paymentDetailService.getDetailByPayId(pay.getId()));
        if (autoFlow) {
//            自动发起审批流程
            try {
                Flow flow;
                if (StringUtils.isNotBlank(flowId)) {
                    flow = flowService.getFlowById(flowId);
                } else {
                    flow = flowService.getFlowByFrameCoding("10563").get(0);
                }

                FlowMessage msg = new FlowMessage();
                msg.setTitle("&关于对《" + pay.getCompany().getName() + "》付款申请：" + pay.getMoneys());
                if (!StringUtils.isBlank(pay.getRemark())) {
                    msg.setContent(pay.getRemark());
                } else {
                    msg.setContent("");
                }

                msg.setFrameCoding("10563");
                msg.setFrameId(pay.getId());
                msg.setFrameColumn("pd06401");
                msg.setFrameName("采购合同付款单");
                FlowHistory flowHistory = new FlowHistory();
                flowHistory.setName(flow.getName());
                flowHistory.setRemark(flow.getRemark());
                flowHistory.setFrameCoding(flow.getFrameCoding());
                flowHistory.setFolderCoding("");
                msg.setFlow(flowHistory);

                flowMessageService.startFlow(msg, null, flow, pay.getStaff());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pay;
    }

    @Override
    public void approve(FlowMessage msg) {

        Payment payment = genPayDetail(msg.getFrameId());
        if (!Objects.isNull(payment)) {
            payment.setApproveStatus((byte) 1);
            payment.setApproveDate(DateUtil.getDate());
            payment.setApproveStaff(msg.getLastApproveUser());
            paymentMapper.updateApprove(payment);

            List<PaymentDetail> pdList = payment.getDetails();
            pdList.forEach(pd -> {
                Contract c = pd.getContract();
                //更新合同累计付款金额
                if (pd.getRatify() == 0) {
                    pd.setRatify(pd.getApplyMoney());
                }
                c.setYetPay(c.getYetPay() + pd.getRatify());
                contractService.updateYetPayMoney(c.getId(), c.getYetPay());
                //更新付款明细审核信息
                pd.setApproveContent("自动审批");
                pd.setApproveStatus((byte) 1);
                pd.setApproveDate(DateUtil.getDate());
                pd.setApproveStaff(msg.getLastApproveUser());
                paymentDetailService.approve(pd);
            });
        }

    }

    @Override
    public Payment genPayDetail(String id) {
        Payment payment = getPaymentById(id);
        if (payment != null && payment.getMoneys() != null) {

            List<PaymentDetail> pds = paymentDetailService.getDetailByPayId(id);
            payment.setDetails(pds);
            for (int i = 0; i < pds.size(); i++) {
                PaymentDetail pd = pds.get(i);
                Project project = projectService.getProjectByid(pd.getProjectId());
                if (project != null) {
                    ProDetail proDetail = proDetailService.getDetailByCompany(pd.getContract().getPartyB().getId(), DateFormat.getDate().substring(0, 4), proDetailService.getCompanyType(project.getFolderId()));
                    if (proDetail != null) {
                        ProPutForDetail ppfd = proPutDetailService.getDetailByMain(proDetail.getId(), project.getId());
                        if (ppfd != null) {
                            //添加实际付款报表
                            ProDetailDP proDetailDP = new ProDetailDP();
                            proDetailDP.setPayMoney(pd.getApplyMoney());
                            proDetailDP.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                            proDetailDP.setProDetailId(ppfd.getId());
                            proDetailDP.setId(UUID.randomUUID().toString());
                            proPutDetailService.insertProDetailDp(proDetailDP);
                        }
                    }
                }
                //更新合同申请中付款金额
                Contract c = pd.getContract();
                if (!Objects.isNull(c)) {
                    Double d = pd.getRatify();
                    if (c.getApplyMoney() > 0) {
                        if (Objects.isNull(d) || d <= 0) {
                            d = pd.getApplyMoney();
                        }
                        c.setApplyMoney((c.getApplyMoney() - d));
                        contractService.updateApplyMoney(c.getId(), c.getApplyMoney());
                    }
                }
            }

//            proDetailDP.setProDetailId();
        }
        return payment;
    }

    @Override
    public void notifyStaff(String id) {
        Payment payment = getPaymentById(id);
        if (payment != null) {
            ArrayList<Staff> list = new ArrayList();
            list.add(payment.getStaff());
            Map<String, Object> msgMap = new HashMap<>(4);
            msgMap.put("title", "合同付款通知");
            msgMap.put("mTitle", "付款申请《" + payment.getSeries() + "》");
            msgMap.put("content", "已经发送到现金会计");
            msgMap.put("url", WebParam.VUETIFY_BASE+"/contract/payment/list?id="+id+"&r="+ AESEncrypt.getRandom8Id());
            flowNotifyService.msgNotify(list, msgMap);
        }
    }

    @Override
    public Payment getPaymentBySeries(String series) {
        PageHelper.startPage(1, 1);
        return paymentMapper.getPaymentBySeries(series);
    }

    @Override
    public int updatePrintDate(String id, String datetime) {
        return paymentMapper.updatePrintDate(id, datetime);
    }
}
