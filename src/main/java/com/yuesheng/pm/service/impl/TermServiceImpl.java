package com.yuesheng.pm.service.impl;


import com.yuesheng.pm.entity.Term;
import com.yuesheng.pm.mapper.TermMapper;
import com.yuesheng.pm.service.ConcatBillService;
import com.yuesheng.pm.service.TermService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * (Term)表服务实现类
 *
 * @author xiaosong
 * @since 2024-02-29 15:33:29
 */
@Service("termService")
public class TermServiceImpl implements TermService {
    @Autowired
    private TermMapper termMapper;
    @Autowired
    @Lazy
    private ConcatBillService concatBillService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Term queryById(String id) {
        return this.termMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param term 筛选条件
     * @return 查询结果
     */
    @Override
    public List<Term> queryByPage(Term term) {
        return this.termMapper.queryAllByLimit(term);
    }

    /**
     * 新增数据
     *
     * @param term 实例对象
     * @return 实例对象
     */
    @Override
    public Term insert(Term term) {
        term.setId(UUID.randomUUID().toString());
        this.termMapper.insert(term);

        concatBillService.genByTerm(term);
        return term;
    }

    /**
     * 修改数据
     *
     * @param term 实例对象
     * @return 实例对象
     */
    @Override
    public Term update(Term term) {
        this.termMapper.update(term);
        concatBillService.deleteBySourceId(term.getId());
        concatBillService.genByTerm(term);
        return this.queryById(term.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        concatBillService.deleteBySourceId(id);
        return this.termMapper.deleteById(id) > 0;
    }

    @Override
    public void genAllTerm() {
        //查询上月有效月度账单条款
        Date startDate = DateUtil.getMonthStartTime();
        List<Term> terms = termMapper.queryByDate(startDate, startDate,"month");
        terms.forEach(item->{
            concatBillService.genByTerm(item);
        });

        //查询上月有效扣点条款
        terms = termMapper.queryByDate(startDate, startDate,"final");
        terms.forEach(item->{
            concatBillService.genByTerm(item);
        });

        //查询下季度有效条款
        startDate = DateUtil.getNextQuarterStartTime();
        terms = termMapper.queryByDate(startDate,startDate,"quarter");
        terms.forEach(item->{
            concatBillService.genByTerm(item);
        });
    }

    @Override
    public int deleteByConcat(String concatId) {
        return termMapper.deleteByConcat(concatId);
    }
}
