package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.PurchasePay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface PurchasePayMapper {
    /**
     * 日查询
     * @param
     * @return
     */
    List<PurchasePay> selectByDay(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

    /**
     * 月查询
     * @param dateSearch
     * @return
     */
    List<PurchasePay> selectByDate(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

    /**
     * 通过pMark查询
     * @param pMark
     * @return
     */
    PurchasePay selectByPMark(String pMark);

    /**
     * 查询合同下的收款记录
     * @param id
     * @return
     */
    List<PurchasePay> selectByID(Integer id);

    /**
     * 插入合同收款登记
     * @param purchasePay
     * @return
     */
    int insert(PurchasePay purchasePay);

    /**
     * 删除合同收款登记
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 删除已删除合同下收款记录
     * @param id
     * @return
     */
    int deleteByID(Integer id);

    /**
     * 根据预收款标记删除
     * @param pMark
     * @return
     */
    int deleteByPMark(String pMark);

    /**
     * 更新合同收款登记信息
     * @param purchasePay
     * @return
     */
    int update(PurchasePay purchasePay);
}
