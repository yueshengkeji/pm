package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProBackMaster;

import java.util.List;

/**
 * (ProBackMaster)表服务接口
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
public interface ProBackMasterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBackMaster queryById(String id);

    /**
     * 分页查询
     *
     * @param proBackMaster 筛选条件
     * @return 查询结果
     */
    List<ProBackMaster> queryByPage(ProBackMaster proBackMaster);

    /**
     * 新增数据
     *
     * @param proBackMaster 实例对象
     * @return 实例对象
     */
    ProBackMaster insert(ProBackMaster proBackMaster);

    /**
     * 修改数据
     *
     * @param proBackMaster 实例对象
     * @return 实例对象
     */
    ProBackMaster update(ProBackMaster proBackMaster);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询采购退库清单
     * @param backId 退库主单据id
     * @return
     */
    List<ProBackMaster> queryByBack(String backId);

    /**
     * 查询退库总数
     * @param proRowId
     * @return
     */
    Double queryBackSum(String proRowId);
}
