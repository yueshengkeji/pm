package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.CoursePerson;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.CoursePersonMapper;
import com.yuesheng.pm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016-08-20.
 */
@Service("coursePersonService")
public class CoursePersonServiceImpl implements CoursePersonService {
    @Autowired
    private CoursePersonMapper coursePersonMapper;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    @Lazy
    private FlowMessageService flowMessageService;

    @Override
    public List<CoursePerson> getPersonByCourseId(String courseId, Staff s) {
        List<CoursePerson> coursePeople = coursePersonMapper.getPersonByCourseId(courseId);
        String name = "";
        for (CoursePerson cp : coursePeople) {
            switch (cp.getStaffType()) {
                case 0:
                    name = "部门主管";
                    cp.setPersons(name);
                    break;
                case 1:     //获取职务名称
                    name = dutyService.getDutyName(cp.getStaffId());
                    cp.setPersons(getPersonsName(flowMessageService.getStaffByType(cp, s)));
                    break;
                case 2:     //获取职员名称
                    Staff staff = staffService.getStaffById(cp.getStaffId());
                    if (staff != null) {
                        name = staff.getName();
                        cp.setPersons(name);
                    }
                    break;
                case 3:     //获取角色名称
                    Role r = roleService.getRoleByCoding(cp.getStaffId());
                    if (r != null) {
                        name = r.getName();
                        cp.setPersons(getPersonsName(flowMessageService.getStaffByType(cp, s)));
                    }
                    break;
                case 4:
                    name = "上级领导";
                    cp.setPersons(name);
                    break;
                case 5:
                    name = "部门主管";
                    cp.setPersons(name);
                    break;
                case 6:
                    name = "发起人";
                    cp.setPersons(name);
                    break;
                default:
                    break;
            }
            cp.setName(name);
        }
        return coursePeople;
    }

    private String getPersonsName(List<Staff> staffs) {
        if (staffs == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Staff ts : staffs) {
            sb.append(ts.getName());
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    public void update(CoursePerson cp) {
        if (cp.getId() == null) {
            insert(cp);
        } else {
            coursePersonMapper.update(cp);
        }
    }

    @Override
    public void insert(CoursePerson cp) {
        if (cp == null || cp.getCourseId() == null || cp.getFlowId() == null) {
            return;
        }

        if (cp.getId() == null) {     //生成id
            cp.setId(UUID.randomUUID().toString());
        }
        coursePersonMapper.insert(cp);
    }

    @Override
    public void delete(String id) {
        coursePersonMapper.delete(id);
    }

    @Override
    public void deleteByCourse(String courseId) {
        coursePersonMapper.deleteByCourse(courseId);
    }
}
