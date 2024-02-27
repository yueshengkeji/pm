package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FixedAssets;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Section;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/12/20 固定资产管理服务.
 */
public interface FixedAssetsService {
    /**
     * 添加固定资产
     *
     * @param fa
     * @return {-1:没有指定目录,-2:目录添加失败，1：添加成功}
     */
    int insert(FixedAssets fa);

    /**
     * 添加固定资产
     * @param fa 材料
     * @param section 部门
     * @return
     */
    int insert(FixedAssets fa, Section section);

    boolean insertType(Folder f);

    Folder getType(String name);

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
     * 获取多个固定资产对象
     * @param ids   多个固定资产对象 id
     */
    List<FixedAssets> getFixedAssetsByMultiId(String[] ids);

    /**
     * 获取固定资产集合
     *
     * @param param      {str:检索串}
     * @return
     */
    List<FixedAssets> queryByParam(Map<String, Object> param);

    /**
     * 根据条件获取总页数
     *
     * @param param 参见:queryByParam
     * @return
     */
    int queryByParamCount(Map<String, Object> param);

    /**
     * 通过申请单删除固定资产明细
     *
     * @param fixedId
     * @return
     */
    int deleteByFixed(@Param("fixedId") String fixedId);
}
