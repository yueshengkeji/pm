package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.model.Marker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * 销售合同登记
 * @author ssk
 * @since 2021-12-27
 */
@Mapper
public interface SalesContractMapper {

    /**
     * 根据合同编号查询
     * @param agreementID
     * @return
     */
    SalesContract selectByID(String agreementID);

    /**
     * 根据合同名称查询
     * @param agreementName
     * @return
     */
    List<SalesContract> selectByName(String agreementName);

    /**
     * 根据单位查询
     * @param companyName
     * @return
     */
    List<SalesContract> selectByCompany(String companyName);

    /**
     * 根据登记人查询
     * @param registrant
     * @return
     */
    List<SalesContract> selectByRegistrant(String registrant);

    /**
     * 获取所有的销售合同登记
     * @return
     */
    List<SalesContract> selectAll();

    /**
     * 分页
     * @return
     */
    List<SalesContract> selectForPage();

    /**
     * 查询合同数量
     * @return
     */
    Integer selectCounts();

    /**
     * 根据年月查询
     * @return
     */
    List<SalesContract> selectByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 根据年月分页查询
     * @param index
     * @param
     * @return
     */
    List<SalesContract> selectByDate2(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    Integer selectByDateCounts(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 年月查询分页
     * @param
     * @param index
     * @return
     */
    List<SalesContract> selectByDateForPage(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("staffId") List staffId);

    /**
     * 年月查询数量
     * @param
     * @return
     */
    Integer selectByDateForPageCounts(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("staffId")List staffId);

    /**
     * 根据年查询
     * @param dateSearch
     * @return
     */
    List<SalesContract> selectByDateYear(String dateSearch);

    /**
     * 插入销售合同登记
     * @param salesContract
     * @return
     */
    int insertContract(SalesContract salesContract);

    /**
     * 删除销售合同登记
     * @param agreementID
     * @return
     */
    int deleteContract(String agreementID);

    /**
     * 更新销售合同登记信息
     * @param salesContract
     * @return
     */
    int updateContract(SalesContract salesContract);

    /**
     * 搜索查询
     * @param search
     * @return
     */
    List<SalesContract> selectBySearch(String search);

    /**
     * 职务层级查询
     * @param staffId
     * @return
     */
    List<SalesContract> selectByDuty(List staffId);

    /**
     * 职务层级查询 数量
     * @param staffId
     * @return
     */
    Integer selectByDutyCounts(List staffId);

    /**
     * 根据日期和登记人Id查询
     * @param dateSearch
     * @param staffId
     * @return
     */
    List<SalesContract> selectByDateAndStaffId(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("staffId")String staffId);

    /**
     * 根据日期和登记人Id查询 数量
     * @param staffId
     * @return
     */
    Integer selectByDateAndStaffIdCounts(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("staffId")String staffId);

    /**
     * 登记人合同
     * @param staffId
     * @return
     */
    List<SalesContract> selectByStaffId(String staffId);

    Integer selectByStaffIdCounts(String staffId);

    /**
     * 更新合同审核状态
     * @param contractId
     * @param state
     * @return
     */
    int updateState(@Param("contractId") String contractId,@Param("state") int state);

    /**
     * 获取工程项目分布
     * @return
     */
    List<Marker> getProjectMap();
}
