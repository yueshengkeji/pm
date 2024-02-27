package com.yuesheng.pm.model;

/**
 * @ClassName ServiceResult
 * @Description
 * @Author ssk
 * @Date 2022/7/20 0020 9:29
 */
public class ServiceResult<T> {

    private boolean success;

    private String errorCode;

    private String errorMsg;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ServiceResult<T> getSuccessResult(T t) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(true);
        result.setData(t);

        return result;
    }

    public static <T> ServiceResult<T> getFailureResult(String code, String msg) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setSuccess(false);
        result.setErrorCode(code);
        result.setErrorMsg(msg);

        return result;
    }
}
