package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProEnquiryMater;
import com.yuesheng.pm.mapper.ProEnquiryMaterMapper;
import com.yuesheng.pm.service.ProEnquiryMaterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author XiaoSong
 * @date 2019-07-01
 */
@Service
public class ProEnquiryMaterServiceImpl implements ProEnquiryMaterService {
    @Autowired
    private ProEnquiryMaterMapper proEnquiryMaterMapper;

    @Override
    public int insert(ProEnquiryMater mater) {
        if (!verify(mater)) {
            return -1;
        }
        return proEnquiryMaterMapper.insert(mater);
    }

    private boolean verify(ProEnquiryMater mater) {
        if (mater == null) {
            return false;
        }
        if (mater.getEnquiryId() == null) {
            return false;
        }
        if (mater.getMaterId() == null) {
            return false;
        }
        if (StringUtils.isBlank(mater.getId()) || "undefined".equals(mater.getId())) {
            mater.setId(UUID.randomUUID().toString());
        }
        if (mater.getDate() == null) {
            mater.setDate(new Date());
        }
        if (mater.getNum() == null) {
            mater.setNum(0.0);
        }
        return true;
    }

    @Override
    public int updateLastDate(Date date, String id) {
        return proEnquiryMaterMapper.updateLastDate(date, id);
    }

    @Override
    public List<ProEnquiryMater> queryList(Map<String, Object> param) {
        return proEnquiryMaterMapper.queryList(param);
    }

    @Override
    public ProEnquiryMater queryById(String id) {
        return proEnquiryMaterMapper.queryById(id);
    }

}
