package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.WorkArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/29 办公用品主单据.
 * @author XiaoSong
 * @date 2016/12/29
 */
@Mapper
public interface WorkArticleMapper {
    /**
     * 获取办公用品领用单+领用明细集合
     * @param id 主键id
     * @return
     */
    WorkArticle getWorkArticleById(@Param("id") String id);
    /**
     * 获取办公用品领用单
     * @param id 主键id
     * @return
     */
    WorkArticle getSimpleById(@Param("id") String id);

    /**
     * 添加领用单
     * @param wa 领用单对象
     */
    void insert(WorkArticle wa);

    /**
     * 删除领用单
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新领料单信息
     * @param wa
     * @return
     */
    int update(WorkArticle wa);

    /**
     * 获取办公领料单集合
     *
     * @param param
     * @param pageBounds
     * @return
     */
    List<WorkArticle> querys(Map<String, Object> param);

    /**
     * 获取领料单总数
     *
     * @param param 查询参数
     * @return
     */
    int querysCount(Map<String, Object> param);

    /**
     * 更新审核状态
     * @param message
     * @return
     */
    int approve(WorkArticle message);
}
