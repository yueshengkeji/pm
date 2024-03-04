package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ConcatBillMapper;
import com.yuesheng.pm.service.ConcatBillService;
import com.yuesheng.pm.service.ProZujinHouseService;
import com.yuesheng.pm.service.ProZujinService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * (ConcatBill)表服务实现类
 *
 * @author xiaosong
 * @since 2024-02-29 16:16:45
 */
@Service("concatBillService")
public class ConcatBillServiceImpl implements ConcatBillService {
    @Autowired
    private ConcatBillMapper concatBillMapper;
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
    public ConcatBill queryById(String id) {
        return this.concatBillMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param concatBill 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ConcatBill> queryByPage(ConcatBill concatBill) {
        return this.concatBillMapper.queryAllByLimit(concatBill);
    }

    /**
     * 新增数据
     *
     * @param concatBill 实例对象
     * @return 实例对象
     */
    @Override
    public ConcatBill insert(ConcatBill concatBill) {
        ConcatBill cb = new ConcatBill();
        cb.setConcatId(concatBill.getConcatId());
        cb.setSourceId(concatBill.getSourceId());
        cb.setStartDate(concatBill.getStartDate());
        cb.setEndDate(concatBill.getEndDate());
        List<ConcatBill> cbs = queryByPage(cb);
        if (cbs.isEmpty()) {
            setPayState(concatBill);
            concatBill.setId(UUID.randomUUID().toString());
            this.concatBillMapper.insert(concatBill);
            return concatBill;
        } else {
            return null;
        }
    }

    private static void setPayState(ConcatBill concatBill) {

        Double sjMoney = concatBill.getMoney();
        if (!Objects.isNull(concatBill.getSjMoney()) && concatBill.getSjMoney() > 0) {
            sjMoney = concatBill.getSjMoney();
        }

        if (Objects.isNull(concatBill.getPayMoney()) || concatBill.getPayMoney() == 0) {
            concatBill.setState("wait");
            concatBill.setArrearage(sjMoney);
        } else if (Double.compare(concatBill.getPayMoney(), sjMoney) == -1) {
            concatBill.setState("pay");
            concatBill.setArrearage(sjMoney - concatBill.getPayMoney());
            concatBill.setApproveState(1);
        } else {
            concatBill.setState("1");
            concatBill.setArrearage(0.0);
            concatBill.setApproveState(1);
        }

        //计算欠款天数
        if (!Objects.isNull(concatBill.getPayEndDate()) && !StringUtils.equals(concatBill.getState(), "1")) {
            int day = DateUtil.getOffsetDays(concatBill.getPayEndDate(), new Date());
            if (day > 0) {
                concatBill.setArrearageDay(day);
            }
        }
    }

    /**
     * 修改数据
     *
     * @param concatBill 实例对象
     * @return 实例对象
     */
    @Override
    public ConcatBill update(ConcatBill concatBill) {
        setPayState(concatBill);
        this.concatBillMapper.update(concatBill);
        return this.queryById(concatBill.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.concatBillMapper.deleteById(id) > 0;
    }

    @Override
    public void genByTerm(Term term) {

        ProZujin zujin = zujinService.queryById(Integer.valueOf(term.getConcatId()));
        List<ProZujinHouse> houses = houseService.getHouseByZujin(zujin.getId());
        String floor = "";
        StringBuffer room = new StringBuffer();
        if (!houses.isEmpty()) {
            ProZujinHouse h = houses.get(0);
            floor = h.getFloor();
            houses.forEach(item -> {
                room.append(item.getPwNumber() + ",");
            });
        }
        Date now = DateUtil.getNowDate();
        Date firstStart = term.getFirstStartDate();
        Date firstEnd = term.getFirstEndDate();
        Double firstMoney = term.getFirstMoney();

        if (!Objects.isNull(firstStart) && !Objects.isNull(firstEnd)) {
            Integer firstDay = DateUtil.getOffsetDays(firstStart, now);
            Integer endDay = DateUtil.getOffsetDays(now, firstEnd);
            if (firstDay >= 0 && endDay >= 0) {
                //在首期结束时间范围内
                insertFirstBill(term, zujin, floor, room, now, firstStart, firstEnd, firstMoney);
                return;
            }
        }

        Integer startDay = DateUtil.getOffsetDays(term.getStartDate(), now);
        Integer endDay = DateUtil.getOffsetDays(now, term.getEndDate());
        if (startDay >= 0 && endDay >= 0) {
            //在计费时间范围内，开始算费
            if (StringUtils.equals(term.getPayCycle(), "quarter")) {
                //季度账单
                insertQuarterBill(term,zujin,floor,room.toString());
            }else if (StringUtils.equals(term.getUnit(), "month")) {
//                每月算费
                insertMonthBill(term, zujin, floor, room.toString());
            } else {
//                一次性算费
                insertOneBill(term, zujin, floor, room.toString());
            }
        }

    }

    @Override
    public int deleteBySourceId(String sourceId) {
        return concatBillMapper.deleteBySourceId(sourceId);
    }

    @Override
    public List<ConcatBill> queryByParam(HashMap<String, String> param) {
        return concatBillMapper.queryByParam(param);
    }


    private void insertQuarterBill(Term term, ProZujin zujin, String floor, String room) {

        Date now = DateUtil.getNowDate();
        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = DateUtil.getNextQuarterStartTime();
        Date endDate = null;

        int endDay = DateUtil.getOffsetDays(now, term.getEndDate());
        if (endDay <= 0) {
            //到结束时间，以结束时间为准
            endDate = term.getEndDate();
        }else{
            //下一季度结束时间
            endDate = DateUtil.getNextQuarterEndTime();
        }

        ConcatBill cb = new ConcatBill();
        cb.setMoney(term.getMoney());
        cb.setConcatId(term.getConcatId());
        cb.setDatetime(DateUtil.getNowDate());
        cb.setApproveState(0);
        cb.setBrand(zujin.getBrand());
        cb.setEndDate(endDate);
        cb.setPayEndDate(date);
        cb.setName(term.getName());
        cb.setConcatType(zujin.getCompanyTypeId() + "");
        cb.setFloor(floor);
        cb.setArrearageDay(0);
        cb.setInvoiceState(0);
        cb.setArrearage(term.getMoney());
        cb.setRoom(room);
        cb.setMonthBill(0);
        cb.setType(term.getType());
        cb.setUnit(term.getUnit());
        cb.setPayCycle(term.getPayCycle());
        cb.setPayType(term.getPayType());
        cb.setPayMoney(0.0);
        cb.setSourceId(term.getId());
        cb.setStartDate(startDate);
        cb.setState("wait");
        insert(cb);
    }

    private void insertMonthBill(Term term, ProZujin zujin, String floor, String room) {

        //获取上个月第一题拿
        Date prevDate = DateUtil.getLastMonthStartTime();

        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = term.getStartDate();
        Date endDate = null;

        int endDay = DateUtil.getOffsetDays(prevDate, term.getEndDate());
        if (endDay == 0) {
            //到结束时间，以结束时间为准
            endDate = term.getEndDate();
        }

        boolean sameMonth = DateUtil.isSameMonth(startDate, prevDate);
        if (!sameMonth) {
            //非同一个月,从本月第一天开始 到 本月最后一天
            startDate = DateUtil.getMonthStartTime(prevDate);
            if (Objects.isNull(endDate)) {
                endDate = DateUtil.getMonthEndTime(prevDate);
            }
        } else if (Objects.isNull(endDate)) {
            //同一个月，设置截止时间为开始日期当月最后一天
            endDate = DateUtil.getMonthEndTime(startDate);
        }

        ConcatBill cb = new ConcatBill();
        cb.setMoney(term.getMoney());
        cb.setConcatId(term.getConcatId());
        cb.setDatetime(DateUtil.getNowDate());
        cb.setApproveState(0);
        cb.setBrand(zujin.getBrand());
        cb.setEndDate(endDate);
        cb.setPayEndDate(date);
        cb.setName(term.getName());
        cb.setConcatType(zujin.getCompanyTypeId() + "");
        cb.setFloor(floor);
        cb.setArrearageDay(0);
        cb.setInvoiceState(0);
        cb.setArrearage(term.getMoney());
        cb.setRoom(room);
        cb.setMonthBill(0);
        cb.setType(term.getType());
        cb.setUnit(term.getUnit());
        cb.setPayCycle(term.getPayCycle());
        cb.setPayType(term.getPayType());
        cb.setPayMoney(0.0);
        cb.setSourceId(term.getId());
        cb.setStartDate(startDate);
        cb.setState("wait");
        insert(cb);
    }

    private void insertOneBill(Term term, ProZujin zujin, String floor, String room) {

        Date date = DateUtil.getDateByDay(term.getPayDay());

        ConcatBill cb = new ConcatBill();
        cb.setMoney(term.getMoney());
        cb.setConcatId(term.getConcatId());
        cb.setDatetime(DateUtil.getNowDate());
        cb.setApproveState(0);
        cb.setBrand(zujin.getBrand());
        cb.setEndDate(term.getEndDate());
        cb.setStartDate(term.getStartDate());
        cb.setPayEndDate(date);
        cb.setName(term.getName());
        cb.setConcatType(zujin.getCompanyTypeId() + "");
        cb.setFloor(floor);
        cb.setArrearageDay(0);
        cb.setInvoiceState(0);
        cb.setArrearage(term.getMoney());
        cb.setRoom(room);
        cb.setMonthBill(0);
        cb.setType(term.getType());
        cb.setUnit(term.getUnit());
        cb.setPayCycle(term.getPayCycle());
        cb.setPayType(term.getPayType());

        cb.setPayMoney(0.0);
        cb.setSourceId(term.getId());
        cb.setState("wait");
        insert(cb);
    }

    private void insertFirstBill(Term term, ProZujin zujin, String floor, StringBuffer room, Date now, Date firstStart, Date firstEnd, Double firstMoney) {
        ConcatBill cb = new ConcatBill();
        cb.setMoney(firstMoney);
        cb.setConcatId(term.getConcatId());
        cb.setDatetime(now);
        cb.setApproveState(0);
        cb.setBrand(zujin.getBrand());
        cb.setEndDate(firstEnd);
        cb.setName(term.getName());
        cb.setConcatType(zujin.getCompanyTypeId() + "");
        cb.setFloor(floor);
        cb.setArrearageDay(0);
        cb.setInvoiceState(0);
        cb.setArrearage(firstMoney);
        cb.setRoom(room.toString());
        cb.setMonthBill(0);
        cb.setType("regular");
        cb.setUnit("one");
        cb.setStartDate(firstStart);
        cb.setPayCycle("one");
        cb.setPayEndDate(firstEnd);
        cb.setPayType("day");

        cb.setPayMoney(0.0);
        cb.setSourceId(term.getId());
        cb.setState("wait");
        insert(cb);
    }
}
