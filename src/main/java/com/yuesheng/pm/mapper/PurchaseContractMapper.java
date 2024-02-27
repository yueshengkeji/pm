package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.PurchaseContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * 采购合同登记
 * @author ssk
 * @since 2022-1-10
 */
@Mapper
public interface PurchaseContractMapper {

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    PurchaseContract selectByID(Integer id);

    /**
     * 根据合同编号查询
     * @param agreementID
     * @return
     */
    PurchaseContract selectByAgreementID(String agreementID);

    /**
     * 搜索查询
     * @param search
     * @return
     */
    List<PurchaseContract> selectBySearch(String search);

    /**
     * 获取所有的采购合同登记
     * @return
     */
    List<PurchaseContract> selectAll();

    /**
     * 分页
     * @return
     */
    List<PurchaseContract> selectForPage();

    /**
     * 查询合同数量
     * @return
     */
    Integer selectCounts();

    /**
     * 根据年月查询
     * @return
     */
    List<PurchaseContract> selectByDateForPage(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    /**
     * 根据年查询
     * @param dateSearch
     * @return
     */
    List<PurchaseContract> selectByDateYear(String dateSearch);

    /**
     * 根据年月查询合同数量
     * @return
     */
    Integer selectCountsByDate(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    /**
     * 插入采购合同登记
     * @param purchaseContract
     * @return
     */
    int insertContract(PurchaseContract purchaseContract);

    /**
     * 删除销售合同登记
     * @param id
     * @return
     */
    int deleteContract(Integer id);

    /**
     * 更新采购合同登记信息
     * @param purchaseContract
     * @return
     */
    int updateContract(PurchaseContract purchaseContract);
}
