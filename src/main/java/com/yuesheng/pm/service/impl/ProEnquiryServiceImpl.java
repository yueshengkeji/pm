package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProEnquiry;
import com.yuesheng.pm.mapper.ProEnquiryMapper;
import com.yuesheng.pm.service.ProEnquiryService;
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
public class ProEnquiryServiceImpl implements ProEnquiryService {
    @Autowired
    private ProEnquiryMapper proEnquiryMapper;

    @Override
    public int insert(ProEnquiry enquiry) {
        if (!verify(enquiry)) {
            return -1;
        }
        return proEnquiryMapper.insert(enquiry);
    }

    private boolean verify(final ProEnquiry enquiry) {
        if (enquiry == null) {
            return false;
        }
        if (enquiry.getStaffId() == null) {
            return false;
        }
        if (enquiry.getDate() == null) {
            enquiry.setDate(new Date());
        }
        if (StringUtils.isBlank(enquiry.getId())) {
            enquiry.setId(UUID.randomUUID().toString());
        }
        if (enquiry.getName() == null) {
            enquiry.setName("");
        }
        if (enquiry.getApplyId() == null) {
            enquiry.setApplyId("");
        }
        return true;
    }

    @Override
    public int updateEnquiry(ProEnquiry proEnquiry) {
        return proEnquiryMapper.updateEnquiry(proEnquiry);
    }

    @Override
    public int closeEnquiry(int state, String id) {
        return proEnquiryMapper.closeEnquiry(state, id);
    }

    @Override
    public List<ProEnquiry> queryEnquiryList(Map<String, Object> param,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,false);
        return proEnquiryMapper.queryEnquiryList(param);
    }

    @Override
    public ProEnquiry queryEnquiry(String id) {
        return proEnquiryMapper.queryEnquiry(id);
    }

    @Override
    public int queryEnquiryCount(Map<String, Object> param) {
        return proEnquiryMapper.queryEnquiryCount(param);
    }

    @Override
    public List<ProEnquiry> queryEnquiryList(Map<String, Object> param) {
        return proEnquiryMapper.queryEnquiryListV2(param);
    }
}
