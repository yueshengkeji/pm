package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProZujinMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * (ProZujin)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Service("proZujinService")
public class ProZujinServiceImpl implements ProZujinService, FileService {
    @Autowired
    private ProZujinMapper proZujinMapper;
    @Autowired
    private ProZujinHouseService houseService;
    @Autowired
    private ProDetailOweService oweService;
    @Autowired
    private AttachService attachService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProZujin queryById(Integer id) {
        return this.proZujinMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProZujin> queryAllByLimit(int offset, int limit) {
        return this.proZujinMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zujin 实例对象
     * @param staff 职员信息
     * @return 实例对象
     */
    @Override
    public ProZujin insert(ProZujin zujin, Staff staff) {
        zujin.setDateTime(DateUtil.format(DateUtil.getNowDate()));
        List<ProZujinHouse> houses = zujin.getHouses();
        updateYeTai(zujin);
        this.proZujinMapper.insert(zujin);
        houseService.insertHouseRelation(houses, zujin);
        //默认会计科目
        ProDetailMoney money = new ProDetailMoney();
        money.setId(UUID.randomUUID().toString());
        money.setMainId(zujin.getId() + "");
        money.setMoney(0.0);
        money.setDate(DateFormat.getDateTime());
        money.setSeries("默认科目");
        money.setRemark("");
        //默认期初欠票
        ProDetailOwe owe = new ProDetailOwe();
        owe.setId(UUID.randomUUID().toString());
        owe.setOweMoney(0.0);
        owe.setMainId(zujin.getId() + "");
        owe.setType(0);
        owe.setDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        owe.setOweDate(owe.getDate());
        owe.setStaff(staff);
        oweService.addOwe(owe);
        //默认期初欠款
        ProDetailOwe owe2 = new ProDetailOwe();
        owe2.setId(UUID.randomUUID().toString());
        owe2.setOweMoney(0.0);
        owe2.setMainId(zujin.getId() + "");
        owe2.setType(1);
        owe2.setDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        owe2.setOweDate(owe.getDate());
        owe2.setStaff(staff);
        oweService.addOwe(owe2);
        return zujin;
    }

    private void updateYeTai(ProZujin proZujin) {
        if (!Objects.isNull(proZujin.getHouses())) {
            proZujin.getHouses().forEach(item -> {
                this.houseService.updateHouseYetai(proZujin.getYt(), item.getId());
            });
        }
    }

    /**
     * 修改数据
     *
     * @param proZujin 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujin update(ProZujin proZujin) {
        updateYeTai(proZujin);
//        String endDate = proZujin.getEndDatetime();
//        Date end = DateUtil.parse(endDate, DateUtil.PATTERN_CLASSICAL_SIMPLE);
//        Date now = DateUtil.getNowDate();
//        if (end.getTime() > now.getTime()) {
//            proZujin.setType((byte) 0);
//        }
        this.proZujinMapper.update(proZujin);
        return this.queryById(proZujin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proZujinMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProZujin> queryByParam(HashMap<String, Object> params) {
        return this.proZujinMapper.queryByParam(params);
    }

    @Override
    public Integer queryCountByParam(HashMap<String, Object> params) {
        return this.proZujinMapper.queryCountByParam(params);
    }

    @Override
    public int updateMoneyCount(ProZujin proZujin) {
        return this.proZujinMapper.updateMoneyCount(proZujin);
    }

    @Override
    public int updateBzj(ProZujin zujin) {
        return this.proZujinMapper.updateBzj(zujin);
    }

    @Override
    public Map<String, Object> queryMoneyTotal(String year) {
        return this.proZujinMapper.queryMoneyTotal(year);
    }

    @Override
    public Double queryEarlyMoney(String year) {
        return this.proZujinMapper.queryEarlyMoney(year);
    }

    @Override
    public Map<String, Object> queryMoneyTotal(String year, Integer type) {
        return this.proZujinMapper.queryMoneyTotalv2(year, type);
    }

    @Override
    public ProZujin queryBySeries(String contractSeries) {
        HashMap param = new HashMap();
        param.put("series", contractSeries);
        param.put("isSh", "0");
        List<ProZujin> zujins = this.proZujinMapper.queryByParam(param);
        if (!zujins.isEmpty()) {
            return zujins.get(0);
        }
        return null;
    }

    @Override
    public ProZujin queryByPay(String contractSeries) {
        HashMap param = new HashMap();
        param.put("series", contractSeries);
        param.put("isSh", "1");
        List<ProZujin> zujins = this.proZujinMapper.queryByParam(param);
        if (!zujins.isEmpty()) {
            return zujins.get(0);
        }
        return null;
    }

    @Override
    public List<ProZujin> queryBrand(String searchText) {
        return this.proZujinMapper.queryBrand(searchText);
    }

    @Override
    public int check() {
        String endDate = DateUtil.getDate();
        List<ProZujin> zujins = this.proZujinMapper.queryExpire(endDate);
        int row = 0;
        for (ProZujin zujin : zujins) {
            if (StringUtils.isNotBlank(zujin.getEndDatetime())) {
                row += houseService.releaseByZujin(zujin.getId());
                zujin.setType((byte) 9);
                this.proZujinMapper.updateType(zujin);
            }
        }
        return row;
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        Integer id = Integer.valueOf(StringUtils.replace(moduleId, "-zujin", ""));
        ProZujin zujin = queryById(id);
        ArrayList<Attach> result = new ArrayList<>();
        if (!Objects.isNull(zujin) && StringUtils.isNotBlank(zujin.getFiles())) {
            String[] files = zujin.getFiles().split(";");
            for (int i = 0; i < files.length; i++) {
                result.add(attachService.getById(files[i]));
            }
        }
        return result;
    }
}