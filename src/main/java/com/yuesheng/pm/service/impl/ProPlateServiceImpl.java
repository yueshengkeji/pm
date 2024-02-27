package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProPlate;
import com.yuesheng.pm.mapper.ProPlateMapper;
import com.yuesheng.pm.service.ProPlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProPlate)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-08-06 15:44:35
 */
@Service("proPlateService")
public class ProPlateServiceImpl implements ProPlateService {
    @Autowired
    private ProPlateMapper proPlateMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProPlate queryById(String id) {
        return this.proPlateMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProPlate> queryAllByLimit(int offset, int limit) {
        return this.proPlateMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proPlate 实例对象
     * @return 实例对象
     */
    @Override
    public ProPlate insert(ProPlate proPlate) {
        this.proPlateMapper.insert(proPlate);
        return proPlate;
    }

    /**
     * 修改数据
     *
     * @param proPlate 实例对象
     * @return 实例对象
     */
    @Override
    public ProPlate update(ProPlate proPlate) {
        this.proPlateMapper.update(proPlate);
        return this.queryById(proPlate.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proPlateMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProPlate> queryAll(ProPlate plate) {
        return this.proPlateMapper.queryAll(plate);
    }

    @Override
    public Integer deleteByStaffId(String staffId) {
        return this.proPlateMapper.deleteByStaffId(staffId);
    }

    @Override
    public int deleteByPlate(String plate) {
        return this.proPlateMapper.deleteByPlate(plate);
    }
}