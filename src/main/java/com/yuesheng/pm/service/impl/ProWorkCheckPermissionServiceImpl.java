package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProWorkCheckPermission;
import com.yuesheng.pm.mapper.ProWorkCheckPermissionMapper;
import com.yuesheng.pm.service.ProWorkCheckPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProWorkCheckPermission)表服务实现类
 *
 * @author xiaoSong
 * @since 2023-03-06 08:51:46
 */
@Service("proWorkCheckPermissionService")
public class ProWorkCheckPermissionServiceImpl implements ProWorkCheckPermissionService {
    @Autowired
    private ProWorkCheckPermissionMapper proWorkCheckPermissionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    @Override
    public List<ProWorkCheckPermission> queryById(String staffId) {
        return this.proWorkCheckPermissionMapper.queryById(staffId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWorkCheckPermission> queryAllByLimit(int offset, int limit) {
        return this.proWorkCheckPermissionMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheckPermission insert(ProWorkCheckPermission proWorkCheckPermission) {
        this.proWorkCheckPermissionMapper.insert(proWorkCheckPermission);
        return proWorkCheckPermission;
    }

    /**
     * 修改数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheckPermission update(ProWorkCheckPermission proWorkCheckPermission) {
        this.proWorkCheckPermissionMapper.update(proWorkCheckPermission);
        return proWorkCheckPermission;
    }

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String staffId) {
        return this.proWorkCheckPermissionMapper.deleteById(staffId) > 0;
    }
}