package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.ProMaterial;
import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.model.CompanyModel;
import com.yuesheng.pm.model.ProMaterReport;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-12 采购订单材料服务接口.
 */
public interface ProcurementMaterService {
    /**
     * 添加采购订单材料
     *
     * @param applyMaterialList 采购订单集合
     * @return 影响的行数
     */
    Integer addMater(List<ProMaterial> applyMaterialList, String proId);

    /**
     * 根据订单id获取材料集合
     *
     * @param id      订单id
     * @param discard 是否获取作废单据
     * @return 材料集合
     */
    List<ProMaterial> getProMatersByProId(String id, String discard);

    /**
     * 根据订单id获取未入库的材料集合
     *
     * @param id
     * @return
     */
    List<ProMaterial> getNotMatersByProId(String id);

    /**
     * 更新材料入库数量和入库时间
     *
     * @param materials 材料对象集合
     */
    void updatePutSum(List<ProMaterial> materials);

    /**
     * 获取该订单采购材料数量和已入库的数量
     *
     * @param id 订单id
     * @return 采购数量和入库数量
     */
    Map<String, BigDecimal> getCount(String id);

    /**
     * 通过采购订单材料表主键id获取订单材料对象
     *
     * @param proMaterId 订单材料主键
     * @return 订单材料集合
     */
    ProMaterial getMatersById(String proMaterId);

    /**
     * 获取材料历史价格集合，目前默认为10
     *
     * @param name   材料名称
     * @param model  型号
     * @param amount 数据行数
     * @param start
     * @param end    @return
     */
    List<ProMaterial> getHistoryPrice(String name, String model, int amount, String start, String end);

    /**
     * 根据供应单位和截止时间，获取该单位采购的金额和时间集合
     *
     * @param params {companyId:单位id,start:开始时间，end:结束时间}
     */
    List<ProPutForDetail> getProMoneyByCompany(Map<String, Object> params);

    /**
     * 获取采购材料集合
     *
     * @param params{ companyId 单位id
     *                start 开始时间
     *                end 结束时间}
     * @return
     */
    List<ProMaterial> getProMatersByCompany(HashMap params);

    /**
     * 删除采购订单材料集合
     *
     * @param id 采购订单id
     */
    void deleteByProId(String id);

    /**
     * 添加采购订单材料对象
     *
     * @param material
     */
    void addMater(ProMaterial material);

    /**
     * 删除订单材料对象
     *
     * @param id 订单材料主键
     * @return
     */
    int deletemater(String id);

    /**
     * 更新订单中材料对象数据
     *
     * @param material
     */
    void updateMater(ProMaterial material);

    /**
     * 查询材料是否被采购过
     * @param id
     * @return
     */
    Material getMaterByMaterId(String id);

    /**
     * 获取采购最多的材料集合
     *
     * @param size  数据大小
     * @param start
     * @param end   @return
     */
    List<ProjectMaterial> getMaterialBYProMax(Integer size, String start, String end);

    Map<String, Object> getMaterialProInfo(String name, String model, String start, String end);

    /**
     * 获取采购金额总和
     *
     * @param companyId 单位id
     * @param start     开始日期
     * @param end       截止日期
     * @return
     */
    Double getProMoneyByDate(@Param("companyId") String companyId, @Param("start") String start, @Param("end") String end);

    /**
     * 获取购买最多的单位集合
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param size  数据大小
     * @return
     */
    List<CompanyModel> getProMoneyByCompanyMax(@Param("start") String start, @Param("end") String end, @Param("size") Integer size);

    /**
     * 获取材料采购信息
     *
     * @param name  材料名称
     * @param model 材料型号
     * @param brand 品牌
     * @param start 开始时间
     * @param end   截止时间
     * @return
     */
    List<ProMaterial> getHistoryPriceV2(String name, String model, String brand, Integer size, String start, String end);

    /**
     * 根据申请单材料明细id查询采购订单材料集合
     *
     * @param applyMaterialId 申请单明细id
     * @return
     */
    List<ProMaterial> getProMaterByApply(String applyMaterialId);

    /**
     * 添加订单材料到作废明细中
     *
     * @param material 订单材料列表
     * @param id       采购订单id
     */
    void addDiscardMater(List<ProMaterial> material, String id);

    /**
     * 通过订单id获取材料明细
     *
     * @param proId 订单id
     * @return
     */
    List<ProMaterial> getProMatersByProId(String proId);

    int addMaterial2(ProMaterial proMaterial, String id);

    /**
     * 获取采购订单总金额
     *
     * @param proId 订单id
     * @return
     */
    Double getProMoneyByProId(String proId);

    /**
     * 获取采购材料列表
     *
     * @param param      查询参数
     * @return
     */
    List<ProMaterReport> getMaterialByParam(Map<String, Object> param);

    /**
     * 查询采购材料总数
     *
     * @param param 查询条件
     * @return
     */
    Map<String,Double> getMaterialByQueryCount(Map<String, Object> param);

    /**
     * 查询未到货的材料清单
     * @param query 查询条件：{searchText:材料字符串，projectId:项目id,dhDate:到货日期}
     * @return
     */
    List<ProMaterial> getNoDhList(HashMap<String, String> query);

    /**
     * 转换日期
     * @param pageNum
     */
    void initDateParse(Integer pageNum);

    /**
     * 触发未到货材料通知
     *
     * @return
     */
    boolean noDhNotify();

    /**
     * 获取采购金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getProMoneyByProject(String projectId);

    /**
     * 获取作废采购采购列表
     *
     * @param proId 作废订单id
     * @return
     */
    List<ProMaterial> getDestroyList(String proId);

    /**
     * 更新入库数量
     *
     * @param item
     * @return
     */
    int updatePutSum(ProMaterial item);

    /**
     * 获取已采购总数
     * @param major 申请单明细行id
     * @return
     */
    Double getProSumByApplyMaterId(String major);

    /**
     * 更新不含税单价
     * @param pm
     * @return
     */
    int updatePrice(ProMaterial pm);

    /**
     * 查询项目采购单材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
