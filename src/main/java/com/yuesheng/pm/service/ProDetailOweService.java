package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDetailOwe;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/16 财务对账单-明细-欠票、欠款记录服务.
 */
public interface ProDetailOweService {
    /**
     * 添加欠票、欠款累计
     *
     * @param owe
     */
    void addOwe(ProDetailOwe owe);

    /**
     * 根据时间获取欠票欠款累计
     *
     * @param params {mainId:主表id,start:开始时间，end:结束时间}
     * @return
     */
    List<ProDetailOwe> getOweByDate(Map<String, Object> params);

    /**
     * 修改时间和金额
     *
     * @param owe
     * @return
     */
    int updateMoney(ProDetailOwe owe);

    /**
     * 根据时间和主表id获取欠款，欠票单据
     *
     * @param date   日期
     * @param mainId 主表id
     * @param type   单据类型{0=欠款，1=欠票}
     * @return
     */
    ProDetailOwe getOweByDate(String date, String mainId, int type);

    /**
     * 根据时间和主表id获取欠款，欠票单据
     *
     * @param date   日期
     * @param mainId 主表id
     * @param type   单据类型{0=欠款，1=欠票}
     * @return
     */
    List<ProDetailOwe> getListByDate(String date, String mainId, int type);

    /**
     * 删除记录数据
     * @param ids 不包含的id
     * @param mainId 对账单主表id
     * @return
     */
    int deleteByNotIn(String ids,String mainId,int type);

    /**
     * 清除无效记录，仅保留最近5条记录
     */
    int clearHistory(String year);
}
