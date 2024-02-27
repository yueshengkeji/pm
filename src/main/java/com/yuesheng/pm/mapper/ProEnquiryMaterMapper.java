package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProEnquiryMater;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-07-01.
 * @author XiaoSong
 * @date 2019/07/01
 */
@Mapper
public interface ProEnquiryMaterMapper {
    /**
     * 添加询价单明细
     *
     * @param mater
     * @return
     */
    int insert(ProEnquiryMater mater);

    /**
     * 更新询价单最后修改日期
     *
     * @param date 最后修改日期
     * @param id   询价单明细id
     * @return
     */
    int updateLastDate(@Param("date") Date date, @Param("id") String id);

    /**
     * 获取询价单集合
     *
     * @param param {apply:申请单id,enquiryId:询价单主体id,str:检索字符串,sort:排序规则}
     * @return
     */
    List<ProEnquiryMater> queryList(Map<String, Object> param);

    /**
     * 获取询价单明细
     *
     * @param id 询价单明细id
     * @return
     */
    ProEnquiryMater queryById(String id);

}
