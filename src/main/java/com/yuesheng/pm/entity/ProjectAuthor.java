package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.Date;

/**
 * (ProjectAuthor)实体类
 *
 * @author xiaoSong
 * @since 2022-11-28 09:00:56
 */
@Schema(name="项目验证")
public class ProjectAuthor extends BaseEntity {
    private static final long serialVersionUID = -13361438773286391L;

    @Schema(name="项目id")
    private String projectId;

    @Schema(name="交换机ip地址")
    private String address;

    @Schema(name="命令集合，分隔符：';'")
    private String command;

    @Schema(name="交换机端口")
    private Integer port;

    @Schema(name="交换机用户名")
    private String username;

    @Schema(name="交换机密码")
    private String password;

    @Schema(name="执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date passDate;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

}