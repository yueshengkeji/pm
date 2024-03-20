package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProBackMaster;
import com.yuesheng.pm.mapper.ProBackMasterMapper;
import com.yuesheng.pm.service.ProBackMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProBackMaster)表服务实现类
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
@Service("proBackMasterService")
public class ProBackMasterServiceImpl implements ProBackMasterService {
    @Autowired
    private ProBackMasterMapper proBackMasterMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProBackMaster queryById(String id) {
        return this.proBackMasterMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param proBackMaster 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProBackMaster> queryByPage(ProBackMaster proBackMaster) {
        return this.proBackMasterMapper.queryAllByLimit(proBackMaster);
    }

    /**
     * 新增数据
     *
     * @param proBackMaster 实例对象
     * @return 实例对象
     */
    @Override
    public ProBackMaster insert(ProBackMaster proBackMaster) {
        this.proBackMasterMapper.insert(proBackMaster);
        return proBackMaster;
    }

    /**
     * 修改数据
     *
     * @param proBackMaster 实例对象
     * @return 实例对象
     */
    @Override
    public ProBackMaster update(ProBackMaster proBackMaster) {
        this.proBackMasterMapper.update(proBackMaster);
        return this.queryById(proBackMaster.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proBackMasterMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProBackMaster> queryByBack(String backId) {
        return this.proBackMasterMapper.queryByBack(backId);
    }

    @Override
    public Double queryBackSum(String proRowId) {
        return this.proBackMasterMapper.queryBackSum(proRowId);
    }
}
