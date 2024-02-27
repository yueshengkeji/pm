package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 合同收款登记
 * @author ssk
 * @since 2021-12-17
 */
@Mapper
public interface CollectionMapper {

    /**
     * 日查询
     * @param
     * @return
     */
    List<Collection> selectByDay(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);

    /**
     * 月查询
     * @param
     * @return
     */
    List<Collection> selectByDate(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);

    /**
     * 通过pMark查询
     * @param pMark
     * @return
     */
    Collection selectByMark(String pMark);

    /**
     * 查询合同下的收款记录
     * @param agreementID
     * @return
     */
    List<Collection> selectByAgreementID(String agreementID);

    /**
     * 插入合同收款登记
     * @param collection
     * @return
     */
    int insertCollection(Collection collection);

    /**
     * 删除合同收款登记
     * @param ID
     * @return
     */
    int deleteCollection(Integer ID);

    /**
     * 删除已删除合同下收款记录
     * @param agreementID
     * @return
     */
    int deleteCollectionByAgreementID(String agreementID);

    /**
     * 根据预收款标记删除
     * @param pMark
     * @return
     */
    int deleteCollectionByPMark(String pMark);

    /**
     * 更新合同收款登记信息
     *
     * @param collection
     * @return
     */
    int updateCollection(Collection collection);

    /**
     * 通过id获取收款信息
     *
     * @param id 收款id
     * @return
     */
    Collection selectById(Integer id);

    /**
     * 更新预收款日期
     *
     * @param collection
     * @return
     */
    Integer updateDate(Collection collection);

    /**
     * 查询指定日期之前数据（包含当日）
     *
     * @param date   日期
     * @param status 收款状态
     * @return
     */
    List<Collection> selectByDateStatus(@Param("date") Date date, @Param("status") String status);

    /**
     * 查询所有
     * @return
     */
    List<Collection> selectAll();

    /**
     * 搜索查询
     * @param str
     * @return
     */
    List<Collection> selectBySearch(@Param("str") String str,@Param("staffId") String staffId);

    /**
     * 根据合同id和收款状态查询
     * @param agreementId
     * @param status
     * @return
     */
    List<Collection> selectByAgreementIDAndStatus(@Param("agreementId") String agreementId,@Param("status") String status);

    /**
     * 根据预收款日期查询
     * @param
     * @return
     */
    List<Collection> selectByPDate(@Param("startTime") java.sql.Date startTime, @Param("endTime") java.sql.Date endTime);

    /**
     * 通过bill标记查询
     * @param billMark
     * @return
     */
    Collection selectByBillMark(String billMark);

    /**
     * 收款列表
     * @param params
     * @return
     */
    List<Collection> collectList(Map<String,Object> params);
}
