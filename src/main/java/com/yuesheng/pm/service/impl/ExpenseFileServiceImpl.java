package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ExpenseFile;
import com.yuesheng.pm.mapper.ExpenseFileMapper;
import com.yuesheng.pm.service.ExpenseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ExpenseFileServiceImpl
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:36
 */
@Service
public class ExpenseFileServiceImpl implements ExpenseFileService {
    @Autowired
    private ExpenseFileMapper expenseFileMapper;

    @Override
    public int insert(ExpenseFile expenseFile) {
        expenseFile.setId(UUID.randomUUID().toString());
        return expenseFileMapper.insert(expenseFile);
    }

    @Override
    public int delete(String id) {
        return expenseFileMapper.delete(id);
    }

    @Override
    public List<ExpenseFile> selectByMark(String mark) {
        return expenseFileMapper.selectByMark(mark);
    }

    @Override
    public ExpenseFile selectByUrl(String fileUrl) {
        return expenseFileMapper.selectByUrl(fileUrl);
    }

    @Override
    public int deleteByUrl(String fileUrl) {
        return expenseFileMapper.deleteByUrl(fileUrl);
    }
}
