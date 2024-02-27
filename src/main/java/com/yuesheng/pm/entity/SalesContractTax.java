package com.yuesheng.pm.entity;

/**
 * @ClassName SalesContractTax
 * @Description 销售合同税率
 * @Author ssk
 * @Date 2022/6/20 0020 14:26
 */
public class SalesContractTax extends BaseEntity{
    /**
     * 合同编号
     */
    private String agreementID;
    /**
     * 税率
     */
    private String taxExtra;
    /**
     * 金额占比
     */
    private String percentForAM;

    /**
     * 新增人id
     */
    private String adderID;

    public String getAdderID() {
        return adderID;
    }

    public void setAdderID(String adderID) {
        this.adderID = adderID;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getTaxExtra() {
        return taxExtra;
    }

    public void setTaxExtra(String taxExtra) {
        this.taxExtra = taxExtra;
    }

    public String getPercentForAM() {
        return percentForAM;
    }

    public void setPercentForAM(String percentForAM) {
        this.percentForAM = percentForAM;
    }
}
