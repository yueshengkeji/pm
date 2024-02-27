package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.entity.PutStorage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-23 入库单mapper sdpm026.
 *
 * @author XiaoSong
 * @date 2016/08/23
 */
@Mapper
public interface PutStorageMapper {
    /**
     * 添加入库单到数据库
     *
     * @param storage 入库单对象
     * @return 影响的行数
     */
    Integer addStorage(PutStorage storage);

    /**
     * 根据时间获取入库单
     *
     * @param params 开始时间和结束时间
     * @return 入库单集合
     */
    List<PutStorage> getStorage(String params);

    /**
     * 更新入库单审核状态
     *
     * @param params {state:状态，id:入库单id，person:审核人员编号，date:审核日期}
     * @return 影响的行数
     */
    Integer updateApprove(Map<String, Object> params);

    /**
     * 通过入库单id获取入库单对象
     *
     * @param id 入库单id
     * @return 入库单对象
     */
    PutStorage getStorageById(String id);

    /**
     * 通过指定的参数获取行数
     *
     * @param params 开始时间和结束时间
     * @return 行数
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 根据指定的参数获取未出库的入库单集合
     *
     * @param params 开始时间和结束时间
     * @return 入库单集合
     */
    List<PutStorage> getPutStorages(Map<String, Object> params);


    /**
     * 更新入库单运杂费
     *
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutMixMoney(PutStorage storage);


    /**
     * 更新入库单税率
     *
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutTax(PutStorage storage);


    /**
     * 更新入库单税率
     *
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutRemark(PutStorage storage);


    /**
     * 更新入库时间
     *
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutDate(PutStorage storage);


    /**
     * 更新入库单编号
     *
     * @param storage 入库单对象
     * @return
     */
    Integer updatePutSerial(PutStorage storage);

    /**
     * 删除入库单
     *
     * @param id 入库单id
     * @return
     */
    Integer deletePut(String id);

    /**
     * 更新入库单信息
     *
     * @param storage 入库单对象
     * @return 影响的行数
     */
    int updatePutMessage(PutStorage storage);

    /**
     * 获取所有入库单集合
     *
     * @return
     */
    List<PutStorage> getPutAll();

    /**
     * 搜索入库单
     *
     * @param str 入库单编号字符串
     * @return 入库单集合
     */
    List<PutStorage> seekPutStorage(String str);

    /**
     * 查看该入库单是否有过出库记录
     *
     * @param id 入库单id
     * @return 入库单id
     */
    String isOut(String id);

    /**
     * 获取审核人员最新的单号
     *
     * @param date 查询日期
     * @return 入库单号
     */
    String getNowPutSerial(@Param("date") String date);

    /**
     * 获取可出库的入库单集合
     *
     * @param proId 项目id
     * @return
     */
    List<PutStorage> getStorageByProject(@Param("proId") String proId);

    /**
     * 参见outStorageService.getPutCount();
     *
     * @param params
     * @return
     */
    int getPutCount(Map<String, Object> params);

    /**
     * 配合 getPutStorageListFast()方法获取指定条件数据总条目
     *
     * @param params
     * @return
     */
    int getPutStoragesListFastCount(Map<String, Object> params);

    /**
     * 通过明细行id获取入库单主单据
     *
     * @param detailId 入库单材料明细主键
     * @return
     */
    PutStorage getPutStorageByDetailId(@Param("detailId") String detailId);

    List<PutStorage> getPutStorageListFast(Map<String, Object> params);

    List<PutStorage> getPutStorageList(Map<String, Object> params);

    /**
     * 获取入库单对象
     *
     * @param putSerial 入库单号
     * @return
     */
    PutStorage getPutStorageBySerial(@Param("putSerial") String putSerial);

    /**
     * 查询未生成入库对账单列表
     *
     * @param startDate 入库开始日期
     * @param endDate   入库截止日期
     * @return
     */
    List<PutStorage> getNoDetailList(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 更新入库单信息
     *
     * @param storage
     * @return
     */
    int updateSimpleInfo(PutStorage storage);

    /**
     * 查询入库单所属项目
     *
     * @param putId 入库单id
     * @return
     */
    Project getProjectIdByMater(@Param("putId") String putId);

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
    List<PutStorage> getPutStorageByProId(@Param("proId") String proId);
}
