package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.FlowMessage;

public class FLowMessageQuery extends FlowMessage {
    /**
     * 分页数据大小
     */
    private int pageSize = 10;
    /**
     * 分页数据索引位置
     */
    private int pageNumber = 1;
    /**
     * 查询数据开始日期
     */
    private String startDate;
    /**
     * 查询数据截止日期
     */
    private String endDate;
    /**
     * 检索内容
     */
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
