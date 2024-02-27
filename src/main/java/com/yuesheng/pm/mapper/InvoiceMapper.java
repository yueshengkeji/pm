package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * 合同开票登记
 * @author ssk
 * @since 2021-12-17
 */
@Mapper
public interface InvoiceMapper {
    /**
     * 分页
     * @param
     * @return
     */
    List<Invoice> selectByDateForPage(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 月开票数
     * @param
     * @return
     */
    Integer selectCountsByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 日查询
     * @param
     * @return
     */
    List<Invoice> selectByDay(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 月查询
     * @param
     * @return
     */
    List<Invoice> selectByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 已审核月查询
     * @param
     * @return
     */
    List<Invoice> selectCheckedByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 通过pMark查询
     * @param pMark
     * @return
     */
    Invoice selectByMark(String pMark);

    /**
     * 通过开票编号查询
     * @param invoiceID
     * @return
     */
    Invoice selectByInvoiceID(String invoiceID);

    /**
     * 查询合同下的开票记录
     * @param agreementID
     * @return
     */
    List<Invoice> selectByAgreementID(String agreementID);

    /**
     * 插入合同开票登记
     * @param invoice
     * @return
     */
    int insertInvoice(Invoice invoice);

    /**
     * 删除合同开票登记
     * @param ID
     * @return
     */
    int deleteInvoice(String ID);

    /**
     * 删除已删除合同下售票记录
     * @return
     */
    int deleteInvoiceByAgreementID(String agreementID);

    /**
     * 更新合同开票登记信息
     * @param invoice
     * @return
     */
    int updateInvoice(Invoice invoice);

    /**
     * 查询所有
     * @return
     */
    List<Invoice> selectAll();

    /**
     * 通过pMark和登录人Id查询
     * @return
     */
    Invoice selectBypMarkAndRID(@Param("pMark") String pMark, @Param("registrantId") String registrantId);

    /**
     * 通过id获取开票明细
     *
     * @param id
     * @return
     */
    Invoice selectById(@Param("id") String id);

    /**
     * 已审核开票信息
     * @param index
     * @param
     * @return
     */
    List<Invoice> selectCheckedByDateForPage(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 已审核开票数量
     * @param
     * @return
     */
    Integer selectCheckedCountsByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 搜索
     * @param search
     * @return
     */
    List<Invoice> searchInvoice(String search);

    /**
     * 搜索数量
     * @param search
     * @return
     */
    Integer searchInvoiceCounts(String search);

    /**
     * 搜索已审核
     * @param search
     * @return
     */
    List<Invoice> searchInvoiceChecked(String search);

    /**
     * 搜索已审核数量
     * @param search
     * @return
     */
    Integer searchInvoiceCheckedCounts(String search);

    /**
     * @param invoice
     * @return
     */
    Invoice selectForBill(Invoice invoice);

    /**
     * 通过bill标记查询
     * @param billMark
     * @return
     */
    Invoice selectByBillMark(String billMark);

    /**
     * 根据id设置开票状态为已审核
     * @param id
     * @return
     */
    boolean setStatus(String id);

    /**
     * 获取合同下同类型税率开票记录
     * @param tax
     * @param agreementID
     * @return
     */
    List<Invoice> selectByTax(@Param("tax")String tax,@Param("agreementID")String agreementID);

    /**
     * 获取合同下税率为null或是空字符开票记录
     * @param agreementID
     * @return
     */
    List<Invoice> selectByNullTax(@Param("agreementID")String agreementID);

    /**
     * 获取已审核的开票记录
     * @return
     */
    List<Invoice> selectAllDone();
    /**
     * 根据单位查询
     * @param params
     * @return
     */
    List<Invoice> getByCompany(Map<String,String> params);
    /**
     * 开票列表
     * @param params
     * @return
     */
    List<Invoice> list(Map<String,String> params);
}
