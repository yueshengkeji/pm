package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Duty;
import com.yuesheng.pm.entity.Staff;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-20 职务服务接口.
 */

public interface DutyService {
    /**
     * 通过职务编码获取该职务所有人员
     * @return 职务人员集合
     */
    List<Staff> getStaffByDuty(String dutyId);

    /**
     * 获取职务名称
     * @param  dutyId 职务id
     * @return
     */
    String getDutyName(String dutyId);

    /**
     * 获取职务集合
     * @param parent 上级职务id
     * @return
     */
    List<Duty> getByParent(String parent);

    /**
     * 检索职务
     * @param str 检索串
     * @return
     */
    List<Duty> getBySeek(String str);

    /**
     * 获取职务对象
     * @param id 职务id
     * @return
     */
    Duty getById(String id);

    /**
     * 更新职务
     * @param duty 职务对象
     * @return
     */
    int update(Duty duty);

    /**
     * 添加职务
     * @param duty 职务对象
     */
    void insert(Duty duty);

    /**
     * 删除职务
     * @param id 职务id
     * @return
     */
    int delete(String id);

    /**
     * 从职务中删除人员
     * @param dutyId 职务id
     * @param staffId 人员id
     * @return
     */
    int deletePerson(String dutyId, String staffId);

    /**
     * 添加职员到职务中
     * @param result {staffId:职员id,dutyId:职务id}
     */
    void insertPerson(Map<String, String> result) throws SQLException;

    /**
     * 获取职务集合
     *
     * @param staffId 职员id
     * @return
     */
    Duty[] getDutyByStaffId(String staffId);

    /**
     * 删除指定职员所有职务
     *
     * @param staffId 职员id
     * @return
     */
    int deletePersonAll(String staffId);

    /**
     * 获取职务人员总数
     *
     * @param id 植物id
     */
    Integer getStaffCountByDuty(String id);

    /**
     * 层级查询（某职务下包含的所有子职务）
     * @param dutyId
     * @return
     */
    List<Duty> getDutyByRoot(String dutyId);
}
