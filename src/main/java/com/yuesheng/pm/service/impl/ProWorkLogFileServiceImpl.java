package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProWorkLogFile;
import com.yuesheng.pm.mapper.ProWorkLogFileMapper;
import com.yuesheng.pm.service.ProWorkLogFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作日记附件关系表(ProWorkLogFile)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-07-03 10:28:17
 */
@Service("proWorkLogFileService")
public class ProWorkLogFileServiceImpl implements ProWorkLogFileService {
    @Autowired
    private ProWorkLogFileMapper proWorkLogFileMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<ProWorkLogFile> queryById(Long id) {
        return this.proWorkLogFileMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWorkLogFile> queryAllByLimit(int offset, int limit) {
        return this.proWorkLogFileMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWorkLogFile 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkLogFile insert(ProWorkLogFile proWorkLogFile) {
        this.proWorkLogFileMapper.insert(proWorkLogFile);
        return proWorkLogFile;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.proWorkLogFileMapper.deleteById(id) > 0;
    }
}