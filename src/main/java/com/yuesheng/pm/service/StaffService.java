package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.HeadMsg;
import com.yuesheng.pm.entity.ProStaffHw;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06 职员服务接口.
 *
 */
public interface StaffService {
    /**
     * 根据职员id获取职员信息
     * @param Id
     * @return
     */
    Staff getStaffById(String Id);
    /**
     * 根据职员代码获取职员对象
     * @param coding 职员代码
     * @return
     */
    Staff getStaffByCoding(String coding);

    /**
     * 获取采购职员集合
     * @return
     */
    List<Staff> getProStaff();

    /**
     * 职员登录
     * @param name 用户名
     * @param passwd    用户密码，在pm2中为【住址：pj00436】
     * @return 职员对象
     */
    Staff login(String name,String passwd);

    /**
     * 获取职员集合
     * @return 职员集合
     */
    List<Staff> getStaffs();

    /**
     * 根据指定的职员id集合获取职员集合对象
     * @param counts 总计集合
     * @return 职员对象
     */
    List<Staff> getStaffByCount(List<Count> counts);


    /**
     * 根据字符串搜索职员集合
     * @param str 字符串
     * @return 职员集合
     */
    List<Staff> seek(String str);

    /**
     * 快捷登陆
     * @param name 用户名
     * @return 用户对象
     */
    Staff login(String name);

    /**
     * 获取职员头部信息标签
     * @param id
     * @return
     */
    HeadMsg getHeadMsg(String id);

    /**
     * 更新职员信息
     * @param staff
     */
    void update(Staff staff);

    /**
     * 添加职员
     *
     * @param staff 职员对象
     */
    Map<String, Object> insert(Staff staff);

    ProStaffHw insertRelation(Staff s);

    /**
     * 添加职员登陆信息
     *
     * @param staff
     */
    void insertLoginInfo(Staff staff);

    /**
     * 根据用户名获取用户对象，判断用户名是否存在
     *
     * @param userName 登陆用户名
     * @return
     */
    Staff getStaffByUserName(String userName);

    /**
     * 更新登陆信息
     *
     * @param staff
     */
    Map<String, Object> updateUserPasswd(Staff staff);

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param s        密码
     * @return
     */
    Staff loginIn(String userName, String s);

    /**
     * 用户是否可以登录
     *
     * @param staff
     */
    void updateIsLogin(Staff staff);

    /**
     * 获取禁止登陆员工信息列表
     *
     * @param str 检索字符串
     * @return
     */
    List<Staff> queryUnLogin(String str);

    /**
     * 更新用户登陆时间
     *
     * @param staff
     * @return
     */
    int updateLoginTime(Staff staff);

    /**
     * 更新职员车牌号
     *
     * @param plate 车牌号
     * @param id    职员id
     * @return
     */
    int updateStaffPlate(String plate, String id);

    /**
     * 同步微信信息
     *
     * @param wxUserId 微信用户id
     * @param staffId  员工id
     * @return
     */
    int syncUserInfo(String wxUserId, String staffId);

    /**
     * 查询未绑定企业微信的员工信息列表
     *
     * @return
     */
    List<Staff> getNoBindWxList();

    /**
     * 同步企业微信用户基础信息
     *
     * @return
     */
    boolean syncUserInfo();

    /**
     * 同步钉钉用户信息
     */
    void syncDingTalkUser();

    /**
     * 同步员工车牌信息
     */
    void syncCarPlan();

    /**
     * 更新用户手机品牌和型号
     * @param staff
     * @return
     */
    int updateMobileInfo(Staff staff);

    /**
     * 将员工头像转换为文件
     */
    void headToFile();

    /**
     * 获取登录信息
     * @param coding 员工id
     * @return
     */
    Staff getUserLogin(String coding);
}
