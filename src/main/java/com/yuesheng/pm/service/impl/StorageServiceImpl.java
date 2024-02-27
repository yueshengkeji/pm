package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.exception.StorageException;
import com.yuesheng.pm.mapper.StorageMapper;
import com.yuesheng.pm.service.CheckMaterChildService;
import com.yuesheng.pm.service.MaterialService;
import com.yuesheng.pm.service.StorageService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Administrator on 2016-08-23.
 * 仓库服务接口实现类
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private CheckMaterChildService checkMaterChildService;

    @Autowired
    private MaterialService materialService;

    @Override
    public Storage getStorageById(String id) {
        return storageMapper.getStorageById(id);
    }

    @Override
    public List<Storage> getStorages() {
        return storageMapper.getStorages();
    }

    @Override
    public Integer addStorageMaters(List<StorageMater> maters) {
        return storageMapper.addStorageMaters(maters);
    }

    @Override
    public Integer addStorageMater(StorageMater mater) {
        return storageMapper.addStorageMater(mater);
    }

    @Override
    public StorageMater getStorageMater(String StorageId, String materId) {
        return storageMapper.getStorageMater(StorageId, materId);
    }

    @Override
    public Integer updateStorageMater(StorageMater mater) {
        return storageMapper.updateStorageMater(mater);
    }

    @Override
    public List<StorageMater> getMaterByStorageId(String id) {
        return storageMapper.getMaterByStorageId(id);
    }

    @Override
    public List<StorageMater> getMaterByStorageIds(String id) {
        return storageMapper.getMaterByStorageIds(id);
    }

    @Override
    public void updateStoragePrice(StorageMater sm) {
        storageMapper.updateStoragePrice(sm);
    }

    @Override
    public List<StorageMater> getMaterByStorageId(String id, String searchText) {
        return storageMapper.searchMaterByStorageId(id, searchText);
    }

    @Override
    public StorageMater updateOrInsert(String storageId, String materId) {
        StorageMater storageMater = getStorageMater(storageId,materId);
        if(Objects.isNull(storageMater))
        {
            storageMater = new StorageMater();
            storageMater.setStorageId(storageId);
            Material temp = new Material();
            temp.setId(materId);
            storageMater.setMaterial(temp);
            storageMater.setSum(0.0);
            storageMater.setPutDate("");
            storageMater.setTax(0.0);
            storageMater.setPrice(0.0);
            storageMater.setMoney(0.0);
            storageMater.setPm00506("");
            addStorageMater(storageMater);
        }
        StorageMater sm = storageMapper.existsStorageOrder(storageId, materId);
        if (sm == null) {
            storageMapper.insertStorageOrder(storageId, materId, DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL));
            sm = storageMapper.existsStorageOrder(storageId, materId);
        } else {
            storageMapper.updateStorageOrder(storageId, materId, DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL));
        }
        return sm;
    }

    @Override
    public int updateOrInsert(String storageId, String materId, Date datetime) {
        int row = 0;
        StorageMater sm = storageMapper.existsStorageOrder(storageId, materId);
        if (datetime == null) {
            datetime = new Date();
        }
        if (sm == null) {
            row = storageMapper.insertStorageOrder(storageId, materId, DateUtil.format(datetime, DateUtil.PATTERN_CLASSICAL));
        } else {
            row = storageMapper.updateStorageOrder(storageId, materId, DateUtil.format(datetime, DateUtil.PATTERN_CLASSICAL));
        }
        return row;
    }

    @Override
    public int insert(Storage storage) {
        if (StringUtils.isBlank(storage.getName())) {
            return -1;
        }
        if (StringUtils.isBlank(storage.getSortNumber())) {
            storage.setSortNumber("");
        }
        storage.setId(UUID.randomUUID().toString());
        storage.setCreateDate(DateUtil.getDate());
        return storageMapper.insert(storage);
    }

    @Override
    @Transactional
    public int updateByCheck(CheckMaterChild item, Storage storage) {
        if (!Objects.isNull(item.getRealitySum())) {
            //更新材料最后入库时间
            StorageMater sm = updateOrInsert(storage.getId(), item.getMaterial().getId());
            //记录更新盘点单之前的库存数
            item.setSum(sm.getSum());
            checkMaterChildService.updateCheckMater(item);
            //设置库存数为盘点实际库存数
            sm.setSum(item.getRealitySum());
            if (!Objects.isNull(item.getPrice())) {
                sm.setPrice(item.getPrice());
            }
            //设置库存总金额
            if (!Objects.isNull(item.getMoney())) {
                sm.setMoney(item.getMoney());
            }
            //更新材料库存数量
            updateStorage(sm);
        }
        return 0;
    }

    @Override
    @Transactional
    public int reUpdateByCheck(CheckMaterChild item, Storage storage) {
        if (!Objects.isNull(item.getRealitySum())) {
            //更新材料最后入库时间
            StorageMater sm = updateOrInsert(storage.getId(), item.getMaterial().getId());
            //设置库存数为盘点实际库存数
            sm.setSum(item.getSum());
            sm.setPrice(0.0);
            sm.setMoney(0.0);
            //更新材料库存数量
            updateStorage(sm);
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePutMaterial(StorageMaterial item, String storageId, Boolean approve) {
        StorageMater sm = getStorageMater(storageId, item.getMaterial().getId());

        if (Objects.isNull(sm)) {
            sm = new StorageMater();
            sm.setStorageId(storageId);
            sm.setMaterial(item.getMaterial());
            sm.setSum(0.0);
            sm.setPutDate("");
            sm.setTax(0.0);
            sm.setPrice(0.0);
            sm.setMoney(0.0);
            sm.setPm00506("");
            addStorageMater(sm);
        }

        if (approve) {
            //设置新的库存数 = 之前库存 + 本次入库库存
            sm.setSum(sm.getSum() + item.getPutSum());
            if(item.getPrice() == 0.0){
                //没有不含税单价，赋予含税单价
                sm.setPrice(item.getTaxPrice());
                sm.setMoney(item.getMoneyTax() + sm.getMoney());
            }else{
                sm.setPrice(item.getPrice());
                sm.setMoney(item.getMoney() + sm.getMoney());
            }
        } else {
            Double sum = sm.getSum() - item.getPutSum();
            if (sum < 0) {
                sum = 0.0;
            }
            if(item.getPrice() == 0.0){
                Double money = sm.getMoney() - item.getMoneyTax();
                if(money < 0.0){
                    money = 0.0;
                }
                sm.setMoney(money);
            }else {
                Double money = sm.getMoney() - item.getMoney();
                if(money < 0.0){
                    money = 0.0;
                }
                sm.setMoney(money);
            }
            sm.setSum(sum);
        }

        int row = updateStorage(sm);

        //更新材料库存总数 Update sdpm002 set pm00209=
        updateMaterStorageSum(item.getMaterial());
        return row;
    }

    private void updateMaterStorageSum(Material item) {
        Material m = materialService.getMaterialByid(item.getId());
        m.setStorageSum(getMaterialSum(item.getId()));
        materialService.updateMaterSum(m);
    }

    @Override
    public Double getMaterialSum(String materialId) {
        return storageMapper.getMaterialSum(materialId);
    }

    @Override
    public void updateOutMaterial(MaterOutChild item, String storageId, boolean approve) throws StorageException {
        StorageMater sm = getStorageMater(storageId,item.getMaterial().getId());
        if (Objects.isNull(sm) || Objects.isNull(sm.getSum())) {
            throw new StorageException("指定的仓库中未查询到该材料：" + sm.getMaterial().getName() + "," + sm.getMaterial().getId(), storageId);
        } else if (Objects.isNull(item.getSum())) {
            throw new StorageException("出库数量不能为0", storageId);
        } else {
            Double storageSum = sm.getSum();
            Double outSum = item.getSum();
            if (approve) {
                //减少库存
                if (outSum > storageSum) {
                    throw new StorageException(item.getMaterial().getName()+"库存不足,本次出库数量：" + outSum + ",仓库中库存数：" + storageSum, storageId);
                } else {
                    sm.setSum(storageSum - outSum);
                    sm.setMoney(sm.getMoney() - item.getTaxMoney());
                }
            } else {
                //回退库存
                sm.setSum(storageSum + outSum);
                sm.setMoney(sm.getMoney() + item.getTaxMoney());
            }
            updateStorage(sm);
            updateMaterStorageSum(item.getMaterial());

        }
    }

    @Override
    public int updateBackMaterial(BackMaterChild item, String storageId,boolean approve) {
        StorageMater sm = getStorageMater(storageId, item.getMaterial().getId());

        if (Objects.isNull(sm)) {
            sm = new StorageMater();
            sm.setStorageId(storageId);
            sm.setMaterial(item.getMaterial());
            sm.setSum(0.0);
            sm.setPutDate("");
            sm.setTax(0.0);
            sm.setPrice(0.0);
            sm.setMoney(0.0);
            sm.setPm00506("");
            addStorageMater(sm);
        }

        if (approve) {
            //设置新的库存数 = 之前库存 + 本次退库数
            sm.setSum(sm.getSum() + item.getSum());
            sm.setPrice(item.getPrice());
            sm.setMoney(item.getMoney() + sm.getMoney());
        } else {
            Double sum = sm.getSum() - item.getSum();
            sm.setMoney(sm.getMoney() - item.getMoney());
            if (sum < 0) {
                throw new StorageException(item.getMaterial().getName()+"库存不足,退库单中材料或已出库",storageId);
            }
            sm.setSum(sum);
        }

        int row = updateStorage(sm);

        //更新材料库存总数 Update sdpm002 set pm00209=
        updateMaterStorageSum(item.getMaterial());
        return row;
    }

    private int updateStorage(StorageMater sm) {
        return storageMapper.updateStorageMater(sm);
    }
}
