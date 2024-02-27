package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProDisposeDetail;
import com.yuesheng.pm.mapper.ProDisposeDetailMapper;
import com.yuesheng.pm.service.ProDisposeDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 处置单明细(ProDisposeDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-06-28 10:39:51
 */
@Service("proDisposeDetailService")
public class ProDisposeDetailServiceImpl implements ProDisposeDetailService {
    @Autowired
    private ProDisposeDetailMapper proDisposeDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProDisposeDetail queryById(String id) {
        return this.proDisposeDetailMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProDisposeDetail> queryAllByLimit(int offset, int limit) {
        return this.proDisposeDetailMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proDisposeDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ProDisposeDetail insert(ProDisposeDetail proDisposeDetail) {
        if (StringUtils.isBlank(proDisposeDetail.getId())) {
            proDisposeDetail.setId(UUID.randomUUID().toString());
        }
        this.proDisposeDetailMapper.insert(proDisposeDetail);
        return proDisposeDetail;
    }

    /**
     * 修改数据
     *
     * @param proDisposeDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ProDisposeDetail update(ProDisposeDetail proDisposeDetail) {
        this.proDisposeDetailMapper.update(proDisposeDetail);
        return this.queryById(proDisposeDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proDisposeDetailMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProDisposeDetail> queryAll(ProDisposeDetail disposeDetail) {
        return this.proDisposeDetailMapper.queryAll(disposeDetail);
    }
}