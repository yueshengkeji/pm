package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.OutMaterChildHistory;
import com.yuesheng.pm.mapper.OutMaterChildMapper;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.OutMaterChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 宋正根 on 2016/9/1.
 */
@Service("outMaterChildService")
public class OutMaterChildServiceImpl implements OutMaterChildService {
    @Autowired
    private OutMaterChildMapper outMaterChildMapper;

    @Override
    public Integer addOutMater(MaterOutChild child) {
        outMaterChildMapper.addOutTax(child);
        return outMaterChildMapper.addOutMater(child);
    }

    @Override
    public Integer addOutMaterHistory(OutMaterChildHistory history) {
        return outMaterChildMapper.addOutMaterHistory(history);
    }

    @Override
    public String getMaxHistory() {
        return outMaterChildMapper.getMaxHistory();
    }

    @Override
    public Integer deleteOutMaterHistory(String materOutId) {
        return outMaterChildMapper.deleteOutMaterHistory(materOutId);
    }

    @Override
    public Integer updateOutMaterHistory(OutMaterChildHistory outMaterChildHistory) {
        return outMaterChildMapper.updateOutMaterHistory(outMaterChildHistory);
    }

    @Override
    public OutMaterChildHistory getChildHistoryByMater(String outMaterId) {
        return outMaterChildMapper.getChildHistoryByMater(outMaterId);
    }

    @Override
    public Integer updateMaterChild(MaterOutChild child) {
        outMaterChildMapper.updateTax(child);
        return outMaterChildMapper.updateMaterChild(child);
    }

    @Override
    public List<MaterOutChild> getOutMatersByOutId(String outId) {
        return outMaterChildMapper.getOutMatersByOutId(outId);
    }

    @Override
    public List<MaterOutChild> getOutMatersByProject(String projectId) {
        return outMaterChildMapper.getOutMatersByProject(projectId);
    }

    @Override
    public Double getTax(String detailId) {
        return outMaterChildMapper.getTax(detailId);
    }

    @Override
    public Material getMaterByMaterId(String id) {
        PageHelper.startPage(1,1);
        return outMaterChildMapper.getMaterByMaterId(id);
    }

    @Override
    public List<MaterOutChild> getMaterialUseByMaterId(String id) {
        return outMaterChildMapper.getMaterialUseMaterId(id);
    }

    @Override
    public List<MaterUseHistory> getOutMaterHistory(Map<String, Object> param) {
        return outMaterChildMapper.getOutMaterHistory(param);
    }

    @Override
    public Integer getOutMaterHistoryCount(Map<String, Object> param) {
        return outMaterChildMapper.getOutMaterHistoryCount(param);
    }

    @Override
    public Double getOutMaterMoney(String projectId) {
        return outMaterChildMapper.getOutMaterMoney(projectId);
    }

    @Override
    public MaterOutChild getOutMaterById(String id) {
        return outMaterChildMapper.getOutMaterById(id);
    }

    @Override
    public int deleteOutMater(String materId) {
        outMaterChildMapper.deleteTax(materId);
        return outMaterChildMapper.deleteOutMater(materId);
    }

    @Override
    public int deleteTax(String materId) {
        return outMaterChildMapper.deleteTax(materId);
    }

    @Override
    public MaterOutChild getLastOutMater(String materId) {
        PageHelper.startPage(1,1);
        return outMaterChildMapper.getLastOutMater(materId);
    }

    @Override
    public Double getOutMaterHistoryMoney(Map<String, Object> param) {
        return outMaterChildMapper.getOutMaterHistoryMoney(param);
    }

    @Override
    public List<ProjectMaterial> getProjectMaterial(String projectId) {
        return outMaterChildMapper.getProjectMaterial(projectId);
    }
}
