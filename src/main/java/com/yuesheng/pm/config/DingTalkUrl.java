package com.yuesheng.pm.config;

/**
 * @ClassName DingTalkUrl 钉钉接口路径url
 * @Description
 * @Author ssk
 * @Date 2022/7/20 0020 10:54
 */
public class DingTalkUrl {
    /**
     * 获取access_token url
     */
    public static String GET_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";
    /**
     * 通过免登授权码获取用户信息 url
     */
    public static String GET_USER_INFO_URL = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";
    /**
     * 根据用户id获取用户详情 url
     */
    public static String USER_GET_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    /**
     * 根据手机号获取userId  根据手机号可以查询在职员工的userId。如果员工离职，无法根据手机号获取用户的userId
     */
    public static String USER_ID_BY_MOBILE_URL = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile";
    /**
     * 媒文体上传路径 url
     */
    public static String MEDIA_UP_LOAD_URL = "https://oapi.dingtalk.com/media/upload";
    /**
     * 消息通知url
     */
    public static String MESSAGE_NOTICE_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    /**
     * 部门创建url
     */
    public static String DEPARTMENT_CREATE = "https://oapi.dingtalk.com/topapi/v2/department/create";
    /**
     * 部门更新url
     */
    public static String DEPARTMENT_UPDATE = "https://oapi.dingtalk.com/topapi/v2/department/update";
    /**
     * 创建用户
     */
    public static String USER_CREATE = "https://oapi.dingtalk.com/topapi/v2/user/create";
    /**
     * 删除用户
     */
    public static String USER_DELETE = "https://oapi.dingtalk.com/topapi/v2/user/delete";
    /**
     * 获取企业信息
     */
    public static String DING_COMPANY_MSG = "https://oapi.dingtalk.com/topapi/industry/organization/get";
}
