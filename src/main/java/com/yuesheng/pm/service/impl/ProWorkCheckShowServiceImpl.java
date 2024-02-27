package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProWorkCheckShow;
import com.yuesheng.pm.mapper.ProWorkCheckShowMapper;
import com.yuesheng.pm.service.ProWorkCheckShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 可见的考勤人员名单(ProWorkCheckShow)表服务实现类
 *
 * @author makejava
 * @since 2020-05-11 15:54:14
 */
@Service("proWorkCheckShowService")
public class ProWorkCheckShowServiceImpl implements ProWorkCheckShowService {
    @Autowired
    private ProWorkCheckShowMapper proWorkCheckShowDao;

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    @Override
    public ProWorkCheckShow queryById(String staffId) {
        return this.proWorkCheckShowDao.queryById(staffId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWorkCheckShow> queryAllByLimit(int offset, int limit) {
        return this.proWorkCheckShowDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheckShow insert(ProWorkCheckShow proWorkCheckShow) {
        this.proWorkCheckShowDao.insert(proWorkCheckShow);
        return proWorkCheckShow;
    }

    /**
     * 修改数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheckShow update(ProWorkCheckShow proWorkCheckShow) {
        this.proWorkCheckShowDao.update(proWorkCheckShow);
        return this.queryById(proWorkCheckShow.getStaffId());
    }

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String staffId) {
        return this.proWorkCheckShowDao.deleteById(staffId) > 0;
    }
}