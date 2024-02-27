package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FixedAssets;
import com.yuesheng.pm.entity.Folder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/12/20. 固定资产maper.
 * @author XiaoSong
 * @date 2017/12/20
 */
@Mapper
public interface FixedAssetsMapper {
    /**
     * 添加固定资产
     *
     * @param fa
     */
    void insert(FixedAssets fa);

    /**
     * 删除固定资产
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新固定资产信息
     *
     * @param fa
     * @return
     */
    int update(FixedAssets fa);

    /**
     * 获取固定资产对象
     *
     * @param id 主键
     * @return
     */
    FixedAssets queryById(String id);

    /**
     * 获取固定资产集合
     *
     * @param param      {str:检索串}
     * @return
     */
    List<FixedAssets> queryByParam(Map<String, Object> param);

    /**
     * 根据条件获取数据条目总数
     *
     * @param param
     * @return
     */
    int queryByParamCount(Map<String, Object> param);

    /**
     * 获取固定资产目录
     *
     * @param name 目录名称
     * @return
     */
    Folder getType(String name);

    /**
     * 获取多个固定资产对象
     * @param map   多个出库单对象 id {"ids",String[] }
     * @return 固定资产集合
     */
    List<FixedAssets> getFixedAssetsByMultiId(Map<String,Object> map);

    /**
     * 添加固定资产目录
     *
     * @param f 目录对象
     */
    void insertType(Folder f);

    /**
     * 获取固定资产目录
     *
     * @param folderId 目录id
     * @return
     */
    Folder queryFolderById(String folderId);

    /**
     * 通过申请单删除固定资产明细
     *
     * @param fixedId
     * @return
     */
    int deleteByFixed(String fixedId);

    /**
     * 查询资产编号前缀总数
     * @param fix 资产编号前缀
     * @return
     */
    Integer querySeriesCount(String fix);
}
