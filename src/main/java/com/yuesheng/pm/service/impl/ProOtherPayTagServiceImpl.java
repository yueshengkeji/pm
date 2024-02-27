package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProOtherPayTag;
import com.yuesheng.pm.mapper.ProOtherPayTagMapper;
import com.yuesheng.pm.service.ProOtherPayTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProOtherPayTag)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:34
 */
@Service("proOtherPayTagService")
public class ProOtherPayTagServiceImpl implements ProOtherPayTagService {
    @Autowired
    private ProOtherPayTagMapper proOtherPayTagMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProOtherPayTag queryById(String id) {
        return this.proOtherPayTagMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProOtherPayTag> queryAllByLimit(int offset, int limit) {
        return this.proOtherPayTagMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proOtherPayTag 实例对象
     * @return 实例对象
     */
    @Override
    public ProOtherPayTag insert(ProOtherPayTag proOtherPayTag) {
        this.proOtherPayTagMapper.insert(proOtherPayTag);
        return proOtherPayTag;
    }

    /**
     * 修改数据
     *
     * @param proOtherPayTag 实例对象
     * @return 实例对象
     */
    @Override
    public ProOtherPayTag update(ProOtherPayTag proOtherPayTag) {
        this.proOtherPayTagMapper.update(proOtherPayTag);
        return this.queryById(proOtherPayTag.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proOtherPayTagMapper.deleteById(id) > 0;
    }
}