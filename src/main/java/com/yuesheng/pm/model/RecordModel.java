package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.DownloadRecord;

/**
 * @author gui_lin
 * @Description 证书下载模板
 * 2022/1/18
 */
public class RecordModel {
    private DownloadRecord record;
    private int index;

    public DownloadRecord getRecord() {
        return record;
    }

    public void setRecord(DownloadRecord record) {
        this.record = record;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
