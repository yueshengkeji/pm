package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.Date;

/**
 * (CollectionNotify)实体类
 *
 * @author xiaoSong
 * @since 2022-03-25 09:45:18
 */
@Schema(name="收款通知状态")
public class CollectionNotify {
    private static final long serialVersionUID = 935566140176066446L;

    private Integer id;

    @Schema(name="收款id")
    private String collectId;

    @Schema(name="合同id")
    private String agreementId;

    @Schema(name="通知时间")
    private Date notifyDate;

    @Schema(name="通知消息")
    private String notifyMsg;

    public String getNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(String notifyMsg) {
        this.notifyMsg = notifyMsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

}