package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDetail;
import com.yuesheng.pm.entity.Procurement;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/10.
 * 采购明细服务接口
 */
public interface ProDetailService {
    /**
     * 添加采购明细主单据
     *
     * @param proDetail 主对象
     */
    Map<String, Object> addDetail(ProDetail proDetail, Staff staff);

    /**
     * 更新单据备注
     * @param remark
     * @return
     */
    int updateRemark(String remark, String id);

    /**
     * 更新销售人员名称
     */
    int updateSaleName(String saleName,String id);

    /**
     * 更新销售人员手机号
     * @param tel 手机号
     * @param id
     * @return
     */
    int updateSaleTel(String tel,String id);

    /**
     * 更新税率
     * @param tax 税率
     * @param id
     * @return
     */
    int updateTax(Double tax,String id);

    /**
     * 更新单据性质
     * @param sexType 0=本地，1=外地
     * @param id
     * @return
     */
    int updateSex(int sexType,String id);

    /**
     * 更新年初欠款
     * @param money 欠款金额
     * @param id 主键id
     * @return
     */
    int updateYearOwe(Double money,String id);

    /**
     * 更新账面欠款
     * @param money 金额
     * @param id
     * @return
     */
    int updatePaper(Double money,String id);

    /**
     * 更新期末欠款
     * @param id 主键
     *
     * @return
     */
    int updateEndOwe(String id);

    /**
     * 更新最后修改人时间和人员
     * @param lastDate 时间
     * @param staffId 职员id
     * @return
     */
    int updateLastMsg(String lastDate,String staffId);
    /**
     * 删除单据
     * @param id
     * @return
     */
    int deleteDetail(String id);

    /**
     * 获取主明细集合
     * @param year 根据年份 yyyy
     * @return
     */
    List<ProDetail> getDetailByYear(String year);

    /**
     * 根据年份判断单位是否存在
     * @param companyId
     * @param companyName
     * @param year 年份 yyyy
     * @param companyBelong 单据对应的公司内部单位
     * @return
     */
    String companyExist(String companyId, String companyName, String year, Integer companyBelong);

    /**
     * 更新单据信息
     * @param detail 单据对象
     * @return
     */
    int updateDetail(ProDetail detail);

    /**
     * 获取主单据对象
     * @param id 主键id
     * @return
     */
    ProDetail getDetailById(String id);

    /**
     * 获取采购对账单单据id
     *
     * @param companyId   单位id
     * @param companyName 单位名称
     * @param newYear     年份
     * @param companyBelong 单位对应公司内部单位
     * @return
     */
    String companyExistDetailId(String companyId, String companyName, String newYear, Integer companyBelong);

    /**
     * 获取采购付款对账单明细
     *
     * @param params    {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @param rowBounds 分页对象
     * @return
     */
    List<ProDetail> getDetailByYear(Map<String, Object> params,Integer pageNum,Integer pageSize);

    /**
     * 根据条件获取采购付款明细总条目
     *
     * @param params {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @return
     */
    Integer getDetailByYear(Map<String, Object> params);

    /**
     * 通过单位id获取采购对账单明细集合
     *
     * @param companyId 单位id
     * @param year      对账单年份
     * @return
     */
    List<ProDetail> getDetailByCompany(String companyId, String year);

    /**
     * 通过单位id获取采购对账单明细集合
     *
     * @param companyId     单位id
     * @param substring
     * @param companyBelong 单位所属
     * @return
     */
    ProDetail getDetailByCompany(String companyId, String substring, String companyBelong);

    /**
     * 获取单位所属
     *
     * @param projectType 项目类型
     * @return
     */
    String getCompanyType(String projectType);

    /**
     * update date
     *
     * @param nowDetail
     * @return
     */
    int updateDate(ProDetail nowDetail);

    /**
     * 根据采购订单查询单位所属类型
     *
     * @param procurement
     * @return
     */
    String getCompanyTypeByPro(Procurement procurement);

    /**
     * 添加对账单
     *
     * @param pd 采购订单
     * @return
     */
    ProDetail addDetailByProcurement(Procurement pd);

    /**
     * 查询采购明细列表
     *
     * @param params
     * @return
     */
    List<ProDetail> getDetailListByYear(Map<String, Object> params);

    /**
     * 获取采购付款单明细
     *
     * @param params {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @return
     */
    List<ProDetail> getDetailByYearV2(Map<String, Object> params);

    /**
     * 更新数据统计字段
     *
     * @param detail
     * @return
     */
    int updateMoney(ProDetail detail);

    /**
     * 更新年度采购金额合计
     * @param id 单据d
     * @param proMoney 采购金额
     * @return
     */
    int updateYearMoney(String id, Double proMoney);

    /**
     * 更新年度入库金额合计
     * @param id 单据id
     * @param putMoney 入库金额
     * @return
     */
    int updatePutMoney(String id, Double putMoney);

    int updateProAndPutMoney(String id, Double proMoney, Double putMoney);

    /**
     * 更新实际付款/收票统计
     * @param id
     * @param payMoney
     * @param billMoney
     * @return
     */
    int updatePayAndBillMoney(String id, Double payMoney, Double billMoney);
    /**
     * 更新采购/入库/付款/收票统计金额
     * 更新会计科目编号信息字符
     */
    int updateTotalMoneys(String year);

    int updateYearOweAndBill(ProDetail item);

    /**
     * 更新主表会计科目字符
     * @param id 主键id
     * @param subSeries 科目字符
     * @return
     */
    int updateSubjects(String id, String subSeries);
}
