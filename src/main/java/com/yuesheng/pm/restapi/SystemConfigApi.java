package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.DingTalkConfig;
import com.yuesheng.pm.config.DiningTimeConfig;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.DatabaseVersionService;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.yuesheng.pm.util.Constant.*;


@Tag(name = "系统参数配置")
@RestController
@RequestMapping("api/systemConfig")
public class SystemConfigApi extends BaseApi {

    @Autowired
    private SystemConfigService configService;
    @Autowired
    private DatabaseVersionService versionService;

    /**
     * 初始化系统参数
     */
    @PostConstruct
    public void init() {
        //企业微信参数初始化
        setWxParam();
        // 邮箱通知参数初始化
        setEmail();
        //    FTP服务器参数初始化
        setFTPParam();
        //    系统临时文件路径
        setTempFilePath();
        //办公用品材料id默认长度、维修通知角色参数
        setArticleCodeLength();
        //    审批消息通知参数
        setApproveParam();
        //钉钉参数
        setDingTalk();
        //就餐时间
        setDiningTime();
    }

    @Operation(description = "获取系统配置")
    @GetMapping("list")
    public ResponsePage getSystemConfig(@Parameter(name="模糊查询字符串") String str,
                                        @Parameter(name="上级配置id") Integer parent,
                                        Integer page,
                                        Integer itemsPerPage,
                                        String sortBy,
                                        Boolean sortDesc) {
        SystemConfig config = new SystemConfig();
        config.setName(str);
        config.setParent(parent);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<SystemConfig> configs = configService.queryByParam(config);
        return new ResponsePage((Page) configs);
    }


    @Operation(description = "添加配置信息")
    @PutMapping
    public ResponseModel insertConfig(@Validated @RequestBody SystemConfig config) {
        configService.insert(config);
        return ResponseModel.ok(config);
    }

    @Operation(description = "更新配置信息")
    @PostMapping
    public ResponseModel updateConfig(@Validated @RequestBody SystemConfig config) {
        configService.update(config);
        //更新系统默认参数
        setWxParam();
        setEmail();
        setFTPParam();
        setTempFilePath();
        setArticleCodeLength();
        setApproveParam();
        setDingTalk();
        setDiningTime();
        return ResponseModel.ok(config);
    }

    @Operation(description = "删除配置信息")
    @DeleteMapping
    public ResponseModel delete(Integer id) {
        configService.deleteById(id);
        return ResponseModel.ok();
    }

    @Operation(description = "获取配置信息明细")
    @GetMapping("configList/{coding}")
    public ResponseModel configList(@PathVariable String coding) {
        return new ResponseModel(configService.queryByCoding(coding));
    }

    @Operation(description = "获取企业微信配置")
    @GetMapping("wxConfig")
    @NoToken
    public ResponseModel wxConfig() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("APPID", CompanyWxUtil.APPID);
        result.put("SCOPE", CompanyWxUtil.SCOPE_CONFIRM);
        result.put("STATE", CompanyWxUtil.RESPONSE_STATE_OK);
        result.put("AGENTID", CompanyWxUtil.AGENTID);
        result.put("NOTIFYIP", WebParam.NOTIFY_IP);
        return new ResponseModel(result);
    }

    private void setEmail() {
        SystemConfig systemConfig = configService.queryByCoding(EMAIL_CONFIG);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()) {
            SystemConfig sc = new SystemConfig();
            sc.setCoding(EMAIL_CONFIG);
            sc.setName("通知邮箱服务设置");
            configService.insert(sc);

            sc.setCoding("HOST");
            sc.setName("邮箱host");
            sc.setValue("smtp.139.com");
            sc.setParent(sc.getId());
            configService.insert(sc);

            sc.setCoding("SEND_EMAIL");
            sc.setName("邮箱用户名");
            sc.setValue("");
            configService.insert(sc);

            sc.setCoding("SEND_PASSWD");
            sc.setName("邮箱密码");
            sc.setValue("");
            configService.insert(sc);

            sc.setCoding("NICE_NAME");
            sc.setName("邮箱别名");
            sc.setValue("");
            configService.insert(sc);

            sc.setCoding("ENCODING");
            sc.setName("字符编码");
            sc.setValue("UTF-8");
            configService.insert(sc);

            sc.setCoding("EMAIL_FOLDER");
            sc.setName("邮箱临时文件相对目录");
            sc.setValue("emailApp" + File.separator);
            configService.insert(sc);

        } else {
            for (int i = 0; i < configListList.size(); i++) {
                SystemConfig sc = configListList.get(i);
                String c = sc.getCoding();
                try {
                    Field field = WebParam.class.getDeclaredField(c);
                    field.set(null, sc.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
    }

    private void setWxParam() {
        SystemConfig systemConfig = configService.queryByCoding(WX_CONFIG);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()) {
            SystemConfig sc = new SystemConfig();
            sc.setCoding(WX_CONFIG);
            sc.setName("企业微信设置");
            configService.insert(sc);

            sc.setCoding("APPID");
            sc.setName("企业appId");
            sc.setValue(CompanyWxUtil.APPID);
            sc.setParent(sc.getId());
            configService.insert(sc);

            sc.setCoding("AGENTID");
            sc.setName("企业应用id");
            sc.setValue(CompanyWxUtil.AGENTID);
            configService.insert(sc);

            sc.setCoding("SECRET");
            sc.setName("企业SECRET");
            sc.setValue(CompanyWxUtil.SECRET);
            configService.insert(sc);

            sc.setCoding("WORK_SECRET");
            sc.setName("打卡SECRET");
            sc.setValue(CompanyWxUtil.WORK_SECRET);
            configService.insert(sc);

            sc.setCoding("SCOPE_CONFIRM");
            sc.setName("授权方式");
            sc.setValue(CompanyWxUtil.SCOPE_CONFIRM);
            sc.setRemark("snsapi_base:静默授权，可获取成员的基础信息（UserId与DeviceId）;snsapi_privateinfo:手动授权，可获取成员的详细信息，包含头像、二维码等敏感信息");
            configService.insert(sc);
        } else {
            configListList.forEach(item -> {
                try {
                    Field field = CompanyWxUtil.class.getField(item.getCoding());
                    field.set(null, item.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            });
        }
    }

    private void setFTPParam() {
        SystemConfig systemConfig = configService.queryByCoding(FTP_CONFIG);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()) {
            SystemConfig sc = new SystemConfig();
            sc.setCoding(FTP_CONFIG);
            sc.setName("FTP服务器设置");
            sc.setRemark("文件上传服务器");
            configService.insert(sc);

            sc.setCoding("FTP_PASSWD");
            sc.setName("FTP密码");
            sc.setValue("admin@123");
            sc.setParent(sc.getId());
            configService.insert(sc);

            sc.setCoding("FTP_USER");
            sc.setName("FTP用户名");
            sc.setValue("user_read");
            configService.insert(sc);

            sc.setCoding("FTP_ADDRESS");
            sc.setName("FTP服务器ip地址");
            sc.setValue("127.0.0.1");
            configService.insert(sc);

            sc.setCoding("FTP_ROOT_FOLDER");
            sc.setName("FTP存储路径");
            sc.setValue("/files");
            configService.insert(sc);

            sc.setCoding("FTP_SERVER_PATH");
            sc.setName("FTP文件预览地址");
            sc.setValue("http://127.0.0.1:8086/files/");
            configService.insert(sc);
        } else {
            for (int i = 0; i < configListList.size(); i++) {
                SystemConfig sc = configListList.get(i);
                String c = sc.getCoding();
                try {
                    Field field = WebParam.class.getDeclaredField(c);
                    if(StringUtils.equals(sc.getValue(),"true") || StringUtils.equals(sc.getValue(),"false")){
                        field.set(null, Boolean.valueOf(sc.getValue()));
                    }else{
                        field.set(null, sc.getValue());
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
    }

    private void setApproveParam() {
        SystemConfig systemConfig = configService.queryByCoding(APPROVE_CONFIG);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()) {
            SystemConfig sc = new SystemConfig();
            sc.setCoding(APPROVE_CONFIG);
            sc.setName("审批推送参数");
            sc.setRemark("审批推送参数");
            configService.insert(sc);

            sc.setCoding("NOTIFY_HOUR");
            sc.setName("通知延时");
            sc.setValue("0");
            sc.setParent(sc.getId());
            sc.setRemark("默认为0，系统通知消息延时推送，单位：秒");
            configService.insert(sc);

            sc.setCoding("NOTIFY_IP");
            sc.setName("通知服务器IP地址");
            sc.setValue("");
            configService.insert(sc);
            try {
                sc.setValue(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {

            }
            sc.setParent(sc.getId());
            configService.insert(sc);

        } else {
            for (int i = 0; i < configListList.size(); i++) {
                SystemConfig sc = configListList.get(i);
                String c = sc.getCoding();
                try {
                    Field field = WebParam.class.getDeclaredField(c);
                    field.set(null, sc.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
    }

    private void setTempFilePath() {
        SystemConfig systemConfig = configService.queryByCoding(TEMP_FOLDER);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
            systemConfig.setCoding(TEMP_FOLDER);
            systemConfig.setName("系统临时文件路径");
            systemConfig.setValue("/assets/");
            configService.insert(systemConfig);
        }
        WebParam.TEMP_FOLDER = systemConfig.getValue();
    }

    private void setArticleCodeLength() {
        SystemConfig systemConfig = configService.queryByCoding(WORK_M_L);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
            systemConfig.setCoding(WORK_M_L);
            systemConfig.setName("办公用品编码长度");
            systemConfig.setValue("10");
            configService.insert(systemConfig);
        }
        WebParam.WORK_M_L = Integer.parseInt(systemConfig.getValue());

        systemConfig = configService.queryByCoding(WEIXIU_ROLE_CODING);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
            systemConfig.setCoding(WEIXIU_ROLE_CODING);
            systemConfig.setName("报修通知角色id");
            systemConfig.setValue("");
            configService.insert(systemConfig);
        }
    }

    public void setDingTalk(){
        SystemConfig systemConfig = configService.queryByCoding(DING_TALK_CONFIG);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()){
            SystemConfig sc = new SystemConfig();
            sc.setCoding(DING_TALK_CONFIG);
            sc.setName("钉钉设置");
            configService.insert(sc);

            sc.setCoding("APP_KEY");
            sc.setName("应用key");
            sc.setValue(DingTalkConfig.APP_KEY);
            sc.setParent(sc.getId());
            configService.insert(sc);

            sc.setCoding("APP_SECRET");
            sc.setName("应用SECRET");
            sc.setValue(DingTalkConfig.APP_SECRET);
            configService.insert(sc);

            sc.setCoding("AGENT_ID");
            sc.setName("应用ID");
            sc.setValue(DingTalkConfig.AGENT_ID);
            configService.insert(sc);

            sc.setCoding("CORP_ID");
            sc.setName("企业ID");
            sc.setValue(DingTalkConfig.CORP_ID);
            configService.insert(sc);

            sc.setCoding("AES_KEY");
            sc.setName("事件订阅KEY");
            sc.setValue(DingTalkConfig.AES_KEY);
            configService.insert(sc);

            sc.setCoding("TOKEN");
            sc.setName("事件订阅TOKEN");
            sc.setValue(DingTalkConfig.TOKEN);
            configService.insert(sc);
        }else {
            configListList.forEach(item -> {
                try {
                    Field field = DingTalkConfig.class.getField(item.getCoding());
                    field.set(null, item.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            });
        }
    }

    public void setDiningTime(){
        SystemConfig systemConfig = configService.queryByCoding(DINING_TIME);
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
        }
        List<SystemConfig> configListList = configService.queryDetailByParent(systemConfig.getId());
        if (configListList.isEmpty()){
            SystemConfig sc = new SystemConfig();
            sc.setCoding(DINING_TIME);
            sc.setName("就餐时间(值填数字)");
            configService.insert(sc);

            sc.setCoding("HOUR");
            sc.setName("时间(24小时制)");
            sc.setValue(DiningTimeConfig.HOUR);
            sc.setParent(sc.getId());
            configService.insert(sc);

            sc.setCoding("MINUTE");
            sc.setName("时间(分)");
            sc.setValue(DiningTimeConfig.MINUTE);
            configService.insert(sc);

        }else {
            configListList.forEach(item -> {
                try {
                    Field field = DiningTimeConfig.class.getField(item.getCoding());
                    field.set(null, item.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            });
        }
    }

    @Operation(description = "查询系统版本号")
    @GetMapping("getVersion")
    public ResponseModel getVersion(){
        return ResponseModel.ok(versionService.queryVersion());
    }
}

