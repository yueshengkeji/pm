package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ConcatBill;
import com.yuesheng.pm.entity.ProBzj;
import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.mapper.ProBzjMapper;
import com.yuesheng.pm.service.ConcatBillService;
import com.yuesheng.pm.service.ProBzjService;
import com.yuesheng.pm.service.ProZujinHouseService;
import com.yuesheng.pm.service.ProZujinService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProBzj)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-08-31 09:32:20
 */
@Service("proBzjService")
public class ProBzjServiceImpl implements ProBzjService {
    @Autowired
    private ProBzjMapper proBzjMapper;
    @Autowired
    @Lazy
    private ConcatBillService concatBillService;
    @Autowired
    @Lazy
    private ProZujinService zujinService;
    @Autowired
    @Lazy
    private ProZujinHouseService houseService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProBzj queryById(Integer id) {
        return this.proBzjMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProBzj> queryAllByLimit(int offset, int limit) {
        return this.proBzjMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    @Override
    public ProBzj insert(ProBzj proBzj) {
        if (proBzj.getDatetime() == null) {
            proBzj.setDatetime(DateUtil.format(DateUtil.getNowDate()));
        }
        this.proBzjMapper.insert(proBzj);


        //添加保证金应收账单
        ProZujin pz = zujinService.queryById(Integer.valueOf(proBzj.getProDetailId()));
        List<ProZujinHouse> houses = houseService.getHouseByZujin(pz.getId());
        String floor = "";
        StringBuffer room = new StringBuffer();
        if (!houses.isEmpty()) {
            ProZujinHouse h = houses.get(0);
            floor = h.getFloor();
            houses.forEach(item -> {
                room.append(item.getPwNumber() + ",");
            });
        }
        ConcatBill cb = new ConcatBill();
        cb.setMoney(proBzj.getMoney());
        cb.setConcatId(proBzj.getProDetailId());
        cb.setDatetime(DateUtil.getNowDate());
        cb.setApproveState(0);
        cb.setBrand(pz.getBrand());
        cb.setEndDate(proBzj.getEndDate());
        cb.setName(proBzj.getType());
        cb.setConcatType(pz.getCompanyTypeId() + "");
        cb.setFloor(floor);
        cb.setArrearageDay(0);
        cb.setInvoiceState(0);
        cb.setArrearage(proBzj.getMoney());
        cb.setRoom(room.toString());
        cb.setMonthBill(0);
        cb.setType("bzj");
        cb.setUnit("one");
        cb.setStartDate(proBzj.getStartDate());
        cb.setPayCycle("one");
        cb.setPayEndDate(proBzj.getEndDate());
        cb.setPayType("day");

        cb.setPayMoney(0.0);
        cb.setSourceId(proBzj.getId());
        cb.setState("wait");

        concatBillService.insert(cb);
        return proBzj;
    }

    /**
     * 修改数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    @Override
    public ProBzj update(ProBzj proBzj) {
        this.proBzjMapper.update(proBzj);

        //同步修改未付款的保证金账单
        ConcatBill cb = new ConcatBill();
        cb.setSourceId(proBzj.getId());
        List<ConcatBill> cbs = concatBillService.queryByPage(cb);
        cbs.forEach(item -> {
            if (StringUtils.equals(item.getState(), "wait") || StringUtils.equals(item.getState(), "pay")) {
                item.setName(proBzj.getType());
                item.setMoney(proBzj.getMoney());
                item.setPayEndDate(proBzj.getEndDate());
                item.setStartDate(proBzj.getStartDate());
                item.setEndDate(proBzj.getEndDate());
                concatBillService.update(item);
            }
        });
        return this.queryById(Integer.valueOf(proBzj.getId()));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        concatBillService.deleteBySourceId(id + "");
        return this.proBzjMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProBzj> queryAll(ProBzj bzj) {
        return this.proBzjMapper.queryAll(bzj);
    }
}