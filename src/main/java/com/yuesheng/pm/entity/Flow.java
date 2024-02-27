package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-15 流程对象实体.
 *
 * @author XiaoSong
 * @date 2016/08/15
 */
public class Flow extends BaseEntity {

    @Schema(name="流程名称")
    private String name;

    @Schema(name="流程说明")
    private String remark;

    @Schema(name=" 流程目录id")
    private String folder;

    @Schema(name="目录")
    private Folder folderObj;

    @Schema(name="是否公开流程 0=否，1=是")
    private Integer isPub = 0;

    @Schema(name="必要条件 0=否")
    private byte condition = 0;

    @Schema(name="窗口编码")
    private String frameCoding;

    @Schema(name="窗口对象")
    private Menu menu;

    @Schema(name="是否自由流程")
    private String pubFlow = "0";

    @Schema(name="模板id")
    private String templement;

    @Schema(name="绑定模板对象")
    private WordModule wordModule;

    @Schema(name="关联类型{2:关联模板，3.无关联，0：关联窗体}")
    private byte type;

    @Schema(name="最近一次使用时间")
    private String lastUseDate;

    public String getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(String lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    public Folder getFolderObj() {
        return folderObj;
    }

    public void setFolderObj(Folder folderObj) {
        this.folderObj = folderObj;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public WordModule getWordModule() {
        return wordModule;
    }

    public void setWordModule(WordModule wordModule) {
        this.wordModule = wordModule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Integer getIsPub() {
        return isPub;
    }

    public void setIsPub(Integer isPub) {
        this.isPub = isPub;
    }

    public byte getCondition() {
        return condition;
    }

    public void setCondition(byte condition) {
        this.condition = condition;
    }

    public String getFrameCoding() {
        return frameCoding;
    }

    public void setFrameCoding(String frameCoding) {
        this.frameCoding = frameCoding;
    }

    public String getPubFlow() {
        return pubFlow;
    }

    public void setPubFlow(String pubFlow) {
        this.pubFlow = pubFlow;
    }

    public String getTemplement() {
        return templement;
    }

    public void setTemplement(String templement) {
        this.templement = templement;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
