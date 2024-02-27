package com.yuesheng.pm.service;

import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskResponseBody;
import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskResponseBody;
import com.dingtalk.api.response.OapiIndustryOrganizationGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentCreateResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.yuesheng.pm.entity.*;

/**
 * @ClassName DingTalkApiService
 * @Description
 * @Author ssk
 * @Date 2022/8/3 0003 16:32
 */
public interface DingTalkApiService {

    /**
     * 媒文体上传 图片绝对路径
     * @param type
     * @param fileURL
     * @return
     */
    String mediaUpload(String type, String fileURL);

    /**
     * 媒文体上传
     * @param attach
     * @return
     */
    String mediaUploadFileBytes(Attach attach);

    /**
     * 发送工作通知
     * @param dingTalkNoticeMSG
     * @return
     */
    Object workNotice(DingTalkNoticeMSG dingTalkNoticeMSG);

    /**
     * 通过手机号获取在职员工userId
     * @return
     */
    String getUserIdByMobile(String mobile);

    /**
     * 通过userId获取员工信息
     * @param userId
     * @return
     */
    OapiV2UserGetResponse.UserGetResponse getUserByUserId(String userId);

    /**
     * 创建待办
     * @return
     */
    CreateTodoTaskResponseBody createTask(DingTalkTaskToDo dingTalkTaskToDo);

    /**
     * 更新待办状态
     * @return
     */
    UpdateTodoTaskResponseBody updateTask(DingTalkTaskUpdate dingTalkTaskUpdate);

    /**
     * 创建部门
     * @param parentId 钉钉上级部门id
     * @param section
     * @return
     */
    OapiV2DepartmentCreateResponse.DeptCreateResponse createDepartment(Long parentId, Section section);

    /**
     * 初始化创建部门（根据系统部门列表创建）
     */
    void createDepartments();

    /**
     * 更新部门
     * @param section
     */
    void updateDepartment(Section section);

    /**
     * 钉钉添加成员
     * @param staff
     */
    void createUser(Staff staff);

    /**
     * 初始化添加成员（根据系统在职成员发出邀请）
     */
    void createUsers();

    /**
     * 删除成员
     * @param staff
     */
    void deleteUser(Staff staff);

    /**
     * 获取企业信息
     * @return
     */
    OapiIndustryOrganizationGetResponse.OpenIndustryOrgInfo getDingCompanyMSG();

}
