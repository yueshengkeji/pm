package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CheckMaterChild;
import com.yuesheng.pm.entity.Material;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 96339 on 2017/3/1 盘点材料服务接口.
 */
public interface CheckMaterChildService {
    /**
     * 添加盘点材料
     * @param mater
     */
    void checkMater(CheckMaterChild mater);

    /**
     * 更新盘点材料
     * @param mater
     * @return
     */
    int updateCheckMater(CheckMaterChild mater);

    /**
     * 删除主单据中所有材料
     * @param mainId 主单据id
     * @return
     */
    int deleteMaterByMain(String mainId);

    /**
     * 删除单个材料
     * @param id 主键id
     * @return
     */
    int deleteById(String id);

    /**
     * 获取盘点单材料集合
     * @param mainId 主单据id
     * @return
     */
    List<CheckMaterChild> getMaterList(String mainId);

    /**
     * 判断材料是否被盘点单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterByMaterId(String id);

    /**
     * 获取材料盘点信息
     *
     * @param id 材料id
     * @return
     */
    List<CheckMaterChild> getMaterUseByMaterId(String id);

    /**
     * 查询材料盘点记录
     *
     * @param param {startDate:开始日期，endDate:截止日期,searchText:搜索字符串,state:审核状态}
     * @return
     */
    List<CheckMaterChild> listByParam(HashMap<String, Object> param);

    /**
     * 获取盘点金额统计
     * @param params
     * @return
     */
    Double getCheckTotalMoney(HashMap<String, Object> params);
}
