package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProQuote;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-07-03 报价单服务.
 */
public interface ProQuoteService {
    /**
     * 添加报价单
     *
     * @param proQuote 报价单明细对象
     * @return
     */
    int insert(ProQuote proQuote);

    /**
     * 查询报价单集合 (询价单id)
     *
     * @param enquiryId 询价单id
     * @return
     */
    List<ProQuote> queryByEnquiry(String enquiryId);

    /**
     * 查询报价单集合（材料字符串检索）
     *
     * @param materStr   材料字符串
     * @param materId    材料id
     * @return
     */
    List<ProQuote> queryByMater(String materStr, String materId, Integer pageNum,Integer pageSize);

    /**
     * 查询报价单总数（材料字符串检索）
     *
     * @param materStr 材料字符串
     * @param materId  材料id
     * @return
     */
    List<ProQuote> queryByMaterCount(String materStr, String materId);

    /**
     * 查询报价单分组(询价单id)
     *
     * @param enquiryId 询价单id
     * @return
     */
    List<ProQuote> queryByGroup(String enquiryId);

    /**
     * 获取报价单集合
     *
     * @param param  {str:"关键字检索,order:排序方式,}
     * @return
     */
    List<ProQuote> queryByMater(Map<String, Object> param);

    /**
     * 获取报价单总数
     *
     * @param param {str:"关键字检索}
     * @return
     */
    int queryByMaterCount(Map<String, Object> param);

    /**
     * 查询报价单位数量
     *
     * @param id 询价单id
     * @return
     */
    List<Integer> queryCount(String id);
}
