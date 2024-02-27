package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProWeixiu;
import com.yuesheng.pm.mapper.ProWeixiuMapper;
import com.yuesheng.pm.model.ProWeixiuGroup;
import com.yuesheng.pm.service.ProWeixiuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * (ProWeixiu)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-08-14 14:15:38
 */
@Service("proWeixiuService")
public class ProWeixiuServiceImpl implements ProWeixiuService {
    @Autowired
    private ProWeixiuMapper proWeixiuMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProWeixiu queryById(String id) {
        return this.proWeixiuMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWeixiu> queryAllByLimit(int offset, int limit) {
        return this.proWeixiuMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWeixiu 实例对象
     * @return 实例对象
     */
    @Override
    public ProWeixiu insert(ProWeixiu proWeixiu) {
        if (StringUtils.isBlank(proWeixiu.getFiles())) {
            proWeixiu.setFiles("");
        }
        this.proWeixiuMapper.insert(proWeixiu);
        return proWeixiu;
    }

    /**
     * 修改数据
     *
     * @param proWeixiu 实例对象
     * @return 实例对象
     */
    @Override
    public ProWeixiu update(ProWeixiu proWeixiu) {
        this.proWeixiuMapper.update(proWeixiu);
        return this.queryById(proWeixiu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proWeixiuMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProWeixiu> queryByParam(Map<String, Object> result) {
        return this.proWeixiuMapper.queryByParam(result);
    }

    @Override
    public Integer queryCountByParam(Map<String, Object> result) {
        return this.proWeixiuMapper.queryCountByParam(result);
    }

    @Override
    public List<ProWeixiuGroup> queryByProjectGroup(String startDate, String endDate) {
        return this.proWeixiuMapper.queryByProjectGroup(startDate, endDate);
    }

    @Override
    public Integer queryWeiXiuCountByDate(String startDate, String endDate) {
        return this.proWeixiuMapper.queryWeiXiuCountByDate(startDate, endDate);
    }

    @Override
    public List<ProWeixiu> selectNoReturn() {
        return this.proWeixiuMapper.selectNoReturn();
    }

    @Override
    public Integer selectNoReturnCount() {
        return this.proWeixiuMapper.selectNoReturnCount();
    }
}