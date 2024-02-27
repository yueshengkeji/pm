package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWorkLogFile;

import java.util.List;

/**
 * 工作日记附件关系表(ProWorkLogFile)表服务接口
 *
 * @author xiaoSong
 * @since 2021-07-03 10:28:17
 */
public interface ProWorkLogFileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<ProWorkLogFile> queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkLogFile> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWorkLogFile 实例对象
     * @return 实例对象
     */
    ProWorkLogFile insert(ProWorkLogFile proWorkLogFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}