package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.mapper.SystemLogMapper;
import com.yuesheng.pm.model.DateCount;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.service.SystemLogService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统日志(SystemLog)表服务实现类
 *
 * @author xiaoSong
 * @since 2020-11-04 16:36:58
 */
@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Autowired
    private SystemConfigService configService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SystemLog queryById(Integer id) {
        return this.systemLogMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SystemLog> queryAllByLimit(int offset, int limit) {
        return this.systemLogMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param systemLog 实例对象
     * @return 实例对象
     */
    @Override
    public SystemLog insert(SystemLog systemLog) {
        try {
            this.systemLogMapper.insert(systemLog);
        } catch (Exception e) {
            //ignore this error
            throw new RuntimeException(e);
        }

        return systemLog;
    }

    /**
     * 修改数据
     *
     * @param systemLog 实例对象
     * @return 实例对象
     */
    @Override
    public SystemLog update(SystemLog systemLog) {
        this.systemLogMapper.update(systemLog);
        return this.queryById(systemLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.systemLogMapper.deleteById(id) > 0;
    }

    @Override
    public Integer queryCountByParam(SystemLog systemLog) {
        return this.systemLogMapper.queryCountByParam(systemLog);
    }

    @Override
    public int deleteByDate(Date start, Date end) {
        return this.systemLogMapper.deleteByDate(start, end);
    }

    @Override
    public List<SystemLog> queryByParam(SystemLog systemLog) {
        return this.systemLogMapper.queryByParam(systemLog);
    }

    @Override
    public List<DateCount> queryByDateGroup() {
        return this.systemLogMapper.queryCountByGroup();
    }

    @Override
    public DateCount queryCountByDate(String date) {
        return this.systemLogMapper.queryCountByDate(date);
    }

    @Override
    public int clearLog(Integer dayNumber) {
        Date start = DateUtil.rollDay(new Date(), dayNumber == null ? -32 : dayNumber);
        Date end = DateUtil.rollDay(start, 1);
        int row = deleteByDate(start, end);
        MDC.put("params", "清除" + DateUtil.format(start) + "-" + DateUtil.format(end) + "之间的日志，总计：" + row + "行");
        MDC.put("username", "system");
        MDC.put("userid", "system");
        MDC.put("method", "DELETE");
        MDC.put("url", "/api/deleteSystemLog");
        MDC.put("ip", "127.0.0.1");
        Logger.getLogger("mylog").info("清除" + DateUtil.format(start) + "-" + DateUtil.format(end) + "之间的日志，总计：" + row + "行");
        return row;
    }

    @Override
    public void checkErrorLog() {
        String date = DateUtil.getDate();
        DateCount dateCount = queryCountByDate(date);
        SystemConfig config = configService.queryByCoding(Constant.LOG_CHECK_COUNT_MAX);
        if (!Objects.isNull(config)) {
            try {
                Integer maxValue = Integer.valueOf(config.getValue());
                if (!Objects.isNull(dateCount) && dateCount.getCount() != null && dateCount.getCount() >= maxValue) {
                    Logger.getLogger("mylog").error("访问数据总量超过系统指定最大预警值“" + maxValue + "”，" + date + "总访问量为：" + maxValue);
                }
            } catch (Exception e) {
                Logger.getLogger("mylog").error("系统未指定最大预警值，请配置’LOG_CHECK_COUNT_MAX‘参数编码");
            }
        } else {
            Logger.getLogger("mylog").error("系统未指定最大预警值，请配置’LOG_CHECK_COUNT_MAX‘参数编码");
        }
    }

}