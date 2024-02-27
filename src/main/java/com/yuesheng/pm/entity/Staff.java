package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by Administrator on 2016-08-04 职员对象.
 *
 * @author XiaoSong
 * @date 2016/08/01
 */
@Schema(name="职员基础信息")
public class Staff extends BaseEntity {
    private static final long serialVersionUID = -13825030241182937L;
    /**
     * 员工名称  pj00402
     */
    @Schema(name = "员工真实姓名,添加员工信息时必填", required = true)
    @Excel(name = "姓名")
    private String name;
    /**
     * 性别    pj00403 (1:男，2:女)
     */
    @Schema(name = "性别")
    private byte sex;
    /**
     * 职员编码
     */
    @Schema(name = "员工编码")
    private String coding;
    /**
     * 部门编码  pj00417 （varchar(20)）
     */
    @Schema(name = "部门编码，添加员工信息时必填")
    private String sectionCoding;
    /**
     * 手机号   pj00420(varchar(200))
     */
    @Schema(name = "手机号")
    private String tel;
    /**
     * 邮箱号（pj00441(varchar(100))）
     */
    @Schema(name = "邮箱号")
    private String email;
    /**
     * 用户密码
     */
    @Schema(name = "登录密码", required = true)
    private String passwd;
    /**
     * 角色集合
     */
    @Schema(name = "角色编码数组，添加员工信息时必填")
    private String[] roleName;
    /**
     * 职务名称
     */
    @Schema(name = "职务名称")
    private String dutyName;
    /**
     * 职务集合
     */
    @Schema(name = "多职务数组，添加员工信息时必填")
    private Duty[] duty;
    /**
     * 是否领导
     */
    @Schema(name = "是否领导", hidden = true)
    private String isLeader;
    /**
     * 职员所属部门
     */
    @Schema(name = "员工部门，添加员工信息时必填")
    private Section section;
    /**
     * 首页头信息
     */
    @Schema(name = "自定义html数据代码")
    private HeadMsg headMsg;
    /**
     * 角色集合
     */
    @Schema(name = "拥有角色数组")
    private Role[] role;
    /**
     * 利用pm2系统登录时指定数据源的参数
     */
    @Schema(name = "登录公司comId", hidden = true)
    private String comId;
    /**
     * 当前用户所在公司数据库地址
     */
    @Schema(name = "当前用户所在公司数据库地址", hidden = true)
    private String dbName;
    /**
     * 登陆用户名
     */
    @Schema(name = "登陆用户名", required = true)
    private String userName;
    /**
     * 注册日期
     */
    @Schema(name = "注册日期")
    private String date;
    /**
     * 最后登陆日期
     */
    @Schema(name = "最后登陆日期")
    private String lastDate;
    /**
     * 最后登陆时间
     */
    @Schema(name = "最后登陆时间")
    private String time;
    /**
     * 文化,最后登陆的ip地址
     */
    @Schema(name = "文化")
    private String wh = "本科";
    @Schema(name = "最后登陆的ip地址")
    private String ip = "";
    /**
     * pj00426:1=禁止登陆，0=可以登录
     */
    @Schema(name = "是否禁止登录，1=禁止登陆，0=可以登录")
    private byte isLogin;
    /**
     * 最后登陆时间
     */
    private String lastLogin;
    /**
     * 头像base64
     */
    @Schema(name = "头像base64")
    private String head;
    /**
     * 汉王打卡机设备id
     */
    @Schema(hidden = true)
    private String hwDeviceId;
    private String token;
    @Schema(name="身份证号码")
    private String number = "";
    @Schema(name="开户行")
    private String openBlank = "";
    @Schema(name="银行卡号")
    private String bankNumber = "";
    @Schema(name="入职时间")
    private String inDate = "";
    @Schema(name="离职时间")
    private String offDate = "";
    @Schema(name="离职原因")
    private String offRemark = "";
    @Schema(name="劳动合同文件路径")
    private String contractPath = "";
    @Schema(name="保险险种")
    private String bxStr = "";
    @Schema(name="保险购买时间")
    private String bxDate = "";
    @Schema(name="所属项目")
    private String projectName = "";
    @Schema(name="所属公司")
    private String companyName = "";
    @Schema(name="民族")
    private String nationName = "";
    @Schema(name="年龄")
    private int age;
    @Schema(name="居住地址")
    private String address = "";
    @Schema(name="截止时间")
    private String endTime = "";
    @Schema(name="学历证书路径")
    private String eduPath;
    @Schema(name="操作证书名称")
    private String actionName;
    @Schema(name="操作证书路径")
    private String actionPath;
    @Schema(name="操作证书取证日期")
    private String actionDate;
    @Schema(name="操作证书复审日期")
    private String actionAgainDate;
    @Schema(name="保险截止时间")
    private String bxEndTime;
    @Schema(name="保险公司名称")
    private String bxCompany;
    @Schema(name="企业微信绑定标记")
    private StaffAdditionInfo additionInfo;
    @Schema(name="钉钉绑定标记")
    private DingTalkStaffInfo dingTalkStaffInfo;
    @Schema(name="手机品牌")
    private String mobileBrand;
    @Schema(name="手机型号")
    private String mobileModel;

    public String getMobileBrand() {
        return mobileBrand;
    }

    public void setMobileBrand(String mobileBrand) {
        this.mobileBrand = mobileBrand;
    }

    public String getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public DingTalkStaffInfo getDingTalkStaffInfo() {
        return dingTalkStaffInfo;
    }

    public void setDingTalkStaffInfo(DingTalkStaffInfo dingTalkStaffInfo) {
        this.dingTalkStaffInfo = dingTalkStaffInfo;
    }

    public StaffAdditionInfo getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(StaffAdditionInfo additionInfo) {
        this.additionInfo = additionInfo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEduPath() {
        return eduPath;
    }

    public void setEduPath(String eduPath) {
        this.eduPath = eduPath;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionPath() {
        return actionPath;
    }

    public void setActionPath(String actionPath) {
        this.actionPath = actionPath;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionAgainDate() {
        return actionAgainDate;
    }

    public void setActionAgainDate(String actionAgainDate) {
        this.actionAgainDate = actionAgainDate;
    }

    public String getBxEndTime() {
        return bxEndTime;
    }

    public void setBxEndTime(String bxEndTime) {
        this.bxEndTime = bxEndTime;
    }

    public String getBxCompany() {
        return bxCompany;
    }

    public void setBxCompany(String bxCompany) {
        this.bxCompany = bxCompany;
    }

    public String getOffDate() {
        return offDate;
    }

    public void setOffDate(String offDate) {
        this.offDate = offDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOpenBlank() {
        return openBlank;
    }

    public void setOpenBlank(String openBlank) {
        this.openBlank = openBlank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOffRemark() {
        return offRemark;
    }

    public void setOffRemark(String offRemark) {
        this.offRemark = offRemark;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getBxStr() {
        return bxStr;
    }

    public void setBxStr(String bxStr) {
        this.bxStr = bxStr;
    }

    public String getBxDate() {
        return bxDate;
    }

    public void setBxDate(String bxDate) {
        this.bxDate = bxDate;
    }

    public static void setNullStaff(Staff nullStaff) {
        Staff.nullStaff = nullStaff;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHwDeviceId() {
        return hwDeviceId;
    }

    public void setHwDeviceId(String hwDeviceId) {
        this.hwDeviceId = hwDeviceId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public byte getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(byte isLogin) {
        this.isLogin = isLogin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Duty[] getDuty() {
        return duty;
    }

    public void setDuty(Duty[] duty) {
        this.duty = duty;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    private static Staff nullStaff = null;

    public synchronized static Staff getNullStaff() {
        if (nullStaff == null) {
            nullStaff = new Staff();
            nullStaff.setCoding("");
        }
        return nullStaff;
    }

    public Role[] getRole() {
        return role;
    }

    public void setRole(Role[] role) {
        this.role = role;
    }

    public HeadMsg getHeadMsg() {
        return headMsg;
    }

    public void setHeadMsg(HeadMsg headMsg) {
        this.headMsg = headMsg;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String[] getRoleName() {
        return roleName;
    }

    public void setRoleName(String[] roleName) {
        this.roleName = roleName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getSectionCoding() {
        return sectionCoding;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public void setSectionCoding(String sectionCoding) {
        this.sectionCoding = sectionCoding;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        try {
            return "user{" +
                    "名称='" + name + '\'' +
                    ", 部门='" + section.getName() + '\'' +
                    ", 职务='" + dutyName + '\'' +
                    '}';
        } catch (NullPointerException e) {
            return "-";
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
