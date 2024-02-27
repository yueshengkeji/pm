package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.InvoiceFile;
import com.yuesheng.pm.mapper.InvoiceFileMapper;
import com.yuesheng.pm.service.InvoiceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName InvoiceFileServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/12/29 0029 11:41
 */
@Service
public class InvoiceFileServiceImpl implements InvoiceFileService {
    @Autowired
    private InvoiceFileMapper invoiceFileMapper;

    @Override
    public int insert(InvoiceFile invoiceFile) {
        return invoiceFileMapper.insert(invoiceFile);
    }

    @Override
    public List<InvoiceFile> select(String mark) {
        return invoiceFileMapper.select(mark);
    }

    @Override
    public int delete(String id) {
        return invoiceFileMapper.delete(id);
    }

    @Override
    public int deleteByMark(String mark) {
        return invoiceFileMapper.deleteByMark(mark);
    }
}
