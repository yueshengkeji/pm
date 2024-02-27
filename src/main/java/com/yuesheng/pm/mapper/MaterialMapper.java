package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Material;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06 材料mapper.
 * @author XiaoSong
 * @date 2016/08/06
 */
@Mapper
public interface MaterialMapper {
    /**
     * 根据材料id(编码)获取材料对象
     * @param id 材料编码
     * @return
     */
    Material getMaterialByid(String id);

    /**
     * 更新材料库存数量 +
     * @param material 材料对象
     * @return 影响的行数
     */
    Integer updateMaterSum(Material material);
    /**
     * 批量更新材料库存数量 +=
     * @param materials 材料对象集合
     * @return 影响的行数
     */
    Integer updateMaterSums(@Param("maters") List<Material> materials);

    /**
     * 更新材料库存数量 -=
     * @param material
     * @return
     */
    Integer updateMaterSum_(Material material);

    /**
     * 根据类型获取总条数
     * @param params 参数{index,type,str}
     * @return
     */
    Integer getMaterialByTypeCount(Map<String, Object> params);

    /**
     * 判断材料是否存在 ，材料名称、型号、品牌、单位确定
     * @param material 材料对象
     * @return
     */
    Material isExist(Material material);

    /**
     * 添加材料对象
     * @param material 材料对象
     */
    void addMater(Material material);

    /**
     * 判断材料目录是否存在
     * @param id 目录id
     * @return
     */
    Folder folderExist(String id);

    /**
     * 添加材料目录
     * @param folder 材料目录对象
     */
    void addFolder(Folder folder);

    /**
     * 更新材料单价和金额
     * @param id 材料主键
     * @param price 单价
     * @param money 金额
     */
    void updateMaterMoney(@Param("id") String id,@Param("price") Double price,@Param("money") Double money);

    /**
     * 获取材料目录集合
     *
     * @param text 检索字符串
     * @return
     */
    List<Folder> seekMaterialFolder(@Param("str") String text);

    /**
     * 获取材料目录
     *
     * @param id 目录id
     * @return
     */
    Folder getFolderById(@Param("id") String id);

    /**
     * 更新材料目录信息
     *
     * @param folder 目录对象
     * @return
     */
    int updateFolder(Folder folder);

    /**
     * 更新材料信息
     *
     * @param material 材料对象
     * @return
     */
    int updateMaterial(Material material);

    /**
     * 删除材料对象
     *
     * @param id 材料id
     * @return
     */
    int deleteMaterial(@Param("id") String id);

    /**
     * 获取材料目录
     *
     * @param parent 上级目录id,null为获取根目录
     * @return
     */
    List<Folder> getFolderByParent(@Param("parent") String parent);

    /**
     * 获取材料目录
     *
     * @param materId 材料id
     * @return
     */
    Folder getFolderByMaterId(@Param("materId") String materId);

    /**
     * 添加辅材
     *
     * @param material
     * @return
     */
    int insertFucai(Material material);

    int updateFucaiPrice(@Param("id") String id, @Param("planPrice") Double planPrice);

    Double getFuCaiPrice(@Param("id") String id);

    /**
     * 删除辅材
     *
     * @param id 辅材id
     * @return
     */
    int deleteFucai(@Param("id") String id);

    List<Material> getMaterialByType(Map<String, Object> params);

    List<Material> queryStorage(HashMap<String, Object> params);

    /**
     * 更新材料最后图库日期
     * @param material
     * @return
     */
    int updateLastPutDate(Material material);

    /**
     * 更新最后采购信息
     * @param m
     * @return
     */
    int updateLastPro(Material m);
}
