package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.DingTalkDepartment;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.SectionMapper;
import com.yuesheng.pm.service.DingTalkApiService;
import com.yuesheng.pm.service.DingTalkDepartmentService;
import com.yuesheng.pm.service.SectionService;
import com.yuesheng.pm.util.AESEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 宋正根 on 2016/8/31.
 * 部门服务实现
 */
@Service("sectionService")
public class SectionServiceImpl implements SectionService {
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    @Lazy
    private DingTalkApiService dingTalkApiService;
    @Autowired
    private DingTalkDepartmentService dingTalkDepartmentService;

    @Override
    public Section getSection(String coding) {
        return sectionMapper.getSevtionByid(coding);
    }

    @Override
    public List<Staff> getStaffList(String id) {
        return sectionMapper.getStaffList(id);
//        return null;
    }

    @Override
    public List<Staff> getStaffListByLeader(String id) {
        return sectionMapper.getStaffLeader(id);
    }

    @Override
    public List<Staff> getStaffByParent(String sectionId) {
        return sectionMapper.getStaffByParent(sectionId);
    }

    @Override
    public List<Section> getSectionList(String str) {
        return sectionMapper.getSectionList(str);
    }

    @Override
    public List<Section> getSectionByParent(String parent, Boolean isChild) {
        List<Section> sections = getSectionByParent(parent);
        if (isChild) {
            for (int i = 0; i < sections.size(); i++) {
                sections.get(i).setChildren(getSectionByParent(sections.get(i).getId(), true));
            }
        }
        return sections;
    }

    @Override
    public List<Section> getSectionByParent(String parent) {
        return sectionMapper.getSectionByParent(parent);
    }

    @Override
    public int update(Section section) {
        if (verifySection(section)) {
            return sectionMapper.update(section);
        }
        return -1;
    }

    @Override
    public void insert(Section section) {
        if(StringUtils.isBlank(section.getManagerid())){
            section.setManagerid("");
        }
        if (StringUtils.isNotBlank(section.getId())) {
            if (verifySection(section)) {
                update(section);
            }
        }
        if (verifySection(section)) {
            sectionMapper.insert(section);
            //钉钉中创建部门
            if (section.getParentid() == null) {
                dingTalkApiService.createDepartment(1L, section);
            } else {
                DingTalkDepartment dingTalkDepartment = dingTalkDepartmentService.selectBySectionId(section.getParentid());
                if(!Objects.isNull(dingTalkDepartment))
                {
                    dingTalkApiService.createDepartment(dingTalkDepartment.getDept_id(), section);
                }
            }
        } else {
            section.setId(null);
        }
    }

    @Override
    public List<Section> getSectionByManagerId(String managerId) {
        return sectionMapper.getSectionByManagerId(managerId);
    }

    @Override
    public List<Section> getAllSectionByParent(String sectionId) {
        List<Section> result = new ArrayList<>();
        result.add(getSection(sectionId));
        List<Section> sections = getSectionByParent(sectionId);
        result.addAll(sections);
        sections.forEach(s -> {
            result.addAll(getAllSectionByParent(s.getId()));
        });
        return result;
    }

    private boolean verifySection(Section section) {
        if (section == null) {
            return false;
        }
        if (section.getId() == null || "".equalsIgnoreCase(section.getId())) {
            section.setId(AESEncrypt.getRandom8Id());
        }
        if (section.getParentid() == null) {
            section.setParentid("");
        }
        //获取父元素
        if (!"".equalsIgnoreCase(section.getParentid())) {
            Section temp = getSection(section.getParentid());
            if (temp != null) {
                section.setRootId(temp.getRootId() + section.getId());
            } else {
                section.setRootId(section.getId());
            }
        } else {
            section.setRootId(section.getId());
        }
        if (section.getCoding() == null) {
            String newCoding = getNewCoding();
            section.setCoding(newCoding);

        }
        if (StringUtils.isBlank(section.getTel())) {
            section.setTel("");
        }
        if (StringUtils.isBlank(section.getAddress())) {
            section.setAddress("");
        }
        if (StringUtils.isBlank(section.getRemark())) {
            section.setRemark("");
        }
        return true;
    }

    private String getNewCoding() {
        String c = AESEncrypt.getFixLenthString(6);
        if (Objects.isNull(getSection(c))) {
            return c;
        }
        return getNewCoding();
    }
}
