package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Declare;
import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.Payment;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by 96339 on 2017/3/10.
 * 添加采购付款 model
 *
 * @author XiaoSong
 * @date 2017/03/10
 */
@Schema(name="添加合同付款model")
public class PaymentModel {
    /**
     * 付款modal
     */
    @Schema(name="付款单对象")
    private Payment pay;
    /**
     * 报销modal
     */
    @Schema(hidden = true)
    private Declare declare;
    @Schema(hidden = true)
    private Flow flow;
    /**
     * 附件集合
     */
    @Schema(name="付款单附件")
    private String[] attachs;

    public String[] getAttachs() {
        return attachs;
    }

    public void setAttachs(String[] attachs) {
        this.attachs = attachs;
    }

    public Declare getDeclare() {
        return declare;
    }

    public void setDeclare(Declare declare) {
        this.declare = declare;
    }

    public Payment getPay() {
        return pay;
    }

    public void setPay(Payment pay) {
        this.pay = pay;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }
}
