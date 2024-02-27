package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.City;
import com.yuesheng.pm.entity.Region;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 96339 on 2016/11/19 地区对象mapper  sdpj015.
 *
 * @author XiaoSong
 * @date 2016/11/19
 */
@Mapper
public interface RegionMapper {
    /**
     * 获取所有地区
     *
     * @return 地区集合，最多100条
     */
    List<Region> getRegions(@Param("name") String name);

    /**
     * 通过地区id获取地区对象
     *
     * @param id
     * @return
     */
    Region getCityById(String id);

    /**
     * 添加地区
     *
     * @param city 地区对象
     * @return 影响的行数
     */
    int insert(Region city);

    /**
     * 修改地区
     *
     * @param region
     * @return
     */
    int update(Region region);

    int insert(City city);

    List<City> getCitys();

    City selectByName(@Param("name") String name);
}
