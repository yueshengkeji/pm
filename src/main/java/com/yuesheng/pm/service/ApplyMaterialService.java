package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.model.ProjectMaterial;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author XiaoSong
 * @date 2016-08-06 申请单材料服务
 */
public interface ApplyMaterialService {
    /**
     * 根据采购申请单id获取未采购材料集合
     * @param applyid 采购申请单id
     * @return 材料集合
     */
    List<ApplyMaterial> getApplyMaterials(String applyid);

    /**
     * 更新申请单已采购数量
     * @param primentId 申请单材料id
     * @param sum 采购数量
     * @return 影响的行数
     */
    int updateMaterProSum(String primentId, Double sum);

    /**
     * 根据申请单id获取已采购和未采购总数
     * @param s 申请单id
     * @return 已采购和未采购数量
     */
    Map<String,BigDecimal> getMaterSums(String s);
    /**
     * 更新采购订单材料入库字段
     * @param params {sum：入库数量，date：入库时间，#{proId：订单id，#{materId：订单材料主键id}}}
     * @return 影响的行数
     */
    Integer updatePutSum(Map<String,Object> params);

    /**
     * 获取申请单所有材料，包括采购过的
     * @param applyId 申请单id
     * @return 材料集合
     */
    List<ApplyMaterial> getMaterAllByApply(String applyId);
    /**
     * 获取某个材料的简要申请集合信息
     * @param materId 材料id
     * @param projectId 项目id
     * @return 材料集合
     */
    List<ApplyMaterial> getMaterByProid(String materId, String projectId);

    /**
     * 更新行备注
     * @param id
     * @param remark
     * @return
     */
    int updateRemark(String id, String remark);

    /**
     * 获取申请单材料对象
     * @param major 申请单材料行id
     * @return
     */
    ApplyMaterial getMaterById(String major);

    /**
     * 添加申请单材料
     * @param material
     */
    void addMater(ApplyMaterial material);

    /**
     * 更新申请单材料信息
     *
     * @param am 申请单材料对象
     */
    void updateMater(ApplyMaterial am);

    /**
     * 判断材料是否被申请单引用过
     *
     * @param id 材料id
     * @return
     */
    Material getMaterialByMaterId(String id);

    /**
     * 通过计划单行id查询申请记录列表
     *
     * @param planRowId 计划单行id
     * @return
     */
    List<ApplyMaterial> listByPlanRowId(String planRowId);

    /**
     * 获取申请单明细行列表（不加载材料信息）
     * @param applyId 申请单id
     * @return
     */
    List<ApplyMaterial> getByApplyId(String applyId);

    int updateProSum(ApplyMaterial am);

    /**
     * 删除申请单材料
     * @param applyId 申请单id
     * @return
     */
    int deleteByApply(String applyId);

    /**
     * 查询项目材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
