package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.mapper.ProDetailDpMapper;
import com.yuesheng.pm.service.ProDetailDpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProDetailDpServiceImpl implements ProDetailDpService {
    @Autowired
    private ProDetailDpMapper proDetailDpMapper;

    @Override
    public List<ProDetailDP> getDpByParam(Map<String, Object> param, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return proDetailDpMapper.getDpByParam(param);
    }

    @Override
    public List<ProDetailDP> getDetailDp(String proDetailId) {
        return proDetailDpMapper.getDetailDp(proDetailId);
    }

    @Override
    public int insertProDetailDp(ProDetailDP detailDp) {
        detailDp.setId(UUID.randomUUID().toString());
        return proDetailDpMapper.insertProDetailDp(detailDp);
    }

    @Override
    public int updateProDetailDp(ProDetailDP proDetailDp) {
        return proDetailDpMapper.updateProDetailDp(proDetailDp);
    }

    @Override
    public Map<String, BigDecimal> getMoneyByPayAndDp(String proDetailId) {
        return proDetailDpMapper.getMoneyByPayAndDp(proDetailId);
    }

    @Override
    public ProDetailDP getProDetailDp(String id) {
        return proDetailDpMapper.getProDetailDp(id);
    }

    @Override
    public Integer getDpCount(Map<String, Object> param) {
        return proDetailDpMapper.getDpCount(param);
    }
}
