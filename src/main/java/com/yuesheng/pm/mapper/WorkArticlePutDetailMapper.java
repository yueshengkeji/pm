package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.WorkArticlePutDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkArticlePutDetailMapper {
    /**
     * 添加办公用品材料入库单
     *
     * @param detail
     * @return
     */
    int insert(WorkArticlePutDetail detail);

    /**
     * 查询办公用品入库记录
     *
     * @param searchText 检索串
     * @param startDate  开始日期
     * @param endDate    截止日期
     * @return
     */
    List<WorkArticlePutDetail> queryAll(@Param("searchText") String searchText,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);

    /**
     * 查询办公用品领用明细
     *
     * @param articleId
     * @return
     */
    List<WorkArticlePutDetail> queryListByArticle(@Param("articleId") String articleId);

    /**
     * 删除入库明细
     *
     * @param putId 入库单id
     * @return
     */
    int deleteByPut(@Param("putId") String putId);
}
