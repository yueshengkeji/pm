package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.TaxType;

import java.util.List;

/**
 * Created by 96339 on 2016/11/21 开票税率服务.
 */
public interface TaxTypeService {
    /**
     * 获取开票类型集合
     * @return 开票类型集合
     */
    List<TaxType> getTaxTypes();

    /**
     * 根据开票id获取开票类型对象
     *
     * @param id 开票id
     * @return
     */
    TaxType getTypeById(String id);

    /**
     * 添加税率类型
     *
     * @param taxType
     * @return
     */
    int insert(TaxType taxType);
}
