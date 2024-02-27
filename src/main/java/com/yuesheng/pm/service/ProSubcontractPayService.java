package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.ProSubcontractPay;
import com.yuesheng.pm.entity.ProSubcontractPayFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProSubcontractPayService
 * @Description
 * @Author ssk
 * @Date 2022/9/16 0016 9:34
 */
public interface ProSubcontractPayService {
    /**
     * 新增分包付款
     * @param proSubcontractPay
     * @return
     */
    int insert(ProSubcontractPay proSubcontractPay);

    /**
     * 修改
     * @param proSubcontractPay
     * @return
     */
    int update(ProSubcontractPay proSubcontractPay);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改合同状态
     * @param id
     * @param state
     * @return
     */
    int updateState(String id,int state);

    /**
     * 更新单据为已审批
     * @param msg
     * @return
     */
    int updateState(FlowMessage msg);
    /**
     * 查询所有合同列表
     * @return
     */
    List<ProSubcontractPay> selectAll(Integer pageNum,Integer pageSize, Map<String, Object> params);

    /**
     * 所有合同数量
     * @return
     */
    Integer selectAllCounts(Map<String, Object> params);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProSubcontractPay selectById(String id);

    /**
     * 绑定附件
     * @param proSubcontractPayFile
     * @return
     */
    int addFile(ProSubcontractPayFile proSubcontractPayFile);

    /**
     * 获取附件
     * @param id
     * @return
     */
    List<ProSubcontractPayFile> getFiles(String id);

    /**
     * 删除附件
     * @param id
     * @return
     */
    int deleteFile(String id);

    /**
     * 根据合同id获取分包付款集合
     * @param contractId
     * @return
     */
    List<ProSubcontractPay> getByContractId(String contractId);
}
