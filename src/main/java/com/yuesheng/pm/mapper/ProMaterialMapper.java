package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.ProMaterial;
import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.model.CompanyModel;
import com.yuesheng.pm.model.ProMaterReport;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-12 采购订单材料mapper.
 *
 * @author XiaoSong
 * @date 2016/08/12
 */
@Mapper
public interface ProMaterialMapper {
    /**
     * 添加采购订单材料，订单id不能为null
     *
     * @param proMaterials 材料集合
     * @param proId        采购订单id
     * @return 影响的行数
     */
    Integer addMaterial(@Param("maters") List<ProMaterial> proMaterials, @Param("proId") String proId);

    /**
     * 添加采购订单材料，订单id不能为null
     *
     * @param proMaterials 材料集合
     * @param proId        采购订单id
     * @return 影响的行数
     */
    Integer addMaterial2(@Param("item") ProMaterial proMaterials, @Param("proId") String proId);

    /**
     * 删除订单中材料
     *
     * @param params {订单id，材料编码}
     * @return 影响的行数
     */
    Integer deleteMaterial(Map<String, String> params);

    /**
     * 根据订单id获取材料集合
     *
     * @param proId   采购订单id
     * @param discard 是否获取作废明细
     * @return 材料集合
     */
    List<ProMaterial> getMaterials(@Param("id") String proId, @Param("discard") String discard);

    /**
     * 获取未入库的订单材料集合
     *
     * @param id 订单id
     * @return 未入库材料集合
     */
    List<ProMaterial> getNotMatersByProId(@Param("id") String id);

    /**
     * 更新材料入库数量和入库时间
     *
     * @param materials 材料集合
     */
    void updatePutSum(@Param("materList") List<ProMaterial> materials);

    /**
     * 获取订单的材料数量和入库数量
     *
     * @param id 订单id
     * @return 采购数量和入库数量
     */
    Map<String, BigDecimal> getCount(@Param("id") String id);

    /**
     * 通过采购订单材料表主键id获取订单材料对象
     *
     * @param proMaterId 订单材料主键
     * @return 订单材料集合
     */
    ProMaterial getMatersById(@Param("id") String proMaterId);

    /**
     * 根据供应单位和截止时间，获取该单位采购的金额和时间集合
     *
     * @param params {companyId:单位id,start:开始时间，end:结束时间}
     * @return
     */
    List<ProPutForDetail> getProMoneyBuCompany(Map<String, Object> params);

    /**
     * 获取采购材料集合
     *
     * @param params{ companyId 单位id
     *                start 开始时间
     *                end 结束时间}
     * @return
     */
    List<ProMaterial> getProMaterByCompany(HashMap params);

    /**
     * 删除采购订单中材料集合
     *
     * @param id 采购订单id
     */
    void deleteByProId(@Param("id") String id);

    /**
     * 更新采购订单材料信息
     *
     * @param material 材料订单材料对象
     */
    void updateMater(ProMaterial material);

    /**
     * 删除订单中材料对象
     *
     * @param id 订单材料id
     * @return
     */
    int deleteMaterial(@Param("id") String id);

    /**
     * 添加材料到订单中
     *
     * @param material
     */
    void addMater(ProMaterial material);

    /**
     * 判断材料是否被采购单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterByMaterId(@Param("id") String id);

    /**
     * 获取采购最多的材料集合
     *
     * @param start
     * @param end   @return
     */
    List<ProjectMaterial> getMaterialByProMax(@Param("start") String start, @Param("end") String end);

    /**
     * 获取材料采购信息
     *
     * @param name  材料名称
     * @param model 材料型号
     * @param start 开始时间
     * @param end   截止时间
     * @return
     */
    Map<String, Object> getMaterialProInfo(@Param("name") String name, @Param("model") String model, @Param("start") String start, @Param("end") String end);

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
     * 获取购买金额最贵的单位集合
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
     * @param params {}
     * @return
     */
    List<ProMaterial> getHistoryPriceV2(Map<String, String> params);

    /**
     * 根据申请单材料明细id查询采购订单材料集合
     *
     * @param applyMaterialId 申请单明细id
     * @return
     */
    List<ProMaterial> getProMaterByApply(@Param("applyMaterialId") String applyMaterialId);

    /**
     * 添加作废材料明细
     *
     * @param proMaterials
     * @param proId
     */
    void addDiscardMater(@Param("maters") List<ProMaterial> proMaterials, @Param("proId") String proId);

    /**
     * 通过订单id获取材料明细
     *
     * @param proId 订单id
     * @return
     */
    List<ProMaterial> getMaterialsByProId(@Param("proId") String proId);

    /**
     * 获取采购材料列表
     *
     * @param param 查询参数
     * @return
     */
    List<ProMaterReport> getMaterialByParam(Map<String, Object> param);

    Map<String,Double> getMaterialByQueryCount(Map<String, Object> param);
    /**
     * 查询未到货的材料清单
     * @param query 查询条件：{searchText:材料字符串，projectId:项目id,dhDate:到货日期}
     * @return
     */
    List<ProMaterial> getNoDhList(HashMap<String, String> query);

    /**
     * 查询所有采购材料
     * @return
     */
    List<ProMaterial> queryAllProMater();

    /**
     * 更新到货日期
     *
     * @param material
     * @return
     */
    int updateDhDate(ProMaterial material);

    /**
     * 获取采购金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getProMoneyByProject(@Param("projectId") String projectId);

    /**
     * 获取作废材料列表
     *
     * @param proId 作废采购订单id
     * @return
     */
    List<ProMaterial> getDestroyList(@Param("proId") String proId);

    /**
     * 更新材料已入库数量
     *
     * @param item
     * @return
     */
    int updatePutSumV2(ProMaterial item);

    /**
     * 获取申请单采购总数
     * @param major 申请单明细id
     * @return
     */
    Double getProSumByApplyMaterId(@Param("major") String major);

    /**
     * 更新不含税单价
     * @param pm
     * @return
     */
    int updatePrice(ProMaterial pm);

    /**
     * 查询项目材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
