package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProQuote;
import com.yuesheng.pm.mapper.ProQuoteMapper;
import com.yuesheng.pm.service.ProQuoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2019-07-03
 */
@Service
public class ProQuoteServiceImpl implements ProQuoteService {
    @Autowired
    private ProQuoteMapper proQuoteMapper;

    @Override
    public int insert(ProQuote proQuote) {
        if (!verify(proQuote)) {
            return -1;
        }
        return proQuoteMapper.insert(proQuote);
    }

    private boolean verify(ProQuote proQuote) {
        if (proQuote == null) {
            return false;
        }
        if (StringUtils.isBlank(proQuote.getTel()) ||
                StringUtils.isBlank(proQuote.getName())) {
            return false;
        }
        if (proQuote.getPrice() == null && proQuote.getMoney() == null) {
            return false;
        } else if (proQuote.getPrice() <= 0 && proQuote.getMoney() <= 0) {
            return false;
        }
        proQuote.setDatetime(new Date());
        proQuote.setId(UUID.randomUUID().toString());
        return true;
    }

    @Override
    public List<ProQuote> queryByEnquiry(String enquiryId) {
        return proQuoteMapper.queryByEnquiry(enquiryId);
    }

    @Override
    public List<ProQuote> queryByMater(String materStr, String materId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return proQuoteMapper.queryByMater(materStr, materId);
    }

    @Override
    public List<ProQuote> queryByMaterCount(String materStr, String materId) {
        return proQuoteMapper.queryCount(materStr, materId);
    }

    @Override
    public List<ProQuote> queryByGroup(String enquiryId) {
        return proQuoteMapper.queryByGroup(enquiryId);
    }

    @Override
    public List<ProQuote> queryByMater(Map<String, Object> param) {
        PageHelper.startPage(Integer.parseInt(param.get("pageNumber").toString()), Integer.parseInt(param.get("pageSize").toString()));
        return proQuoteMapper.queryBySearch(param);
    }

    @Override
    public int queryByMaterCount(Map<String, Object> param) {
        return proQuoteMapper.queryCount(param);
    }

    @Override
    public List<Integer> queryCount(String id) {
        return proQuoteMapper.queryQuoteCount(id);
    }
}
