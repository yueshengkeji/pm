package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SystemConfig;

import java.util.List;

/**
 * 系统配置主表(SystemConfig)表服务接口
 *
 * @author xiaoSong
 * @since 2022-07-07 14:40:20
 */
public interface SystemConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemConfig queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SystemConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    SystemConfig insert(SystemConfig systemConfig);

    /**
     * 修改数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    SystemConfig update(SystemConfig systemConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询所有配置
     *
     * @return
     */
    List<SystemConfig> queryAll();

    /**
     * 通过参数编码获取参数对象
     *
     * @param coding 参数编码
     * @return
     */
    SystemConfig queryByCoding(String coding);

    /**
     * 获取系统配置信息
     *
     * @param config 查询参数
     * @return
     */
    List<SystemConfig> queryByParam(SystemConfig config);

    /**
     * 获取配置明细
     *
     * @param coding 配置编码
     * @return
     */
    List<SystemConfig> queryDetailByParent(Integer coding);

    /**
     * 获取配置参数value
     *
     * @param coding       配置参数coding
     * @param parentCoding 上级参数coding
     * @return
     */
    String getValueByParentCoding(String coding, String parentCoding);
}