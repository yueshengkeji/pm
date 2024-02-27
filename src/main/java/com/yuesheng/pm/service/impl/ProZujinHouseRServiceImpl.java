package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProZujinHouseR;
import com.yuesheng.pm.mapper.ProZujinHouseRMapper;
import com.yuesheng.pm.service.ProZujinHouseRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProZujinHouseR)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-07-12 09:29:58
 */
@Service("proZujinHouseRService")
public class ProZujinHouseRServiceImpl implements ProZujinHouseRService {
    @Autowired
    private ProZujinHouseRMapper proZujinHouseRMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProZujinHouseR queryById(Integer id) {
        return this.proZujinHouseRMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProZujinHouseR> queryAllByLimit(int offset, int limit) {
        return this.proZujinHouseRMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proZujinHouseR 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinHouseR insert(ProZujinHouseR proZujinHouseR) {
        this.proZujinHouseRMapper.insert(proZujinHouseR);
        return proZujinHouseR;
    }

    /**
     * 修改数据
     *
     * @param proZujinHouseR 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinHouseR update(ProZujinHouseR proZujinHouseR) {
        this.proZujinHouseRMapper.update(proZujinHouseR);
        return this.queryById(proZujinHouseR.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proZujinHouseRMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProZujinHouseR> queryAll(ProZujinHouseR query) {
        return this.proZujinHouseRMapper.queryAll(query);
    }

    @Override
    public int deleteByZujinId(Integer zujinId) {
        return this.proZujinHouseRMapper.deleteByZujinId(zujinId);
    }

    @Override
    public int updateType(ProZujinHouseR houseR) {
        return this.proZujinHouseRMapper.updateType(houseR);
    }
}