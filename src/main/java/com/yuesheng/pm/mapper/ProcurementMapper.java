package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Procurement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-08 采购订单mapper.
 * @author XiaoSong
 * @date 2016/08/08
 */
@Mapper
public interface ProcurementMapper {
    /**
     * 添加采购订单
     * @param procurementMapper 采购订单对象
     */
    void addProcurement(Procurement procurementMapper);

    /**
     * 通过采购订单id获取采购订单对象
     * @param pId
     * @return
     */
    Procurement getProcurementById(@Param("pId") String pId);

    /**
     * 获取采购订单集合，通过指定页数和开始时间与结束时间
     * @param params 开始时间和结束时间
     * @return
     */
    List<Procurement> getProcurements(Map params);

    /**
     * 更新采购订单信息
     * @param procurementMapper 采购订单对象
     * @return 影响的行数
     */
    int updateProcurement(Procurement procurementMapper);

    /**
     * 通过指定订单id删除采购订单
     * @param id 采购订单id
     * @return  影响的行数
     */
    int deleteProcurement(@Param("id") String id);

    /**
     * 删除作废单据
     * @param id
     * @return
     */
    int deleteDiscard(@Param("id")String id);

    /**
     * 获取指定时间内的订单总数
     * @param params 开始和结束时间
     * @return 总行数
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 更新采购订单状态
     * @param state 订单状态
     * @param proId 订单id
     */
    void updatePutState(@Param("state") byte state,@Param("proId") String proId);

    /**
     * 获取所有订单集合
     * @return 所有订单集合
     */
    List<Procurement> getProcurementAll();
    /**
     * 获取订单集合
     * @param contract 合同id
     * @return 订单集合+订单中材料集合
     */
    List<Procurement> getProcurementByContract(@Param("contract") String contract);

    /**
     * 审核采购订单单据
     * @param id 订单id
     * @param state 状态 2=同意，3=不同意（反审核）
     * @param coding 职员代码
     * @param date 审批时间
     */
    void approve(@Param("id") String id,@Param("state") int state,@Param("coding") String coding,@Param("date") String date);

    /**
     * 更新订单合同
     * @param procurement 订单对象
     * @return
     */
    int updateProContract(Procurement procurement);

    /**
     * 获取总页数
     * @param rp 参见Service getProcurementByParam()
     * @return
     */
    int getProCountByParam(Map<String, Object> rp);

    /**
     * 获取采购总金额（已审核的单据）
     * @param companyId 单位id
     * @return
     */
    Double getProMoneyByCompany(@Param("companyId") String companyId);

    /**
     * 删除采购订单关联的合同
     * @param contractId 合同主键
     * @param staffCoding
     * @return
     */
    int deleteContract(@Param("contractId") String contractId,@Param("voucherCoding") String staffCoding);

    /**
     * 更新税率
     * @param procurement 采购订单对象
     */
    int updateTax(Procurement procurement);

    /**
     * 获取供应单位采购次数
     *
     * @param id    单位id
     * @param start 开始日期
     * @param end   截止日期
     * @return
     */
    Integer getProNumberByDate(@Param("companyId") String id, @Param("start") String start, @Param("end") String end);

    /**
     * 修改作废状态
     *
     * @param proId        订单id
     * @param discardState 1=作废,''=取消作废
     * @return
     */
    int discard(@Param("proId") String proId, @Param("discordState") String discardState);

    /**
     * 添加作废订单
     *
     * @param p
     * @return
     */
    int insertDiscard(Procurement p);

    /**
     * 获取采购订单，不包含材料集合
     *
     * @param id
     * @return
     */
    Procurement getProcurementSimpleById(String id);

    /**
     * 更新制单时间
     *
     * @param p
     * @return
     */
    int updateVoucherDate(Procurement p);

    List<Procurement> getProByParam(Map<String, Object> rp);

    int addProDhDate(Procurement procurement);

    /**
     * 修改采购方
     *
     * @param procurement
     * @return
     */
    int updateProCompany(Procurement procurement);

    /**
     * 查询采购订单列表
     *
     * @param materialId 材料id
     * @param projectId  项目id
     * @return
     */
    List<Procurement> getProByMaterial(@Param("materialId") String materialId, @Param("projectId") String projectId);

    /**
     * 根据开始和截止日期获取采购订单列表
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    List<Procurement> getProcurementByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 设置快递单号
     *
     * @param procurement
     * @return
     */
    int expressCode(Procurement procurement);

    /**
     * 获取采购单入库状态
     *
     * @param id
     * @return {0：未入库，3：部分入库，4：全部入库}
     */
    Integer getPutState(@Param("id") String id);

    /**
     * 更新订单状态
     * @param p
     * @return
     */
    Integer updateState(Procurement p);

    /**
     * 查询优惠金额
     * @param id
     * @return
     */
    String getProSaleMoney(String id);
}
