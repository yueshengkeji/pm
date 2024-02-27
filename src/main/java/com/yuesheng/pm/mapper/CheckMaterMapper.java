package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CheckMater;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/3/1 盘点单mapper.
 * @author XiaoSong
 * @date 2017/03/01
 */
@Mapper
public interface CheckMaterMapper {
    /**
     * 添加盘点单
     *
     * @param mater
     */
    void addMater(CheckMater mater);

    /**
     * 更新盘点单信息
     *
     * @param mater 盘点单材料对象
     * @return 影响的行数
     */
    int updateCheckMater(CheckMater mater);

    /**
     * 删除盘点单信息
     *
     * @param id
     * @return
     */
    int deleteCheckMater(String id);

    /**
     * 获取盘点单集合
     *
     * @param params 查询参数
     * @return
     */
    List<CheckMater> getCheckMater(Map<String, Object> params);

    /**
     * 获取盘点单总数
     * @param params
     * @return
     */
    int getCheckMaterCount(Map<String,Object> params);

    /**
     * 获取盘点单对象
     * @param id 主键id
     * @return
     */
    CheckMater getCheckById(String id);

    /**
     * 查询盘点单单号最大值
     * @param date 单据日期
     * @return
     */
    String getMaxCheckNumber(String date);
}
