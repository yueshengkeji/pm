package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujinPromotion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProZujinPromotionMapper
 * @Description
 * @Author ssk
 * @Date 2024/3/8 0008 10:11
 */
@Mapper
public interface ProZujinPromotionMapper {
    int insert(ProZujinPromotion proZujinPromotion);
    int update(ProZujinPromotion proZujinPromotion);
    int delete(String id);
    List<ProZujinPromotion> list(String contractId);
}
