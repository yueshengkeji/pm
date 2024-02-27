package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.PurchaseFiles;

import java.util.List;

/**
 * 采购合同附件   purchase_contract_registration_files
 * @author ssk
 * @since 2022-1-11
 */
public interface PurchaseContractFilesService {
    /**
     * 插入附件信息
     * @param purchaseFiles
     * @return
     */
    int insert(PurchaseFiles purchaseFiles);

    /**
     * 查询
     * @param mark
     * @return
     */
    List<PurchaseFiles> select(Integer mark);

    /**
     * 删除附件
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 删除已删除合同下附件
     * @param mark
     * @return
     */
    int deleteByMark(Integer mark);
}
