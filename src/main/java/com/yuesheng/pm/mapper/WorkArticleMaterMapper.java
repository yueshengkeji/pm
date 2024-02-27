package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.model.WorkOutMaterModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/29 办公用品明细.
 *
 * @author XiaoSong
 * @date 2016/12/29
 */
@Mapper
public interface WorkArticleMaterMapper {
    /**
     * 获取办公用品明细集合
     *
     * @param articleId 办公用品对象id
     * @return
     */
    List<MaterOutChild> getArticleMaterByArticleId(@Param("articleId") String articleId);

    /**
     * 获取办公用品明细对象
     *
     * @param id 办公用品明细id
     * @return
     */
    MaterOutChild getArticleMaterById(@Param("id") String id);

    /**
     * 获取办公用品材料
     *
     * @param id 办公用品材料编码
     * @return
     */
    Material getMaterById(@Param("id") String id);

    /**
     * 获取办公用品材料集合
     *
     * @param params     参见workArticleMaterService.queryMater()
     * @return
     */
    List<Material> queryMater(Map<String, Object> params);

    /**
     * 获取办公用品总数
     *
     * @param params 参见workArticleMaterService.queryMater()
     * @return
     */
    int queryMaterCount(Map<String, Object> params);

    /**
     * 获取办公用品目录对象
     *
     * @param id 办公用品主键
     * @return
     */
    Folder queryFolder(String id);

    /**
     * 添加办公用品对象
     *
     * @param material
     */
    void insert(Material material);

    /**
     * 获取材料对象
     *
     * @param m 材料对象
     * @return
     */
    Material getMaterByParam(Material m);

    /**
     * 获取办公用品领料单明细材料对象（第一个）
     *
     * @param materId 领料单材料对象主键
     * @return
     */
    MaterOutChild isUse(@Param("materId") String materId);

    /**
     * 删除办公用品材料
     *
     * @param id 材料主键
     * @return 影响的行数
     */
    int deleteMater(String id);

    /**
     * 更新办公用品材料信息
     *
     * @param material 办公用品材料对象
     * @return
     */
    int updateAM(Material material);

    /**
     * 添加领料单明细
     *
     * @param m
     */
    void insertArticle(MaterOutChild m);

    /**
     * 更新领料单明细数据
     *
     * @param m
     * @return
     */
    int updateArticle(MaterOutChild m);

    /**
     * 删除领料单明细
     *
     * @param id 明细行id
     * @return
     */
    int deleteArticle(String id);

    /**
     * 删除领料单明细
     *
     * @param mainId 主单据id
     * @return
     */
    int deleteArticleByMain(String mainId);

    /**
     * 获取领料单明细集合
     *
     * @param param {附带参数}
     * @return
     */
    List<MaterOutChild> querys(Map<String, Object> param);

    /**
     * 获取领料单明细总数
     *
     * @param param 附加参数
     * @return
     */
    int querysCount(Map<String, Object> param);

    /**
     * 获取办公用品库存数量
     *
     * @param id 办公用品id
     * @return
     */
    Double queryMaterStorage(String id);

    /**
     * 获取最新单价
     *
     * @param id
     * @return
     */
    Double queryNewPlace(String id);

    /**
     * 查询所有办公用品领用明细
     *
     * @param materOutChild 查询条件{pm02110=领用人,pm02112=搜索字符}
     * @return
     */
    List<WorkOutMaterModel> queryAll(MaterOutChild materOutChild);

    /**
     * 获取办公用品材料集合
     *
     * @param params 参见 workArticleMaterService.queryMater()
     * @return
     */
    List<Material> queryMaterV2(Map<String, Object> params);

    /**
     * 更新领用库存数
     * @param m
     * @return
     */
    int updateOutSum(Material m);

    /**
     * 更新库存数量和单价
     * @param m
     * @return
     */
    int updatePutSum(Material m);
}
