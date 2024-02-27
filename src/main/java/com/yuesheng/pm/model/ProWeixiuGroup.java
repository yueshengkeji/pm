package com.yuesheng.pm.model;

import com.yuesheng.pm.annotation.CellLink;
import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

@Schema(name="报修分组")
public class ProWeixiuGroup extends BaseEntity {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 报修次数
     */
    private Integer count;
    /**
     * 项目经理列表
     */
    private List<Staff> projectManager;
    /**
     * 销售职员（项目负责人）
     */
    private Staff saleStaff;
    /**
     * 导出excel明细查询参数
     */
    private String actionParam;

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setProjectManager(List<Staff> projectManager) {
        this.projectManager = projectManager;
    }

    public List<Staff> getProjectManager() {
        return projectManager;
    }

    public Staff getSaleStaff() {
        return saleStaff;
    }

    public void setSaleStaff(Staff saleStaff) {
        this.saleStaff = saleStaff;
    }

    @CellLink
    public String getAction() {
        return WebParam.NOTIFY_IP + WebParam.VUETIFY_BASE+"/login?redirect=/projectAfter/list" + actionParam + ";查看明细";
    }

    public String getParseProjectManager() {
        List<Staff> staff = this.getProjectManager();
        if (staff != null && !staff.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            staff.forEach(item -> {
                sb.append(item.getName());
                sb.append(",");
            });
            if (sb.length() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        } else {
            return "-";
        }
    }

}
