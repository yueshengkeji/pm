package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProBzj;
import com.yuesheng.pm.mapper.ProBzjMapper;
import com.yuesheng.pm.service.ProBzjService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProBzj)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-08-31 09:32:20
 */
@Service("proBzjService")
public class ProBzjServiceImpl implements ProBzjService {
    @Autowired
    private ProBzjMapper proBzjMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProBzj queryById(Integer id) {
        return this.proBzjMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProBzj> queryAllByLimit(int offset, int limit) {
        return this.proBzjMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    @Override
    public ProBzj insert(ProBzj proBzj) {
        if (proBzj.getDatetime() == null) {
            proBzj.setDatetime(DateUtil.format(DateUtil.getNowDate()));
        }
        this.proBzjMapper.insert(proBzj);
        return proBzj;
    }

    /**
     * 修改数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    @Override
    public ProBzj update(ProBzj proBzj) {
        this.proBzjMapper.update(proBzj);
        return this.queryById(Integer.valueOf(proBzj.getId()));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proBzjMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProBzj> queryAll(ProBzj bzj) {
        return this.proBzjMapper.queryAll(bzj);
    }
}