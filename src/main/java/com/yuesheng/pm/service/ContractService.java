package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ContractModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-11 合同服务接口.
 */
public interface ContractService {
    /**
     * 根据供应单位id获取采购合同集合
     *
     * @param params {companyId :单位id,isPay:是否获取未付款完毕的，1=已付款，2=未付清}
     * @return 采购合同集合
     */
    List<Contract> getContractByCompany(Map<String, Object> params, Integer pageNum,Integer pageSize);

    /**
     * 获取采购合同集合
     *
     * @return 合同集合
     */
    List<Contract> getContracts(Integer pageNum,Integer pageSize);

    /**
     * 检索合同
     *
     * @param str  检索字符串
     * @param type 合同类型
     * @return 合同集合
     */
    List<Contract> seek(String str, ContractType type);

    /**
     * 通过合同id获取合同对象
     *
     * @param id 合同id
     * @return
     */
    Contract getContractById(String id);

    /**
     * 根据时间获取合同集合
     *
     * @param param {}start: 开始时间  end:结束时间}
     * @return 合同集合
     */
    List<Contract> getContractByParam(Map<String, Object> param,Integer pageNum,Integer pageSize);

    /**
     * 根据日期获取合同金额累计
     * * @param param {start: 开始时间  end:结束时间,companyId:单位id,payStateSql:查询支付状态}
     *
     * @return
     */
    Map<String, Double> getContractMoneyByCompany(Map<String, Object> param);

    /**
     * 根据日期获取合同已付款金额累计
     * * @param param {start: 开始时间  end:结束时间,companyId:单位id,payStateSql:查询支付状态}
     *
     * @return
     */
    Double getContractYetMoneyByCompany(Map<String, Object> param);

    void deleteByFlowEvent(String id);

    /**
     * 添加合同
     *
     * @param contract 合同对象
     */
    int addContract(Contract contract);

    int delete(String id);

    /**
     * 获取合同数量
     *
     * @param params 参见getContractByParam()
     * @return
     */
    int getContractCount(Map<String, Object> params);

    /**
     * 获取拟定合同
     *
     * @param id 合同主键
     * @return
     */
    Contract queryProtocol(String id);

    /**
     * 获取合同的项目集合
     *
     * @param id 拟定合同id
     * @return
     */
    List<Project> queryProjectByProtocol(String id);

    /**
     * 审核|反审核合同
     *
     * @param id
     * @param i
     * @param coding
     * @param date
     */
    void approve(String id, int i, String coding, String date);

    /**
     * 添加合同附件
     *
     * @param attaches
     * @param contract
     */
    void addContractAttache(String[] attaches, Contract contract);

    /**
     * 合同预付款处理
     *
     * @param payPlan
     * @param contract
     */
    void yuPay(ContractPayPlan payPlan, Contract contract);

    /**
     * 生成合同id
     *
     * @return
     */
    String genId();

    /**
     * 通过合同模型添加合同
     *
     * @param model 合同模型
     * @param staff 添加人员
     * @return
     */
    Contract addContract(ContractModel model, Staff staff);

    /**
     * 删除合同
     *
     * @param id    合同id
     * @param s     删除人员
     * @param force 是否强制删除该合同
     * @return
     */
    Map<String, Object> delete(String id, Staff s, boolean force);

    void setApplyMoney(String id);

    /**
     * 修改合同状态为失效
     *
     * @param id
     * @return
     */
    int lose(String id) throws Exception;

    /**
     * 获取合同列表
     *
     * @param params {start：数据开始日期，end：数据截止日期，str:检索字符串，staffCoding：合同登记员工coding，payStateSql：支付状态比较sql}
     * @return
     */
    List<Contract> getContractByParam(Map<String, Object> params);

    /**
     * 获取指定日期内合同总金额（已审核）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Double getMoneyByDate(String startDate, String endDate);

    void approve(FlowMessage msg);

    /**
     * 更新合同状态
     * @param id id
     * @param state 状态
     * @param coding 审核人编码
     */
    void updateState(String id, int state,String coding);

    /**
     * 更新申请中付款金额
     * @param contractId
     * @param applyMoney
     */
    void updateApplyMoney(String contractId, Double applyMoney);

    /**
     * 更新合同已付款金额
     * @param id
     * @param v
     */
    void updateYetPayMoney(String id, Double v);

    /**
     * 查询合同列表(不包含项目信息)
     * @param params {companyId:供应单位id}
     * @return
     */
    List<Contract> getContractByCompany(Map<String, Object> params);
}
