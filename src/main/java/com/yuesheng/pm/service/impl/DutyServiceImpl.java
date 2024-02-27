package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Duty;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.DutyMapper;
import com.yuesheng.pm.service.DutyService;
import com.yuesheng.pm.util.AESEncrypt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-20 部门服务实现.
 */
@Service("dutyService")
public class DutyServiceImpl implements DutyService {
    @Autowired
    private DutyMapper dutyMapper;

    @Override
    public List<Staff> getStaffByDuty(String dutyId) {
        return dutyMapper.getStaffByDuty(dutyId);
    }

    @Override
    public String getDutyName(String dutyId) {
        return dutyMapper.getDutyName(dutyId);
    }

    @Override
    public List<Duty> getByParent(String parent) {
        return dutyMapper.getByParent(parent);
    }

    @Override
    public List<Duty> getBySeek(String str) {
        return dutyMapper.getBySeek(str);
    }

    @Override
    public Duty getById(String id) {
        return dutyMapper.getById(id);
    }

    @Override
    public int update(Duty duty) {
        if (StringUtils.isBlank(duty.getId())) {
            return -1;
        } else {
            if (verifyDuty(duty)) {
                return dutyMapper.update(duty);
            } else {
                return -1;
            }
        }

    }

    @Override
    public void insert(Duty duty) {
        if (duty != null && duty.getId() != null && !"".equalsIgnoreCase(duty.getId())) {      //修改
            if (verifyDuty(duty)) {
                update(duty);
            }
        } else {
            if (verifyDuty(duty)) {
                dutyMapper.insert(duty);
            } else {
                duty.setId(null);
            }
        }
    }

    @Override
    public int delete(String id) {
        return dutyMapper.delete(id);
    }

    @Override
    public int deletePerson(String dutyId, String staffId) {
        return dutyMapper.deletePerson(dutyId, staffId);
    }

    @Override
    public void insertPerson(Map<String, String> result) throws SQLException {
        dutyMapper.insertPerson(result);
    }

    @Override
    public Duty[] getDutyByStaffId(String staffId) {
        return dutyMapper.getDutyByStaff(staffId);
    }

    @Override
    public int deletePersonAll(String staffId) {
        return dutyMapper.deletePersonAll(staffId);
    }

    @Override
    public Integer getStaffCountByDuty(String id) {
        return dutyMapper.getStaffCountByDuty(id);
    }

    @Override
    public List<Duty> getDutyByRoot(String dutyId) {
        return dutyMapper.getDutyByRoot(dutyId);
    }

    private boolean verifyDuty(Duty duty) {
        if (duty == null) {
            return false;
        }
        if (duty.getId() == null || "".equalsIgnoreCase(duty.getId())) {
            duty.setId(AESEncrypt.getRandom8Id());
        }
        if (duty.getName() == null) {
            return false;
        }
        if (duty.getParentId() == null) {
            duty.setParentId("");
        }
        Duty parent = getById(duty.getParentId());
        if (parent != null) {
            duty.setRootId(parent.getRootId() + duty.getId());
        } else {
            duty.setRootId(duty.getId());
        }
        if (duty.getCoding() == null || "".equalsIgnoreCase(duty.getCoding())) {
            duty.setCoding(AESEncrypt.getFixLenthString(8));
        }
        return true;
    }
}
