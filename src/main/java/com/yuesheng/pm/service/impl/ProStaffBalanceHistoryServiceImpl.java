package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.DiningDayStatistics;
import com.yuesheng.pm.entity.PersonalDiningStatistics;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import com.yuesheng.pm.mapper.ProStaffBalanceHistoryMapper;
import com.yuesheng.pm.service.ProStaffBalanceHistoryService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProStaffBalanceHistory)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-05-20 14:28:22
 */
@Service("proStaffBalanceHistoryService")
public class ProStaffBalanceHistoryServiceImpl implements ProStaffBalanceHistoryService {
    @Autowired
    private ProStaffBalanceHistoryMapper proStaffBalanceHistoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProStaffBalanceHistory queryById(Integer id) {
        return this.proStaffBalanceHistoryMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProStaffBalanceHistory> queryAllByLimit(int offset, int limit) {
        return this.proStaffBalanceHistoryMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(ProStaffBalanceHistory proStaffBalanceHistory) {
        if (StringUtils.isBlank(proStaffBalanceHistory.getDatetime())) {
            proStaffBalanceHistory.setDatetime(DateUtil.getDatetime());
        }
        return this.proStaffBalanceHistoryMapper.insert(proStaffBalanceHistory);
    }

    /**
     * 修改数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 实例对象
     */
    @Override
    public ProStaffBalanceHistory update(ProStaffBalanceHistory proStaffBalanceHistory) {
        this.proStaffBalanceHistoryMapper.update(proStaffBalanceHistory);
        return this.queryById(proStaffBalanceHistory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proStaffBalanceHistoryMapper.deleteById(id) > 0;
    }

    @Override
    public ProStaffBalanceHistory queryByStaff(String staffId, String date, Integer type) {
        return this.proStaffBalanceHistoryMapper.queryByStaff(staffId, date, type);
    }

    @Override
    public List<ProStaffBalanceHistory> queryAll(ProStaffBalanceHistory query, String startDate, String endDate) {
        return this.proStaffBalanceHistoryMapper.queryAll(query, startDate, endDate);
    }

    @Override
    public List<DiningDayStatistics> selectDiningDayStatistics(ProStaffBalanceHistory q, String startDate, String endDate) {
        return this.proStaffBalanceHistoryMapper.selectDiningDayStatistics(q, startDate, endDate);
    }

    @Override
    public List<PersonalDiningStatistics> selectPersonalStatistics(ProStaffBalanceHistory q, String startDate, String endDate) {
        return this.proStaffBalanceHistoryMapper.selectPersonalStatistics(q, startDate, endDate);
    }

    @Override
    public List<ProStaffBalanceHistory> selectStaffLastHistory(String startDate, String endDate) {
        return this.proStaffBalanceHistoryMapper.selectStaffLastHistory(startDate, endDate);
    }
}