package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProBid;
import com.yuesheng.pm.model.ProBidCount;

import java.util.List;

/**
 * 投标盖章申请信息表(ProBid)表服务接口
 *
 * @author xiaoSong
 * @since 2022-05-16 14:41:21
 */
public interface ProBidService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBid queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProBid> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proBid 实例对象
     * @return 实例对象
     */
    ProBid insert(ProBid proBid);

    /**
     * 修改数据
     *
     * @param proBid 实例对象
     * @return 实例对象
     */
    ProBid update(ProBid proBid);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 获取投标信息列表
     * @param bid
     * @return
     */
    List<ProBid> query(ProBid bid);

    /**
     * 更细单据状态
     * @param id 主键
     * @param state 状态码
     * @return
     */
    int updateState(String id, Integer state);

    /**
     * 更新单据状态为已审核
     * @param id 主键
     * @return
     */
    int updateState(String id);

    /**
     * 办文转为投标数据
     * @param begin 开始日期
     * @param end 截止日期
     * @return
     */
    int parse(String begin, String end);

    /**
     * 查询中标统计信息
     * @return
     */
    ProBidCount queryCountInfo(ProBidCount count);
}