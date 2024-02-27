package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProSend;
import com.yuesheng.pm.entity.ProSendMaterial;
import com.yuesheng.pm.mapper.ProSendMapper;
import com.yuesheng.pm.service.ProSendMaterialService;
import com.yuesheng.pm.service.ProSendService;
import com.yuesheng.pm.util.DateFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2019-12-09 10:35
 */
@Service
public class ProSendServiceImpl implements ProSendService {
    @Autowired
    private ProSendMapper proSendMapper;
    @Autowired
    private ProSendMaterialService proSendMaterialService;

    @Override
    public ProSend insert(ProSend proSend) {
        if (verify(proSend, true)) {
            proSendMapper.insert(proSend);
            for (ProSendMaterial psm : proSend.getProSendMaterialList()) {
                psm.setProId(proSend.getId());
                proSendMaterialService.insert(psm);
            }
        }
        return proSend;
    }

    /**
     * 验证单据完整性
     *
     * @param proSend
     * @param isGenUuid 是否自动生成主键
     */
    private boolean verify(ProSend proSend, boolean isGenUuid) {
        if (proSend == null) {
            return false;
        }
        if (StringUtils.isBlank(proSend.getName()) ||
                StringUtils.isBlank(proSend.getAddressDetail()) ||
                StringUtils.isBlank(proSend.getAcceptPerson()) ||
                StringUtils.isBlank(proSend.getTel())) {
            return false;
        } else {
            if (isGenUuid) {
                proSend.setId(UUID.randomUUID().toString());
                proSend.setDate(DateFormat.getDateTime());
            }
            return true;
        }
    }

    @Override
    public int update(ProSend proSend) {
        if (verify(proSend, false)) {
            if (proSend.getProSendMaterialList() != null) {
//                删除旧材料
                proSendMaterialService.deleteByProId(proSend.getId());
                for (ProSendMaterial psm : proSend.getProSendMaterialList()) {
//                    添加新材料
                    psm.setProId(proSend.getId());
                    proSendMaterialService.insert(psm);
                }
            }
//            更新主单据
            return proSendMapper.update(proSend);
        } else {
            return -1;
        }
    }

    @Override
    public int delete(String id) {
//        删除明细集合
        proSendMaterialService.delete(id);
//        删除主单据
        return proSendMapper.delete(id);
    }

    @Override
    public ProSend queryById(String id) {
        return proSendMapper.queryById(id);
    }

    @Override
    public List<ProSend> queryByParam(Map<String, Object> params,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return proSendMapper.queryByParam(params);
    }

    @Override
    public Integer queryByParamCount(Map<String, Object> params) {
        return proSendMapper.queryByParamCount(params);
    }
}
