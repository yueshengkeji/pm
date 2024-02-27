package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2017/11/24 系统消息状态
 *
 * @author XiaoSong
 * @date 2017/11/24
 */
@Schema(name="系统消息状态")
public class InfoState extends BaseEntity {

    @Schema(name="主键")
    private String id;

    @Schema(name="职员id")
    private String staffId;

    @Schema(name="主单据id")
    private String mainId;

    @Schema(name="消息对象")
    private Info info;

    @Schema(name="0=未读，1=已读")
    private int state;

    @Schema(name="阅读时间")
    private String readDate;

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
