package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-06 部门mapper.
 * @author XiaoSong
 * @date 2016/08/06
 */
@Mapper
public interface SectionMapper {
    /**
     * 通过部门id获取部门对象
     * @param id 部门id
     * @return 部门对象
     */
    Section getSevtionByid(String id);

    /**
     * 根据部门id获取部门下职员集合
     * @param id 部门id
     * @return 职员集合
     */
    List<Staff> getStaffList(String id);

    /**
     * 通过部门id获取部门主管集合
     * @param id 部门id
     * @return 主管集合
     */
    List<Staff> getStaffLeader(String id);

    /**
     * 获取上级领导
     * @param section 当前职员部门id
     * @return 领导集合
     */
    List<Staff> getStaffByParent(String section);

    /**
     * 获取部门集合
     * @param str
     * @return
     */
    List<Section> getSectionList(@Param("str") String str);

    /**
     * 获取部门集合
     * @param parent 父元素id
     * @return
     */
    List<Section> getSectionByParent(@Param("parentId") String parent);

    /**
     * 添加部门
     * @param section 部门对象
     */
    void insert(Section section);
    /**
     * 更新部门
     * @param section
     * @return 影响的行数
     */
    int update(Section section);

    /**
     * 获取部门列表
     *
     * @param managerId 员工id
     * @return
     */
    List<Section> getSectionByManagerId(@Param("managerId") String managerId);
}
