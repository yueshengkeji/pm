package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ProPutDetailCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/11 采购与入库明细mapper.
 *
 * @author XiaoSong
 * @date 2017/02/11
 */
@Mapper
public interface ProPutDetailMapper {
    /**
     * 添加采购明细
     *
     * @param proPutForDetail
     */
    void addDetail(ProPutForDetail proPutForDetail);

    /**
     * 更新采购入库明细
     *
     * @param detail 采购入库明细
     * @return 影响的行数
     */
    int updateDetail(ProPutForDetail detail);

    /**
     * 更新采购金额
     *
     * @param detail 采购入库明细
     * @return
     */
    int updateProMoney(ProPutForDetail detail);

    /**
     * 更新入库金额
     *
     * @param detail 采购入库明细
     * @return
     */
    int updatePutMoney(ProPutForDetail detail);

    /**
     * 更新采购明细的项目对象
     *
     * @param detail 采购入库明细
     * @return
     */
    int updateProject(ProPutForDetail detail);

    /**
     * 更新最后修改人信息
     *
     * @param lastDate  修改时间
     * @param lastStaff 修改人对象
     * @param id        采购入库明细主键
     * @return
     */
    int updateLastMsg(@Param("lastDate") String lastDate, @Param("lastStaff") Staff lastStaff, @Param("id") String id);

    /**
     * 删除采购入库明细
     *
     * @param id 采购入库明细
     * @return 影响的行数
     */
    int deleteDetail(@Param("id") String id);

    /**
     * 根据主单据删除明细集合
     *
     * @param mainId 主单据id
     * @return
     */
    int deleteDetailForMain(@Param("mainId") String mainId);

    /**
     * 获取采购入库明细对象
     *
     * @param id 主键
     * @return
     */
    ProPutForDetail getDetail(@Param("id") String id);

    /**
     * 获取采购入库 明细集合
     *
     * @param mainId 主表id
     * @return
     */
    List<ProPutForDetail> getDetailListByMain(@Param("mainId") String mainId);

    /**
     * 通过时间获取采购入库明细集合
     *
     * @param params {mainId:主表id,start:开始时间，end:结束时间}
     * @return
     */
    List<ProPutForDetail> getDetailByDate(Map<String, Object> params);

    /**
     * 根据主单据id获取采购金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Double getProMoneySumByMain(String mainId);

    /**
     * 根据主单据id获取入库金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Double getPutMoneySumByMain(String mainId);

    /**
     * 获取采购金额&入库金额总数
     *
     * @param mainId 主单据id
     * @return
     */
    Map<String, BigDecimal> getMoneySumByMain(String mainId);

    /**
     * 获取登记的采购金额|入库金额合计
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param type  0=采购金额，1=入库金额
     * @return
     */
    Double getProMoneyByDate(@Param("start") String start, @Param("end") String end, @Param("type") String type);



    /**
     * 通过采购订单id删除采购明细对账
     *
     * @param proId 采购订单id
     * @return
     */
    int deleteByProId(@Param("proId") String proId);

    /**
     * 更新备注
     *
     * @param detail
     * @return
     */
    int updateRemark(ProPutForDetail detail);

    /**
     * 根据采购订单，获取入库对账单明细
     *
     * @param proId 采购订单id
     * @return
     */
    List<ProPutForDetail> getProDetailByProId(@Param("proId") String proId, @Param("detailId") String detailId);

    /**
     * 根据主表id和项目id获取采购登记明细
     *
     * @param id        proDetail id
     * @param projectId 项目id
     * @return
     */
    ProPutForDetail getDetailByMain(@Param("proDetailId") String id, @Param("projectId") String projectId);


    int deleteByPutId(@Param("putId") String putId);

    /**
     * 根据入库单id获取采购明细对账单
     *
     * @param putId 入库单id
     * @return
     */
    List<ProPutForDetail> getProDetailByPutId(@Param("putId") String putId);

    Double getProMoneySumByMainDate(@Param("mainId") String mainId, @Param("endTime") String endTime);

    int deleteBySeries(@Param("mainId") String mainId, @Param("proDate") String proDate);

    List<ProPutForDetail> getBySeries(@Param("mainId") String mainId, @Param("proDate") String proDate);

    /**
     * 通过合同编号+明细id获取对账单数据
     *
     * @param contractId
     * @param detailId
     * @return
     */
    ProPutForDetail getByContractDetailId(@Param("contractId") String contractId, @Param("detailId") String detailId);

    /**
     * 通过合同编号+明细id删除
     *
     * @param contractId 合同id
     * @param detailId   明细id
     * @return
     */
    int deleteByContractDetailId(@Param("contractId") String contractId, @Param("detailId") String detailId);

    /**
     * 通过合同编号删除
     *
     * @param contractId 合同编号
     * @return
     */
    int deleteByContract(@Param("contractId") String contractId);

    List<ProPutForDetail> getDetailAll(HashMap<String, Object> param);

    /**
     * 查询金额统计
     * @param param
     * @return
     */
    ProPutDetailCount getDetailCount(HashMap<String, Object> param);

    /**
     * 查询年度采购金额合计
     * @param year
     * @return
     */
    Double getProMoneySum(String year);

    /**
     * 查询年度入库金额合计
     * @param year
     * @return
     */
    Double getPutMoneySum(String year);
}
