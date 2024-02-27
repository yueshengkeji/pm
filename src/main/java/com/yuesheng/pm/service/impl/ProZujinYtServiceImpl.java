package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProZujinYt;
import com.yuesheng.pm.mapper.ProZujinYtMapper;
import com.yuesheng.pm.service.ProZujinYtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProZujinYt)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Service("proZujinYtService")
public class ProZujinYtServiceImpl implements ProZujinYtService {
    @Autowired
    private ProZujinYtMapper proZujinYtMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProZujinYt queryById(Integer id) {
        return this.proZujinYtMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProZujinYt> queryAllByLimit(int offset, int limit) {
        return this.proZujinYtMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proZujinYt 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinYt insert(ProZujinYt proZujinYt) {
        ProZujinYt yt = new ProZujinYt();
        yt.setName(proZujinYt.getName());
        List<ProZujinYt> ytList = queryAll(yt);
        if (ytList.isEmpty()) {
            this.proZujinYtMapper.insert(proZujinYt);
        }
        return proZujinYt;
    }

    /**
     * 修改数据
     *
     * @param proZujinYt 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinYt update(ProZujinYt proZujinYt) {
        this.proZujinYtMapper.update(proZujinYt);
        return this.queryById(proZujinYt.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proZujinYtMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProZujinYt> queryAll(ProZujinYt yt) {
        return this.proZujinYtMapper.queryAll(yt);
    }
}