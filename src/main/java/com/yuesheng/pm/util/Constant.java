package com.yuesheng.pm.util;

import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-08-15.
 *
 * @author XiaoSong
 * @date 2016/08/15
 */
public class Constant {
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";
    /**
     * 采购申请单状态
     * 0.未采购
     * 1.部分采购
     * 2.完全采购
     */
    public static final String FAIL = "1";
    public static final String SUCCESS = "0";
    public final static int STATE_NO_APPLY = 0;
    public final static int STATE_PART_APPLY = 1;
    public final static int STATE_ALL_APPLY = 2;
    public static final int PAGESIZE = 500;
    public final static String PUBPERSON_SELECT = "pubPerson";
    public final static String PUBPERSON_NOTIFY = "notify";
    /**
     * 申请单申请数量Key
     */
    public final static String APPLY_SUM_KEY = "applySum";
    /**
     * 申请单材料已采购数量key
     */
    public final static String APPLY_YSUM_KEY = "ySum";

    /**
     * 采购订单主表id
     */
    public final static String FRAME_COLUMN = "pm01301";
    public final static String FRAME_CONTRACT = "pd00401";

    /**
     * 采购订单窗体代号
     */
    public final static String FRAME_CODING_PROCUREMENT = "15306";

    /**
     * 审批人员类型常量
     * 部门
     */
    public final static int APPROVE_SECTION = 0;
    /**
     * 职务
     */
    public final static int APPROVE_ZHIWU = 1;
    /**
     * 人员
     */
    public final static int APPROVE_PERSON = 2;
    /**
     * 角色
     */
    public final static int APPROVE_JS = 3;
    /**
     * 上级领导
     */
    public final static int APPROVE_LEADER = 4;
    /**
     * 部门主管
     */
    public final static int APPROVE_SECTION_LEADER = 5;
    /**
     * 发起人
     */
    public final static int APPROVE_FAQIREN = 6;

    /**
     * 用户session Key
     */
    public final static String SESSION_KEY = "user";
    /**
     * 订单入库状态
     * 未入库
     */
    public final static byte STATE_0 = 0;
    /**
     * 部分入库
     */
    public final static byte STATE_3 = 3;
    /**
     * 完全入库
     */
    public final static byte STATE_4 = 4;
    public final static byte STATE_1 = 1;
    public final static byte STATE_2 = 2;
    /**
     * 采购订单材料总数key
     */
    public final static String YSUM = "ySum";
    public final static String PUTSUM = "putSum";
    /**
     * 未读
     */
    public final static int MSG_unread = 0;
    /**
     * 已读
     */
    public final static int MSG_READ = 1;
    /**
     * 同意
     */
    public final static int MSG_AGREED = 2;
    /**
     * 知会未读
     */
    public final static int MSG_INFORM_UNREAD = 5;
    /**
     * 知会已读
     */
    public final static int MSG_INFORM_READ = 6;
    /**
     * 驳回
     */
    public final static int MSG_REJECTED = 7;
    public final static String SOCK_TYPE = "data-type";
    public final static String SOCK_MESSAGE = "data";
    public static final String SESSTIONLISTENER_INFO = "info";
    /**
     * 投标盖章申请目录id
     */
    public static final String BID_FOLDER = "907@W$A6";
    /**
     * 就餐申请目录id
     */
    public static final String DINE_FOLDER = "JBB68H11";
    public static final String DINING_STR = "餐厅消费";
    public static final String BALANCE_STR = "余额充值";
    public static final String RETURN_BALANCE_STR = "退款";
    public static final String LOG_CHECK_COUNT_MAX = "LOG_CHECK_COUNT_MAX";
    /**
     * 采购单位编码配置key
     */
    public static final String PRO_COMPANY_TYPE = "PRO_COMPANY_TYPE";
    /**
     * 添加用户时默认角色配置编码key
     */
    public static final String DEFAULT_ROLE_CODE = "DEFAULT_ROLE_CODE";
    public static final String ERROR_NOTIFY_KEY = "ERROR_NOTIFY_KEY";
    public static final String PRO_EDIT_TEMP = "PRO_EDIT_TEMP";
    /**
     * 固定资产项目id配置编码
     */
    public static final String FIXED_PROJECT_CODING = "FIXED_PROJECT_CODING";
    /**
     * 用户ip key
     */
    public static String ADDRESS_CLIENT = "cip";
    /**
     * 材料目录（系统默认）
     */
    public final static String MATER_FOLDER_GENERATE = "sa#gen78";
    /**
     * 加班申请单 办文目录主键
     */
    public final static String OVERTIMR_FOLDER = "L0D&1K$6";
    /**
     * 办文模板目录（系统默认目录主键）
     */
    public final static String WORD_FOLDER_GENERATE = "LOD$1W$*";
    /**
     * 审批通知配置编码
     */
    public static String APPROVE_CONFIG = "APPROVE_CONFIG";
    /**
     * 企业微信配置编码
     */
    public static String WX_CONFIG = "WX_CONFIG";
    /**
     * 钉钉配置编码
     */
    public static String DING_TALK_CONFIG = "DING_TALK_CONFIG";
    /**
     * 用餐时间编码
     */
    public static String DINING_TIME = "DINING_TIME";
    /**
     * 邮箱通知配置编码
     */
    public static String EMAIL_CONFIG = "EMAIL_CONFIG";
    /**
     * FTP配置编码
     */
    public static String FTP_CONFIG = "FTP_CONFIG";
    /**
     * 系统临时目录编码
     */
    public static String TEMP_FOLDER = "TEMP_FOLDER";
    /**
     * 办公用品材料编码长度
     */
    public static String WORK_M_L = "WORK_M_L";
    /**
     * 报修通知角色编码
     */
    public static String WEIXIU_ROLE_CODING = "WEIXIU_ROLE_CODING";
    /**
     * 有负责人的报修，通知其他人员角色编码
     */
    public static final String WEIXIU_ROLE_CODING_OTHER = "WEIXIUR_ROLE_CODING";
    /**
     * 流程删除动态执行方法
     */
    public static final HashMap<String, String> FLOW_DEL_HANDLER = new HashMap<>();
    /**
     * 流程执行成功完成执行的回调方法
     */
    public static final HashMap<String, String> FLOW_SUCCESS_HANDLER = new HashMap<>();

    public static final HashMap<String, String> FILE_TABLE_MAP = new HashMap<>();
    /**
     * 流程驳回事件
     */
    public static final HashMap<String, String> FLOW_BREAK_HANDLER = new HashMap<>();

    static {
        /*
         * 流程审批完成执行的事件
         */
        FLOW_SUCCESS_HANDLER.put("132027", "SalesContractService.updateState");
        FLOW_SUCCESS_HANDLER.put("132029", "proOtherPayService.approve");
        FLOW_SUCCESS_HANDLER.put("1320276", "overtimeService.approve");
        FLOW_SUCCESS_HANDLER.put("137214", "leaveService.checkOa");
//        FLOW_SUCCESS_HANDLER.put("10564", "contractPayPlanService.startContractPay");
        FLOW_SUCCESS_HANDLER.put("10564", "contractService.approve");
        FLOW_SUCCESS_HANDLER.put("15306", "procurementService.oaSuccessChange");
        FLOW_SUCCESS_HANDLER.put("10563", "paymentService.approve");
        FLOW_SUCCESS_HANDLER.put("15304", "applyService.checkOa");
        FLOW_SUCCESS_HANDLER.put("1320274", "TravelApplicationService.checkOa");
        FLOW_SUCCESS_HANDLER.put("1320275", "InvoiceService.approve");
        FLOW_SUCCESS_HANDLER.put("1320278", "proBidService.updateState");
        FLOW_SUCCESS_HANDLER.put("1320280", "proApplyDineService.approve");
        FLOW_SUCCESS_HANDLER.put("1320281", "proSubcontractPayService.updateState");
        FLOW_SUCCESS_HANDLER.put("1320283", "proFixedApplyService.approve");
        FLOW_SUCCESS_HANDLER.put("1320282", "subcontractService.updateState");
        FLOW_SUCCESS_HANDLER.put("1320286", "entertainService.oaSuccessChange");
        FLOW_SUCCESS_HANDLER.put("1320284", "expenseService.oaSuccessChange");
        FLOW_SUCCESS_HANDLER.put("1320285", "outCarExpenseService.oaSuccessChange");
        FLOW_SUCCESS_HANDLER.put("137213", "performanceService.oaSuccessChange");
        FLOW_SUCCESS_HANDLER.put("102012", "projectService.updateApprove");
        FLOW_SUCCESS_HANDLER.put("13270", "planService.approve");
        FLOW_SUCCESS_HANDLER.put("1320272", "trajectoryDataService.approve");
        FLOW_SUCCESS_HANDLER.put("1320273", "proHouseBillDetailService.approve");
        FLOW_SUCCESS_HANDLER.put("15323", "proHouseBillDetailService.approve");
        FLOW_SUCCESS_HANDLER.put("15223", "workArticleService.approve");
        FLOW_SUCCESS_HANDLER.put("15203", "ArticleService.approve");
//        待实现
//        FLOW_SUCCESS_HANDLER.put("1320287", "proZujinService");
//        FLOW_SUCCESS_HANDLER.put("15313", "");
//        FLOW_SUCCESS_HANDLER.put("15309", "");
//        FLOW_SUCCESS_HANDLER.put("1320276", "");
//        FLOW_SUCCESS_HANDLER.put("15327", "");
//        FLOW_SUCCESS_HANDLER.put("15323", "");
//        FLOW_SUCCESS_HANDLER.put("15321", "");
//        FLOW_SUCCESS_HANDLER.put("15313", "");
//        FLOW_SUCCESS_HANDLER.put("15309", "");

//        目前没有业务需求实现的表单事件
//        FLOW_SUCCESS_HANDLER.put("13202782", "");
//        FLOW_SUCCESS_HANDLER.put("1320273", "");
//        FLOW_SUCCESS_HANDLER.put("10531", "");



        /*
        流程删除事件
         */
        FLOW_DEL_HANDLER.put("137214", "leaveService.delete");
        FLOW_DEL_HANDLER.put("10564", "contractService.deleteByFlowEvent");
        FLOW_DEL_HANDLER.put("137213", "performanceService.deleteFlowHandler");
        FLOW_DEL_HANDLER.put("1320285", "outCarExpenseService.deleteById");
        FLOW_DEL_HANDLER.put("1320284", "expenseService.delete");
        FLOW_DEL_HANDLER.put("1320286", "entertainService.delete");
        FLOW_DEL_HANDLER.put("1320283", "proFixedApplyService.deleteById");
        FLOW_DEL_HANDLER.put("1320281", "proSubcontractPayService.delete");
        FLOW_DEL_HANDLER.put("1320280", "proApplyDineService.deleteById");
        FLOW_DEL_HANDLER.put("1320278", "proBidService.deleteById");
        FLOW_DEL_HANDLER.put("1320275", "InvoiceService.deleteInvoice");
        FLOW_DEL_HANDLER.put("1320274", "TravelApplicationService.deleteTravelApplication");
        FLOW_DEL_HANDLER.put("15304", "applyService.delete");
        FLOW_DEL_HANDLER.put("10563", "paymentService.delete");
        FLOW_DEL_HANDLER.put("15306", "procurementService.deleteProcurement");
        FLOW_DEL_HANDLER.put("102012", "projectService.delete");
        FLOW_DEL_HANDLER.put("137212", "proWorkLogService.clearScore");

        /*
        文件关系表映射
         */
        FILE_TABLE_MAP.put("sdpd004", "sdpd013");
        FILE_TABLE_MAP.put("sdpo009", "sdpo010");
        FILE_TABLE_MAP.put("sdpm071", "sdpm070F");
        FILE_TABLE_MAP.put("sdpm034", "sdpm058");
        FILE_TABLE_MAP.put("sdpd031", "sdpd032");
        FILE_TABLE_MAP.put("sdpd017", "sdpd025");
        FILE_TABLE_MAP.put("sdpd064", "sdpd064FJ");
        FILE_TABLE_MAP.put("sdpa001", "sdpa001FJ");
        FILE_TABLE_MAP.put("sdpj004", "sdpj004fj");
        FILE_TABLE_MAP.put("sdpm013", "sdpm013FJ");
        FILE_TABLE_MAP.put("pro_subcontract_pay", "pro_subcontract_payFJ");
        FILE_TABLE_MAP.put("pro_subcontract", "pro_subcontractFJ");

        /*
         * 流程驳回事件
         */
        FLOW_BREAK_HANDLER.put("137213", "performanceService.breakFlowHandler");
        FLOW_DEL_HANDLER.put("137212", "proWorkLogService.clearScore");
    }

    /**
     * 只识别BigDecimal类型，其他类型转换错误返回0
     *
     * @param bd
     * @return
     */
    public static Double bigToDouble(Object bd) {
        try {
            return ((BigDecimal) bd).doubleValue();
        } catch (Exception e) {
            return new Double(0.0);
        }
    }

    public static String getTableName(String frameCoding) {
        String tableName = null;
        if (StringUtils.isNotBlank(frameCoding)) {
            switch (frameCoding) {
                case "15203":
                    tableName = "sdpo009";
                    break;
                case "15306":
                    tableName = "sdpm013";
                    break;
                case "10564":
                    tableName = "sdpd004";
                    break;
                case "10563":
                    tableName = "sdpd064";
                    break;
                case "1320281":
                    tableName = "pro_subcontract_pay";
                    break;
                case "1320282":
                    tableName = "pro_subcontract";
                    break;
                case "132029":
                    tableName = "pro_other_pay";
                    break;
                default:
                    break;
            }
        }
        return tableName;
    }
    public static String inStrParse(@NotNull String[] putState) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (String temp : putState) {
            sb.append("'");
            sb.append(temp);
            sb.append("'");
            i++;
            if (i != putState.length) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public enum Status {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        private Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
