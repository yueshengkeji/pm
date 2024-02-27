package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ProPutDetailCount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/11 采购与入库-对账单-明细服务接口.
 */
public interface ProPutDetailService {
    /**
     * 添加采购明细
     *
     * @param proPutForDetail
     */
    void addDetail(ProPutForDetail proPutForDetail);

    /**
     * 更新采购入库明细
     *
     * @param detail
     * @return 返回更新后的采购/入库总数
     */
    ProPutForDetail updateDetail(ProPutForDetail detail);

    ProPutForDetail updateMainDetail(ProPutForDetail detail);

    /**
     * 更新采购金额
     *
     * @param detail
     * @return
     */
    int updateProMoney(ProPutForDetail detail);

    /**
     * 更新入库金额
     *
     * @param detail
     * @return
     */
    int updatePutMoney(ProPutForDetail detail);

    /**
     * 更新采购明细的项目对象
     *
     * @param detail
     * @return
     */
    int updateProject(ProPutForDetail detail);

    /**
     * 更新最后修改人信息
     *
     * @param lastDate  修改时间
     * @param lastStaff 修改人对象
     * @param id
     * @return
     */
    int updateLastMsg(String lastDate, Staff lastStaff, String id);

    /**
     * 删除采购入库明细
     */
    int deleteDetail(String id);

    /**
     * 根据主单据删除明细集合
     *
     * @param mainId 主单据id
     * @return
     */
    int deleteDetailForMain(String mainId);

    /**
     * 获取采购入库明细集合
     *
     * @param mainId 主单据id
     * @return
     */
    List<ProPutForDetail> getDetailByMain(String mainId);

    /**
     * 获取采购入库明细对象
     *
     * @param id 主键id
     * @return
     */
    ProPutForDetail getDetail(String id);

    /**
     * 获取采购入库集合
     *
     * @param params {mainId:主表id,start:开始时间，end:结束时间}
     * @return
     */
    List<ProPutForDetail> getDetailByMain(Map<String, Object> params);

    /**
     * 根据主单据id获取采购金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Double getProMoneySumByMain(String mainId);

    /**
     * 根据主单据id获取入库金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Double getPutMoneySumByMain(String mainId);

    /**
     * 获取采购金额&入库金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Map<String, BigDecimal> getMoneySumByMain(String mainId);

    /**
     * 获取登记的采购金额|入库金额合计
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param type  0=采购金额，1=入库金额
     * @return
     */
    Double getProMoneyByDate(String start, String end, String type);

    /**
     * 获取采购部使用-采购付款与到票情况
     *
     * @param proDetailId 付款与入库单据明细id（pro_detail_c)主键
     * @return
     */
    List<ProDetailDP> getDetailDp(String proDetailId);

    /**
     * 添加采购付款与到票明细
     *
     * @param detailDp 付款到票明细对象
     * @return
     */
    int insertProDetailDp(ProDetailDP detailDp);

    /**
     * 修改采购付款与到票情况
     *
     * @param proDetailDp 采购付款与到票情况
     * @return
     */
    int updateProDetailDp(ProDetailDP proDetailDp);

    /**
     * 获取采购部已付款金额 和 已到票金额
     *
     * @param proDetailId 采购入库明细主键
     * @return
     */
    Map<String, BigDecimal> getMoneysByPayAndDp(String proDetailId);

    /**
     * 获取采购部使用-采购付款与到票情况
     *
     * @param id 主键
     * @return
     */
    ProDetailDP getProDetailDp(String id);

    /**
     * 添加采购入库对账明细
     *
     * @param detail
     * @param staff
     * @return
     */
    boolean addDetail(ProPutForDetail detail, Staff staff);

    /**
     * 删除采购明细对账单记录
     *
     * @param proId 采购订单id
     */
    void deleteByProId(String proId);

    /**
     * 更新备注
     *
     * @param detail
     * @return
     */
    int updateRemark(ProPutForDetail detail);

    /**
     * 根据采购订单获取采购对账单明细
     *
     * @param proId    采购订单id
     * @param detailId 对账单主表id
     * @return
     */
    List<ProPutForDetail> getProDetailBYProId(String proId, String detailId);

    /**
     * 根据主表id和项目id获取采购登记明细
     *
     * @param id        proDetail id
     * @param projectId 项目id
     * @return
     */
    ProPutForDetail getDetailByMain(String id, String projectId);

    /**
     * 获取到票数据
     *
     * @param param {project:"检索项目",companyName:“检索单位”,putNumber:"检索入库单",date:"指定年-月",order:"数据排序方式",pageBound:"分页"}
     * @return
     */
    List<ProDetailDP> getDpByParam(Map<String, Object> param,Integer pageNum,Integer pageSize);

    Integer getDpCount(Map<String, Object> result);

    /**
     * 根据入库单id删除对账单数据
     *
     * @param putId 入库单id
     * @return
     */
    int deleteByPutId(String putId);

    /**
     * 根据入库单id获取采购明细对账单
     *
     * @param putId 入库单id
     * @return
     */
    List<ProPutForDetail> getProDetailByPutId(String putId);

    /**
     * 查询主单据入库（应收）金额合计
     *
     * @param mainId  主单据id
     * @param endTime 截止日期
     * @return
     */
    Double getProMoneySumByMain(String mainId, String endTime);

    /**
     * 删除采购数据
     *
     * @param mainId  主单据id
     * @param proDate 采购时间
     * @return
     */
    int deleteBySeries(String mainId, String proDate);

    /**
     * 查询采购账单
     *
     * @param contractSeries 合同编号
     * @param proceedsSeries 采购编号（采购时间）
     * @return
     */
    List<ProPutForDetail> getBySeries(String contractSeries, String proceedsSeries);

    /**
     * 通过合同编号+明细id获取对账单数据
     *
     * @param contractId
     * @param detailId
     * @return
     */
    ProPutForDetail getByContractDetailId(String contractId, String detailId);

    /**
     * 通过合同编号+明细id删除
     *
     * @param contractId 合同id
     * @param detailId   明细id
     * @return
     */
    int deleteByContractDetailId(String contractId, String detailId);

    /**
     * 通过合同编号删除
     *
     * @param contractId
     * @return
     */
    int deleteByContract(String contractId);

    /**
     * 查询采购/入库明细
     *
     * @param detailId detail表主键
     * @return
     */
    List<ProPutForDetail> getDetailAndDpByMain(String detailId);

    void setProPayAndDp(Map<String, BigDecimal> moneysResult, ProPutForDetail proPutForDetail);

    /**
     * 根据日期查询采购入库明细列表
     *
     * @param params {start:开始日期，end:截止日期,mainId:采购对账单id}
     * @return
     */
    List<ProPutForDetail> getDetailByDate(Map<String, Object> params);

    List<ProPutForDetail> getDetailByDate(Map<String, Object> params, boolean loadProMoney);

    /**
     * 查询所有对账单数据
     * @param param {searchText:检索内容,type:0=采购/应收,1=入库/实收,startDate:开始日期,endDate:截止日期}
     * @return
     */
    List<ProPutForDetail> getDetail(HashMap<String, Object> param);

    /**
     * 查询金额统计
     * @param param
     * @return
     */
    ProPutDetailCount getDetailCount(HashMap<String, Object> param);

    /**
     * 查询年度采购金额合计
     * @param year
     * @return
     */
    Double getProMoneySum(String year);

    /**
     * 查询年度入库金额合计
     * @param year
     * @return
     */
    Double getPutMoneySum(String year);
}
