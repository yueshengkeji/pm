package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.City;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-11 收货地址对象.
 * @author XiaoSong
 * @date 2016/08/11
 */
@Mapper
public interface CityMapper {
    /**
     * 获取收货地址对象
     * @param id 地址id
     * @return 地址对象
     */
    City getCityById(String id);

    /**
     * 获取所有收货地址集合
     * @return 地址集合
     */
    List<City> getCitys();

    /**
     * 检索收货地址
     *
     * @param str 字符串
     * @return 地址集合
     */
    List<City> seek(@Param("str") String str);

    /**
     * 获取收货地址总数
     * @return 总数
     */
    int count();

    /**
     * 添加收获地址
     *
     * @param c
     */
    void insert(City c);
}
