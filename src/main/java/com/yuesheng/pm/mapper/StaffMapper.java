package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.HeadMsg;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-06 职员mapper.
 * @author XiaoSong
 * @date 2016/08/06
 */
@Mapper
public interface StaffMapper {
    /**
     * 根据职员id获取职员对象
     * @param id
     * @return 职员对象
     */
    Staff getStaffById(String id);

    /**
     * 根据职员代码获取职员对象
     * @param coding 职员代码
     * @return 职员对象
     */
    Staff getStaffByCoding(String coding);

    /**
     * 获取采购人员集合
     * @return 采购人员集合
     */
    List<Staff> getProStaff();

    /**
     * 职员登录
     * @param name 用户名
     * @param passwd    用户密码，在pm2中为【住址：pj00436】
     * @return 职员对象
     */
    Staff login(@Param("name") String name,@Param("passwd") String passwd);

    /**
     * 根据字符串检索职员集合
     *
     * @param str 字符串
     * @return 职员集合
     */
    List<Staff> seek(@Param("str") String str);


    /**
     * 根据id集合获取职员集合
     * @param counts id集合
     * @return 职员集合
     */
    List<Staff> getStaffByCount(@Param("list") List<Count> counts);

    /**
     * 根据分页对象获取职员
     * @return 职员集合
     */
    List<Staff> getStaffs();

    /**
     * 快捷登陆
     * @param name 用户名
     * @return
     */
    Staff fastLogin(@Param("name") String name);

    /**
     * 获取职员角色集合
     * @param staffId 职员id
     * @return
     */
    String[] getRoleNames(@Param("staffId") String staffId);

    /**
     * 获取职员头部标签
     * @param id
     * @return
     */
    HeadMsg getHeadMsg(String id);

    /**
     * 更新职员信息
     * @param staff 职员对象
     */
    int update(Staff staff);

    /**
     * 添加职员
     * @param staff
     */
    void insert(Staff staff);

    /**
     * 添加最后登录信息
     *
     * @param staff 职员对象
     */
    void insertLoginInfo(Staff staff);

    /**
     * 通过用户名，获取职员对象
     * @param userName 用户名
     * @return
     */
    Staff getStaffByUserName(@Param("userName") String userName);

    /**
     * 更新登陆信息
     *
     * @param staff
     */
    void updateUserPasswd(Staff staff);

    /**
     * 职员登陆
     *
     * @param userName 用户名
     * @param s        密码
     * @return
     */
    Staff loginIn(@Param("userName") String userName, @Param("passwd") String s);

    /**
     * 更新职员登录信息
     * @param staff
     */
    void updateLoginInfo(Staff staff);

    /**
     * 更新职员是否登录状态
     *
     * @param staff
     */
    void updateIsLogin(Staff staff);

    /**
     * 获取禁止登录的职员信息列表
     *
     * @param str 检索字符串
     * @return
     */
    List<Staff> queryUnLogin(@Param("str") String str);

    /**
     * 更新用户最后登陆时间
     */
    int updateLoginTime(Staff staff);

    /**
     * 查询未绑定企业微信的员工信息列表
     *
     * @return
     */
    List<Staff> getNoBindWxList();

    /**
     * 查询未绑定钉钉的员工信息列表
     * @return
     */
    List<Staff> getNoBindDingList();

    /**
     * 获取登录信息
     * @param coding 员工编码
     * @return
     */
    Staff getUserLogin(@Param("coding") String coding);
}
