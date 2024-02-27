package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProQuote;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-07-03.
 * @author XiaoSong
 * @date 2019/07/03
 */
@Mapper
public interface ProQuoteMapper {
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
     * @param pageBounds 分页对象
     * @return
     */
    List<ProQuote> queryByMater(@Param("str") String materStr, @Param("id") String materId);

    /**
     * 查询报价单总条目（材料字符串检索）
     *
     * @param materStr 材料字符串
     * @param materId  材料id
     * @return
     */
    List<ProQuote> queryCount(@Param("str") String materStr, @Param("id") String materId);

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
    List<ProQuote> queryBySearch(Map<String, Object> param);

    /**
     * 获取报价单总数
     *
     * @param param {str:"关键字检索}
     * @return
     */
    int queryCount(Map<String, Object> param);

    /**
     * 获取报价单位数量
     *
     * @param enquiryId 询价单id
     * @return
     */
    List<Integer> queryQuoteCount(@Param("enquiryId") String enquiryId);
}
