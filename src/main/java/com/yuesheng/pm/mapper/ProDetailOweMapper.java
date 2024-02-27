package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDetailOwe;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/16 欠票欠款mapper.
 * @author XiaoSong
 * @date 2017/02/16
 */
@Mapper
public interface ProDetailOweMapper {
    /**
     * 添加欠票、欠款累计
     * @param owe
     */
    void addOwe(ProDetailOwe owe);

    /**
     * 根据时间获取欠票欠款累计
     * @param params {mainId:主表id,start:开始时间，end:结束时间}
     * @return
     */
    List<ProDetailOwe> getOweByDate(Map<String,Object> params);

    /**
     * 修改时间和金额
     * @param owe 修改欠票金额和时间
     * @return
     */
    int updateMoney(ProDetailOwe owe);

    /**
     * 根据时间和主表id获取欠款，欠票单据
     * @param date 日期
     * @param mainId 主表id
     * @param type 单据类型{0=欠款，1=欠票}
     * @return
     */
    ProDetailOwe getOweByDate2(@Param("date") String date,@Param("mainId") String mainId, @Param("type") int type);
    /**
     * 根据时间和主表id获取欠款，欠票单据
     * @param date 日期
     * @param mainId 主表id
     * @param type 单据类型{0=欠款，1=欠票}
     * @return
     */
    List<ProDetailOwe> getListByDate(@Param("date") String date,@Param("mainId") String mainId, @Param("type") int type);

    /**
     * 删除数据
     * @param ids 不删除的ids
     * @param mainId 对账单主表id
     * @return
     */
    int deleteByNotIn(@Param("ids") String ids,@Param("mainId") String mainId,@Param("type") int type);
}
