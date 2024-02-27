package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.OutMaterChildHistory;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.model.ProjectMaterial;

import java.util.List;
import java.util.Map;

/**
 * Created by 宋正根 on 2016/9/1 出库单材料服务接口.
 */
public interface OutMaterChildService {
    /**
     * 添加出库单材
     *
     * @param child 出库单材料对象
     * @return 影响的行数
     */
    Integer addOutMater(MaterOutChild child);

    /**
     * 添加出库材料关系对象
     */
    Integer addOutMaterHistory(OutMaterChildHistory history);

    /**
     * 获取数据库中最大的主键
     *
     * @return 最大的主键值
     */
    String getMaxHistory();

    /**
     * 根据出库材料主键id，删除该关系
     *
     * @param materOutId
     * @return
     */
    Integer deleteOutMaterHistory(String materOutId);

    /**
     * 更新出库材料记录信息
     *
     * @param outMaterChildHistory 出库材料关系对象
     * @return 影响的行数
     */
    Integer updateOutMaterHistory(OutMaterChildHistory outMaterChildHistory);

    /**
     * 根据出库材料主键获取该关系对象
     *
     * @param outMaterId 出库材料主键
     * @return 出库材料关系对象
     */
    OutMaterChildHistory getChildHistoryByMater(String outMaterId);

    /**
     * 更新出库单材料信息
     *
     * @param child 出库单材料对象
     * @return 影响的行数
     */
    Integer updateMaterChild(MaterOutChild child);

    /**
     * 通过出库单id获取出库材料集合
     *
     * @param outId 出库单id
     * @return 材料集合
     */
    List<MaterOutChild> getOutMatersByOutId(String outId);

    /**
     * 根据项目id获取出库材料集合
     *
     * @param projectId 项目id
     * @return 出库材料集合
     */
    List<MaterOutChild> getOutMatersByProject(String projectId);

    /**
     * 获取税率
     *
     * @param detailId 明细行主键
     * @return
     */
    Double getTax(String detailId);

    /**
     * 判断材料是否被出库单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterByMaterId(String id);

    /**
     * 获取材料出库信息
     *
     * @param id 材料id
     * @return
     */
    List<MaterOutChild> getMaterialUseByMaterId(String id);

    /**
     * 获取材料出库记录
     *
     * @param param {name:材料名称,model:型号,brand:品牌,projectId:项目id}
     * @return
     */
    List<MaterUseHistory> getOutMaterHistory(Map<String, Object> param);

    Integer getOutMaterHistoryCount(Map<String, Object> param);

    /**
     * 获取项目出库金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getOutMaterMoney(String projectId);

    /**
     * 通过出库单材料主键获取数据
     *
     * @param id
     * @return
     */
    MaterOutChild getOutMaterById(String id);

    /**
     * 删除出库材料
     *
     * @param materId
     * @return
     */
    int deleteOutMater(String materId);

    /**
     * 删除出库材料税率
     *
     * @param materId 出库单材料
     * @return
     */
    int deleteTax(String materId);

    /**
     * 获取最后一次出库记录
     *
     * @param materId 材料id
     * @return
     */
    MaterOutChild getLastOutMater(String materId);

    /**
     * 获取材料出库记录
     *
     * @param param {name:材料名称,model:型号,brand:品牌,projectId:项目id}
     * @return
     */
    Double getOutMaterHistoryMoney(Map<String, Object> param);

    /**
     * 查询项目出库材料集合
     * @param projectId 项目id
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
