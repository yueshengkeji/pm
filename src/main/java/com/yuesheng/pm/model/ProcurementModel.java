package com.yuesheng.pm.model;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.Procurement;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(name="采购订单操作模型")
public class ProcurementModel {
    @Schema(name="订单对象")
    private Procurement procurement;
    @Schema(name="申请单id集合")
    private String[] applyList;
    @Schema(name="订单附件集合")
    private String[] attachs;
    @Schema(name = "修改过的申请单材料集合", hidden = true)
    private Map<String, JSONObject> uml;

    @Schema(name="删除材料集合")
    private String[] delMater;

    @Schema(name="兼容旧版本请求")
    private Procurement pro;

    @Schema(name="是否修改了原材料(客户端可判断该值，true=修改了原材料信息)")
    private boolean update = false;

    public String[] getDelMater() {
        return delMater;
    }

    public void setDelMater(String[] delMater) {
        this.delMater = delMater;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Procurement getPro() {
        return pro;
    }

    public void setPro(Procurement pro) {
        this.pro = pro;
    }

    @Schema(name="采购订单材料明细（添加时模型）")
    private List<ProMaterialModel> materialModel;

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public String[] getApplyList() {
        return applyList;
    }

    public void setApplyList(String[] applyList) {
        this.applyList = applyList;
    }

    public String[] getAttachs() {
        return attachs;
    }

    public void setAttachs(String[] attachs) {
        this.attachs = attachs;
    }

    public Map<String, JSONObject> getUml() {
        return uml;
    }

    public void setUml(Map<String, JSONObject> uml) {
        this.uml = uml;
    }

    public List<ProMaterialModel> getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(List<ProMaterialModel> materialModel) {
        this.materialModel = materialModel;
    }
}
