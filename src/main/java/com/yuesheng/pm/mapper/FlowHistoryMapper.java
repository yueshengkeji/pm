package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FlowHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2016-08-16 流程记录mapper.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Mapper
public interface FlowHistoryMapper {
    /**
     * 添加流程使用记录
     *
     * @param flowHistory 记录对象
     * @return 影响的行数
     */
    Integer addHistory(FlowHistory flowHistory);

    /**
     * 根据流程名称，查询第一个流程记录
     *
     * @param name 流程名称
     * @return
     */
    FlowHistory getFlowByName(String name);
}
