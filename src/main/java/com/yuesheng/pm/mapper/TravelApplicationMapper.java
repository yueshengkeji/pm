package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.TravelApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出差申请
 * @author ssk
 * @since 2022-3-2
 */
@Mapper
public interface TravelApplicationMapper {

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
     * @return
     */
    List<TravelApplication> selectByTraveller(String travellerId);

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
    List<TravelApplication> selectByPosition(String departmentId);

    /**
     * 部门月分页查询
     * @param departmentId
     * @return
     */
    List<TravelApplication> selectByPositionDate(@Param("departmentId") String departmentId,@Param("dateSearch") String dateSearch);

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
    Integer selectCountsByPositionDate(@Param("departmentId") String departmentId,@Param("dateSearch") String dateSearch);

    /**
     * 根据月份查询
     * @param dateSearch
     * @return
     */
    List<TravelApplication> selectTravelApplicationByDateSearch(String dateSearch);

    /**
     * 月份查询分页
     * @return
     */
    List<TravelApplication> selectByDateSearch(String dateSearch);

    /**
     * 月份部门查询分页
     * @return
     */
    List<TravelApplication> selectByDateSearchDepartment(@Param("dateSearch") String dateSearch, @Param("departmentId") String departmentId);


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
    Integer selectCountsByDateSearchDepartment(@Param("dateSearch") String dateSearch,@Param("departmentId") String departmentId);

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
    List<TravelApplication> selectByDateAndDepartment(@Param("dateSearch") String dateSearch,@Param("departmentId") String departmentId);

    /**
     * 通过主键获取数据元素
     *
     * @param id 数据主键
     * @return
     */
    TravelApplication selectById(String id);
}
