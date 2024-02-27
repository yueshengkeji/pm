package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.PerformanceMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 绩效考核表(Performance)表服务实现类
 *
 * @author xiaosong
 * @since 2023-10-17 09:02:39
 */
@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService, FrameStateCheckService, FileService {
    @Autowired
    private PerformanceMapper performanceMapper;
    @Autowired
    private ProWorkLogService logService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private DutyService dutyService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Performance queryById(String id) {
        Performance per = this.performanceMapper.queryById(id);
        if (!Objects.isNull(per)) {
            per.setWorkLogs(logService.queryByPerId(id));
            Staff s = staffService.getStaffById(per.getStaffId());
            if(!Objects.isNull(s)){
                s.setDuty(dutyService.getDutyByStaffId(s.getId()));
                per.setStaff(s);
            }
        }
        return per;
    }

    @Override
    public Performance getPerByScoreId(String id) {
        Performance per = this.performanceMapper.queryById(id);
        if (!Objects.isNull(per)) {
            per.setWorkLogs(logService.queryByScoreId(id));
            Staff s = staffService.getStaffById(per.getStaffId());
            if(!Objects.isNull(s)){
                s.setDuty(dutyService.getDutyByStaffId(s.getId()));
                per.setStaff(s);
            }
        }
        return per;
    }

    /**
     * 分页查询
     *
     * @param performance 筛选条件
     * @return 查询结果
     */
    @Override
    public List<Performance> queryByPage(Performance performance) {
        return this.performanceMapper.queryAllByLimit(performance);
    }

    /**
     * 新增数据
     *
     * @param performance 实例对象
     * @return 实例对象
     */
    @Override
    public Performance insert(Performance performance) {
        performance.setId(UUID.randomUUID().toString());
        performance.setDatetime(DateUtil.getDatetime());
        this.performanceMapper.insert(performance);
        List<ProWorkLog> list = performance.getWorkLogs();
        if(!Objects.isNull(list)){
            if (performance.getTempScoreFlag() == 1){
                list.forEach(item -> {
                    if (!Objects.isNull(item)) {
                        logService.updateScoreId(item.getId(), performance.getId());
                    }
                });
            }else {
                list.forEach(item -> {
                    if (!Objects.isNull(item)) {
                        logService.updatePerId(item.getId(), performance.getId());
                    }
                });
            }
        }
        return performance;
    }

    /**
     * 修改数据
     *
     * @param performance 实例对象
     * @return 实例对象
     */
    @Override
    public Performance update(Performance performance) {
        this.performanceMapper.update(performance);
        return this.queryById(performance.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.performanceMapper.deleteById(id) > 0;
    }

    @Override
    public void deleteFlowHandler(String id) {
        logService.updateEditByPer(id,0);
    }

    @Override
    public void updateEditHandler(String id) {
        logService.updateEditByPer(id,1);
    }

    @Override
    public void breakFlowHandler(String id) {
        logService.updateEditByPer(id,0);
        logService.clearPerId(id);
    }

    @Override
    public void oaSuccessChange(FlowMessage message) {
        Performance p = new Performance();
        p.setFlag(1);
        p.setId(message.getFrameId());
        p.setSignStaffId(message.getStaffId());
        this.performanceMapper.update(p);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        return new ArrayList<>();
    }

}
