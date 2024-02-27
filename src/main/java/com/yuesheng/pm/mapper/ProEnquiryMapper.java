package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProEnquiry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-07-01.
 * @author XiaoSong
 * @date 2019/07/01
 */
@Mapper
public interface ProEnquiryMapper {
    /**
     * 添加询价单主体
     *
     * @param enquiry 询价单对象
     * @return
     */
    int insert(ProEnquiry enquiry);

    /**
     * 修改询价单
     *
     * @param proEnquiry 询价单主体
     * @return
     */
    int updateEnquiry(ProEnquiry proEnquiry);

    /**
     * 关闭询价单
     *
     * @param state 1=关闭，0=开启
     * @param id    询价单主键
     * @return
     */

    int closeEnquiry(@Param("state") int state, @Param("id") String id);

    /**
     * 查询询价单集合
     *
     * @param param      {applyId:申请单id,isClose:是否关闭，staffId:职员id,startDate:开始日期,endDate:截止日期,str:检索字符串}
     * @return
     */
    List<ProEnquiry> queryEnquiryList(Map<String, Object> param);

    /**
     * 查询询价单总条目数
     *
     * @param param {applyId:申请单id,isClose:是否关闭，staffId:职员id,startDate:开始日期,endDate:截止日期,str:检索字符串}
     * @return
     */
    Integer queryEnquiryCount(Map<String, Object> param);

    /**
     * 获取询价单主体和询价单明细
     *
     * @param id 询价单id
     * @return
     */
    ProEnquiry queryEnquiry(String id);

    /**
     * 查询询价单集合
     *
     * @param param      {applyId:申请单id,isClose:是否关闭，staffId:职员id,startDate:开始日期,endDate:截止日期,str:检索字符串}
     * @return
     */
    List<ProEnquiry> queryEnquiryListV2(Map<String, Object> param);
}
