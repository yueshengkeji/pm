package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name="api响应数据封装类")
public class ResponseModel<T> extends BaseEntity {
    private int code;
    private Object data;
    private String msg;

    public ResponseModel(Object data) {
        this.data = data;
        this.code = 200;
    }

    public ResponseModel(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public ResponseModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static ResponseModel ok() {
        return new ResponseModel(200, null);
    }

    public static <T> ResponseModel ok(T data) {
        return new ResponseModel(data);
    }

    public static ResponseModel error(String error) {
        return new ResponseModel(500, error);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
