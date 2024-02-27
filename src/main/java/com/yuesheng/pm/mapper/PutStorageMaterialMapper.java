package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.StorageMaterial;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-23 入库单材料mapper.
 *
 * @author XiaoSong
 * @date 2016/08/23
 */
@Mapper
public interface PutStorageMaterialMapper {
    /**
     * 添加入库单材料集合到数据库
     *
     * @param storageMaterial 入库单材料集合
     * @return 影响的行数
     */
    Integer addMaterials(@Param("materials") List<StorageMaterial> storageMaterial);

    /**
     * 通过入库单id获取未出库的材料集合
     *
     * @param putId 入库单id
     * @return 材料集合
     */
    List<StorageMaterial> getMaterialByPutId(@Param("putId") String putId);

    /**
     * 通过入库单id获取材料集合
     *
     * @param putId 入库单id
     * @return 材料集合
     */
    List<StorageMaterial> getMaterAllByPutId(@Param("putId") String putId);

    /**
     * 获取入库材料关系表中最大的值
     *
     * @return 最大的值
     */
    String getFifoiMax();

    /**
     * 删除入库材料记录
     *
     * @param putMaterId 入库材料id
     * @return 影响的行数
     */
    Integer deleteFifois(@Param("id") String putMaterId);

    /**
     * 根据入库单id删除材料
     *
     * @param id 入库单id
     * @return 影响的行数
     */
    Integer deleteMaterByPutId(@Param("id") String id);

    /**
     * 根据入库单材料主键id删除单个材料
     *
     * @param putId 入库单id
     * @param id    入库单材料主键id
     * @return 影响得到行数
     */
    Integer deleteMaterById(@Param("putId") String putId, @Param("id") String id);

    /**
     * 更新入库单材料信息
     *
     * @param material 入库单材料对象
     */
    void updateMaterMoney(StorageMaterial material);


    /**
     * 根据供应单位id获取入库材料集合
     *
     * @param params {company:单位id,start:开始时间，end：结束时间}
     * @return 入库材料集合
     */
    List<StorageMaterial> getMaterialByCompany(Map<String, String> params);

    /**
     * 获取项目id
     *
     * @param detailId 入库明细行主键
     * @return
     */
    String getProjectByDetailId(@Param("detailId") String detailId);

    /**
     * 更新材料金额信息&入库数量
     *
     * @param sm 入库单明细行
     */
    void updateMaterMoneyAll(StorageMaterial sm);

    /**
     * 获取材料入库单价总和+入库次数
     *
     * @param materId 材料id
     * @return
     */
    Map<String, Object> getAvgByMaterId(@Param("materId") String materId);

    /**
     * 判断材料是否被入库单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterialByMaterId(@Param("id") String id);


    /**
     * 获取材料入库信息集合
     *
     * @param id 材料id
     * @return
     */
    List<StorageMaterial> getMaterialMsgByMaterId(String id);

    /**
     * 获取该单据入库总额（含税）
     *
     * @param storageId 入库单主表id
     * @return
     */
    Double getPutMoneyByStorageId(@Param("storageId") String storageId);

    StorageMaterial getPutDetailByMaterId(@Param("materId") String materId);

    /**
     * 根据采购订单明细行获取入库单材料集合
     *
     * @param proMaterId 采购订单明细行id
     * @return
     */
    List<StorageMaterial> getMaterByProMater(@Param("proMaterId") String proMaterId);

    /**
     * 查询项目入库金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getPutMoneyByProject(@Param("projectId") String projectId);

    /**
     * 查询指定时间内已审核入库总金额（含税）
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    Double getPutMoneyByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 更具项目获取入库材料（未出库）
     *
     * @param projectId 项目id
     * @return
     */
    List<StorageMaterial> getMaterListByProject(@Param("projectId") String projectId, @Param("searchText") String searchText);

    /**
     * 查询入库材料列表
     *
     * @param proMaterId 采购单材料行id
     * @return
     */
    List<StorageMaterial> getByProMaterId(@Param("proMaterId") String proMaterId);

    /**
     * 查询材料最后入库信息
     * @param materId
     * @return
     */
    StorageMaterial getPutDateByMaterId(@Param("materId") String materId);

    /**
     * 查询项目入库材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(@Param("projectId") String projectId);

    /**
     * 入库金额统计
     * @param params
     * @return
     */
    Double getPutTotalMoney(Map<String, String> params);

    /**
     * 查询采购单已审核入库总数
     * @param proMaterId 采购单明细行id
     * @return
     */
    Double getPutSumByProId(@Param("proMaterId") String proMaterId);
}
