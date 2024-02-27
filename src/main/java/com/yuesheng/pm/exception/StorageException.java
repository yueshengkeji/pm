package com.yuesheng.pm.exception;

public class StorageException extends RuntimeException {
    private String storageId;
    private String msg;

    public StorageException(String msg, String storageId) {
        super(msg);
        this.msg = msg;
        this.storageId = storageId;
    }


    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
