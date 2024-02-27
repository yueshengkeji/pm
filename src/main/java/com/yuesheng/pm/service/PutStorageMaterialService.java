package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.StorageMaterial;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-23 入库单材料服务接口.
 *
 */
public interface PutStorageMaterialService {
    /**
     * 添加入库单材料集合到数据库
     * @param storageMaterial 入库单材料集合
     * @return 影响的行数
     */
    int addMaterials(List<StorageMaterial> storageMaterial);

    /**
     * 通过入库单id获取材料集合
     * @param putId 入库单id
     * @return 材料集合
     */
    List<StorageMaterial> getMaterialByPutId(String putId);

    /**
     * 根据入库单id删除材料
     * @param id 入库单id
     * @return
     */
    Integer deleteMaterByPutId(String id);

    /**
     * 通过入库单材料id删除材料
     * @param id 入库单材料主键id
     * @return 影响的行数
     */
    Integer deleteMaterById(@Param("putId") String putId,@Param("id") String id);

    /**
     * 更新入库单材料信息
     * @param material 入库单材料对象
     */
    void updateMaterMoney(StorageMaterial material);

    /**
     * 根据供应单位id获取入库材料集合(已审核数据)
     *
     * @param params {company:单位id，start:开始时间，end：结束时间}
     * @return 入库材料集合
     */
    List<StorageMaterial> getMaterialByCompany(Map<String, String> params);

    /**
     * 入库金额统计
     * @param params
     * @return
     */
    Double getPutTotalMoney(Map<String, String> params);

    /**
     * 通过入库单id获取材料集合
     * @param putId 入库单id
     * @return 材料集合
     */
    List<StorageMaterial> getMaterAllByPutId(String putId);

    /**
     * 获取项目id
     * @param detailId 明细行主键
     * @return
     */
    String getProjectByDetailId(String detailId);

    /**
     * 更新入库单金额&数量信息
     * @param sm
     */
    void updateMaterMoneyAll(StorageMaterial sm);

    /**
     * 获取材料入库单价总和+入库次数
     *
     * @param materId 材料id
     * @return
     */
    Map<String, Object> getAvgByMaterId(String materId);

    /**
     * 判断材料是否被入库单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterialByMaterId(String id);

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
    Double getPutMoneyByStorageId(String storageId);

    /**
     * 根据材料id查询最后一次入库记录
     *
     * @param materId 材料id
     * @return
     */
    StorageMaterial getPutDetailByMaterId(String materId);

    /**
     * 根据采购订单明细行获取入库单材料集合
     *
     * @param proMaterId 采购订单明细行id
     * @return
     */
    List<StorageMaterial> getMaterByProMater(String proMaterId);

    /**
     * 查询项目入库金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getPutMoneyByProject(String projectId);

    /**
     * 查询指定时间内已审核入库总金额（含税）
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    Double getPutMoneyByDate(String startDate, String endDate);

    /**
     * 更具项目获取入库材料（未出库）
     *
     * @param projectId  项目id
     * @param searchText 检索字符串
     * @return
     */
    List<StorageMaterial> getMaterListByProject(String projectId, String searchText);

    /**
     * 查询入库材料列表
     *
     * @param proMaterId 采购单材料行id
     * @return
     */
    List<StorageMaterial> getByProMaterId(String proMaterId);

    /**
     * 查询材料最后入库信息
     * @param materId
     * @return
     */
    StorageMaterial getPutDateByMaterId(String materId);

    /**
     * 查询项目入库材料集合
     * @param projectId
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);

    /**
     * 查询入库总数
     * @param proMaterId 采购订单明细行id
     * @return
     */
    Double getPutSumByProId(String proMaterId);
}
