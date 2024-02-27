package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ApplyDelete;
import com.yuesheng.pm.mapper.ApplyDeleteMapper;
import com.yuesheng.pm.service.ApplyDeleteService;
import com.yuesheng.pm.service.ProPutDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019-11-25.
 */
@Service
public class ApplyDeleteServiceImpl implements ApplyDeleteService {
    @Autowired
    private ApplyDeleteMapper applyDeleteMapper;
    @Autowired
    private ProPutDetailService proPutDetailService;

    @Override
    public void applyDelete(ApplyDelete applyDetele) {
        applyDeleteMapper.applyDelete(applyDetele);
        //删除采购明细对账单记录
//        proPutDetailService.deleteByProId(applyDetele.getProId());
    }

    @Override
    public void updateState(ApplyDelete applyDelete) {
        applyDeleteMapper.updateState(applyDelete);
    }

    @Override
    public List<ApplyDelete> queryAll() {
        return applyDeleteMapper.queryAll();
    }

    @Override
    public ApplyDelete queryByParam(ApplyDelete ad) {
        List<ApplyDelete> adList = applyDeleteMapper.queryByParam(ad);
        if (!adList.isEmpty()) {
            return adList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int delete(String proId) {
        return applyDeleteMapper.delete(proId);
    }
}
