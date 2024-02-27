package com.yuesheng.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.CompanyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-08 采购订单服务.
 */
public interface ProcurementService {
    /**
     * 添加采购订单
     *
     * @param procurement 采购订单对象
     * @param applyy      申请单集合
     * @param updateMater 申请单材料修改集合
     */
    boolean addProcurement(Procurement procurement, String[] applyy, Map<String, JSONObject> updateMater);

    /**
     * 通过采购订单id获取采购订单对象
     *
     * @param pId
     * @return
     */
    Procurement getProcurementById(String pId);

    /**
     * 获取采购订单集合，通过指定页数和开始时间与结束时间
     *
     * @param params 开始时间和结束时间
     * @return
     */
    List<Procurement> getProcurements(Integer pageNum,Integer pageSize, Map params);

    /**
     * 更新采购订单信息
     *
     * @param procurementMapper 采购订单对象
     * @return 影响的行数
     */
    int updateProcurement(Procurement procurementMapper);

    /**
     * 通过指定订单id删除采购订单
     *
     * @param pId 采购订单id
     * @return 影响的行数
     */
    int deleteProcurement(String pId);

    /**
     * 审核采购订单单据
     *
     * @param id     订单id
     * @param state  状态 2=同意，3=不同意（反审核）
     * @param coding 职员代码
     * @param date   审批时间
     */
    void approve(String id, int state, String coding, String date);

    /**
     * 获取指定时间的总行数
     *
     * @param params 开始和结束时间
     * @return 行数
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 更新采购订单状态
     *
     * @param b         状态码{0：未入库，3：部分入库，4：完全入库}
     * @param proId     订单id
     * @param materials 入库材料集合
     */
    void updatePutState(byte b, String proId, List<ProMaterial> materials);

    /**
     * 更新采购订单状态
     *
     * @param b     状态码{0：未入库，3：部分入库，4：完全入库}
     * @param proId 订单id
     */
    void updatePutState(byte b, String proId);

    /**
     * 获取所有订单集合
     *
     * @return 订单集合
     */
    List<Procurement> getProcurementAll();

    /**
     * 获取订单集合+订单材料集合
     *
     * @param contract 合同id
     * @return 订单集合+材料集合
     */
    List<Procurement> getProcurementByContract(String contract);

    /**
     * 更新订单合同
     *
     * @param procurement 订单对象
     * @return
     */
    int updateProContract(Procurement procurement);

    /**
     * 根据条件获取总页数
     *
     * @param rp 参见getProcurementByParam()
     * @return
     */
    int getProCountByParam(Map<String, Object> rp);

    /**
     * 获取已审核的采购总金额
     *
     * @param companyId 单位id
     * @return
     */
    Double getProMoneyByCompany(String companyId);

    /**
     * 删除采购订单关联的合同集合
     *
     * @param contractId 合同主键
     * @return
     */
    int deleteContract(String contractId, String staffCoding);

    /**
     * 更新采购订单税率
     *
     * @param procurement 采购订单对象
     */
    int updateTax(Procurement procurement);

    /**
     * 获取采购总金额
     *
     * @param companyId 供应单位id
     * @param start     开始日期
     * @param end       截止日期
     * @return
     */
    Double getProMoneyByDate(String companyId, String start, String end);

    /**
     * 获取购买最多的单位集合
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param size  数据大小
     * @return
     */
    List<CompanyModel> getProMoneyByCompany(String start, String end, Integer size);

    /**
     * 获取供应单位采购次数
     *
     * @param id    供应单位id
     * @param start 开始日期
     * @param end   截止日期
     * @return
     */
    Integer getProNumberByDate(String id, String start, String end);

    /**
     * 审核通过后调用
     *
     * @param proId
     */
    Procurement approveOk(String proId);

    /**
     * 根据采购订单生成采购记录明细对账单
     *
     * @param p
     * @param b
     * @param putId
     */
    void genProDetail(Procurement p, List<ProMaterial> materials, boolean b, String putId);


    /**
     * 根据采购订单/入库单材料列表 生成采购记录明细对账单
     *
     * @param p
     * @param b
     * @param putId
     */
    void genProDetailByPut(Procurement p, List<StorageMaterial> materials, boolean b, String putId);

    /**
     * 修改单据作废状态
     *
     * @param proId        采购订单id
     * @param discardState 状态：1=作废，''=取消作废
     * @return
     */
    int discard(String proId, String discardState);

    /**
     * 添加订单到作废单据中
     *
     * @param p 订单对象
     */
    int insertDiscard(Procurement p);

    Procurement getProcurementSimpleById(String id);

    /**
     * 更新采购订单
     *
     * @param pro         采购订单主体
     * @param staff       更新人员
     * @param updateMater 更新材料集合
     * @return
     */
    int updatePro(Procurement pro, Staff staff, Map<String, JSONObject> updateMater);

    /**
     * 生成采购订单凭证下载记录
     *
     * @param p
     */
    ProDownloadHistory genDownloadHistory(Procurement p);

    /**
     * 获取采购订单列表
     *
     * @param materialId 材料id
     * @param projectId  项目id
     * @return
     */
    List<Procurement> getProByMaterial(String materialId, String projectId);

    /**
     * 根据开始和截止日期获取采购订单列表
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    List<Procurement> getProcurementByDate(String startDate, String endDate);

    List<Procurement> getProcurementByParam(Map<String, Object> rp);

    int updateProComapny(Procurement procurement);

    /**
     * 获取采购金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getProMoneyByProject(String projectId);

    /**
     * 更新采购订单合同
     *
     * @param procurement
     * @param staff
     * @return
     */
    String updateProContract(Procurement procurement, Staff staff);

    /**
     * 设置采购单快递单号
     *
     * @param procurement 采购订单
     * @return
     */
    int expressCode(Procurement procurement);

    /**
     * 获取采购单入库状态
     *
     * @param id 主键
     * @return
     */
    Integer getPutState(String id);

    /**
     * 生成采购订单凭证图片
     *
     * @param procurement
     * @return 图片：base64字符
     */
    String getApproveVoucherImage(Procurement procurement);

    /**
     * 回退采购订单入库状态
     *
     * @param id 采购订单id
     * @return
     */
    int rebackState(String id);

    /**
     * 检查入库状态异常单据（入库状态为部分入库或未入库，实际的材料已经全部入库的单据），并自动修复为完全入库
     *
     * @param day 指定天数之前的单据
     */
    void checkErrorPutState(Integer day);

    /**
     * 更新订单状态
     * @param p
     * @return
     */
    int updateState(Procurement p);

    /**
     * 查询销售金额
     * @param proId 订单id
     * @return
     */
    String getProSaleMoney(String proId);
}
