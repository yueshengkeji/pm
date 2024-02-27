package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class CollectionTask {
    @Autowired
    private CollectionService collectionService;

    /**
     * 每天10:00执行一次（应收款到期提醒）
     */
    @Scheduled(cron = "0 00 10 ? * MON-FRI")
    public void notifyCollection() {
        if (!WebParam.SYSTEM_IS_DEBUG) {
            collectionService.expireNotify();
        }
    }
}
