package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDetailMoney;

import java.util.List;

/**
 * Created by 96339 on 2017/2/16 账面欠款服务接口.
 *
 */
public interface ProDetailMoneyService {
    /**
     * 添加账面欠款
     * @param money
     */
    void addMoney(ProDetailMoney money);

    void updateDetailSubject(ProDetailMoney detailMoney);

    /**
     * 更新账面欠款
     * @param money
     */
    int update(ProDetailMoney money);

    /**
     * 获取账面欠款集合
     * @param mainId 主单据id
     * @return
     */
    List<ProDetailMoney> getMoneyByMainId(String mainId);

    /**
     * 删除账面欠款集合（会计凭证号）
     *
     * @param mainId 主单据id
     */
    void deleteByMain(String mainId);
}
