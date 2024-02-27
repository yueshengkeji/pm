package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.DingTalkDepartment;
import com.yuesheng.pm.mapper.DingTalkDepartmentMapper;
import com.yuesheng.pm.service.DingTalkDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DingTalkDepartmentServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/10/22 0022 11:34
 */
@Service
public class DingTalkDepartmentServiceImpl implements DingTalkDepartmentService {
    @Autowired
    private DingTalkDepartmentMapper dingTalkDepartmentMapper;

    @Override
    public int insert(DingTalkDepartment dingTalkDepartment) {
        return dingTalkDepartmentMapper.insert(dingTalkDepartment);
    }

    @Override
    public int update(DingTalkDepartment dingTalkDepartment) {
        return dingTalkDepartmentMapper.update(dingTalkDepartment);
    }

    @Override
    public int delete(DingTalkDepartment dingTalkDepartment) {
        return dingTalkDepartmentMapper.delete(dingTalkDepartment);
    }

    @Override
    public DingTalkDepartment selectByDeptId(Long deptId) {
        return dingTalkDepartmentMapper.selectByDeptId(deptId);
    }

    @Override
    public DingTalkDepartment selectBySectionId(String sectionId) {
        return dingTalkDepartmentMapper.selectBySectionId(sectionId);
    }

    @Override
    public List<DingTalkDepartment> selectAll() {
        return dingTalkDepartmentMapper.selectAll();
    }
}
