package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-23 仓库主对象 sdpm004.
 *
 * @author XiaoSong
 * @date 2016/08/23
 */
@Schema(name="仓库实体")
public class Storage extends BaseEntity {
    /**
     * 仓库名称  02
     */
    @Schema(name="仓库名称")
    private String name;
    /**
     * 仓库管理员 04
     */
    @Schema(name="仓库创建人")
    private Staff staff;
    /**
     * 创建时间  06
     */
    @Schema(name="创建时间")
    private String createDate;

    @Schema(name="排序序号")
    private String sortNumber;

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
