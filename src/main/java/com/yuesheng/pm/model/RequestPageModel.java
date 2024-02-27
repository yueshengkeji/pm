package com.yuesheng.pm.model;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分页请求数据model.
 *
 * @author xiaoSong
 * @date 2020-07-27
 */
@Schema(name="数据请求分页model")
public class RequestPageModel {
    @Schema(name="职员id")
    private String staffId;
    @Schema(name="用户id")
    private Integer userId;
    @Schema(name = "数据索引位置", required = true)
    private Integer offset;
    @Schema(name = "数据大小", required = true)
    private Integer limit;
    @Schema(name="排序方式")
    private String order;
    @Schema(name = "排序名称")
    private String sort;
    @Schema(name="检索字符串")
    private String searchText;
    @Schema(name="数据类型")
    private Integer dataType;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
