package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDetailDP;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProDetailDpMapper {
    /**
     * 获取到票登记信息
     *
     * @param param {}
     * @return
     */
    List<ProDetailDP> getDpByParam(Map<String, Object> param);

    /**
     * 获取采购部使用-采购付款与到票情况
     *
     * @param proDetailId 付款与入库单据明细id（pro_detail_c)主键
     * @return
     */
    List<ProDetailDP> getDetailDp(@Param("proDetailId") String proDetailId);

    /**
     * 添加采购付款与到票明细
     *
     * @param detailDp 付款到票明细对象
     * @return
     */
    int insertProDetailDp(ProDetailDP detailDp);

    /**
     * 修改采购付款与到票情况
     *
     * @param proDetailDp 采购付款与到票情况
     * @return
     */
    int updateProDetailDp(ProDetailDP proDetailDp);

    /**
     * 获取已付款并且已到票的金额
     *
     * @param proDetailId
     * @return
     */
    Map<String, BigDecimal> getMoneyByPayAndDp(@Param("proDetailId") String proDetailId);

    /**
     * 获取采购部使用-采购付款与到票情况
     *
     * @param id 主键id
     * @return
     */
    ProDetailDP getProDetailDp(@Param("id") String id);

    /**
     * 通过参数获取到票总数
     *
     * @param param 参见 getDpByParam()
     * @return
     */
    Integer getDpCount(Map<String, Object> param);
}
