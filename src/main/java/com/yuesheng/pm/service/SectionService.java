package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 宋正根 on 2016/8/31 部门服务.
 *
 */
public interface SectionService {
    Section getSection(String coding);

    /**
     * 通过部门id获取职员集合(在职人员）
     *
     * @param id
     */
    List<Staff> getStaffList(String id);

    /**
     * 通过部门id获取部门主管集合
     * @param id 部门id
     * @return 部门主管集合
     */
    List<Staff> getStaffListByLeader(String id);

    /**
     * 获取上级领导集合
     * @param sectionId 当前人员部门id
     * @return 领导集合
     */
    List<Staff> getStaffByParent(String sectionId);

    /**
     * 获取部门集合
     * @param str
     * @return
     */
    List<Section> getSectionList(String str);

    /**
     * 获取部门集合
     *
     * @param parent  父元素id
     * @param isChild 是否遍历“子孙”部门
     * @return
     */
    List<Section> getSectionByParent(String parent, Boolean isChild);

    List<Section> getSectionByParent(String parent);

    /**
     * 更新部门
     *
     * @param section
     */
    int update(Section section);

    /**
     * 添加部门
     * @param section
     * @return
     */
    void insert(Section section);

    /**
     * 获取部门列表
     *
     * @param managerId 员工id
     * @return
     */
    List<Section> getSectionByManagerId(@Param("managerId") String managerId);

    /**
     * 获取所有子部门（包含子孙节点）
     *
     * @param sectionId 部门id
     * @return
     */
    List<Section> getAllSectionByParent(String sectionId);
}
