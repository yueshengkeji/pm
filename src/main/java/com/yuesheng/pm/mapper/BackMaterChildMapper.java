package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.BackMaterChild;
import com.yuesheng.pm.entity.Material;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/22 退料单详情mapper.
 * @author XiaoSong
 * @date 2017/02/22
 */
@Mapper
public interface BackMaterChildMapper {
    /**
     * 添加退料单详情
     * @param child 材料行对象
     */
    void addMater(BackMaterChild child);

    /**
     * 更新材料行信息
     * @param child 材料行对象
     * @return
     */
    int update(BackMaterChild child);

    /**
     * 删除材料对象
     * @param id 主键id
     * @return
     */
    int delete(@Param("id") String id);

    /**
     * 删除退料单所有材料
     * @param backId 退料单主对象id
     * @return
     */
    int deleteList(@Param("backId") String backId);

    /**
     * 获取退料单材料集合
     * @param backId 退料单主键
     * @return
     */
    List<BackMaterChild> getMaterByBack(@Param("backId") String backId);

    /**
     * 判断材料是否被退料单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterByMaterId(String id);

    /**
     * 获取材料退料信息集合
     *
     * @param id 材料id
     * @return
     */
    List<BackMaterChild> getMaterUseByMaterId(String id);

    /**
     * 获取退料金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getBackMoneyByProject(@Param("projectId") String projectId);

    /**
     * 查询退料单材料列表
     *
     * @param params {startDate:开始时间,endDate:截止时间,projects:项目id集合，格式：'项目id1','项目id2'}
     * @return
     */
    List<BackMaterChild> getMaterByParam(Map<String, String> params);

    /**
     * 获取退库金额合计
     * @param params
     * @return
     */
    Double getBackTotalMoney(Map<String,String> params);

    /**
     * 查询退库材料
     * @param outMater 出库材料主键
     * @return
     */
    List<BackMaterChild> getBackMaterByOutMater(@Param("outId") String outMater);
}
