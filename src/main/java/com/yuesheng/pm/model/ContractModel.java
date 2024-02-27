package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Contract;
import com.yuesheng.pm.entity.ContractPayPlan;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "采购合同model")
public class ContractModel {

    @Schema(name = "合同对象")
    private Contract contract;

    @Schema(name = "合同附件")
    private String[] attaches;

    @Schema(name = "预申请支付，当合同审批完成后，自动发起合同付款申请")
    private ContractPayPlan payPlan;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String[] getAttaches() {
        return attaches;
    }

    public void setAttaches(String[] attaches) {
        this.attaches = attaches;
    }

    public ContractPayPlan getPayPlan() {
        return payPlan;
    }

    public void setPayPlan(ContractPayPlan payPlan) {
        this.payPlan = payPlan;
    }
}
