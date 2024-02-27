package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskResponse;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskResponseBody;
import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskResponse;
import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.yuesheng.pm.config.DingTalkConfig;
import com.yuesheng.pm.config.DingTalkUrl;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DingTalkAccessTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.HOURS;

/**
 * @ClassName DingTalkApiServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/8/3 0003 15:34
 */
@Service
public class DingTalkApiServiceImpl implements DingTalkApiService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private DingTalkLinkNoticeImageService dingTalkLinkNoticeImageService;
    @Autowired
    @Lazy
    private SectionService sectionService;
    @Autowired
    @Lazy
    private StaffService staffService;
    @Autowired
    private DingTalkDepartmentService dingTalkDepartmentService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private DingTalkStaffInfoService dingTalkStaffInfoService;

    //通常钉钉组织没有解散，上传文件返回的media_id可以一直使用。 文件路径只能是绝对路径
    @Override
    public String mediaUpload(String type, String fileURL) {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.MEDIA_UP_LOAD_URL);
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType("image");
        req.setMedia(new FileItem(fileURL));
        OapiMediaUploadResponse rsp = null;
        String mediaId = null;
        try {
            rsp = client.execute(req, getDingTalkAccessToken());

        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        if (rsp.getErrmsg().equals("ok")) {
            mediaId = rsp.getMediaId();
        }
        return mediaId;
    }

    @Override
    public String mediaUploadFileBytes(Attach attach) {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.MEDIA_UP_LOAD_URL);
        OapiMediaUploadRequest req = new OapiMediaUploadRequest();
        req.setType("image");
        req.setMedia(new FileItem(attach.getFileName(), attach.getFileBytes()));
        OapiMediaUploadResponse rsp = null;
        String mediaId = null;
        try {
            rsp = client.execute(req, getDingTalkAccessToken());

        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        if (rsp.getErrmsg().equals("ok")) {
            mediaId = rsp.getMediaId();
            DingTalkLinkNoticeImage dingTalkLinkNoticeImage = new DingTalkLinkNoticeImage();
            dingTalkLinkNoticeImage.setName(attach.getName());
            dingTalkLinkNoticeImage.setPicUrl(attach.getName() + attach.getId() + attach.getFileName().substring(attach.getFileName().lastIndexOf(".")));
            dingTalkLinkNoticeImage.setMediaId(mediaId);
            dingTalkLinkNoticeImageService.insert(dingTalkLinkNoticeImage);
        }
        return mediaId;
    }

    @Override
    public Object workNotice(DingTalkNoticeMSG dingTalkNoticeMSG) {
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.MESSAGE_NOTICE_URL);
            OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
            req.setAgentId(Long.valueOf(DingTalkConfig.AGENT_ID));
            req.setUseridList(dingTalkNoticeMSG.getUser_list());
            req.setToAllUser(dingTalkNoticeMSG.isTo_all_user());
            OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            obj1.setMsgtype(dingTalkNoticeMSG.getMsgType());
            OapiMessageCorpconversationAsyncsendV2Request.Link obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Link();
            DingTalkLinkNoticeImage dingTalkLinkNoticeImage = dingTalkLinkNoticeImageService.selectUsed();
            if (!Objects.isNull(dingTalkLinkNoticeImage)) {
                obj2.setPicUrl(dingTalkLinkNoticeImage.getMediaId());
            } else {
                obj2.setPicUrl("@lALOACZwe2Rk");//钉钉测试用图片
            }
            obj2.setMessageUrl("dingtalk://dingtalkclient/action/openapp?" +
                    "corpid=" + DingTalkConfig.CORP_ID +
                    "&container_type=work_platform" +
                    "&app_id=0_" + DingTalkConfig.AGENT_ID +
                    "&redirect_type=jump" +
                    "&redirect_url=" + dingTalkNoticeMSG.getLinkUrl());
            obj2.setText(dingTalkNoticeMSG.getContent());
            obj2.setTitle(dingTalkNoticeMSG.getTitle());
            obj1.setLink(obj2);
            req.setMsg(obj1);
            rsp = client.execute(req, getDingTalkAccessToken());
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return rsp;
    }

    @Override
    public String getUserIdByMobile(String mobile) {
        String userId = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.USER_ID_BY_MOBILE_URL);
            OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
            req.setMobile(mobile);
            OapiV2UserGetbymobileResponse rsp = client.execute(req, getDingTalkAccessToken());
            JSONObject jsonObject = JSON.parseObject(rsp.getBody());
            if (!Objects.isNull(jsonObject)) {
                userId = jsonObject.getJSONObject("result").getString("userid");
            }
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return userId;
    }

    @Override
    public OapiV2UserGetResponse.UserGetResponse getUserByUserId(String userId) {
        OapiV2UserGetResponse.UserGetResponse qu = null;
        DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.USER_GET_URL);
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");

        try {
            OapiV2UserGetResponse rsp = client.execute(req, getDingTalkAccessToken());
            if (rsp.isSuccess()) {
                qu = rsp.getResult();
            }
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return qu;
    }

    @Override
    public CreateTodoTaskResponseBody createTask(DingTalkTaskToDo dingTalkTaskToDo) {
        com.aliyun.dingtalktodo_1_0.Client client = createClient();
        com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskHeaders createTodoTaskHeaders = new com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskHeaders();
        createTodoTaskHeaders.xAcsDingtalkAccessToken = getDingTalkAccessToken();
        com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl detailUrl = new com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl()
                .setPcUrl(dingTalkTaskToDo.getPcUrl())
                .setAppUrl(dingTalkTaskToDo.getAppUrl());
        com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs notifyConfigs = new com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs()
                .setDingNotify(dingTalkTaskToDo.getDingNotify());
        com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest createTodoTaskRequest = new com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest()
                .setSourceId(dingTalkTaskToDo.getSourceId())
                .setSubject(dingTalkTaskToDo.getSubject())
                .setNotifyConfigs(notifyConfigs)
                .setCreatorId(dingTalkTaskToDo.getCreatorId())
                .setParticipantIds(dingTalkTaskToDo.getParticipantIds())
                .setDetailUrl(detailUrl)
                .setExecutorIds(dingTalkTaskToDo.getExecutorIds());
        try {
            CreateTodoTaskResponse todoTaskWithOptions = client.createTodoTaskWithOptions(dingTalkTaskToDo.getCreatorId(), createTodoTaskRequest, createTodoTaskHeaders, new RuntimeOptions());
            CreateTodoTaskResponseBody body = todoTaskWithOptions.getBody();
            return body;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                Logger.getLogger("mylog").error(err.code + err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                Logger.getLogger("mylog").error(err.code + err.message);
            }

        }
        return null;
    }

    @Override
    public UpdateTodoTaskResponseBody updateTask(DingTalkTaskUpdate dingTalkTaskUpdate) {
        com.aliyun.dingtalktodo_1_0.Client client = createClient();
        com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskHeaders updateTodoTaskHeaders = new com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskHeaders();
        updateTodoTaskHeaders.xAcsDingtalkAccessToken = getDingTalkAccessToken();
        com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskRequest updateTodoTaskRequest = new com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskRequest()
                .setDescription(dingTalkTaskUpdate.getDescription())
                .setParticipantIds(dingTalkTaskUpdate.getParticipantIds())
                .setExecutorIds(dingTalkTaskUpdate.getExecutorIds())
                .setDueTime(dingTalkTaskUpdate.getDueTime())
                .setDone(dingTalkTaskUpdate.getDone())
                .setSubject(dingTalkTaskUpdate.getSubject());
        try {
            UpdateTodoTaskResponse updateTodoTaskResponse = client.updateTodoTaskWithOptions(dingTalkTaskUpdate.getUnionId(), dingTalkTaskUpdate.getId(), updateTodoTaskRequest, updateTodoTaskHeaders, new RuntimeOptions());
            UpdateTodoTaskResponseBody body = updateTodoTaskResponse.getBody();
            return body;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                Logger.getLogger("mylog").error(err.code + err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                Logger.getLogger("mylog").error(err.code + err.message);
            }

        }
        return null;
    }

    @Override
    public OapiV2DepartmentCreateResponse.DeptCreateResponse createDepartment(Long parentId, Section section) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.DEPARTMENT_CREATE);
            OapiV2DepartmentCreateRequest req = new OapiV2DepartmentCreateRequest();
            req.setParentId(parentId);
            req.setName(section.getName());
            OapiV2DepartmentCreateResponse rsp = client.execute(req, getDingTalkAccessToken());
            OapiV2DepartmentCreateResponse.DeptCreateResponse result = rsp.getResult();
            DingTalkDepartment dingTalkDepartment = new DingTalkDepartment();
            dingTalkDepartment.setDept_id(result.getDeptId());
            dingTalkDepartment.setName(section.getName());
            dingTalkDepartment.setSectionId(section.getId());
            dingTalkDepartmentService.insert(dingTalkDepartment);
            return result;
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return null;
    }

    @Override
    public void createDepartments() {
        List<Section> sectionList1 = new ArrayList<>();
        //系统根部门
        List<Section> sectionList = sectionService.getSectionByParent("");
        //所有钉钉绑定部门信息
        List<DingTalkDepartment> dingTalkDepartments = dingTalkDepartmentService.selectAll();

        if (dingTalkDepartments.size() > 0) {
            List<String> sectionIdList = new ArrayList<>();
            dingTalkDepartments.forEach(item -> {
                sectionIdList.add(item.getSectionId());
            });
            sectionList.forEach(item -> {
                if (sectionIdList.contains(item.getId())) {
                    sectionList1.add(item);
                }
            });
        }

        sectionList1.forEach(item -> {
            if (sectionList.contains(item)) {
                sectionList.remove(item);
            }
        });

        sectionList.forEach(item -> {
            OapiV2DepartmentCreateResponse.DeptCreateResponse department = createDepartment(1L, item);
            //下一级部门
            List<Section> sectionListByParent = sectionService.getSectionByParent(item.getId());
            if (!Objects.isNull(sectionListByParent)) {
                createDepartments2(department.getDeptId(), sectionListByParent);
            }

        });

    }

    @Override
    public void updateDepartment(Section section) {
        try {
            DingTalkDepartment dingTalkDepartment = dingTalkDepartmentService.selectBySectionId(section.getId());
            if (!Objects.isNull(dingTalkDepartment)){
                DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.DEPARTMENT_UPDATE);
                OapiV2DepartmentUpdateRequest req = new OapiV2DepartmentUpdateRequest();
                req.setDeptId(dingTalkDepartment.getDept_id());
                req.setName(section.getName());
                OapiV2DepartmentUpdateResponse rsp = client.execute(req, getDingTalkAccessToken());
                System.out.println(rsp.getBody());
            }else {
                Logger.getLogger("mylog").error("未创建该部门，更新无效！");
            }

        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
    }

    @Override
    public void createUser(Staff staff) {
        String dutyStr = "";
        Duty[] dutyByStaffId = dutyService.getDutyByStaffId(staff.getId());
        if (dutyByStaffId != null && dutyByStaffId.length > 0) {
            for (int i = 0; i < dutyByStaffId.length - 1; i++) {
                dutyStr += dutyByStaffId[i].getName() + " ";
            }
        }
        try {
            DingTalkDepartment dingTalkDepartment = dingTalkDepartmentService.selectBySectionId(staff.getSection().getId());
            DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.USER_CREATE);
            OapiV2UserCreateRequest req = new OapiV2UserCreateRequest();
            req.setName(staff.getName());
            req.setMobile(staff.getTel());
            req.setDeptIdList(String.valueOf(dingTalkDepartment.getDept_id()));
            List<OapiV2UserCreateRequest.DeptTitle> list2 = new ArrayList<OapiV2UserCreateRequest.DeptTitle>();
            OapiV2UserCreateRequest.DeptTitle obj3 = new OapiV2UserCreateRequest.DeptTitle();
            list2.add(obj3);
            obj3.setDeptId(dingTalkDepartment.getDept_id());
            obj3.setTitle(dutyStr);
            req.setDeptTitleList(list2);
            OapiV2UserCreateResponse rsp = client.execute(req, getDingTalkAccessToken());
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            Logger.getLogger("mylog").error(e);
        }

    }

    @Override
    public void createUsers() {
        List<Staff> staffs = staffService.getStaffs();
        staffs.forEach(item -> {
            createUser(item);
        });
    }

    @Override
    public void deleteUser(Staff staff) {
        DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(staff.getId());
        if (!Objects.isNull(dingTalkStaffInfo)){
            try {
                DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.USER_DELETE);
                OapiV2UserDeleteRequest req = new OapiV2UserDeleteRequest();
                req.setUserid(dingTalkStaffInfo.getDingTalkUserId());
                OapiV2UserDeleteResponse rsp = client.execute(req, getDingTalkAccessToken());
                if (rsp.getErrcode() == 0 && rsp.getErrmsg().equals("ok")){
                    dingTalkStaffInfoService.deleteByStaffId(staff.getId());
                }
            } catch (ApiException e) {
                Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
            }
        }

    }

    @Override
    public OapiIndustryOrganizationGetResponse.OpenIndustryOrgInfo getDingCompanyMSG() {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.DING_COMPANY_MSG);
            OapiIndustryOrganizationGetRequest req = new OapiIndustryOrganizationGetRequest();
            OapiIndustryOrganizationGetResponse rsp = client.execute(req, getDingTalkAccessToken());
            OapiIndustryOrganizationGetResponse.OpenIndustryOrgInfo result = rsp.getResult();
            return result;
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return null;
    }

    public void createDepartments2(Long dpt_id, List<Section> sectionList) {
        sectionList.forEach(item -> {
            OapiV2DepartmentCreateResponse.DeptCreateResponse department = createDepartment(dpt_id, item);
            //下一级部门
            List<Section> sectionListByParent = sectionService.getSectionByParent(item.getId());
            if (!Objects.isNull(sectionListByParent)) {
                createDepartments2(department.getDeptId(), sectionListByParent);
            }
        });

    }

    public com.aliyun.dingtalktodo_1_0.Client createClient() {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        try {
            return new com.aliyun.dingtalktodo_1_0.Client(config);
        } catch (Exception e) {
            Logger.getLogger("mylog").error(e.getMessage());
            return null;
        }
    }


    //获取accessToken
    public String getDingTalkAccessToken() {
        String dingTalkAccessToken = (String) redisService.getValue("dingTalk_accessToken");
        if (dingTalkAccessToken == null) {
            dingTalkAccessToken = DingTalkAccessTokenUtil.getDingTalkAccessToken(DingTalkConfig.APP_KEY, DingTalkConfig.APP_SECRET);
            redisService.setKey("dingTalk_accessToken", dingTalkAccessToken);
            redisService.expireKey("dingTalk_accessToken", 2, HOURS);
        }
        return dingTalkAccessToken;
    }
}
