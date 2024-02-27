package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProSendMaterial;
import com.yuesheng.pm.mapper.ProSendMaterialMapper;
import com.yuesheng.pm.service.ProSendMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2019-12-09 11:03
 */
@Service
public class ProSendMaterialServiceImpl implements ProSendMaterialService {
    @Autowired
    private ProSendMaterialMapper proSendMaterialMapper;

    @Override
    public void insert(ProSendMaterial psm) {
        if (verify(psm)) {
            proSendMaterialMapper.insert(psm);
        }
    }

    private boolean verify(ProSendMaterial psm) {
        if (psm == null) {
            return false;
        } else if (StringUtils.isBlank(psm.getProId()) ||
                psm.getSendNum() == null ||
                psm.getSendNum() <= 0 ||
                psm.getMaterial() == null ||
                StringUtils.isBlank(psm.getMaterial().getId())) {
            return false;
        }
        if (StringUtils.isBlank(psm.getId())) {
            psm.setId(UUID.randomUUID().toString());
        }
        return true;
    }

    @Override
    public int delete(String id) {
        return proSendMaterialMapper.delete(id);
    }

    @Override
    public int deleteByProId(String proId) {
        return proSendMaterialMapper.deleteByProId(proId);
    }

    @Override
    public List<ProSendMaterial> queryByProId(String proId) {
        return proSendMaterialMapper.queryByProId(proId);
    }
}
