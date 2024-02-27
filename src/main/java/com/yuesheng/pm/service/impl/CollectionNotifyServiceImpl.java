package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.CollectionNotify;
import com.yuesheng.pm.mapper.CollectionNotifyMapper;
import com.yuesheng.pm.service.CollectionNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (CollectionNotify)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-03-25 09:45:18
 */
@Service("collectionNotifyService")
public class CollectionNotifyServiceImpl implements CollectionNotifyService {
    @Autowired
    private CollectionNotifyMapper collectionNotifyMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CollectionNotify queryById(Integer id) {
        return this.collectionNotifyMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CollectionNotify> queryAllByLimit(int offset, int limit) {
        return this.collectionNotifyMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param collectionNotify 实例对象
     * @return 实例对象
     */
    @Override
    public CollectionNotify insert(CollectionNotify collectionNotify) {
        this.collectionNotifyMapper.insert(collectionNotify);
        return collectionNotify;
    }

    /**
     * 修改数据
     *
     * @param collectionNotify 实例对象
     * @return 实例对象
     */
    @Override
    public CollectionNotify update(CollectionNotify collectionNotify) {
        this.collectionNotifyMapper.update(collectionNotify);
        return this.queryById(collectionNotify.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.collectionNotifyMapper.deleteById(id) > 0;
    }

    @Override
    public CollectionNotify selectByCollect(CollectionNotify query) {
        PageHelper.startPage(1, 1, "notify_date desc");
        List<CollectionNotify> notifies = this.collectionNotifyMapper.queryAll(query);
        if (notifies.isEmpty()) {
            return null;
        } else {
            return notifies.get(0);
        }

    }
}