package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.exception.StorageException;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-08-23 仓库服务接口.
 */
public interface StorageService {
    /**
     * 通过仓库id获取仓库对象
     *
     * @param id 仓库id
     * @return 仓库对象
     */
    Storage getStorageById(String id);

    /**
     * 获取所有仓库集合
     *
     * @return 仓库集合
     */
    List<Storage> getStorages();

    /**
     * 添加材料到指定的仓库中
     *
     * @param maters 仓库材料对象集合
     * @return 影响的行数
     */
    Integer addStorageMaters(List<StorageMater> maters);

    /**
     * 添加材料到指定的仓库中
     *
     * @param mater 仓库材料对象
     * @return 影响的行数
     */
    Integer addStorageMater(StorageMater mater);

    /**
     * 通过仓库id和材料编码获取仓库中材料对象
     *
     * @return 仓库中材料对象
     */
    StorageMater getStorageMater(String StorageId, String materId);

    /**
     * 更新仓库中材料
     *
     * @param mater
     * @return
     */
    Integer updateStorageMater(StorageMater mater);

    /**
     * 根据仓库id获取仓库中有库存的材料集合
     *
     * @param id 仓库id
     * @return 材料集合
     */
    List<StorageMater> getMaterByStorageId(String id);

    /**
     * 根据仓库id获取该仓库中所有材料集合，包括库存为0的材料
     *
     * @param id 仓库id
     * @return 仓库材料集合
     */
    List<StorageMater> getMaterByStorageIds(String id);

    /**
     * 更新仓库中材料单价
     *
     * @param sm
     */
    void updateStoragePrice(StorageMater sm);

    List<StorageMater> getMaterByStorageId(String id, String searchText);

    /**
     * 更新材料最后入库时间
     *
     * @param storageId
     * @param materId
     * @return
     */
    StorageMater updateOrInsert(String storageId, String materId);

    /**
     * 更新材料最后入库时间
     *
     * @param storageId
     * @param materId
     * @param datetime  入库时间
     * @return
     */
    int updateOrInsert(String storageId, String materId, Date datetime);

    /**
     * 添加仓库
     *
     * @param storage
     * @return
     */
    int insert(Storage storage);

    /**
     * 更新盘点单材料库存数、单价、金额
     *
     * @param item    盘点单材料
     * @param storage 仓库
     * @return
     */
    int updateByCheck(CheckMaterChild item, Storage storage);

    /**
     * 回滚盘点单材料库存
     *
     * @param item    盘点单材料
     * @param storage 仓库
     * @return
     */
    int reUpdateByCheck(CheckMaterChild item, Storage storage);

    /**
     * 更新材料入库，如果材料在指定仓库不存在，则自动新增
     *
     * @param item      入库明细
     * @param storageId 仓库id
     * @param approve   审核：增加库存，翻审核：减少库存
     * @return
     */
    int updatePutMaterial(StorageMaterial item, String storageId, Boolean approve);

    /**
     * 查询材料库存总数
     *
     * @param materialId 材料id
     * @return
     */
    Double getMaterialSum(String materialId);

    /**
     * 更新材料出库，如果库存不足，则抛出异常
     *
     * @param item      出库单材料行
     * @param storageId 仓库id
     * @param approve   true：审核(库存出库)，false:反审核(回退库存)
     */
    void updateOutMaterial(MaterOutChild item, String storageId, boolean approve) throws StorageException;

    /**
     * 仓库盘点更新库存
     * @param item 盘点材料
     * @param storageId 仓库id
     * @param approve true:审核（库存增加）,false:反审核（回退库存）
     * @return
     */
    int updateBackMaterial(BackMaterChild item, String storageId,boolean approve);
}
