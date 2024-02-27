package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.StorageMaterial;
import com.yuesheng.pm.mapper.PutStorageMaterialMapper;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.MaterialService;
import com.yuesheng.pm.service.PutStorageMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-23.
 */
@Service("putStorageMaterialService")
public class PutStorageMaterialServiceImpl implements PutStorageMaterialService {
    @Autowired
    PutStorageMaterialMapper putStorageMaterialMapper;
    @Autowired
    MaterialService materialService;

    @Override
    public int addMaterials(List<StorageMaterial> storageMaterial) {
        return putStorageMaterialMapper.addMaterials(storageMaterial);
    }

    @Override
    public List<StorageMaterial> getMaterialByPutId(String putId) {
        return putStorageMaterialMapper.getMaterialByPutId(putId);
    }

    @Override
    public Integer deleteMaterByPutId(String id) {
        return putStorageMaterialMapper.deleteMaterByPutId(id);
    }

    @Override
    public Integer deleteMaterById(String putId,String id) {
        return putStorageMaterialMapper.deleteMaterById(putId,id);
    }

    @Override
    public void updateMaterMoney(StorageMaterial material) {
        putStorageMaterialMapper.updateMaterMoney(material);
    }

    @Override
    public List<StorageMaterial> getMaterialByCompany(Map<String, String> params) {
        return putStorageMaterialMapper.getMaterialByCompany(params);
    }

    @Override
    public Double getPutTotalMoney(Map<String, String> params) {
        return putStorageMaterialMapper.getPutTotalMoney(params);
    }

    @Override
    public List<StorageMaterial> getMaterAllByPutId(String putId) {
        return putStorageMaterialMapper.getMaterAllByPutId(putId);
    }

    @Override
    public String getProjectByDetailId(String detailId) {
        PageHelper.startPage(1,1);
        return putStorageMaterialMapper.getProjectByDetailId(detailId);
    }

    @Override
    public void updateMaterMoneyAll(StorageMaterial sm) {
        putStorageMaterialMapper.updateMaterMoneyAll(sm);
    }

    @Override
    public Map<String, Object> getAvgByMaterId(String materId) {
        return putStorageMaterialMapper.getAvgByMaterId(materId);
    }

    @Override
    public Material getMaterialByMaterId(String id) {
        PageHelper.startPage(1,1);
        return putStorageMaterialMapper.getMaterialByMaterId(id);
    }

    @Override
    public List<StorageMaterial> getMaterialMsgByMaterId(String id) {
        List<StorageMaterial> smList = putStorageMaterialMapper.getMaterialMsgByMaterId(id);
        return smList;
    }

    @Override
    public Double getPutMoneyByStorageId(String storageId) {
        return putStorageMaterialMapper.getPutMoneyByStorageId(storageId);
    }

    @Override
    public StorageMaterial getPutDetailByMaterId(String materId) {
        PageHelper.startPage(1,1);
        return putStorageMaterialMapper.getPutDetailByMaterId(materId);
    }

    @Override
    public List<StorageMaterial> getMaterByProMater(String proMaterId) {
        return putStorageMaterialMapper.getMaterByProMater(proMaterId);
    }

    @Override
    public Double getPutMoneyByProject(String projectId) {
        return putStorageMaterialMapper.getPutMoneyByProject(projectId);
    }

    @Override
    public Double getPutMoneyByDate(String startDate, String endDate) {
        return putStorageMaterialMapper.getPutMoneyByDate(startDate, endDate);
    }

    @Override
    public List<StorageMaterial> getMaterListByProject(String projectId, String searchText) {
        return putStorageMaterialMapper.getMaterListByProject(projectId, searchText);
    }

    @Override
    public List<StorageMaterial> getByProMaterId(String proMaterId) {
        return putStorageMaterialMapper.getByProMaterId(proMaterId);
    }

    @Override
    public StorageMaterial getPutDateByMaterId(String materId) {
        PageHelper.startPage(1,1);
        return putStorageMaterialMapper.getPutDateByMaterId(materId);
    }

    @Override
    public List<ProjectMaterial> getProjectMaterial(String projectId) {
        return putStorageMaterialMapper.getProjectMaterial(projectId);
    }

    @Override
    public Double getPutSumByProId(String proMaterId) {
        return putStorageMaterialMapper.getPutSumByProId(proMaterId);
    }
}
