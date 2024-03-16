package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.PlaceUseContract;
import com.yuesheng.pm.mapper.PlaceUseContractMapper;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.service.FileService;
import com.yuesheng.pm.service.PlaceUseContractService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName PlaceUseContractServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/3/11 0011 9:34
 */
@Service("placeUseContractService")
public class PlaceUseContractServiceImpl implements PlaceUseContractService, FileService {

    @Autowired
    private PlaceUseContractMapper placeUseContractMapper;
    @Autowired
    private AttachService attachService;

    @Override
    public int insert(PlaceUseContract placeUseContract) {
        placeUseContract.setId(UUID.randomUUID().toString());
        placeUseContract.setCreateDate(DateUtil.getDatetime());
        return placeUseContractMapper.insert(placeUseContract);
    }

    @Override
    public int update(PlaceUseContract placeUseContract) {
        return placeUseContractMapper.update(placeUseContract);
    }

    @Override
    public int delete(String id) {
        return placeUseContractMapper.delete(id);
    }

    @Override
    public List<PlaceUseContract> list(Map<String,Object> params) {
        return placeUseContractMapper.list(params);
    }

    @Override
    public PlaceUseContract selectById(String id) {
        return placeUseContractMapper.selectById(id);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        PlaceUseContract placeUseContract = placeUseContractMapper.selectById(moduleId);
        ArrayList<Attach> res = new ArrayList<>();
        if (!Objects.isNull(placeUseContract) && StringUtils.isNotBlank(placeUseContract.getFiles())){
            String attachIds[] = placeUseContract.getFiles().split(";");
            for (int i = 0; i < attachIds.length; i++) {
                res.add(attachService.getById(attachIds[i]));
            }
        }

        return res;
    }
}
