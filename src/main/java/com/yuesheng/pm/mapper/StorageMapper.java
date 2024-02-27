package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Storage;
import com.yuesheng.pm.entity.StorageMater;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-23 仓库mapper.
 * @author XiaoSong
 * @date 2016/08/23
 */
@Mapper
public interface StorageMapper {
    /**
     * 通过仓库id获取仓库对象
     * @param id 仓库id
     * @return 仓库对象
     */
    Storage getStorageById(String id);

    /**
     * 获取所有仓库集合
     * @return 仓库集合
     */
    List<Storage> getStorages();

    /**
     * 添加材料到指定的仓库中
     * @param maters 仓库材料对象集合
     * @return 影响的行数
     */
    Integer addStorageMaters(@Param("maters") List<StorageMater> maters);

    /**
     * 添加材料到指定的仓库中
     * @param mater 仓库材料对象
     * @return 影响的行数
     */
    Integer addStorageMater(StorageMater mater);

    /**
     * 通过仓库id和材料id获取仓库中材料对象
     * @param StorageId 仓库id
     * @param materId 材料id
     * @return 仓库中材料对象
     */
    StorageMater getStorageMater(@Param("id") String StorageId,@Param("materId") String materId);

    /**
     * 更新仓库中材料
     * @param mater
     * @return
     */
    int updateStorageMater(StorageMater mater);

    /**
     * 根据仓库id获取仓库中有库存的材料集合
     * @param id 仓库id
     * @return 材料集合
     */
    List<StorageMater> getMaterByStorageId(@Param("storageId") String id);

    /**
     * 根据仓库id获取该仓库中所有材料集合，包括库存为0的材料
     * @param id 仓库id
     * @return 仓库材料集合
     */
    List<StorageMater> getMaterByStorageIds(@Param("storageId") String id);

    /**
     * 更新仓库材料单价
     *
     * @param sm
     */
    void updateStoragePrice(StorageMater sm);

    List<StorageMater> searchMaterByStorageId(@Param("storageId") String id, @Param("searchText") String searchText);

    /**
     * 查询库存材料排序是否存在
     *
     * @param storageId 仓库id
     * @param materId   材料id
     * @return
     */
    StorageMater existsStorageOrder(@Param("storageId") String storageId, @Param("materId") String materId);

    /**
     * 添加库存材料排序
     *
     * @param storageId 仓库id
     * @param materId   材料id
     * @param datetime  入库时间
     * @return
     */
    int insertStorageOrder(@Param("storageId") String storageId, @Param("materId") String materId, @Param("datetime") String datetime);

    /**
     * 更新库存材料排序
     *
     * @param storageId 仓库id
     * @param materId   材料id
     * @param datetime  入库时间
     * @return
     */
    int updateStorageOrder(@Param("storageId") String storageId, @Param("materId") String materId, @Param("datetime") String datetime);

    /**
     * 添加仓库
     *
     * @param storage
     * @return
     */
    int insert(Storage storage);

    /**
     * 查询材料库存总数
     * @param materialId 材料id
     * @return
     */
    Double getMaterialSum(@Param("materialId") String materialId);
}
