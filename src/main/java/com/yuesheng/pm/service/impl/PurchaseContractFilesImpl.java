package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.PurchaseFiles;
import com.yuesheng.pm.mapper.PurchaseContractFilesMapper;
import com.yuesheng.pm.service.PurchaseContractFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购合同附件   purchase_contract_registration_files
 * @author ssk
 * @since 2022-1-11
 */
@Service("PurchaseContractFilesService")
public class PurchaseContractFilesImpl implements PurchaseContractFilesService {
    @Autowired
    private PurchaseContractFilesMapper purchaseContractFilesMapper;
    @Override
    public int insert(PurchaseFiles purchaseFiles) {
        return purchaseContractFilesMapper.insert(purchaseFiles);
    }

    @Override
    public List<PurchaseFiles> select(Integer mark) {
        return purchaseContractFilesMapper.select(mark);
    }

    @Override
    public int delete(Integer id) {
        return purchaseContractFilesMapper.delete(id);
    }

    @Override
    public int deleteByMark(Integer mark) {
        return purchaseContractFilesMapper.deleteByMark(mark);
    }
}
