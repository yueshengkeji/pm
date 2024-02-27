package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProStaffAccount;

/**
 * 食堂消费
 */
public interface DiningExpenseService {
    /**
     * 员工进餐消费
     *
     * @param staffId 员工id
     * @return
     */
    ProStaffAccount dining(String staffId) throws Exception;

    /**
     * 补充员工进餐消费
     *
     * @param staffId  员工id
     * @param datetime 消费时间
     * @param money    消费金额
     * @return
     */
    ProStaffAccount diningByDate(String staffId, String datetime, Double money) throws Exception;
}
