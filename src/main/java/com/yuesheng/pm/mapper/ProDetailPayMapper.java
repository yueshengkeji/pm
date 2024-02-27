package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDetailPay;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/14 采购付款明细mapper.
 * @author XiaoSong
 * @date 2017/02/14
 */
@Mapper
public interface ProDetailPayMapper {
    /**
     * 添加付款明细对象
     * @param pay
     */
    void addPay(ProDetailPay pay);

    /**
     * 更新付款金额&时间
     * @param pay
     * @return
     */
    int updatePayMoney(ProDetailPay pay);
    /**
     * 更新收钱金额&时间
     * @param pay 采购付款明细对象
     * @return 影响的行数
     */
    int updateBillMoney(ProDetailPay pay);

    /**
     * 删除付款明细对象
     * @param id 主键
     * @return
     */
    int deletePay(String id);

    /**
     * 删除付款明细集合
     * @param mainId 主单据id
     * @return
     */
    int deletePayByMainId(String mainId);

    /**
     * 更新最后修改时间
     * @param date 最后修改时间
     * @param staff 修改人
     * @param id 单据id
     * @return
     */
    int updateLastMsg(@Param("date") String date,@Param("staff") Staff staff,@Param("id") String id);

    /**
     * 获取采购付款集合
     * @param mainId 主对象id
     * @return
     */
    List<ProDetailPay> getPayDetails(String mainId);

    /**
     * 获取采购付款明细对象
     * @param id 主键
     * @return
     */
    ProDetailPay getPayDetailById(String id);

    /**
     * 获取已付款&已收票总金额
     * @param mainId 主对象id
     * @return
     */
    Map<String,BigDecimal> getMoneySumByMain(String mainId);

    /**
     * 获取已付款总额
     * @param mainId 主单据id
     * @return
     */
    Double getPayMoneyByMain(String mainId);

    /**
     * 获取收票金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Double getBillMoneyByMain(String mainId);

    /**
     * 删除财务开票/收款数据
     *
     * @param mainId   主单据id
     * @param billDate 开票日期
     * @return
     */
    int deleteBySeries(@Param("mainId") String mainId, @Param("billDate") String billDate);

    /**
     * 查询开票信息
     *
     * @param billDate 合同编号
     * @param mainId   开票编号
     * @return
     */
    List<ProDetailPay> getBySeries(@Param("mainId") String mainId, @Param("billDate") String billDate);

    /**
     * 通过合同id和合同明细id查财务收付数据
     *
     * @param contractId 合同id
     * @param detailId   明细id
     * @return
     */
    ProDetailPay getByContractDetailId(@Param("contractId") String contractId, @Param("detailId") String detailId);

    /**
     * 通过合同id和合同明细id删除
     *
     * @param contractId 合同id
     * @param detailId   合同明细id
     * @return
     */
    int deleteByContractDetailId(@Param("contractId") String contractId, @Param("detailId") String detailId);

    /**
     * 查询年度付款金额合计
     * @param year
     * @return
     */
    Double getPayMoney(String year);

    /**
     * 查询年度收票金额合计
     * @param year
     * @return
     */
    Double getBillMoney(String year);

    /**
     * 更新付款和收票金额
     * @param pay
     * @return
     */
    int updatePayAndBillMoney(ProDetailPay pay);
}
