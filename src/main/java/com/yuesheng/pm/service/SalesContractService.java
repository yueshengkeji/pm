package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.model.Marker;

import java.util.List;

/**
 * 销售合同登记
 * @author ssk
 * @since 2021-12-27
 */
public interface SalesContractService {

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
    List<SalesContract> selectForPage(Integer pageNum,Integer pageSize);

    /**
     * 查询合同数量
     * @return
     */
    Integer selectCounts();

    /**
     * 根据年月查询
     * @return
     */
    List<SalesContract> selectByDate(String dateSearch);

    /**
     * 根据年月分页查询
     * @param dateSearch
     * @return
     */
    List<SalesContract> selectByDate2(Integer pageNum,Integer pageSize,String dateSearch);

    Integer selectByDateCounts(String dateSearch);

    /**
     * 年月查询分页层级
     * @param dateSearch
     * @return
     */
    List<SalesContract> selectByDateForPage(Integer pageNum,Integer pageSize,String dateSearch,List staffId);

    /**
     * 年月查询数量
     * @param dateSearch
     * @return
     */
    Integer selectByDateCounts(String dateSearch,List staffId);

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
     * 查询
     * @param search
     * @return
     */
    List<SalesContract> selectBySearch(String search);

    /**
     * 职务层级查询
     * @param index
     * @param staffId
     * @return
     */
    List<SalesContract> selectByDuty(Integer pageNum,Integer pageSize,List staffId);

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
    List<SalesContract> selectByDateAndStaffId(String dateSearch, String staffId,Integer pageNum,Integer pageSize);

    /**
     * 根据日期和登记人Id查询 数量
     * @param dateSearch
     * @param staffId
     * @return
     */
    Integer selectByDateAndStaffIdCounts(String dateSearch, String staffId);

    /**
     * 登记人合同
     * @param staffId
     * @return
     */
    List<SalesContract> selectByStaffId(Integer pageNum,Integer pageSize,String staffId);

    Integer selectByStaffIdCounts(String staffId);

    void updateState(String id);

    /**
     * 更新合同审核状态
     * @param contractId
     * @param state
     * @return
     */
    int updateState(String contractId,int state);

    /**
     * 获取工程项目分布
     * @return
     */
    List<Marker> getProjectMap();

    int setProjectLngLat();
}
