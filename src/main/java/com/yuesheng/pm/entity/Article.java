package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2016/12/24 我的办文   sdpo009.
 * @author XiaoSong
 * @date 2016/12/21
 *
 */
public class Article extends BaseEntity {
    /**
    文档目录    02
     */
    private ArticleFolder folder;
    /**
    文档代码    03
     */
    private String code;
    /**
    文档名称    04
     */
    private String name;
    /**
    文档标题    06
     */
    private String title;
    /**
    制单人     08
     */
    private Staff staff;
    /**
    word文档数据    09
     */
    private byte[] data;
    /**
    制单时间
     */
    private String date;
    /**
    关联项目      16
     */
    private Project project;
    /**
     * 制单人id 08
     */
    private String staffId;
    /**
     * 办文内容 兼容pm2，html文本转换成doc存到ftp中
     */
    private String content;

    private String contentBlob;

    public String getContentBlob() {
        return contentBlob;
    }

    public void setContentBlob(String contentBlob) {
        this.contentBlob = contentBlob;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public ArticleFolder getFolder() {
        return folder;
    }

    public void setFolder(ArticleFolder folder) {
        this.folder = folder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStaffId() {
        return staffId;
    }
}
