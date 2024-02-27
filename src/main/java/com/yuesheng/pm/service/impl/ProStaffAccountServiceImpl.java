package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProStaffAccount;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProStaffAccountMapper;
import com.yuesheng.pm.service.ProStaffAccountService;
import com.yuesheng.pm.service.ProStaffBalanceHistoryService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.DateUtil;
import jakarta.annotation.PostConstruct;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 员工钱包(ProStaffBalance)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-05-20 11:16:49
 */
@Service("proStaffAccountService")
@DependsOn("databaseVersionService")
public class ProStaffAccountServiceImpl implements ProStaffAccountService {

    @Autowired
    private ProStaffAccountMapper proStaffAccountMapper;

    @Autowired
    @Lazy
    private StaffService staffService;

    @Autowired
    private ProStaffBalanceHistoryService historyService;

    @PostConstruct
    public void init() {
        try {
            List<Staff> staffList = staffService.seek(null);
            staffList.forEach(item -> {
                insertByStaff(item);
            });
        } catch (Exception e) {

        }
    }

    @Override
    public ProStaffAccount insertByStaff(Staff item) {
        if (!isExists(item)) {
            //初始化职员余额信息
            ProStaffAccount balance = new ProStaffAccount();
            balance.setStaff(item);
            balance.setId(UUID.randomUUID().toString());
            balance.setBalance(0.0);
            return insert(balance);
        } else {
            return null;
        }
    }

    @Override
    public boolean isExists(Staff staff) {
        return !Objects.isNull(queryByStaffId(staff.getId()));
    }

    @Override
    public ProStaffAccount queryByStaffId(String staffId) {
        PageHelper.startPage(1,1);
        return proStaffAccountMapper.queryByStaffId(staffId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProStaffAccount queryById(String id) {
        return this.proStaffAccountMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProStaffAccount> queryAllByLimit(int offset, int limit) {
        return this.proStaffAccountMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proStaffAccount 实例对象
     * @return 实例对象
     */
    @Override
    public ProStaffAccount insert(ProStaffAccount proStaffAccount) {
        this.proStaffAccountMapper.insert(proStaffAccount);
        return proStaffAccount;
    }

    /**
     * 修改数据
     *
     * @param proStaffAccount 实例对象
     * @return 实例对象
     */
    @Override
    public ProStaffAccount update(ProStaffAccount proStaffAccount) {
        this.proStaffAccountMapper.update(proStaffAccount);
        return this.queryById(proStaffAccount.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proStaffAccountMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public ProStaffAccount updateBalance(String staffId, Double money) {
        ProStaffAccount balance = this.proStaffAccountMapper.queryByIdForUpdate(staffId);
        balance.setBalance(money);
        balance.setLastDatetime(DateUtil.getDatetime());
        if (this.proStaffAccountMapper.update(balance) > 0) {
            return balance;
        }
        return null;
    }

    @Override
    @Transactional
    public ProStaffAccount subtract(ProStaffBalanceHistory history, Staff operate) throws Exception {
        ProStaffAccount balance = proStaffAccountMapper.queryByStaffId(history.getStaff().getId());
        if (!Objects.isNull(balance)) {
            Double newMoney = 0.0;
            if ((newMoney = balance.getBalance() - history.getMoney()) < 0) {
                throw new Exception("余额不足，扣费失败");
            } else {
                ProStaffAccount psb = updateBalance(history.getStaff().getId(), newMoney);
                if (psb == null) {
                    throw new Exception("更新钱包余额失败");
                } else {
                    history.setOperate(operate);
                    history.setAfterBalance(balance.getBalance());
                    saveHistory(history, operate);
                    balance.setBalance(newMoney);
                }
            }
        } else {
            throw new Exception("该员工：" + history.getStaff().getId() + history.getStaff().getName() + ",没有钱包,消费失败");
        }
        return balance;
    }

    @Override
    @Transactional
    public ProStaffAccount rechargeAmount(ProStaffBalanceHistory history, Staff operate) throws Exception {
        ProStaffAccount balance = proStaffAccountMapper.queryByStaffId(history.getStaff().getId());
        if (!Objects.isNull(balance)) {
            Double newMoney = history.getMoney() + balance.getBalance();
            if (history.getMoney() < 1) {
                throw new Exception("充值金额不能小于等于0");
            } else {
                ProStaffAccount psb = updateBalance(history.getStaff().getId(), newMoney);
                if (psb == null) {
                    throw new Exception("更新钱包余额失败");
                } else {
                    //记录消费历史数据
                    history.setType((byte) 0);
                    history.setAfterBalance(balance.getBalance());
                    saveHistory(history, operate);
                }
            }
        } else {
            throw new Exception("该员工：" + history.getStaff().getId() + history.getStaff().getName() + ",没有钱包,消费失败");
        }
        return balance;
    }

    @Override
    public List<ProStaffAccount> queryAll(ProStaffAccount account) {
        return this.proStaffAccountMapper.queryAll(account);
    }

    private void saveHistory(ProStaffBalanceHistory history, Staff operate) {
        // history.setType(type);
        // history.setStaff(balance.getStaff());
        // history.setMoney(money);
        // history.setAfterBalance(balance.getBalance());
        // history.setRemark(remark);
        // history.setOperate(operate);
        if (historyService.insert(history) == 0) {
            LogManager.getLogger("mylog").error("添加消费记录失败：" + JSON.toJSONString(history));
        }
    }
}