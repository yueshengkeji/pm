package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.InvoiceFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName InvoiceFileMapper
 * @Description
 * @Author ssk
 * @Date 2022/12/29 0029 11:37
 */
@Mapper
public interface InvoiceFileMapper {
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
