package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProStaffAccount;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * 员工钱包(ProStaffBalance)表服务接口
 *
 * @author xiaoSong
 * @since 2022-05-20 11:16:49
 */
public interface ProStaffAccountService {
    /**
     * 通过职员初始化员工钱包
     *
     * @param item
     * @return
     */
    ProStaffAccount insertByStaff(Staff item);

    /**
     * 判断职员是否初始化账户余额
     *
     * @param staff
     * @return
     */
    boolean isExists(Staff staff);

    /**
     * 查找卡余额信息
     *
     * @param staffId 员工id
     * @return
     */
    ProStaffAccount queryByStaffId(String staffId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffAccount queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProStaffAccount> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proStaffAccount 实例对象
     * @return 实例对象
     */
    ProStaffAccount insert(ProStaffAccount proStaffAccount);

    /**
     * 修改数据
     *
     * @param proStaffAccount 实例对象
     * @return 实例对象
     */
    ProStaffAccount update(ProStaffAccount proStaffAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 更新卡余额
     *
     * @param staffId 员工id
     * @param money   变更后金额
     */
    ProStaffAccount updateBalance(String staffId, Double money);

    /**
     * 钱包消费
     *
     * @param history 消费记录
     * @param operate 操作员
     * @throws Exception 余额不足，抛出消费失败异常
     */
    ProStaffAccount subtract(ProStaffBalanceHistory history, Staff operate) throws Exception;

    /**
     * 钱包充值
     *
     * @param balanceHistory 充值记录
     * @param staff          操作员工
     */
    ProStaffAccount rechargeAmount(ProStaffBalanceHistory balanceHistory, Staff staff) throws Exception;

    /**
     * 查询所有账户
     * @param account 查询条件
     * @return
     */
    List<ProStaffAccount> queryAll(ProStaffAccount account);
}