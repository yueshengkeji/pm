package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.ProZujinEnd;
import com.yuesheng.pm.mapper.ProZujinEndMapper;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.service.FileService;
import com.yuesheng.pm.service.ProZujinEndService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName ProZujinEndServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/3/16 0016 8:40
 */
@Service("proZujinEndService")
public class ProZujinEndServiceImpl implements ProZujinEndService, FileService {
    @Autowired
    private ProZujinEndMapper proZujinEndMapper;
    @Autowired
    private AttachService attachService;

    @Override
    public int insert(ProZujinEnd proZujinEnd) {
        proZujinEnd.setId(UUID.randomUUID().toString());
        proZujinEnd.setCreateDate(DateUtil.getDatetime());
        return proZujinEndMapper.insert(proZujinEnd);
    }

    @Override
    public int update(ProZujinEnd proZujinEnd) {
        proZujinEnd.setUpdateDate(DateUtil.getDatetime());
        return proZujinEndMapper.update(proZujinEnd);
    }

    @Override
    public int delete(String id) {
        return proZujinEndMapper.delete(id);
    }

    @Override
    public List<ProZujinEnd> list(Map<String, Object> params) {
        return proZujinEndMapper.list(params);
    }

    @Override
    public ProZujinEnd selectById(String id) {
        return proZujinEndMapper.selectById(id);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        ProZujinEnd proZujinEnd = proZujinEndMapper.selectById(moduleId);
        ArrayList<Attach> res = new ArrayList<>();
        if (!Objects.isNull(proZujinEnd) && StringUtils.isNotBlank(proZujinEnd.getFiles())){
            String attachIds[] = proZujinEnd.getFiles().split(";");
            for (int i = 0; i < attachIds.length; i++) {
                res.add(attachService.getById(attachIds[i]));
            }
        }

        return res;
    }
}
