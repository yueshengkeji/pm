package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.model.OutCarHistoryGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (OutCarHistory)表服务接口
 *
 * @author xiaosong
 * @since 2023-03-29 14:54:25
 */
public interface OutCarHistoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarHistory queryById(String id);

    /**
     * 分页查询
     *
     * @param outCarHistory 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<OutCarHistory> queryByPage(OutCarHistory outCarHistory, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param outCarHistory 实例对象
     * @return 实例对象
     */
    OutCarHistory insert(OutCarHistory outCarHistory);

    /**
     * 修改数据
     *
     * @param outCarHistory 实例对象
     * @return 实例对象
     */
    OutCarHistory update(OutCarHistory outCarHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询所有记录
     * @param q
     * @return
     */
    List<OutCarHistory> queryAll(OutCarHistory q);

    /**
     * 通过项目分组查询
     * @param q
     * @return
     */
    List<OutCarHistoryGroup> queryGroupProject(OutCarHistory q);

    /**
     * 查询空项目的记录
     * @param q
     * @return
     */
    List<OutCarHistory> queryByNoProject(OutCarHistory q);

    void recoverImgToSmartKm(String startTime,String endTime);

    /**
     * 识别并更新用车记录拍照的里程数据
     * @param och
     */
    void updateSmartKm(OutCarHistory och);
}
