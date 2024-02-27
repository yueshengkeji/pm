package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.PurchaseFiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 采购合同附件   purchase_contract_registration_files
 * @author ssk
 * @since 2022-1-11
 */
@Mapper
public interface PurchaseContractFilesMapper {
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
