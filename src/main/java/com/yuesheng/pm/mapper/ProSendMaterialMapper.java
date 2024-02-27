package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProSendMaterial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发货申请单材料明细mapper
 *
 * @author XiaoSong
 * @date 2019-12-09 10:50
 */
@Mapper
public interface ProSendMaterialMapper {
    /**
     * 添加发货申请单明细
     *
     * @param psm
     */
    void insert(ProSendMaterial psm);

    /**
     * 删除明细项
     *
     * @param id 明细主键
     * @return
     */
    int delete(@Param("id") String id);

    /**
     * 删除明细集合
     *
     * @param proId 发货申请单主单据id
     * @return
     */
    int deleteByProId(@Param("proId") String proId);

    /**
     * 获取发货申请单明细集合
     *
     * @param proId 主单据id
     * @return
     */
    List<ProSendMaterial> queryByProId(@Param("proId") String proId);
}
