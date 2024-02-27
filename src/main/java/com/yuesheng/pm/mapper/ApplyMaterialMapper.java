package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06 采购申请单材料mapper.
 * @author XiaoSong
 * @date 2016/08/06
 */
@Mapper
public interface ApplyMaterialMapper {
    /**
     * 根据申请单id获取未采购的材料集合
     * @param applyid 申请单id
     * @return 材料集合
     */
    List<ApplyMaterial> getApplyMaterials(String applyid);

    /**
     * 更新采购申请单材料状态
     * @param params {ySum:已采购数量,applyId:采购申请单id，materCoding:材料编码,cnfParam:配置参数}
     * @return
     */
    Integer updateMaterial(Map<String,Object> params);

    /**
     * 根据申请单id ，获取已采购和未采购总数
     * @param s 申请单id
     * @return 已采购、未采购数量
     */
    Map<String,BigDecimal> getMaterSums(@Param("applyId") String s);

    /**
     * 更新采购订单材料入库字段
     * @param params {sum：入库数量，date：入库时间，#{proId：订单id，#{materId：订单材料主键id}}}
     * @return 影响的行数
     */
    Integer updatePutSum(Map<String,Object> params);

    /**
     * 根据申请单id获取所有材料集合，包括采购过的
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
    List<ApplyMaterial> getMaterByProid(@Param("materId") String materId, @Param("projectId") String projectId);

    /**
     * 更新行备注
     * @param id 主键
     * @param remark
     * @return
     */
    int updateRemark(@Param("id") String id,@Param("remark") String remark);

    /**
     * 获取申请单材料对象
     * @param id 材料行id
     * @return
     */
    ApplyMaterial getMaterById(String id);

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
     * 判断该材料是否被申请单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterialByMaterId(@Param("id") String id);

    /**
     * 通过计划单行id查询申请记录列表
     *
     * @param planRowId 计划单行id
     * @return
     */
    List<ApplyMaterial> listByPlanRowId(@Param("planRowId") String planRowId);

    /**
     * 获取申请单明细行列表（不加载材料信息）
     * @param applyId 申请单id
     * @return
     */
    List<ApplyMaterial> getByApplyId(@Param("applyId") String applyId);

    /**
     * 更新已采购数量
     * @param am
     * @return
     */
    int updateProSum(ApplyMaterial am);

    /**
     * 删除申请单明细
     * @param applyId 申请单id
     * @return
     */
    int deleteByApply(@Param("applyId") String applyId);

    /**
     * 查询项目材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
