package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SalesFiles;
import com.yuesheng.pm.mapper.SalesContractFilesMapper;
import com.yuesheng.pm.service.SalesContractFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售合同附件
 * @author ssk
 * @since 2022-1-7
 */
@Service("SalesContractFilesService")
public class SalesContractFilesImpl implements SalesContractFilesService {

    @Autowired
    private SalesContractFilesMapper salesContractFilesMapper;

    @Override
    public int insert(SalesFiles salesFiles) {
        return salesContractFilesMapper.insert(salesFiles);
    }

    @Override
    public List<SalesFiles> select(String mark) {
        return salesContractFilesMapper.select(mark);
    }

    @Override
    public int delete(Integer id) {
        return salesContractFilesMapper.delete(id);
    }

    @Override
    public int deleteByMark(String mark) {
        return salesContractFilesMapper.deleteByMark(mark);
    }
}
