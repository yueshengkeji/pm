package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.WorkArticlePutDetail;
import com.yuesheng.pm.mapper.WorkArticlePutDetailMapper;
import com.yuesheng.pm.service.WorkArticlePutDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkArticlePutDetailServiceImpl implements WorkArticlePutDetailService {
    @Autowired
    private WorkArticlePutDetailMapper detailMapper;
    @Override
    public int insert(WorkArticlePutDetail detail) {
        if(StringUtils.isBlank(detail.getRemark())){
            detail.setRemark("");
        }
        if(StringUtils.isBlank(detail.getId())){
            detail.setId(UUID.randomUUID().toString());
        }
        if(Objects.isNull(detail.getBeforeSum())) {
            detail.setBeforeSum(0.0);
        }
        if(Objects.isNull(detail.getPrice())) {
            detail.setPrice(0.0);
            detail.setMoney(0.0);
        }
        return detailMapper.insert(detail);
    }

    @Override
    public List<WorkArticlePutDetail> queryAll(String searchText,String startDate,String endDate) {
        return detailMapper.queryAll(searchText,startDate,endDate);
    }

    @Override
    public List<WorkArticlePutDetail> queryListByArticle(String articleId) {
        return detailMapper.queryListByArticle(articleId);
    }

    @Override
    public int deleteByPut(String putId) {
        return detailMapper.deleteByPut(putId);
    }
}
