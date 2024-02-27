package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.entity.ProZujinYt;

import java.util.HashMap;
import java.util.List;

/**
 * (ProZujinHouse)表服务接口
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
public interface ProZujinHouseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinHouse queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinHouse> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proZujinHouse 实例对象
     * @return 实例对象
     */
    ProZujinHouse insert(ProZujinHouse proZujinHouse);

    /**
     * 修改数据
     *
     * @param proZujinHouse 实例对象
     * @return 实例对象
     */
    ProZujinHouse update(ProZujinHouse proZujinHouse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * @param params
     * @return
     */
    List<ProZujinHouse> queryByParam(HashMap<String, Object> params,Integer pageNum,Integer pageSize);

    Integer queryCountByParam(HashMap<String, Object> params);

    List<String> queryFloor();

    /**
     * 添加租金与商铺关系表
     *
     * @param houseList
     * @return
     */
    int insertHouseRelation(List<ProZujinHouse> houseList, ProZujin zujin);

    /**
     * 通过租金id获取商铺列表
     *
     * @param zujinId
     * @return
     */
    List<ProZujinHouse> getHouseByZujin(Integer zujinId);

    /**
     * 通过租金id删除与商铺关联
     *
     * @param zujinId
     * @return
     */
    int deleteByZujin(Integer zujinId);

    int releaseByZujin(Integer zujinId);

    int updateHouseYetai(ProZujinYt yt, Integer id);
}