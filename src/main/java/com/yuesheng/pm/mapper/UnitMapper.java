package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-12 材料单位mapper sdpa013.
 * @author XiaoSong
 * @date 2016/08/12
 */
@Mapper
public interface UnitMapper {
    /**
     * 通过单位id获取单位对象
     * @param id 单位id
     * @return
     */
    Unit getUnit(String id);

    /**
     * 通过材料id获取单位对象
     * @param materId
     * @return
     */
    Unit getUnitByMater(@Param("materId") String materId);

    /**
     * 根据单位名称获取单位对象
     * @param name 单位名称
     * @return
     */
    Unit isExist(@Param("name") String name);

    /**
     * 添加材料单位
     * @param unit
     */
    void addUnit(Unit unit);

    /**
     * 获取单位集合
     *
     * @param str 检索串
     * @return
     */
    List<Unit> seek(@Param("str") String str);

    /**
     * 添加材料与单位绑定
     * @param material
     * @return
     */
    int addUnitToMater(Material material);

    /**
     * 删除材料与单位的绑定
     * @param materId 材料id
     * @param id 单位id
     * @return
     */
    int deleteBind(@Param("materId") String materId,@Param("id") String id);

    /**
     * 查询材料所有单位列表
     * @param materId 材料id
     * @return
     */
    List<Unit> getUnitAllByMaterial(String materId);
}
