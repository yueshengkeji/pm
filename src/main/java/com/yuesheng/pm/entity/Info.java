package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2017/11/24 系统消息实体.
 *
 * @author XiaoSong
 * @date 2017/11/24
 */
@Schema(name="系统消息实体")
public class Info extends BaseEntity {
    @Schema(name="消息标题")
    private String title;
    @Schema(name="消息内容")
    private String content;


    @Schema(name="发送人id")
    private Staff staff;

    @Schema(name="发送时间")
    private String sendDate;

    @Schema(name="阅读数量")
    private int count;

    @Schema(name="当前消息状态对象")
    private InfoState is;

    @Schema(name="通知部门列表")
    private List<Section> sectionList;


    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public InfoState getIs() {
        return is;
    }

    public void setIs(InfoState is) {
        this.is = is;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
