package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.DingTalkLinkNoticeImage;
import com.yuesheng.pm.mapper.DingTalkLinkNoticeImageMapper;
import com.yuesheng.pm.service.DingTalkLinkNoticeImageService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName DingTalkLinkNoticeImageServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/9/9 0009 15:11
 */
@Service
public class DingTalkLinkNoticeImageServiceImpl implements DingTalkLinkNoticeImageService {

    @Autowired
    private DingTalkLinkNoticeImageMapper dingTalkLinkNoticeImageMapper;

    @Override
    public int insert(DingTalkLinkNoticeImage dingTalkLinkNoticeImage) {
        dingTalkLinkNoticeImage.setId(UUID.randomUUID().toString());
        dingTalkLinkNoticeImage.setCreateTime(DateUtil.getNowDate());
        return dingTalkLinkNoticeImageMapper.insert(dingTalkLinkNoticeImage);
    }

    @Override
    public List<DingTalkLinkNoticeImage> selectAll() {
        return dingTalkLinkNoticeImageMapper.selectAll();
    }

    @Override
    public int delete(String id) {
        return dingTalkLinkNoticeImageMapper.delete(id);
    }

    @Override
    public int update(String id) {
        return dingTalkLinkNoticeImageMapper.update(id);
    }

    @Override
    public int resetStatus() {
        return dingTalkLinkNoticeImageMapper.resetStatus();
    }

    @Override
    public DingTalkLinkNoticeImage selectUsed() {
        return dingTalkLinkNoticeImageMapper.selectUsed();
    }

    @Override
    public DingTalkLinkNoticeImage selectFirst() {
        return dingTalkLinkNoticeImageMapper.selectFirst();
    }
}
