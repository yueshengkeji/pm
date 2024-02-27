package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.WorkArticlePutDetail;

import java.util.List;

public interface WorkArticlePutDetailService {
    /**
     * 添加办公用品材料入库单
     * @param detail
     * @return
     */
    public int insert(WorkArticlePutDetail detail);

    /**
     * 查询办公用品入库记录
     * @param searchText 检索串
     * @param startDate 开始日期
     * @param endDate 截止日期
     * @return
     */
    List<WorkArticlePutDetail> queryAll(String searchText,
                                        String startDate,
                                        String endDate);

    /**
     * 查询办公用品入库明细
     * @param articleId
     * @return
     */
    List<WorkArticlePutDetail> queryListByArticle(String articleId);

    /**
     * 删除入库明细
     * @param putId
     * @return
     */
    int deleteByPut(String putId);
}
