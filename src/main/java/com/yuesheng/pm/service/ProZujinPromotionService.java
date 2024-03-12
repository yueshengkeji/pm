package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujinPromotion;

import java.util.List;

/**
 * @ClassName ProZujinPromotionService
 * @Description
 * @Author ssk
 * @Date 2024/3/8 0008 10:08
 */
public interface ProZujinPromotionService {

    int insert(ProZujinPromotion proZujinPromotion);
    int update(ProZujinPromotion proZujinPromotion);
    int delete(String id);
    List<ProZujinPromotion> list(String contractId);
}
