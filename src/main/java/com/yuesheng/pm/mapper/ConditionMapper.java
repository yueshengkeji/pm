package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Condition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-08-26 流程判断条件mapper.
 * @author XiaoSong
 * @date 2019/08/26
 */
@Mapper
public interface ConditionMapper {
    /**
     * 获取判断条件
     *
     * @param courseId 流程过程id
     * @return
     */
    List<Condition> getByCourseId(String courseId);

    /**
     * 添加条件
     *
     * @param condition
     * @return
     */
    int insert(Condition condition);

    /**
     * 修改条件
     *
     * @param condition
     * @return
     */
    int update(Condition condition);
}
