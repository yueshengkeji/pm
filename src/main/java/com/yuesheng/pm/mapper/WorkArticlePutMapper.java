package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.WorkArticlePut;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkArticlePutMapper {
    /**
     * 添加办公用品入库单
     * @param put
     * @return
     */
    public int insert(WorkArticlePut put);

    /**
     * 删除办公用品入库
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新办公用品领用入库
     * @param article
     * @return
     */
    int update(WorkArticlePut article);

    /**
     * 查询入库单信息
     * @param id
     * @return
     */
    WorkArticlePut queryById(@Param("id") String id);
}
