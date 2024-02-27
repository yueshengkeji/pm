package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DingTalkDepartment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName DingTalkDepartmentMapper
 * @Description
 * @Author ssk
 * @Date 2022/10/22 0022 11:31
 */
@Mapper
public interface DingTalkDepartmentMapper {
    /**
     * 新增
     * @param dingTalkDepartment
     */
    int insert(DingTalkDepartment dingTalkDepartment);

    /**
     * 更新
     * @param dingTalkDepartment
     * @return
     */
    int update(DingTalkDepartment dingTalkDepartment);

    /**
     * 删除
     * @param dingTalkDepartment
     * @return
     */
    int delete(DingTalkDepartment dingTalkDepartment);

    /**
     * 根据deptId查询
     * @return
     */
    DingTalkDepartment selectByDeptId(Long deptId);

    /**
     * 根据sectionId查询
     * @return
     */
    DingTalkDepartment selectBySectionId(String sectionId);

    /**
     * 获取部门列表
     * @return
     */
    List<DingTalkDepartment> selectAll();
}
