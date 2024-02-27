package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.ProSubcontractPay;
import com.yuesheng.pm.entity.ProSubcontractPayFile;
import com.yuesheng.pm.mapper.ProSubcontractPayMapper;
import com.yuesheng.pm.service.ProSubcontractPayService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ProSubcontractPayServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/9/16 0016 9:35
 */
@Service("proSubcontractPayService")
public class ProSubcontractPayServiceImpl implements ProSubcontractPayService {
    @Autowired
    private ProSubcontractPayMapper proSubcontractPayMapper;

    @Override
    public int insert(ProSubcontractPay proSubcontractPay) {
        proSubcontractPay.setId(UUID.randomUUID().toString());
        proSubcontractPay.setDateTime(DateUtil.getDatetime());
        return proSubcontractPayMapper.insert(proSubcontractPay);
    }

    @Override
    public int update(ProSubcontractPay proSubcontractPay) {
        return proSubcontractPayMapper.update(proSubcontractPay);
    }

    @Override
    public int delete(String id) {
        proSubcontractPayMapper.deleteFileByPay(id);
        return proSubcontractPayMapper.delete(id);
    }

    @Override
    public int updateState(String id, int state) {
        return proSubcontractPayMapper.updateState(id,state);
    }

    @Override
    public int updateState(FlowMessage msg) {
        return updateState(msg.getFrameId(),1);
    }

    @Override
    public List<ProSubcontractPay> selectAll(Integer pageNum,Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum,pageSize,false);
        return proSubcontractPayMapper.selectAll(params);
    }

    @Override
    public Integer selectAllCounts(Map<String, Object> params) {
        return proSubcontractPayMapper.selectAllCounts(params);
    }

    @Override
    public ProSubcontractPay selectById(String id) {
        return proSubcontractPayMapper.selectById(id);
    }

    @Override
    public int addFile(ProSubcontractPayFile proSubcontractPayFile) {
        return proSubcontractPayMapper.addFile(proSubcontractPayFile);
    }

    @Override
    public List<ProSubcontractPayFile> getFiles(String id) {
        return proSubcontractPayMapper.getFiles(id);
    }

    @Override
    public int deleteFile(String id) {
        return proSubcontractPayMapper.deleteFile(id);
    }

    @Override
    public List<ProSubcontractPay> getByContractId(String contractId) {
        return proSubcontractPayMapper.getByContractId(contractId);
    }
}
