package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProPutSign;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * 入库签字表(ProPutSign)表服务接口
 *
 * @author makejava
 * @since 2020-06-05 11:07:57
 */
public interface ProPutSignService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProPutSign queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProPutSign> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proPutSign 实例对象
     * @return 实例对象
     */
    ProPutSign insert(ProPutSign proPutSign);

    /**
     * 修改数据
     *
     * @param proPutSign 实例对象
     * @return 实例对象
     */
    ProPutSign update(ProPutSign proPutSign);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 根据对象属性查询
     *
     * @param proPutSign
     * @return
     */
    ProPutSign queryByParam(ProPutSign proPutSign);

    /**
     * 通过参数查询签字单集合
     *
     * @param pps
     * @return
     */
    List<ProPutSign> queryListByParam(ProPutSign pps);

    /**
     * 通过入库单明细行id查询签名列表
     *
     * @param putMaterIds
     * @return
     */
    Map<String, Object> queryByMaterIds(String... putMaterIds);

    /**
     * 确认入库签字
     * @param proPutSign
     * @return
     */
    Map<String, Object> updatePutSign(ProPutSign proPutSign, Staff staff) throws Exception;

    /**
     * 确认出库签字
     * @param sign 签字对象
     * @param staff 签字人员信息
     * @return
     */
    ProPutSign updateOutSign(ProPutSign sign, Staff staff) throws Exception;
}