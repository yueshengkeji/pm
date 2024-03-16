package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.AdvertPlaceContract;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.mapper.AdvertPlaceContractMapper;
import com.yuesheng.pm.service.AdvertPlaceContractService;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.service.FileService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName AdverPlaceContractServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/3/14 0014 9:07
 */
@Service("advertPlaceContractService")
public class AdvertPlaceContractServiceImpl implements AdvertPlaceContractService, FileService {
    @Autowired
    private AdvertPlaceContractMapper advertPlaceContractMapper;
    @Autowired
    private AttachService attachService;

    @Override
    public int insert(AdvertPlaceContract advertPlaceContract) {
        advertPlaceContract.setId(UUID.randomUUID().toString());
        advertPlaceContract.setCreateDate(DateUtil.getDatetime());
        return advertPlaceContractMapper.insert(advertPlaceContract);
    }

    @Override
    public int update(AdvertPlaceContract advertPlaceContract) {
        advertPlaceContract.setUpdateDate(DateUtil.getDatetime());
        return advertPlaceContractMapper.update(advertPlaceContract);
    }

    @Override
    public int delete(String id) {
        return advertPlaceContractMapper.delete(id);
    }

    @Override
    public List<AdvertPlaceContract> list(Map<String, Object> params) {
        return advertPlaceContractMapper.list(params);
    }

    @Override
    public AdvertPlaceContract selectById(String id) {
        return advertPlaceContractMapper.selectById(id);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        AdvertPlaceContract advertPlaceContract = advertPlaceContractMapper.selectById(moduleId);
        ArrayList<Attach> res = new ArrayList<>();
        if (!Objects.isNull(advertPlaceContract) && StringUtils.isNotBlank(advertPlaceContract.getFiles())){
            String attachIds[] = advertPlaceContract.getFiles().split(";");
            for (int i = 0; i < attachIds.length; i++) {
                res.add(attachService.getById(attachIds[i]));
            }
        }

        return res;
    }
}
