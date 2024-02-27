package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProNotifyType;
import com.yuesheng.pm.mapper.ProNotifyTypeMapper;
import com.yuesheng.pm.service.ProNotifyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProNotifyType)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-10-11 15:11:31
 */
@Service("proNotifyTypeService")
public class ProNotifyTypeServiceImpl implements ProNotifyTypeService {
    @Autowired
    private ProNotifyTypeMapper proNotifyTypeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    @Override
    public ProNotifyType queryById(String staffId) {
        return this.proNotifyTypeMapper.queryById(staffId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProNotifyType> queryAllByLimit(int offset, int limit) {
        return this.proNotifyTypeMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proNotifyType 实例对象
     * @return 实例对象
     */
    @Override
    public ProNotifyType insert(ProNotifyType proNotifyType) {
        this.proNotifyTypeMapper.insert(proNotifyType);
        return proNotifyType;
    }

    /**
     * 修改数据
     *
     * @param proNotifyType 实例对象
     * @return 实例对象
     */
    @Override
    public ProNotifyType update(ProNotifyType proNotifyType) {
        this.proNotifyTypeMapper.update(proNotifyType);
        return this.queryById(proNotifyType.getStaffId());
    }

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String staffId) {
        return this.proNotifyTypeMapper.deleteById(staffId) > 0;
    }
}