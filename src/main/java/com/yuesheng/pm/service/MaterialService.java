package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06 材料服务.
 *
 */
public interface MaterialService {
    /**
     * 根据材料id(编码)获取材料对象
     * @param id 材料编码
     * @return
     */
    Material getMaterialByid(String id);

    /**
     * 更新材料库存数量
     * @return 影响的行数
     */
    Integer updateMaterSum(Material material);

    /**
     * 批量更新材料库存数量
     * @return 影响的行数
     */
    Integer updateMaterSums(@Param("maters") List<Material> materials);

    /**
     * 更新材料库存数量 -=
     * @param material 材料对象
     * @return
     */
    Integer updateMaterSum_(Material material);

    /**
     * 根据参数信息获取材料集合
     *
     * @param params 参数{index,type,str}
     * @return 材料集合
     */
    List<Material> getMaterialByType(Map<String, Object> params);

    /**
     * 根据类型获取总条数
     * @param params 参数{index,type,str}
     * @return
     */
    Integer getMaterByTypeCount(Map<String, Object> params);

    /**
     * 判断材料是否存在，材料名称、型号、品牌、单位确定
     * @param material 材料对象
     * @return
     */
    Material isExist(Material material);

    /**
     * 添加材料对象
     * @param material
     */
    void addMater(Material material) throws SQLException;

    /**
     * 判断系统导入时默认的材料目录是否存在，不存在则添加
     * @param materFolderGenerate
     */
    Folder folderExist(String materFolderGenerate);

    /**
     * 添加材料目录
     * @param folder 目录对象
     */
    void addFolder(Folder folder);

    /**
     * 获取材料编码
     * @return 新的材料编码
     */
    String getCode();

    /**
     * 更新材料最后采购单价和价格
     * @param id
     * @param price
     * @param money
     */
    void updateMaterMoney(String id, Double price, Double money);

    /**
     * 检索材料目录集合
     *
     * @param text 检索关键字
     * @return
     */
    List<Folder> seekMaterialFolder(String text);

    /**
     * 获取材料目录
     *
     * @param id 目录id
     * @return
     */
    Folder getFolderById(String id);

    /**
     * 更新材料目录信息
     *
     * @param folder 目录对象
     * @return
     */
    int updateFolder(Folder folder);

    /**
     * 更新基本材料信息
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
    Map<String, String> deleteMaterial(String id);

    /**
     * 获取材料目录
     *
     * @param parent 上级目录id,null为获取根目录
     * @return
     */
    List<Folder> getFolderByParent(String parent);

    /**
     * 获取材料使用信息集合
     *
     * @param id        材料id
     * @param typeArray {1=计划单信息，2=申请单信息，3=采购单信息，4=入库单信息，5=出库单信息，6=退料单信息，7=盘点单信息}
     * @return
     */
    Map<String, Object> getMaterUseMsg(String id, String[] typeArray);

    /**
     * 获取材料目录
     *
     * @param materId 材料id
     * @return
     */
    Folder getFolderByMaterId(String materId);

    /**
     * 添加材料
     *
     * @param material 材料数组
     * @param staff    添加人员
     * @return 添加成功的材料数组
     */
    Material[] insert(Material[] material, Staff staff);

    /**
     * 添加单个材料
     *
     * @param staff 添加人
     * @param mater 材料对象
     */
    Material insert(Staff staff, Material mater);

    int insertFucai(Material material);

    int updateFucaiPrice(String id, Double planPrice);

    Double getFucaiPrice(String id);

    /**
     * 删除辅材
     *
     * @param id 辅材id
     * @return
     */
    int deleteFucai(String id);

    /**
     * 查询材料库存
     * @param params {materialName:材料名称，materialModel：材料型号，materialBrand：品牌，storage：仓库id}
     * @return
     */
    List<Material> queryStorage(HashMap<String, Object> params);

    int updateLastPutDate(Material material);

    /**
     * 更新最后采购信息
     * @param m
     * @return
     */
    int updateLastPro(Material m);
}
