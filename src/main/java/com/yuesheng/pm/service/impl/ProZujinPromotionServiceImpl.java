package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProZujinPromotion;
import com.yuesheng.pm.mapper.ProZujinPromotionMapper;
import com.yuesheng.pm.service.ProZujinPromotionService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ProZujinPromotionServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/3/8 0008 10:12
 */
@Service
public class ProZujinPromotionServiceImpl implements ProZujinPromotionService {

    @Autowired
    private ProZujinPromotionMapper proZujinPromotionMapper;

    @Override
    public int insert(ProZujinPromotion proZujinPromotion) {
        proZujinPromotion.setId(UUID.randomUUID().toString());
        proZujinPromotion.setCreateTime(DateUtil.getDatetime());
        return proZujinPromotionMapper.insert(proZujinPromotion);
    }

    @Override
    public int update(ProZujinPromotion proZujinPromotion) {
        return proZujinPromotionMapper.update(proZujinPromotion);
    }

    @Override
    public int delete(String id) {
        return proZujinPromotionMapper.delete(id);
    }

    @Override
    public List<ProZujinPromotion> list(String contractId) {
        return proZujinPromotionMapper.list(contractId);
    }
}
