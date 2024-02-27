package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2016/11/16 附件对象  表：sdpk008
 * ftpl路径为：文件名+id+文件后缀，及：上传123.jpg,ftp位置为：123id号.jpg.
 *
 * @author XiaoSong
 * @date 2016/11/16
 */
@Schema(name="附件类")
public class Attach extends BaseEntity {
    @Schema(name = "附件原始名称", required = true)
    private String name;
    @Schema(name = "附件上传成功后名称")
    private String fileName;
    /**
     * 文件流对象
     */
    @Schema(name="附件原始数据")
    private byte[] fileBytes;
    /**
     * 上传时间
     */
    @Schema(name="上传时间")
    private String uploadDate;
    /**
     * 更新时间
     */
    @Schema(name="更新时间")
    private String updateDate;
    /**
     * 上传人员
     */
    @Schema(name="上传人员")
    private Staff uploadUser;
    /**
     * 文件类型,合同上传为=2，其他未测试
     */
    @Schema(hidden = true)
    private int type = 2;
    /**
     * pdf转为图片集合后的路径，使用;分割多个图片
     */
    private String pdfImgPathStr = "";
    /**
     * pdf转图片后的路径集合
     */
    private List<String> pdfImgPath;
    /**
     * 预留字段
     */
    private String pk00811 = "", pk00812 = "", pk00813 = "";
    /**
     * 窗体id
     */
    @Schema(name="窗口表单id")
    private String moduleId;
    /**
     * 显示路径，解决中文等特殊字符问题，服务端编码后，客户端直接使用，无需处理，有时客户端处理后和服务端对不上号
     */
    @Schema(name="文件预览url")
    private String showPath;
    /**
     * 附件转成html后的内容
     */
    @Schema(name="附件为html时的文本内容")
    private String content;
    @Schema(name="文件大小")
    private int size;

    public String getPdfImgPathStr() {
        return pdfImgPathStr;
    }

    public void setPdfImgPathStr(String pdfImgPathStr) {
        this.pdfImgPathStr = pdfImgPathStr;
    }

    public List<String> getPdfImgPath() {
        return pdfImgPath;
    }

    public void setPdfImgPath(List<String> pdfImgPath) {
        this.pdfImgPath = pdfImgPath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowPath() {
        return showPath;
    }

    public void setShowPath(String showPath) {
        this.showPath = showPath;
    }

    public String getPk00811() {
        return pk00811;
    }

    public void setPk00811(String pk00811) {
        this.pk00811 = pk00811;
    }

    public String getPk00812() {
        return pk00812;
    }

    public void setPk00812(String pk00812) {
        this.pk00812 = pk00812;
    }

    public String getPk00813() {
        return pk00813;
    }

    public void setPk00813(String pk00813) {
        this.pk00813 = pk00813;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Staff getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(Staff uploadUser) {
        this.uploadUser = uploadUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
