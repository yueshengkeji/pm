package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ContractMapper;
import com.yuesheng.pm.model.ContractModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.ChineseHeaderGet;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016-08-11.
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractPayPlanService contractPayPlanService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    @Lazy
    private ProcurementService procurementService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Override
    public List<Contract> getContractByCompany(Map<String, Object> params, Integer pageNum,Integer pageSize) {
        String isPay = (String) params.get("isPay");
        if (StringUtils.isNotBlank(isPay)) {
            switch (isPay) {
                case "1":
                    params.put("isPay", "pd00409 <= pd00448");
                    break;
                case "2":
                    params.put("isPay", "pd00409 > pd00448 and pd00416=1");
                    break;
                default:
//                    ignored default
                    params.remove("isPay");
                    break;
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        return contractMapper.getContractByCompany(params);
    }

    @Override
    public List<Contract> getContracts(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return contractMapper.getContracts();
    }

    @Override
    public List<Contract> seek(String str, ContractType type) {
        return contractMapper.seek(str, type);
    }

    @Override
    public Contract getContractById(String id) {
        return contractMapper.getContractById(id);
    }

    @Override
    public List<Contract> getContractByParam(Map<String, Object> params, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return contractMapper.getContractByParam(params);
    }

    @Override
    public Map<String, Double> getContractMoneyByCompany(Map<String, Object> param) {
        return contractMapper.getContractMoneyByCompany(param);
    }

    @Override
    public Double getContractYetMoneyByCompany(Map<String, Object> param) {
        return contractMapper.getContranctYetMoneyByCompany(param);
    }


    @Override
    public void deleteByFlowEvent(String id) {
        Contract contract = getContractById(id);
        if (!Objects.isNull(contract)) {
            delete(id, contract.getStaff(), true);
        }
    }

    @Override
    @Transactional
    public int addContract(Contract contract) {
        if (contract != null) {       //添加合同成功
            if (contract.getCity().getId() == null) {
                ChineseHeaderGet chineseHeaderGet = new ChineseHeaderGet();
                contract.getCity().setId(AESEncrypt.getRandom8Id());
                contract.getCity().setCoding(chineseHeaderGet.getSpells(contract.getCity().getName()));
                Region region = new Region();
                region.setName(contract.getCity().getName());
                region.setId(contract.getCity().getId());
                region.setCoding(contract.getCity().getCoding());
                regionService.insert(region);
            }

            String date = DateFormat.getDate();
            List<Attach> attachList = new ArrayList<>();
            if (StringUtils.isBlank(contract.getId())) {
                contract.setId(UUID.randomUUID().toString());
            }
            if (StringUtils.isBlank(contract.getSerialNumber())) {
                if (StringUtils.length(contract.getName()) >= 13) {
                    contract.setSerialNumber(StringUtils.substring(contract.getName(), 0, 13));
                } else {
                    contract.setSerialNumber(contract.getName());
                }
            }
            if (Objects.isNull(contract.getPrice())) {
                return -1;
            }
            contract.setDate(date);
            contract.setAttaches(attachList);
            //执行状态
            contract.setState((byte) 0);
            int row = contractMapper.addContract(contract);
            List<Project> projects = contract.getProjects();
            projects.forEach(item->{
                contractMapper.addProject(contract.getId(),item.getId());
            });
            return row;
        }
        return -1;
    }

    @Override
    public int delete(String id) {
        contractMapper.deleteProject(id);
        return contractMapper.delete(id);
    }

    @Override
    public int getContractCount(Map<String, Object> params) {
        return contractMapper.getContractCount(params);
    }

    @Override
    public Contract queryProtocol(String id) {
        return contractMapper.queryProtocol(id);
    }

    @Override
    public List<Project> queryProjectByProtocol(String id) {
        return contractMapper.queryProjectByProtocol(id);
    }

    @Override
    public void approve(String id, int i, String coding, String date) {
        contractMapper.approve(id, i, coding, date);
    }

    @Override
    public void setApplyMoney(String id) {
        Double applyMoney = paymentDetailService.getPayMoneyByContract(id);
        if (!Objects.isNull(applyMoney)) {
            updateApplyMoney(id, applyMoney);
        } else {
            updateApplyMoney(id, 0.0);
        }
    }

    @Override
    public int lose(String id) throws Exception {
        Contract contract = getContractById(id);
        if (!Objects.isNull(contract)) {
            if (contract.getYetPay() > 0.0) {
                throw new Exception("合同已经产生付款，作废失败，请先删除合同付款再操作");
            } else if (contract.getApplyMoney() > 0) {
                throw new Exception("合同有付款正在审批，作废失败，请先删除合同付款再操作");
            }
        }
        return contractMapper.lose(id);
    }

    @Override
    public List<Contract> getContractByParam(Map<String, Object> params) {
        return contractMapper.getContractByParamV2(params);
    }

    @Override
    public Double getMoneyByDate(String startDate, String endDate) {
        return contractMapper.getMoneyByDate(startDate, endDate);
    }

    @Override
    public void approve(FlowMessage msg) {
        updateState(msg.getFrameId(), 1, msg.getLastApproveUser().getCoding());
        contractPayPlanService.startContractPay(msg.getFrameId());
    }

    @Override
    public void updateState(String id, int state, String coding) {
        contractMapper.updateState(id, state, coding);
    }

    @Override
    public void updateApplyMoney(String contractId, Double applyMoney) {
        contractMapper.updateApplyMoney(contractId, applyMoney);
    }

    @Override
    public void updateYetPayMoney(String id, Double money) {
        contractMapper.updateYetPayMoney(id, money);
    }

    @Override
    public List<Contract> getContractByCompany(Map<String, Object> params) {
        String isPay = (String) params.get("isPay");
        if (StringUtils.isNotBlank(isPay)) {
            switch (isPay) {
                case "1":
                    params.put("isPay", "pd00409 <= pd00448");
                    break;
                case "2":
                    params.put("isPay", "pd00409 > pd00448 and pd00416=1");
                    break;
                default:
//                    ignored default
                    params.remove("isPay");
                    break;
            }
        }
        return contractMapper.getContractByCompanyV2(params);
    }

    @Override
    public void addContractAttache(String[] attaches, Contract contract) {
        Map<String, String> p = new HashMap<>(16);
        p.put("field", "pd01301");
        p.put("table", "sdpd013");
        p.put("field2", "pd01302");
        p.put("moduleId", contract.getId());
        if (attaches != null) {
            for (String aid : attaches) {
                if (aid != null && !"".equalsIgnoreCase(aid)) {
                    p.put("id", aid);
                    attachService.addAttachRelation(p);
                }
            }
        }
    }

    @Override
    public void yuPay(ContractPayPlan payPlan, Contract contract) {
        try {
            payPlan.setContractId(contract.getId());
            planContractPay(payPlan);
        } catch (Exception ignore) {
        }
    }

    @Override
    public String genId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Contract addContract(ContractModel model, Staff staff) {
        Contract contract = model.getContract();
        String[] attaches = model.getAttaches();
        contract.setStaff(staff);
        //如果合同添加成功，添加合同附件
        if (addContract(contract) != -1) {
            /*
             * 添加合同附件
             */
            addContractAttache(attaches, contract);
            /*
            合同预付款处理
             */
            yuPay(model.getPayPlan(), contract);
            return contract;
        }
        return null;
    }

    @Override
    public Map<String, Object> delete(String id, Staff s, boolean force) {
        Map<String, Object> result = new HashMap<>(16);
        Contract c = getContractById(id);
        result.put("id", id);
        result.put("state", "-1");
        if ("".equals(id)) {
            result.put("msg", "没有该单据");
            return result;
        }
        if (c == null) {
            result.put("msg", "合同不存在");
            return result;
        }
        if (!s.getId().equals(c.getStaff().getId()) && !force) {
            //判断合同是否属于本人
            result.put("msg", "合同不属于您");
            return result;
        }
        FlowMessage fm = flowMessageService.getMessageByFrameId(id);
        if (fm != null && fm.getState() == 2 && !force) {
            //判断是否审批通过
            result.put("msg", "审核已通过，不能删除");
            return result;
        }
        //判断合同是否有付款单产生
        List<Payment> pArr = paymentService.getPaymentByContract(id);
        if (pArr.size() > 0) {
            //判断合同是否产生付款
            result.put("msg", "合同已经产生付款,不能删除");
            return result;
        }
        //更新该合同关联的采购订单信息
        procurementService.deleteContract(id, s.getCoding());
//        删除该合同预申请付款信息
        contractPayPlanService.deleteByContract(id);
        //删除合同附件
        List<Attach> attachList = attachService.getAttachByModuleId(id, "sdpd004");
        int lastIndex;
        for (Attach a : attachList) {
            lastIndex = a.getFileName().lastIndexOf(".");
            lastIndex = lastIndex != -1 ? lastIndex : a.getFileName().length();
            try {
                FtpUtil.deleteFile(a.getName() + a.getId() + a.getFileName().substring(lastIndex));
            } catch (IOException e) {
                LoggerFactory.getLogger(ContractServiceImpl.class).error(e.getLocalizedMessage());
            }
            attachService.deleteByContractId(id);
        }

        //删除合同
        delete(id);
//        删除流程
        if (fm != null) {
            flowMessageService.deleteMessage(id);
        }
        result.put("state", "0");
        result.put("msg", "删除成功");
        return result;
    }

    private void planContractPay(ContractPayPlan payPlan) {
        if (payPlan != null && payPlan.getPayMoney() != null && payPlan.getPayMoney() > 0) {
            payPlan.setId(genId());
            contractPayPlanService.insert(payPlan);
        }
    }

}
