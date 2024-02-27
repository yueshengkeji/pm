package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SaleData;
import com.yuesheng.pm.model.DateGroupModel;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (SaleData)表服务接口
 *
 * @author xiaosong
 * @since 2023-06-26 15:31:14
 */
public interface SaleDataService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleData queryById(String id);

    /**
     * 分页查询
     *
     * @param saleData 筛选条件
     * @return 查询结果
     */
    List<SaleData> queryByPage(SaleData saleData);

    /**
     * 新增数据
     *
     * @param saleData 实例对象
     * @return 实例对象
     */
    SaleData insert(SaleData saleData);

    /**
     * 修改数据
     *
     * @param saleData 实例对象
     * @return 实例对象
     */
    SaleData update(SaleData saleData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 批量添加
     * @param saleData
     * @return
     */
    int insertByArray(ArrayList<SaleData> saleData);

    /**
     * 查询销售数据
     * @param query
     * @return
     */
    List<SaleData> queryAll(HashMap<String, Object> query);

    /**
     * 查询销售金额合计
     * @param query
     * @return
     */
    Double queryMoney(HashMap<String, Object> query);

    /**
     * 通过日期分组统计销售金额
     * @param param {saleStartDate:开始日期,saleEndDate:截止日期}
     * @return
     */
    List<DateGroupModel> queryMoneyGroupSaleDate(HashMap<String, Object> param);

    /**
     * 按天和品牌统计数据
     * @param saleStartDate 开始日期
     * @param saleEndDate 截止日期
     * @return
     */
    List<DateGroupModel> queryByDayTopList(String saleStartDate, String saleEndDate);

    /**
     * 按品牌统计数据
     * @param saleStartDate 开始日期
     * @param saleEndDate 截止日期
     * @return
     */
    List<DateGroupModel> queryByDateTopList(@Param("saleStartDate")String saleStartDate,
                                            @Param("saleEndDate") String saleEndDate);
}
