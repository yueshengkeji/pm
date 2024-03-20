package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProBack;

import java.util.List;

/**
 * (ProBack)表服务接口
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
public interface ProBackService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBack queryById(String id);

    /**
     * 分页查询
     *
     * @param proBack 筛选条件
     * @return 查询结果
     */
    List<ProBack> queryByPage(ProBack proBack);

    /**
     * 新增数据
     *
     * @param proBack 实例对象
     * @return 实例对象
     */
    ProBack insert(ProBack proBack);

    /**
     * 修改数据
     *
     * @param proBack 实例对象
     * @return 实例对象
     */
    ProBack update(ProBack proBack);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
