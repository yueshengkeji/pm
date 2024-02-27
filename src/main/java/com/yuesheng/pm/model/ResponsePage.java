package com.yuesheng.pm.model;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.BaseEntity;

import java.util.HashMap;

public class ResponsePage<T> extends BaseEntity {
    private int code;
    private String msg;
    private HashMap<String, Object> data;

    public static ResponsePage ok(Page page) {
        return new ResponsePage(page);
    }

    public ResponsePage(Page<T> page) {
        this.data = new HashMap();
        this.data.put("rows", page);
        this.data.put("total", page.getTotal());
        this.code = 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
