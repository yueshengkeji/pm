package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.WorkArticle;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/29 办公用品服务接口.
 *
 */
public interface WorkArticleService {
    /**
     * 获取办公用品领用单+领用明细集合
     * @param id 主键id
     * @return
     */
    WorkArticle getWorkArticleById(String id);
    /**
     * 获取办公用品领用单
     * @param id 主键id
     * @return
     */
    WorkArticle getSimpleById(String id);

    /**
     * 添加办公用品领用单
     * @param wa 领用单对象
     */
    void insert(WorkArticle wa);
    /**
     * 删除领用单
     */
    int delete(String id);
    /**
     * @param wa 办公用品对象
     * 更新领用单 & 子表明细集合
     */
    int update(WorkArticle wa);

    /**
     * 获取办公领料单集合
     *
     * @param param
     * @return
     */
    List<WorkArticle> querys(Map<String, Object> param, Integer pageNum,Integer pageSize);

    /**
     * 获取领料单总数
     *
     * @param param
     * @return
     */
    int querysCount(Map<String, Object> param);

    /**
     * 新增办公用品单据
     * @param materOutChild 办公用品材料
     * @return
     */
    WorkArticle insertByMater(List<MaterOutChild> materOutChild, Staff staff);

    void approve(FlowMessage message);
}
