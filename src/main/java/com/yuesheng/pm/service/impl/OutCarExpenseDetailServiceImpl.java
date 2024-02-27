package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.OutCarExpenseDetail;
import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.mapper.OutCarExpenseDetailMapper;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.OutCarExpenseCount;
import com.yuesheng.pm.service.OutCarExpenseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * (OutCarExpenseDetail)表服务实现类
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:11
 */
@Service("outCarExpenseDetailService")
public class OutCarExpenseDetailServiceImpl implements OutCarExpenseDetailService {
    @Autowired
    private OutCarExpenseDetailMapper outCarExpenseDetailMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutCarExpenseDetail queryById(String id) {
        return this.outCarExpenseDetailMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param outCarExpenseDetail 筛选条件
     * @return 查询结果
     */
    @Override
    public List<OutCarExpenseDetail> queryByPage(OutCarExpenseDetail outCarExpenseDetail) {
        return outCarExpenseDetailMapper.queryAllByLimit(outCarExpenseDetail);
    }

    /**
     * 新增数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarExpenseDetail insert(OutCarExpenseDetail outCarExpenseDetail) {
        this.outCarExpenseDetailMapper.insert(outCarExpenseDetail);
        return outCarExpenseDetail;
    }

    /**
     * 修改数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarExpenseDetail update(OutCarExpenseDetail outCarExpenseDetail) {
        this.outCarExpenseDetailMapper.update(outCarExpenseDetail);
        return this.queryById(outCarExpenseDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.outCarExpenseDetailMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteByExpense(String expenseId) {
        return outCarExpenseDetailMapper.deleteByExpense(expenseId) > 0;
    }

    @Override
    public List<OutCarExpenseDetail> insertBatch(List<OutCarExpenseDetail> details,String expenseId){
        details.forEach(item->{
            item.setOutCarExpenseId(expenseId);
            item.setId(UUID.randomUUID().toString());
        });
        outCarExpenseDetailMapper.insertBatch(details);
        return details;
    }

    @Override
    public OutCarExpenseCount queryMoneyByProject(String projectId) {
        return outCarExpenseDetailMapper.queryMoneyByProject(projectId);
    }

    @Override
    public List<OutCarHistory> queryByParam(HashMap<String, Object> param) {
        return outCarExpenseDetailMapper.queryByParam(param);
    }

    @Override
    public DateGroupModel queryMoney(HashMap<String, Object> param) {
        return outCarExpenseDetailMapper.queryMoney(param);
    }
}
