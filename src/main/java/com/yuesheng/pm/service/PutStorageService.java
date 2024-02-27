package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-23 入库服务接口.
 *
 */
public interface PutStorageService {
    /**
     * 添加入库单到数据库
     *
     * @param storage 入库单对象
     * @return 影响的行数
     */
    int addStorage(PutStorage storage);

    /**
     * 根据时间获取入库单
     *
     * @param params 开始时间和结束时间
     * @return 入库单集合
     */
    List<PutStorage> getStorage(String params);

    /**
     * 更新入库单审核状态
     * @param params  {state:状态，id:入库单id，person:审核人员编号，date:审核日期}
     * @return 影响的行数
     */
    int updateApprove(Map<String,Object> params);

    /**
     * 通过入库单id获取入库单对象
     * @param id 入库单id
     * @return 入库单对象
     */
    PutStorage getStorageById(String id);

    /**
     * 通过制定单数获取该时间节点下的总条数
     * @param params 开始时间&结束时间
     * @return 行数
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 根据指定的时间获取数据
     * @param params 开始时间和结束时间
     * @return 入库单集合
     */
    List<PutStorage> getPutStorages(Map<String, Object> params);

    /**
     * 更新入库单运杂费
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutMixMoney(PutStorage storage);


    /**
     * 更新入库单税率
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutTax(PutStorage storage);


    /**
     * 更新入库单备注
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutRemark(PutStorage storage);


    /**
     * 更新入库时间
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutDate(PutStorage storage);


    /**
     * 更新入库单编号
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutSerial(PutStorage storage);

    /**
     * 删除入库单
     * @param id 入库单id
     * @return
     */
    Integer deletePut(String id);

    /**
     * 更新入库单信息
     * @param storage 入库单对象
     * @return 影响的行数
     */
    int updatePutMessage(PutStorage storage);

    /**
     * 获取所有入库单
     * @return 入库单集合
     */
    List<PutStorage> getPutAll();

    /**
     * 搜索入库单
     * @param str 搜索字符串
     * @return 入库单集合
     */
    List<PutStorage> seekPutStorage(String str);

    /**
     * 判断入库单是否有出库记录
     * @param id 入库单id
     * @return 入库单id
     */
    String isOut(String id);

    /**
     * 获取审核人员最新的审核的单号
     * @param date 查询日期
     * @return 入库单号
     */
    String getNowPutSerial(String date);

    /**
     * 获取可以出库的入库单集合
     * @param proId 项目id
     * @return 入库单集合
     */
    List<PutStorage> getStorageByProject(String proId);


    void approve(FlowMessage msg);

    /**
     * 审核入库单
     * @param putStorage 入库单
     * @param tempState 状态
     * @param coding 审核人员编码
     * @param date 审核时间
     */
    void approve(PutStorage putStorage, int tempState, String coding, String date);

    void setProMaterial(HashMap<String, Procurement> proMap, StorageMaterial item, boolean approve);

    /**
     * 获取入库单总数
     * @param params {str:检索串，order:排序sql,start:开始时间，end,截止时间,storage:仓库筛选}
     * @return
     */
    int getPutCount(Map<String, Object> params);

    /**
     * 配合getPutStoragesListFast 方法使用，获取指定条件下数据总条目
     *
     * @param params
     * @return
     */
    int getPutStoragesListFastCount(Map<String, Object> params);

    /**
     * 选择入库单添加
     *
     * @param putStorage
     * @param staff
     * @return
     */
    int addPutStorageSelect(PutStorage putStorage, Staff staff);

    /**
     * 更新采购订单状态
     *
     * @param materialList 采购订单材料集合
     * @param procurement  采购订单对象
     * @param putSum       此次入库总数
     * @param state        是否更新
     * @return 3"部分入库，4:完全入库
     */
    int updateProState(List<ProMaterial> materialList, Procurement procurement, Integer state, double putSum);

    /**
     * 通过明细行id获取入库单主单据
     *
     * @param detailId 入库单材料明细主键
     * @return
     */
    PutStorage getPutStorageByDetailId(String detailId);

    List<PutStorage> getPutStorageListFast(Map<String, Object> params);

    List<PutStorage> getPutStorageList(Map<String, Object> params);

    /**
     * 获取入库单对象
     *
     * @param putSerial 入库单号
     * @return
     */
    PutStorage getPutBySerial(String putSerial);

    /**
     * 根据采购订单一键入库
     *
     * @param putStorage  {proId：不能为null,storage：不能为null}
     * @param procurement 采购订单
     * @param staff       入库人员
     * @return
     */
    int putStorageByPro(PutStorage putStorage, Procurement procurement, Staff staff);

    /**
     * 项目经理签名入库
     *
     * @param putStorage 入库单对象
     * @param attribute  入库人员
     * @return
     */
    int addPutSign(PutStorage putStorage, Staff attribute);

    /**
     * 查询项目入库金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getPutMoneyByProject(String projectId);

    /**
     * 查询未生成入库对账单列表
     *
     * @param startDate 入库开始日期
     * @param endDate   入库截止日期
     * @return
     */
    List<PutStorage> getNoDetailList(String startDate, String endDate);

    /**
     * 生成入库对账单
     *
     * @param storage
     * @return
     */
    boolean genPutDetail(PutStorage storage);

    /**
     * 回滚对账单
     *
     * @param storage
     * @return
     */
    boolean backPutDetail(PutStorage storage);

    /**
     * 审核(反审核)入库单
     *
     * @param storage 入库单对象
     * @param staff   操作人
     * @return
     */
    PutStorage approve(PutStorage storage, Staff staff) throws Exception;

    /**
     * 入库通知消息
     *
     * @param procurement 采购单
     * @param acceptStaff 接收人
     * @param msg         消息内容
     * @param sendStaff   发送用户
     * @return
     */
    int weiChartNotify(Procurement procurement, Staff acceptStaff, String msg, Staff sendStaff);

    /**
     * 删除入库大，自动回滚对账单
     *
     * @param putId 入库单id
     * @param staff 删除人员
     * @return
     */
    int delete(String putId, Staff staff);

    /**
     * 添加其他入库
     *
     * @param putStorage 入库单
     * @param staff      入库人员
     * @return
     */
    Integer addOtherStorage(PutStorage putStorage, Staff staff);

    /**
     * 查询数据总数
     * @param params
     * @return
     */
    Integer getPutStorageListCount(HashMap<String, Object> params);

    /**
     * 查询入库单列表
     * @param proId 采购订单id
     * @return
     */
    List<PutStorage> getPutStorageByProId(String proId);
}
