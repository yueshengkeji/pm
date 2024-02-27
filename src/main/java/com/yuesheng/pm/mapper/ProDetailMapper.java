package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/10 采购明细mapper.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
@Mapper
public interface ProDetailMapper {
    /**
     * 添加采购明细主单据
     *
     * @param proDetail 主对象
     */
    void addDetail(ProDetail proDetail);

    /**
     * 更新单据备注
     *
     * @param remark 新的备注信息
     * @param id     主键
     * @return
     */
    int updateRemark(@Param("remark") String remark, @Param("id") String id);

    /**
     * 更新销售人员名称
     *
     * @param saleName 销售人名称
     * @param id       主键
     * @return 影响的行数
     */
    int updateSaleName(@Param("saleName") String saleName, @Param("id") String id);

    /**
     * 更新销售人员手机号
     *
     * @param tel 手机号
     * @param id  主键
     * @return
     */
    int updateSaleTel(@Param("tel") String tel, @Param("id") String id);

    /**
     * 更新税率
     *
     * @param tax 税率
     * @param id  主键
     * @return
     */
    int updateTax(@Param("tax") Double tax, @Param("id") String id);

    /**
     * 更新单据性质
     *
     * @param sexType 0=本地，1=外地
     * @param id      主键
     * @return
     */
    int updateSex(@Param("type") int sexType, @Param("id") String id);

    /**
     * 更新年初欠款
     *
     * @param money 欠款金额
     * @param id    主键id
     * @return
     */
    int updateYearOwe(@Param("money") Double money, @Param("id") String id);

    /**
     * 更新账面欠款
     *
     * @param money 金额
     * @param id    主键
     * @return
     */
    int updatePaper(@Param("money") Double money, @Param("id") String id);

    /**
     * 更新期末欠款
     *
     * @param money 期末欠款金额
     * @param id    主键
     * @return
     */
    int updateEndOwe(@Param("money") Double money, @Param("id") String id);

    /**
     * 更新最后修改人时间和人员
     *
     * @param lastDate 时间
     * @param staffId  职员id
     * @return
     */
    int updateLastMsg(@Param("lastDate") String lastDate, @Param("staffId") String staffId);

    /**
     * 删除单据
     *
     * @param id
     * @return
     */
    int deleteDetail(@Param("id") String id);

    /**
     * 获取主明细集合
     *
     * @param year 根据年份 yyyy
     * @return
     */
    List<ProDetail> getDetailByYear(String year);

    /**
     * 根据年份判断单位是否存在
     *
     * @param companyId
     * @param companyName
     * @param year          年份 yyyy
     * @param companyBelong 单据对应的公司内部单位
     * @return
     */
    String companyExist(@Param("companyId") String companyId, @Param("companyName") String companyName, @Param("year") String year, @Param("companyBelong") Integer companyBelong);

    /**
     * 获取年初欠款
     *
     * @param id 主键
     * @return
     */
    Double getYearMoney(String id);

    /**
     * 更新单据信息
     *
     * @param detail 单据对象
     * @return
     */
    int updateDetail(ProDetail detail);

    /**
     * 获取主单据对象
     *
     * @param id 主键id
     * @return
     */
    ProDetail getDetailById(String id);

    /**
     * 获取采购明细对账单对象id
     *
     * @param companyId     单位id
     * @param companyName   单位名称
     * @param newYear       年份
     * @param companyBelong 单位对应公司内部所属单位类型
     * @return
     */
    String companyExistDetailId(@Param("companyId") String companyId, @Param("companyName") String companyName, @Param("newYear") String newYear, @Param("companyBelong") Integer companyBelong);

    /**
     * 获取采购付款对账单明细
     *
     * @param params    {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @param rowBounds 分页对象
     * @return
     */
    List<ProDetail> getDetailByParam(Map<String, Object> params);

    /**
     * 根据条件获取采购付款明细总条目
     *
     * @param params {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @return
     */
    Integer getDetailCountByParam(Map<String, Object> params);

    /**
     * 通过单位id获取采购对账单明细集合
     *
     * @param companyId
     * @return
     */
    List<ProDetail> getDetailByCompany(@Param("companyId") String companyId, @Param("year") String year);

    /**
     * 通过单位id获取采购对账单明细集合
     *
     * @param companyId     单位id
     * @param year          单据年份
     * @param companyBelong 单位所属
     * @return
     */
    ProDetail getDetailByCompanyBelong(@Param("companyId") String companyId, @Param("year") String year, @Param("companyBelong") String companyBelong);

    /**
     * update date
     *
     * @param nowDetail
     * @return
     */
    int updateDate(ProDetail nowDetail);

    /**
     * 获取采购付款对账单明细
     *
     * @param params {year:"获取的年份（必选",searchText:"检索字符串（可选）"}
     * @return
     */
    List<ProDetail> getDetailByParamV2(Map<String, Object> params);

    /**
     * 更新数据统计字段
     *
     * @param detail
     * @return
     */
    int updateMoney(ProDetail detail);

    /**
     * 更新年度采购金额
     */
    int updateYearPro(ProDetail detail);
    /**
     * 更新年度入库金额
     */
    int updatePutMoney(ProDetail detail);

    /**
     * 更新收票金额合计
     * @param detail
     * @return
     */
    int updateBillMoney(ProDetail detail);

    /**
     * 更新已付款金额合计
     * @param detail
     * @return
     */
    int updateYetMoney(ProDetail detail);

    /**
     * 更新采购金额和入库金额
     * @param pd
     * @return
     */
    int updateProAndPutMoney(ProDetail pd);

    /**
     * 更新实际付款/收票统计
     * @param detail
     * @return
     */
    int updatePayAndBillMoney(ProDetail detail);
    /**
     * 更新主表会计科目字符
     * @param id 主键id
     * @param subSeries 科目字符
     * @return
     */
    int updateSubjects(@Param("id") String id,@Param("subSeries") String subSeries);

    /**
     * 更新年初欠款/欠票金额
     * @param item
     * @return
     */
    int updateYearOweAndBill(ProDetail item);
}
