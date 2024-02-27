package com.yuesheng.pm.util;

/**
 * @ClassName DingTalkException
 * @Description
 * @Author ssk
 * @Date 2022/7/20 0020 10:57
 */
public class DingTalkException extends RuntimeException{
    private static final long serialVersionUID = -238091758285157331L;
    private String errCode;
    private String errMsg;
    private String subErrCode;
    private String subErrMsg;

    public String getSubErrCode() {
        return this.subErrCode;
    }

    public void setSubErrCode(String subErrCode) {
        this.subErrCode = subErrCode;
    }

    public String getSubErrMsg() {
        return this.subErrMsg;
    }

    public void setSubErrMsg(String subErrMsg) {
        this.subErrMsg = subErrMsg;
    }

    public DingTalkException() {
    }

    public DingTalkException(String message, Throwable cause) {
        super(message, cause);
    }

    public DingTalkException(String message) {
        super(message);
    }

    public DingTalkException(Throwable cause) {
        super(cause);
    }

    public DingTalkException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public DingTalkException(String errCode, String errMsg, String subErrCode, String subErrMsg) {
        super(errCode + ":" + errMsg + ":" + subErrCode + ":" + subErrMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.subErrCode = subErrCode;
        this.subErrMsg = subErrMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
