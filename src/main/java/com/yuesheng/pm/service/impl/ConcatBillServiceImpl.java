package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ConcatBill;
import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.entity.Term;
import com.yuesheng.pm.mapper.ConcatBillMapper;
import com.yuesheng.pm.service.ConcatBillService;
import com.yuesheng.pm.service.ProZujinHouseService;
import com.yuesheng.pm.service.ProZujinService;
import com.yuesheng.pm.service.SaleDataService;
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

    @Autowired
    private SaleDataService saleDataService;

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
            if (StringUtils.equals(term.getUnit(), "month")) {
                if (StringUtils.equals(term.getPayCycle(), "quarter")) {
                    //季度账单
                    insertQuarterBill(term, zujin, floor, room.toString());
                } else if (StringUtils.equals(term.getPayCycle(), "towMonth")) {
                    //两月付
                    insertTowMonthBill(term, zujin, floor, room.toString());
                } else {
//                    每月算费
                    insertMonthBill(term, zujin, floor, room.toString());
                }
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

    private void insertFinalBill(Term term, ProZujin zujin, String floor, String room) {

        //获取上个月第一天
        Date prevDate = DateUtil.getLastMonthStartTime();

        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = term.getStartDate();
        Date endDate = null;

        int endDay = DateUtil.getOffsetDays(prevDate, term.getEndDate());
        if (endDay <= 0) {
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

        Double money = getTermMoney(term, prevDate, endDate, zujin.getBrand());
        ConcatBill cb = new ConcatBill();
        cb.setMoney(money);
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

    private void insertQuarterBill(Term term, ProZujin zujin, String floor, String room) {

        Date now = DateUtil.getNowDate();
        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = DateUtil.getNextQuarterStartTime();
        Date endDate = null;

        int endDay = DateUtil.getOffsetDays(now, term.getEndDate());
        if (endDay <= 0) {
            //到结束时间，以结束时间为准
            endDate = term.getEndDate();
        } else {
            //下一季度结束时间
            endDate = DateUtil.getNextQuarterEndTime();
        }

        Double money = getTermMoney(term, startDate, endDate, zujin.getBrand());

        ConcatBill cb = new ConcatBill();
        cb.setMoney(money);
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

    private void insertTowMonthBill(Term term, ProZujin zujin, String floor, String room) {

        //获取两个月之前的第一天日期
        Date prevDate = DateUtil.getMonthStartTime(2);

        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = term.getStartDate();
        Date endDate = null;

        int day = DateUtil.getOffsetDays(prevDate, startDate);
        if (day > 0) {
            //账单周期开始时间比账单开始时间还要小，未到账单生成周期
            return;
        }

        int endDay = DateUtil.getOffsetDays(prevDate, term.getEndDate());
        if (endDay <= 0) {
            //到结束时间，以结束时间为准
            endDate = term.getEndDate();
        }

        boolean sameMonth = DateUtil.isSameMonth(startDate, prevDate);
        if (!sameMonth) {
            //非同一个月,从上两个月第一天开始 到 上月的最后一天
            startDate = prevDate;
            if (Objects.isNull(endDate)) {
                endDate = DateUtil.getMonthEndTime(DateUtil.getLastMonthStartTime());
            }
        }else{
            if (Objects.isNull(endDate)) {
                endDate = DateUtil.getMonthEndTime(DateUtil.getLastMonthStartTime());
            }
        }


        Double money = getTermMoney(term, startDate, endDate, zujin.getBrand());

        ConcatBill cb = new ConcatBill();
        cb.setMoney(money);
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

        //获取上个月第一天
        Date prevDate = DateUtil.getLastMonthStartTime();

        Date date = DateUtil.getDateByDay(term.getPayDay());
        Date startDate = term.getStartDate();
        Date endDate = null;

        int endDay = DateUtil.getOffsetDays(prevDate, term.getEndDate());
        if (endDay <= 0) {
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

        Double money = getTermMoney(term, startDate, endDate, zujin.getBrand());
        ConcatBill cb = new ConcatBill();
        cb.setMoney(money);
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

    private Double getTermMoney(Term term, Date startDate, Date endDate, String brand) {
        String type = term.getType();
        Double x = 1.0;
        if (StringUtils.equals(term.getPayCycle(), "towMonth")) {
            x = 2.0;
        } else if (StringUtils.equals(term.getPayCycle(), "quarter")) {
            x = 3.0;
        }

        Double termMoney = term.getMoney() * x;

        if (StringUtils.equals(type, "regular")) {
            return termMoney;
        } else if (StringUtils.equals(type, "commission")) {
            Double money = getCommossionMoney(term, startDate, endDate, brand);
            return money;
        } else {
            //如果提成高，就以提成租金算，如果固定金额高，就以固定金额算
            Double comMoney = getCommossionMoney(term, startDate, endDate, brand);
            if (Double.compare(comMoney, termMoney) > 0) {
                return comMoney;
            } else {
                return termMoney;
            }
        }
    }

    private Double getCommossionMoney(Term term, Date startDate, Date endDate, String brand) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("saleStartDate", DateUtil.format(startDate, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        param.put("saleEndDate", DateUtil.format(endDate, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        param.put("brand", brand);
        Double money = saleDataService.queryMoney(param);
        if (Objects.isNull(money)) {
            money = 0.0;
        } else {
            money = money * term.getMoney();
        }
        return money;
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
