package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.FlowCount;
import com.yuesheng.pm.mapper.FlowCountMapper;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.service.FlowCountService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 客流统计(FlowCount)表服务实现类
 *
 * @author xiaosong
 * @since 2023-08-22 08:47:48
 */
@Service("flowCountService")
public class FlowCountServiceImpl implements FlowCountService {
    @Autowired
    private FlowCountMapper flowCountMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public FlowCount queryById(Integer id) {
        return this.flowCountMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param flowCount 筛选条件
     * @return 查询结果
     */
    @Override
    public List<FlowCount> queryByPage(FlowCount flowCount) {
        return this.flowCountMapper.queryAllByLimit(flowCount);
    }

    /**
     * 新增数据
     *
     * @param flowCount 实例对象
     * @return 实例对象
     */
    @Override
    public FlowCount insert(FlowCount flowCount) {
        flowCount.setDate(DateUtil.getDatetime());
        if(StringUtils.isNotBlank(flowCount.getCountDate())){
            try {
                flowCount.setWeek(DateUtil.getWeekdayDesc(DateUtil.parse(flowCount.getCountDate(),DateUtil.PATTERN_CLASSICAL_SIMPLE)));
            } catch (Exception e) {
                //ignore error
            }
        }
        this.flowCountMapper.insert(flowCount);
        return flowCount;
    }

    /**
     * 修改数据
     *
     * @param flowCount 实例对象
     * @return 实例对象
     */
    @Override
    public FlowCount update(FlowCount flowCount) {
        this.flowCountMapper.update(flowCount);
        return this.queryById(Integer.valueOf(flowCount.getId()));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.flowCountMapper.deleteById(id) > 0;
    }

    @Override
    public List<FlowCount> queryAll(FlowCount query) {
        return this.flowCountMapper.queryAllByLimit(query);
    }

    @Override
    public List<DateGroupModel> queryMoneyGroupFlowDate(HashMap<String, Object> param) {
        return this.flowCountMapper.queryMoneyGroupFlowDate(param);
    }
}
