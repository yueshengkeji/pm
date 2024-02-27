package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.DateCount;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.service.SystemLogService;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 日志清除任务
 */
@Component
@Lazy(false)
public class LogClearTask {
    @Autowired
    private SystemLogService logService;

    @Autowired
    private SystemConfigService configService;

    /**
     * 日志清除任务，每天执行一次
     */
    @Scheduled(cron = "0 30 01 * * *")
    public void clearLog() {
        if (!WebParam.SYSTEM_IS_DEBUG) {
            Date start = DateUtil.rollDay(new Date(), -32);
            Date end = DateUtil.rollDay(start, 1);
            int row = logService.deleteByDate(start, end);
            MDC.put("params", "清除" + DateUtil.format(start) + "-" + DateUtil.format(end) + "之间的日志，总计：" + row + "行");
            MDC.put("username", "system");
            MDC.put("userid", "system");
            MDC.put("method", "DELETE");
            MDC.put("url", "/api/deleteSystemLog");
            MDC.put("ip", "127.0.0.1");
            Logger.getLogger("mylog").info("清除" + DateUtil.format(start) + "-" + DateUtil.format(end) + "之间的日志，总计：" + row + "行");
        }
    }

    @Scheduled(cron = "0 30 01 * * *")
    public void checkLogError(){
        if(WebParam.SYSTEM_IS_DEBUG){
            String date = DateUtil.getDate();
            DateCount dateCount = logService.queryCountByDate(date);
            SystemConfig config = configService.queryByCoding(Constant.LOG_CHECK_COUNT_MAX);
            if(!Objects.isNull(config)) {
                try {
                    Integer maxValue = Integer.valueOf(config.getValue());
                    if (!Objects.isNull(dateCount) && dateCount.getCount() != null && dateCount.getCount() >= maxValue) {
                        Logger.getLogger("mylog").error("访问数据总量超过系统指定最大预警值“"+maxValue+"”，"+date+"总访问量为："+maxValue);
                    }
                }catch(Exception e){
                    Logger.getLogger("mylog").error("系统未指定最大预警值，请配置’LOG_CHECK_COUNT_MAX‘参数编码");
                }
            }else {
                Logger.getLogger("mylog").error("系统未指定最大预警值，请配置’LOG_CHECK_COUNT_MAX‘参数编码");
            }
        }
    }
}
