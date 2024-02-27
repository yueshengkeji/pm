package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SaleData;
import com.yuesheng.pm.mapper.SaleDataMapper;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.service.SaleDataService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * (SaleData)表服务实现类
 *
 * @author xiaosong
 * @since 2023-06-26 15:31:14
 */
@Service("saleDataService")
public class SaleDataServiceImpl implements SaleDataService {
    @Autowired
    private SaleDataMapper saleDataMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SaleData queryById(String id) {
        return this.saleDataMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param saleData 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SaleData> queryByPage(SaleData saleData) {
        return this.saleDataMapper.queryAllByLimit(saleData);
    }

    /**
     * 新增数据
     *
     * @param saleData 实例对象
     * @return 实例对象
     */
    @Override
    public SaleData insert(SaleData saleData) {
        saleData.setDate(DateUtil.getDatetime());
        saleData.setId(UUID.randomUUID().toString());
        this.saleDataMapper.insert(saleData);
        return saleData;
    }

    /**
     * 修改数据
     *
     * @param saleData 实例对象
     * @return 实例对象
     */
    @Override
    public SaleData update(SaleData saleData) {
        this.saleDataMapper.update(saleData);
        return this.queryById(saleData.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.saleDataMapper.deleteById(id) > 0;
    }

    @Override
    public int insertByArray(ArrayList<SaleData> saleData) {
        return this.saleDataMapper.insertBatch(saleData);
    }

    @Override
    public List<SaleData> queryAll(HashMap<String, Object> query) {
        return this.saleDataMapper.queryAll(query);
    }

    @Override
    public Double queryMoney(HashMap<String, Object> query) {
        return this.saleDataMapper.queryMoney(query);
    }

    @Override
    public List<DateGroupModel> queryMoneyGroupSaleDate(HashMap<String, Object> param) {
        return this.saleDataMapper.queryMoneyGroupSaleDate(param);
    }

    @Override
    public List<DateGroupModel> queryByDayTopList(String saleStartDate, String saleEndDate) {
        return this.saleDataMapper.queryByDayTopList(saleStartDate,saleEndDate);
    }

    @Override
    public List<DateGroupModel> queryByDateTopList(String saleStartDate, String saleEndDate) {
        return this.saleDataMapper.queryByDateTopList(saleStartDate,saleEndDate);
    }
}
