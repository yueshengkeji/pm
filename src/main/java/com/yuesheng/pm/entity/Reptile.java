package com.yuesheng.pm.entity;


/**
 * (Reptile)实体类
 *
 * @author xiaosong
 * @since 2023-04-06 11:16:26
 */
public class Reptile extends BaseEntity {
    private static final long serialVersionUID = 777070369497755149L;
    
    private String id;
    
    private String title;
    
    private String content;
    
    private String datetime;
    
    private String reptileDatetime;
    
    private String key;
    
    private String sourceName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getReptileDatetime() {
        return reptileDatetime;
    }

    public void setReptileDatetime(String reptileDatetime) {
        this.reptileDatetime = reptileDatetime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

}

