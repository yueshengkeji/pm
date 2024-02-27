package com.yuesheng.pm.model;

import java.io.Serializable;

public class Base64UploadModel implements Serializable {
    private String fileName;
    private String data;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
