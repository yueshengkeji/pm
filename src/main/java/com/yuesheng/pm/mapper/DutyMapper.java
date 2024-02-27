package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Duty;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-20 职务mapper.
 * @author XiaoSong
 * @date 2016/08/20
 */
@Mapper
public interface DutyMapper {
    /**
     * 通过职务Id获取该职务所有人员
     * @param dutyId 职务id
     * @return 职务人员集合
     */
    List<Staff> getStaffByDuty(String dutyId);

    /**
     * 获取职务名称
     * @param dutyId 职务id
     * @return
     */
    String getDutyName(String dutyId);

    /**
     * 获取职务集合
     * @param parent 上级职务id
     * @return
     */
    List<Duty> getByParent(@Param("parent") String parent);

    /**
     * 检索职务
     * @param str 检索串
     * @return
     */
    List<Duty> getBySeek(@Param("str") String str);

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
    int deletePerson(@Param("dutyId") String dutyId,@Param("staffId") String staffId);

    /**
     * 添加职员到职务中
     * @param result {staffId:职员id,dutyId:职务id}
     */
    void insertPerson(Map<String, String> result);

    /**
     * 获取职务集合
     *
     * @param staffId 职员id
     * @return
     */
    Duty[] getDutyByStaff(@Param("staffId") String staffId);

    /**
     * 删除职员所有职务
     *
     * @param staffId 职员id
     * @return
     */
    int deletePersonAll(@Param("staffId") String staffId);

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
