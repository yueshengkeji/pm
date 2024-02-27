package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.TaxType;
import com.yuesheng.pm.mapper.TaxTypeMapper;
import com.yuesheng.pm.service.TaxTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by 96339 on 2016/11/21.
 */
@Service("taxTypeService")
public class TaxTypeServiceImpl implements TaxTypeService {
    @Autowired
    TaxTypeMapper taxTypeMapper;

    @Override
    public List<TaxType> getTaxTypes() {
        return taxTypeMapper.getTaxTypes();
    }

    @Override
    public TaxType getTypeById(String id) {
        return taxTypeMapper.getTypeById(id);
    }

    @Override
    public int insert(TaxType taxType) {
        if (StringUtils.isBlank(taxType.getName())) {
            return -1;
        } else if (Objects.isNull(taxType.getTax())) {
            return -2;
        }
        taxType.setId(UUID.randomUUID().toString());
        if (StringUtils.isBlank(taxType.getRemark())) {
            taxType.setRemark("");
        }
        return taxTypeMapper.insert(taxType);
    }
}
