package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.model.WorkOutMaterModel;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/29 办公用品明细服务.
 *
 */
public interface WorkArticleMaterService {
    /**
     * 获取办公用品明细集合
     * @param articleId 办公用品对象id
     * @return
     */
    List<MaterOutChild> getArticleMaterByArticleId(String articleId);

    /**
     * 获取办公用品明细对象
     * @param id 办公用品明细id
     * @return
     */
    MaterOutChild getArticleMaterById(String id);
    /**
     * 获取办公用品材料集合
     * @param params 参见 workArticleService.queryMater();
     * @return
     */
    List<Material> queryMater(Map<String, Object> params,Integer pageNum,Integer pageSize);
    /**
     * 获取办公用品总数
     * @param params 参见workArticleMaterService.queryMater()
     * @return
     */
    int queryMaterCount(Map<String,Object> params);
    /**
     * 获取办公用品目录对象
     * @param id 办公用品主键
     * @return
     */
    Folder queryFolder(String id);

    /**
     * 添加办公用品材料,材料单价请添加为 costPrice 成本单价
     * @param material 办公用品对象
     */
    void insert(Material material);

    /**
     * 获取办公用品材料
     * @param id 材料编码
     * @return
     */
    Material getMaterById(String id);

    /**
     * 获取材料对象
     * @param am {name,model,brand}为组合主键
     * @return
     */
    Material getMaterByParam(Material am);

    /**
     * 通过材料id获取办公用品领料单明细对象（第一个）
     * @param materId 材料id
     * @return
     */
    MaterOutChild isUse(String materId);

    /**
     * 删除办公用品材料
     * @param id 材料主键
     */
    int deleteMater(String id);

    /**
     * 更新办公用品材料信息
     * @param material 材料对象
     */
    void updateAM(Material material);

    /**
     * 添加领料单明细对象
     * @param m
     */
    void insertArticle(MaterOutChild m);

    /**
     * 更新领料单明细
     * @param m
     * @return
     */
    int updateArticle(MaterOutChild m);

    /**
     * 删除领料单明细
     * @param id 明细行id
     * @return
     */
    int deleteArticle(String id);

    /**
     * 删除领料单明细
     * @param mainId 主单据id
     * @return
     */
    int deleteArticleByMain(String mainId);

    /**
     * 获取领料单总数
     *
     * @param param
     * @return
     */
    int querysCount(Map<String, Object> param);

    /**
     * 获取所有领料单明细集合
     *
     * @param param {}
     * @return
     */
    List<MaterOutChild> querys(Map<String, Object> param,Integer pageNum,Integer pageSize);

    /**
     * 获取办公用品库存
     *
     * @param id 办公用品材料id
     * @return
     */
    Double queryMaterStorage(String id);

    /**
     * 获取办公用品最新单价
     *
     * @param id
     * @return
     */
    Double queryNewPlace(String id);

    /**
     * 查询所有办公用品领用明细
     * @param materOutChild 查询条件{pm02110=领用人,pm02112=搜索字符}
     * @return
     */
    List<WorkOutMaterModel> queryAll(MaterOutChild materOutChild);

    /**
     * 查询办公用品库存
     * @param params
     * @return
     */
    List<Material> queryMater(Map<String, Object> params);

    /**
     * 更新领用库存数
     * @param m
     * @return
     */
    int updateOutSum(Material m);

    /**
     * 更新入库数量和单价
     * @param m
     * @return
     */
    int updatePutSum(Material m);
}
