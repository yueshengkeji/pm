package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDetailMoney;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 96339 on 2017/2/16 账面欠款明细 mapper  pro_detail_money.
 * @author XiaoSong
 * @date 2017/02/16
 */
@Mapper
public interface ProDetailMoneyMapper {
    /**
     * 添加账面欠款
     * @param money
     */
    void addMoney(ProDetailMoney money);
    /**
     * 更新账面欠款
     * @param money 账面欠款金额
     * @return 影响的行数
     */
    int update(ProDetailMoney money);

    /**
     * 获取账面欠款集合
     * @param mainId 主单据id
     * @return
     */
    List<ProDetailMoney> getMoneyByMainId(String mainId);

    /**
     * 获取账面欠款总额
     * @param mainId 主单据id
     * @return
     */
    Double getMoneySumByMainId(String mainId);

    /**
     * 删除账面欠款集合
     *
     * @param mainId 主单据id
     */
    void deleteByMain(@Param("mainId") String mainId);
}
