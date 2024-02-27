package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema(name="合同预申请付款单")
public class ContractPayPlan extends BaseEntity {
    /**
     * 合同id
     */
    @Schema(name="合同id")
    private String contractId;
    /**
     * 付款金额
     */
    @Schema(name="支付金额")
    private Double payMoney;
    /**
     * 状态，0=未生成付款单，1=已生成付款单
     */
    @Schema(name="状态：0=未生成付款单，1=已生成付款单")
    private boolean state = false;
    /**
     * 支付类型
     */
    @Schema(name="支付类型id,参见合同付款类型")
    private String payTypeId;
    /**
     * 开始时指定的流程id
     */
    @Schema(name="付款申请审批流程id")
    private String flowId;
    /**
     * 付款备注
     */
    @Schema(name="付款备注信息")
    private String remark;

    public String getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(String payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
