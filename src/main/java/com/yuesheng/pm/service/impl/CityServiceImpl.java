package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.City;
import com.yuesheng.pm.mapper.CityMapper;
import com.yuesheng.pm.service.CityService;
import com.yuesheng.pm.util.AESEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XiaoSong
 * @date 2016-08-11 地址服务实现
 */
@Service("cityService")
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;

    @Override
    public City getCityById(String id) {
        return cityMapper.getCityById(id);
    }

    @Override
    public List<City> getCitys(Integer pageNum,Integer pageSize) {
        return cityMapper.getCitys();
    }

    @Override
    public List<City> seek(String str) {
        return cityMapper.seek(str);
    }

    @Override
    public int count() {
        return cityMapper.count();
    }

    @Override
    public City insert(City c) {
        if (c == null || c.getName() == null) {
            return null;
        }
        if (StringUtils.isNotBlank(c.getId())) {
            City dataCity = getCityById(c.getId());
            if (dataCity != null && c.getName().equals(dataCity.getName())) {
                return c;
            }
        }
        c.setId(AESEncrypt.getRandom8Id());
        if (c.getName().length() > 25 && c.getCoding() == null) {
            c.setCoding(c.getName().substring(0, 25));
        } else if (c.getCoding() == null) {
            c.setCoding(c.getName());
        }
        if (c.getCz() == null) {
            c.setCz("");
        }
        if (c.getPerson() == null) {
            c.setPerson("");
        }
        if (c.getTel() == null) {
            c.setTel("");
        }
        if (c.getEmail() == null) {
            c.setEmail("");
        }
        if (c.getRemark() == null) {
            c.setRemark("");
        }
        cityMapper.insert(c);
        return c;
    }
}
