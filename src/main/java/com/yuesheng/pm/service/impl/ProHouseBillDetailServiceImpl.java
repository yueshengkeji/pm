package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.HouseBillDetail;
import com.yuesheng.pm.mapper.ProHouseBillDetailMapper;
import com.yuesheng.pm.service.ProHouseBillDetailService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * (ProHouseBillDetail)表服务实现类
 *
 * @author makejava
 * @since 2020-04-17 11:27:41
 */
@Service("proHouseBillDetailService")
public class ProHouseBillDetailServiceImpl implements ProHouseBillDetailService {
    @Autowired
    private ProHouseBillDetailMapper proHouseBillDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public HouseBillDetail queryById(String id) {
        return this.proHouseBillDetailDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<HouseBillDetail> queryAllByLimit(int offset, int limit) {
        return this.proHouseBillDetailDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proHouseBillDetail 实例对象
     * @return 实例对象
     */
    @Override
    public HouseBillDetail insert(HouseBillDetail proHouseBillDetail) {
        this.proHouseBillDetailDao.insert(proHouseBillDetail);
        return proHouseBillDetail;
    }

    /**
     * 修改数据
     *
     * @param proHouseBillDetail 实例对象
     * @return 实例对象
     */
    @Override
    public HouseBillDetail update(HouseBillDetail proHouseBillDetail) {
        this.proHouseBillDetailDao.update(proHouseBillDetail);
        return this.queryById(proHouseBillDetail.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proHouseBillDetailDao.deleteById(id) > 0;
    }

    @Override
    public List<HouseBillDetail> queryByMain(String mainId) {
        return this.proHouseBillDetailDao.queryByMain(mainId);
    }

    @Override
    public Double queryMoneyByMainId(String mainId) {
        return proHouseBillDetailDao.queryMoneyByMainId(mainId);
    }

    @Override
    public int destroy(String id) {
        return proHouseBillDetailDao.destroy(id);
    }

    @Override
    public Double getMoneyByYear(Map<String, Object> result) {
        return proHouseBillDetailDao.getMoneyByYear(result);
    }

    @Override
    public void approve(FlowMessage msg){
        HouseBillDetail hbd = queryById(msg.getFrameId());
        hbd.setApproveDate(DateUtil.getDatetime());
        hbd.setIsApprove((byte) 1);
        hbd.setApproveStaff(msg.getLastApproveUser());
        update(hbd);
    }
}