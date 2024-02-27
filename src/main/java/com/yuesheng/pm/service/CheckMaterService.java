package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CheckMater;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 *
 * @author XiaoSong
 * @date 2017/3/1 盘点主单据服务接口
 */
public interface CheckMaterService {
    /**
     * 添加盘点单
     * @param mater
     */
    void addMater(CheckMater mater);

    /**
     * 更新盘点单信息
     * @param mater
     * @return 影响的行数
     */
    int updateCheckMater(CheckMater mater);

    /**
     * 删除盘点单信息
     * @param id
     * @return
     */
    int deleteCheckMater(String id);
    /**
     * 获取盘点单集合
     * @param params
     * @return
     */
    List<CheckMater> getCheckMater(Map<String,Object> params);

    /**
     * 获取盘点单总数
     * @param params
     * @return
     */
    int getCheckMaterCount(Map<String, Object> params);

    /**
     * 获取盘点单主对象
     *
     * @param id
     * @return
     */
    CheckMater getCheckById(String id);

    /**
     * 添加盘点单，自动审核
     *
     * @param check 盘点单
     * @param staff 审核人员
     * @return
     */
    CheckMater addMater(CheckMater check, Staff staff);

    /**
     * 审核、反审核盘点单(根据传入的盘点单状态判断审核、反审核操作)
     *
     * @param checkMater 盘点单
     * @param staff      审核人
     * @return
     */
    Map<String, Object> approve(CheckMater checkMater, Staff staff);
}
