package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProSubcontractPay;
import com.yuesheng.pm.entity.ProSubcontractPayFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProSubcontractPayMapper
 * @Description
 * @Author ssk
 * @Date 2022/9/16 0016 9:26
 */
@Mapper
public interface ProSubcontractPayMapper {

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
    int updateState(@Param("id") String id,@Param("state") int state);

    /**
     * 查询合同列表
     * @param params
     * @return
     */
    List<ProSubcontractPay> selectAll(Map<String, Object> params);

    /**
     * 合同数量
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
     * @param id 分包合同付款id
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

    /**
     * 删除附件
     * @param payId 付款表单主键
     * @return
     */
    int deleteFileByPay(String payId);
}
