package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.TravelApplication;

import java.util.List;

/**
 * 出差申请
 * @author ssk
 * @since 2022-3-2
 */
public interface TravelApplicationService {
    /**
     * 新增申请
     * @param travelApplication
     * @return
     */
    int insertTravelApplication(TravelApplication travelApplication);

    /**
     * 删除申请
     * @param id
     * @return
     */
    int deleteTravelApplication(String id);

    /**
     * 修改
     * @param travelApplication
     * @return
     */
    int updateTravelApplication(TravelApplication travelApplication);

    /**
     * 根据申请人查询
     * @param travellerId
     * @return
     */
    List<TravelApplication> selectTravelApplicationByTraveller(String travellerId);

    /**
     * 申请人分页查询
     * @param index
     * @return
     */
    List<TravelApplication> selectByTraveller(Integer pageNum,Integer pageSize,String travellerId);

    /**
     * 申请人已申请记录数
     * @return
     */
    Integer selectCountsByTraveller(String travellerId);

    /**
     * 根据部门查询
     * @param departmentId
     * @return
     */
    List<TravelApplication> selectTravelApplicationByPosition(String departmentId);

    /**
     * 部门分页查询
     * @param departmentId
     * @return
     */
    List<TravelApplication> selectByPosition(Integer pageNum,Integer pageSize,String departmentId);

    /**
     * 部门月分页查询
     * @param departmentId
     * @return
     */
    List<TravelApplication> selectByPositionDate(Integer pageNum,Integer pageSize,String departmentId,String dateSearch);

    /**
     * 部门分页查询记录数
     * @param departmentId
     * @return
     */
    Integer selectCountsByPosition(String departmentId);

    /**
     * 部门月分页查询记录数
     * @param departmentId
     * @return
     */
    Integer selectCountsByPositionDate(String departmentId,String dateSearch);

    /**
     * 根据月份查询
     * @param dateSearch
     * @return
     */
    List<TravelApplication> selectTravelApplicationByDateSearch(String dateSearch);

    /**
     * 月份查询分页
     * @param index
     * @return
     */
    List<TravelApplication> selectByDateSearch(Integer pageNum,Integer pageSize,String dateSearch);

    /**
     * 月份部门查询分页
     * @return
     */
    List<TravelApplication> selectByDateSearchDepartment(Integer pageNum,Integer pageSize,String dateSearch,String departmentId);


    /**
     * 月份查询记录数
     * @param dateSearch
     * @return
     */
    Integer selectCountsByDateSearch(String dateSearch);

    /**
     * 月份查询记录数
     * @param dateSearch
     * @return
     */
    Integer selectCountsByDateSearchDepartment(String dateSearch,String departmentId);

    /**
     * 搜索查询
     *
     * @param search
     * @return
     */
    List<TravelApplication> selectTravelApplicationBySearch(String search);

    /**
     * 根据部门和日期查询
     * @param dateSearch
     * @param departmentId
     * @return
     */
    List<TravelApplication> selectByDateAndDepartment(String dateSearch,String departmentId);

    /**
     * 通过主键获取数据元素
     *
     * @param id 数据主键
     * @return
     */
    TravelApplication selectById(String id);

    void checkOa(String id);
}
