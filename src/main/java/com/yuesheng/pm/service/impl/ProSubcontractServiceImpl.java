package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProSubcontract;
import com.yuesheng.pm.entity.ProSubcontractFile;
import com.yuesheng.pm.mapper.ProSubcontractMapper;
import com.yuesheng.pm.service.ProSubcontractService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ProSubcontractServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/9/22 0022 10:26
 */
@Service("subcontractService")
public class ProSubcontractServiceImpl implements ProSubcontractService {
    @Autowired
    private ProSubcontractMapper proSubcontractMapper;

    @Override
    public int insert(ProSubcontract proSubcontract) {
        proSubcontract.setDateTime(DateUtil.getDatetime());
        proSubcontract.setId(UUID.randomUUID().toString());
        return proSubcontractMapper.insert(proSubcontract);
    }

    @Override
    public int delete(String id) {
        return proSubcontractMapper.delete(id);
    }

    @Override
    public int update(ProSubcontract proSubcontract) {
        return proSubcontractMapper.update(proSubcontract);
    }

    @Override
    public int updateState(String id) {
        return proSubcontractMapper.updateState(id,"1");
    }
    @Override
    public int updateState(String id, String state) {
        return proSubcontractMapper.updateState(id,state);
    }

    @Override
    public List<ProSubcontract> selectAll(Integer pageNum,Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum,pageSize,false);
        return proSubcontractMapper.selectAll(params);
    }

    @Override
    public Integer selectAllCounts(Map<String, Object> params) {
        return proSubcontractMapper.selectAllCounts(params);
    }

    @Override
    public ProSubcontract selectById(String id) {
        return proSubcontractMapper.selectById(id);
    }

    @Override
    public int addFile(ProSubcontractFile proSubcontractFile) {
        return proSubcontractMapper.addFile(proSubcontractFile);
    }

    @Override
    public List<ProSubcontractFile> getFiles(String id) {
        return proSubcontractMapper.getFiles(id);
    }

    @Override
    public int deleteFile(String id) {
        return proSubcontractMapper.deleteFile(id);
    }

    @Override
    public List<ProSubcontract> getSubcontract(String str) {
        return proSubcontractMapper.getSubcontract(str);
    }
}
