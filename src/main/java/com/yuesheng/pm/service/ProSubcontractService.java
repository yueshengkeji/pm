package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProSubcontract;
import com.yuesheng.pm.entity.ProSubcontractFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProSubcontractService
 * @Description
 * @Author ssk
 * @Date 2022/9/22 0022 10:21
 */
public interface ProSubcontractService {

    /**
     * 新增合同
     * @param proSubcontract
     * @return
     */
    int insert(ProSubcontract proSubcontract);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新
     * @param proSubcontract
     * @return
     */
    int update(ProSubcontract proSubcontract);

    /**
     * 更新状态为已审核
     * @param id
     * @return
     */
    int updateState(String id);

    /**
     * 更新合同状态
     * @param id
     * @param state
     * @return
     */
    int updateState(String id, String state);

    /**
     * 查询合同列表
     * @param params
     * @return
     */
    List<ProSubcontract> selectAll(Integer pageNum,Integer pageSize, Map<String, Object> params);

    /**
     * 合同数量
     * @return
     */
    Integer selectAllCounts(Map<String, Object> params);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    ProSubcontract selectById(String id);

    /**
     * 绑定附件
     * @param proSubcontractFile
     * @return
     */
    int addFile(ProSubcontractFile proSubcontractFile);

    /**
     * 获取附件
     * @param id 分包合同id
     * @return
     */
    List<ProSubcontractFile> getFiles(String id);

    /**
     * 删除附件
     * @param id
     * @return
     */
    int deleteFile(String id);

    /**
     * 检索合同
     * @param str
     * @return
     */
    List<ProSubcontract> getSubcontract(String str);
}
