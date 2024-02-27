package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.InvoiceFile;

import java.util.List;

/**
 * @ClassName InvoiceFileService
 * @Description
 * @Author ssk
 * @Date 2022/12/29 0029 11:41
 */
public interface InvoiceFileService {
    /**
     * 插入附件信息
     * @param invoiceFile
     * @return
     */
    int insert(InvoiceFile invoiceFile);

    /**
     * 查询
     * @param mark
     * @return
     */
    List<InvoiceFile> select(String mark);

    /**
     * 删除附件
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 删除已删除合同下附件
     * @param mark
     * @return
     */
    int deleteByMark(String mark);
}
