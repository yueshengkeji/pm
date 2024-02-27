package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.WorkArticlePutMapper;
import com.yuesheng.pm.service.WorkArticleMaterService;
import com.yuesheng.pm.service.WorkArticlePutDetailService;
import com.yuesheng.pm.service.WorkArticlePutService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkArticlePutServiceImpl implements WorkArticlePutService {

    @Autowired
    private WorkArticlePutMapper putMapper;

    @Autowired
    private WorkArticlePutDetailService detailService;

    @Autowired
    private WorkArticleMaterService materService;

    @Override
    @Transactional
    public int insert(WorkArticlePut article) {
        article.setDate(DateUtil.getDate());
        if (StringUtils.isBlank(article.getRemark())) {
            article.setRemark("");
        }
        article.setId(UUID.randomUUID().toString());
        int row = putMapper.insert(article);
        List<WorkArticlePutDetail> detailList = article.getDetailList();
        WorkArticlePut p = new WorkArticlePut();
        p.setId(article.getId());
        for (int i = 0; i < detailList.size(); i++) {
            WorkArticlePutDetail detail = detailList.get(i);

            //判断材料是否存在
            Material m = detail.getMaterial();
            Material temp = materService.getMaterById(m.getId());
            if (Objects.isNull(temp)) {
                m.setPutSum(0.0);
                m.setOutSum(0.0);
                materService.insert(m);
                if (Objects.isNull(m.getId()) || StringUtils.isBlank(m.getId()) || StringUtils.equals(m.getId(), "-1")) {
                    continue;
                }
            }
            detail.setArticlePut(p);
            if (detail.getSeries() == 0) {
                detail.setSeries(i + 1);
            }
            detailService.insert(detail);
        }
        return row;
    }

    @Override
    public int approveByFlow(FlowMessage msg) {
        WorkArticlePut wap = putMapper.queryById(msg.getFrameId());
        if (!Objects.isNull(wap)) {
            wap.setApprove(msg.getLastApproveUser());
            wap.setApproveState(1);
            wap.setApproveDate(DateUtil.getDate());
            approve(wap);
        }
        return 1;
    }

    @Override
    public int approve(WorkArticlePut article) {
        putMapper.update(article);
        List<WorkArticlePutDetail> details = detailService.queryListByArticle(article.getId());
        details.forEach(item -> {
            Material m = item.getMaterial();
            m.setPutSum(m.getPutSum() + item.getSum());
            m.setLastPrice(item.getPrice());
            materService.updatePutSum(m);
        });
        return 1;
    }

    @Override
    public int unApprove(WorkArticlePut articlePut) {
        if (!Objects.isNull(articlePut)) {
            Staff s = new Staff();
            s.setId("");
            s.setCoding("");
            articlePut.setApprove(s);
            articlePut.setApproveState(0);
            articlePut.setApproveDate("");

            putMapper.update(articlePut);
            List<WorkArticlePutDetail> details = detailService.queryListByArticle(articlePut.getId());
            details.forEach(item -> {
                Material m = item.getMaterial();
                m.setPutSum(m.getPutSum() - item.getSum());
                m.setLastPrice(0.0);
                materService.updatePutSum(m);
            });
            return 1;
        }
        return -1;
    }


    @Override
    public int delete(String id) {
        detailService.deleteByPut(id);
        return putMapper.delete(id);
    }
}
