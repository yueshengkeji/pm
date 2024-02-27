package com.yuesheng.pm.entity;

/**
 * @ClassName InvoiceFile 开票附件
 * @Description
 * @Author ssk
 * @Date 2022/12/29 0029 11:33
 */
public class InvoiceFile extends BaseEntity{

    /**
     * 文件名
     */
    private String name;
    /**
     * 文件路径
     */
    private String url;
    /**
     * 开票id
     */
    private String mark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
