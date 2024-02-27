package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Staff;

import java.math.BigDecimal;

public interface PayService {
    /**
     * 添加合同对账单
     *
     * @param companyName     单位名称
     * @param companyPerson   单位联系人
     * @param companyPhone    单位联系方式
     * @param contractSeries  合同编号
     * @param contractEndDate 合同截止日期 （yyyy-MM-dd）
     * @param billType        账期类型
     * @param remark          备注信息
     * @param contractMoney   合同金额（单位分)
     * @param staff           操作人信息
     * @return 影响行数
     */
    int insert(String companyName,
               String companyPerson,
               String companyPhone,
               String contractSeries,
               String contractEndDate,
               String billType,
               String remark,
               BigDecimal contractMoney,
               Staff staff);

    /**
     * 新增收票事件
     *
     * @param contractSeries  合同编号
     * @param invoicingSeries 收票编号
     * @param remark          备注
     * @param money           金额（单位分）
     * @param staff           操作人信息
     * @return 影响行数
     */
    int insertInvoicing(String contractSeries,
                        String invoicingSeries,
                        String remark,
                        BigDecimal money,
                        Staff staff);

    /**
     * 删除收票事件
     *
     * @param contractSeries  合同编号
     * @param invoicingSeries 收票编号
     * @param staff           操作人信息
     * @return 影响行数
     */
    int deleteInvoicing(String contractSeries,
                        String invoicingSeries,
                        Staff staff);

    /**
     * 修改收票信息
     *
     * @param contractSeries  合同编号
     * @param invoicingSeries 收票编号
     * @param remark          备注
     * @param money           金额
     * @param staff           操作人信息
     * @return 影响行数
     */
    int updateInvoicing(String contractSeries,
                        String invoicingSeries,
                        String remark,
                        BigDecimal money,
                        Staff staff);

    /**
     * 添加付款事件
     *
     * @param contractSeries 合同编号
     * @param proceedsSeries 付款编号
     * @param remark         备注
     * @param money          付款金额(单位分）
     * @param staff          操作人信息
     * @return 影响行数
     */
    int insertProceeds(String contractSeries,
                       String proceedsSeries,
                       String remark,
                       BigDecimal money,
                       Staff staff);

    /**
     * 删除付款信息
     *
     * @param contractSeries 合同编号
     * @param proceedsSeries 付款编号
     * @param staff          操作人信息
     * @return 影响行数
     */
    int deleteProceeds(String contractSeries,
                       String proceedsSeries,
                       Staff staff);

    /**
     * 修改付款信息
     *
     * @param contractSeries 合同编号
     * @param proceedsSeries 付款编号
     * @param remark         备注
     * @param money          付款金额
     * @param staff          操作人信息
     * @return 影响行数
     */
    int updateProceeds(String contractSeries,
                       String proceedsSeries,
                       String remark,
                       BigDecimal money,
                       Staff staff);


    /**
     * 付款计划发生变化事件
     *
     * @param contractSeries 合同编号
     * @param proceedsSeries 付款编号
     * @param state          付款状态
     * @param money          付款金额
     * @param staff          操作人信息
     * @return 影响行数
     */
    int proceedsStateChange(String contractSeries,
                            String proceedsSeries,
                            boolean state,
                            BigDecimal money,
                            Staff staff);

    /**
     * 修改合同账单
     *
     * @param companyName     单位名称
     * @param companyPerson   单位联系人
     * @param companyPhone    联系人手机号
     * @param contractSeries  合同编号
     * @param contractEndDate 合同截止时间
     * @param billType        账期类型
     * @param remark          备注信息
     * @param contractMoney   合同金额
     * @param staff           修改人
     * @return
     */
    int update(String companyName,
               String companyPerson,
               String companyPhone,
               String contractSeries,
               String contractEndDate,
               String billType,
               String remark,
               BigDecimal contractMoney,
               Staff staff);
}
