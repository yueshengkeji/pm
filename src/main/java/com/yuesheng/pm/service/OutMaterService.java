package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.MaterOut;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.exception.StorageException;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.model.OutMaterData;
import com.yuesheng.pm.model.ProjectMaterial;

import java.util.List;
import java.util.Map;

/**
 * Created by 宋正根 on 2016/8/30 出库单服务接口.
 *
 */
public interface OutMaterService {
    /**
     * 获取最新添加的出库单
     *
     * @param staffName 职员名称
     * @return 出库单对象
     */
    MaterOut getNowOutMater(String staffName, String data);

    /**
     * 添加入库单到数据库
     *
     * @param materOut 入库单对象
     * @return 影响的行数
     */
    Integer addOutMater(MaterOut materOut);

    /**
     * 获取指定时间内的出库单集合
     *
     * @param params 开始和结束时间{start,end}
     * @return 出库单集合
     */
    List<MaterOut> getOutMaterList(Map<String, Object> params);

    /**
     * 获取领用最多的人员集合
     *
     * @return 领用人id集合
     */
    List<Count> getCountForStaff();

    /**
     * 更新出库单状态
     */
    void updateState(MaterOut materOut);

    /**
     * 通过出库单id获取出库单对象
     *
     * @param outId 出库单id
     * @return 出库单对象
     */
    MaterOut getOutMaterById(String outId);


    /**
     * 通过项目id获取出库单集合
     *
     * @param projectId 项目id
     * @param searchStr 检索字符串
     * @return 出库单集合
     */
    List<MaterOut> getOutMaterByProjectId(String projectId, String searchStr);

    /**
     * 获取材料出库单集合
     *
     * @param materId 材料id
     * @param start   开始时间,结束时间，其他暂定
     * @return
     */
    List<MaterOut> getOutMaterByMaterId(String materId, String... start);

    /**
     * 更新出库单信息，可修改列为{出库单编号，出库日期，备注}
     *
     * @param materOut 出库单对象
     * @return
     */
    Integer updateMaterOut(MaterOut materOut);

    /**
     * 删除出库单和材料集合
     *
     * @param outId 出库单id
     * @return
     */
    void deleteOut(String outId);

    /**
     * 审核出库单 新
     *
     * @param out 出库单对象
     */
    void approveOutMater(MaterOut out) throws StorageException;

    /**
     * 获取出库单总数
     * @param params {start 开始时间
     *               end 结束时间
     *               type 类型{0=未审核，1=已审核}
     *               staffCoding:审核职员编号
     *               }
     * @return
     */
    int getOutSum(Map<String, Object> params);

    /**
     * 获取数据总大小
     * @param params {参见getOutMaterList}
     * @return
     */
    int getOutSumByParam(Map<String, Object> params);

    /**
     * 获取项目耗材最多的项目集合
     *
     * @param year  查询年份
     * @param month 查询月份
     * @param size  数据大小
     * @return
     */
    List<ProjectMaterial> getOutMaxByProject(String year, String month, Integer size, Integer number);


    /**
     * 获取领料最多的项目集合
     *
     * @param start  开始日期
     * @param end    截止日期
     * @param number 当前页码
     * @param size   数据大小
     * @return
     */
    List<ProjectMaterial> getOutMaxNumberByProject(String start, String end, Integer number, Integer size);

    /**
     * 获取项目领料金额
     *
     * @param param {start:开始日期,end:截止日期}
     * @return
     */
    List<OutMaterData> getOutMaterialMoney(Map<String, Object> param);

    /**
     * 查询材料出库记录
     *
     * @param param
     * @return
     */
    List<MaterUseHistory> getOutMaterHistory(Map<String, Object> param);

    /**
     * 获取项目出库金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getOutMaterialMoney(String projectId);

    /**
     * 添加出库单
     *
     * @param materOut  出库单对象
     * @param attribute 添加人员
     * @param isApprove 是否自动审核
     * @return
     */
    MaterOut addOutMater(MaterOut materOut, Staff attribute, int isApprove);

    /**
     * 审核出库单
     *
     * @param out   出库单
     * @param staff 审核人
     * @return
     */
    int approve(MaterOut out, Staff staff);

    /**
     * 查询出库单
     *
     * @param outNumber 出库单编号
     * @return
     */
    MaterOut getOutMaterByNumber(String outNumber);
}
