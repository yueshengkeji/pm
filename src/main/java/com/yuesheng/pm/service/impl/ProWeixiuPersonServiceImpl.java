package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProWeixiuPerson;
import com.yuesheng.pm.mapper.ProWeixiuPersonMapper;
import com.yuesheng.pm.service.ProWeixiuPersonService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * (ProWeixiuPerson)表服务实现类
 *
 * @author xiaoSong
 * @since 2023-02-08 16:06:30
 */
@Service("proWeixiuPersonService")
public class ProWeixiuPersonServiceImpl implements ProWeixiuPersonService {
    @Autowired
    private ProWeixiuPersonMapper proWeixiuPersonMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProWeixiuPerson queryById(String id) {
        return this.proWeixiuPersonMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWeixiuPerson> queryAllByLimit(int offset, int limit) {
        return this.proWeixiuPersonMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 实例对象
     */
    @Override
    public ProWeixiuPerson insert(ProWeixiuPerson proWeixiuPerson) {
        proWeixiuPerson.setDate(DateUtil.getDatetime());
        proWeixiuPerson.setId(UUID.randomUUID().toString());
        this.proWeixiuPersonMapper.insert(proWeixiuPerson);
        return proWeixiuPerson;
    }

    /**
     * 修改数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 实例对象
     */
    @Override
    public ProWeixiuPerson update(ProWeixiuPerson proWeixiuPerson) {
        this.proWeixiuPersonMapper.update(proWeixiuPerson);
        return this.queryById(proWeixiuPerson.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proWeixiuPersonMapper.deleteById(id) > 0;
    }

    @Override
    public ProWeixiuPerson getByProjectName(String projectName) {
        ProWeixiuPerson proWeixiuPerson = new ProWeixiuPerson();
        proWeixiuPerson.setProjectName(projectName);
        PageHelper.startPage(1, 1);
        List<ProWeixiuPerson> personList = proWeixiuPersonMapper.queryAll(proWeixiuPerson);
        if (personList.size() > 0) {
            return personList.get(0);
        }
        return null;
    }

    @Override
    public List<ProWeixiuPerson> queryAll(ProWeixiuPerson pwp) {
        return proWeixiuPersonMapper.queryAll(pwp);
    }
}