package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.StaffCard;
import com.yuesheng.pm.mapper.StaffCardMapper;
import com.yuesheng.pm.service.StaffCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * (StaffCard)表服务实现类
 *
 * @author xiaosong
 * @since 2024-01-09 13:35:52
 */
@Service("staffCardService")
public class StaffCardServiceImpl implements StaffCardService {
    @Autowired
    private StaffCardMapper staffCardMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StaffCard queryById(String id) {
        return this.staffCardMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param staffCard 筛选条件
     * @return 查询结果
     */
    @Override
    public List<StaffCard> queryByPage(StaffCard staffCard) {
        return this.staffCardMapper.queryAllByLimit(staffCard);
    }

    /**
     * 新增数据
     *
     * @param staffCard 实例对象
     * @return 实例对象
     */
    @Override
    public StaffCard insert(StaffCard staffCard) {
        StaffCard sc = queryByStaff(staffCard.getStaff().getId());
        if (Objects.isNull(sc)) {
            staffCard.setId(UUID.randomUUID().toString());
            this.staffCardMapper.insert(staffCard);
            return staffCard;
        } else {
            return null;
        }
    }

    /**
     * 修改数据
     *
     * @param staffCard 实例对象
     * @return 实例对象
     */
    @Override
    public StaffCard update(StaffCard staffCard) {
        this.staffCardMapper.update(staffCard);
        return this.queryById(staffCard.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.staffCardMapper.deleteById(id) > 0;
    }

    @Override
    public StaffCard queryByStaff(String staffId) {
        PageHelper.startPage(1, 1);
        List<StaffCard> sc = this.staffCardMapper.queryByStaff(staffId);
        if (!sc.isEmpty()) {
            return sc.get(0);
        }
        return null;
    }

    @Override
    public List<StaffCard> queryAllByStaff(String staffId) {
        return this.staffCardMapper.queryByStaff(staffId);
    }
}
