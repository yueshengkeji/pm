package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProMaterialHistory;
import com.yuesheng.pm.mapper.ProMaterialHistoryMapper;
import com.yuesheng.pm.service.ProMaterialHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 项目耗材记录表(ProMaterialHistory)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-06-18 15:02:14
 */
@Service("proMaterialHistoryService")
public class ProMaterialHistoryServiceImpl implements ProMaterialHistoryService {
    @Autowired
    private ProMaterialHistoryMapper proMaterialHistoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProMaterialHistory queryById(Integer id) {
        return this.proMaterialHistoryMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProMaterialHistory> queryAllByLimit(int offset, int limit) {
        return this.proMaterialHistoryMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proMaterialHistory 实例对象
     * @return 实例对象
     */
    @Override
    public ProMaterialHistory insert(ProMaterialHistory proMaterialHistory) {
        ProMaterialHistory history = extracted(proMaterialHistory);
        if(Objects.isNull(history)){
            this.proMaterialHistoryMapper.insert(proMaterialHistory);
        }else{
            proMaterialHistory.setId(history.getId());
            update(proMaterialHistory);
        }
        return proMaterialHistory;
    }

    private ProMaterialHistory extracted(ProMaterialHistory proMaterialHistory) {
        ProMaterialHistory query = new ProMaterialHistory() ;
        query.setMaterialId(proMaterialHistory.getMaterialId());
        query.setProjectId(proMaterialHistory.getProjectId());
        List<ProMaterialHistory> histories = queryAll(query);
        if(histories.isEmpty()){
            return null;
        }else{
            return histories.get(0);
        }
    }

    /**
     * 修改数据
     *
     * @param proMaterialHistory 实例对象
     * @return 实例对象
     */
    @Override
    public ProMaterialHistory update(ProMaterialHistory proMaterialHistory) {
        this.proMaterialHistoryMapper.update(proMaterialHistory);
        return this.queryById(Integer.valueOf(proMaterialHistory.getId()));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proMaterialHistoryMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProMaterialHistory> queryAll(ProMaterialHistory query) {
        return proMaterialHistoryMapper.queryAll(query);
    }
}