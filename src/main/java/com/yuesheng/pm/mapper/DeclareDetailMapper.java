package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DeclareDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/26 报销单详细科目mapper.
 * @author XiaoSong
 * @date 2016/12/26
 */
@Mapper
public interface DeclareDetailMapper {
    /**
     * 根据报销单主对象获取报销详情集合
     * @param declareId 报销单主id
     * @return
     */
    List<DeclareDetail> getDetailByDeclare(@Param("declareId") String declareId);

    /**
     * 获取报销单明细集合
     * @param params {courseId:科目id,begin:开始时间，end:截止时间}
     * @return
     */
    List<DeclareDetail> getByCourse(Map<String, String> params);

    /**
     * 添加报销明细
     * @param dd
     */
    void insert(DeclareDetail dd);

    /**
     * 删除报销明细
     * @param mainId
     */
    void delete(String mainId);

    /**
     * 获取科目报销金额总和
     *
     * @param start      开始时间
     * @param end        截止时间
     * @param pageBounds
     * @param courseId   科目id,不指定则查询所有
     * @return
     */
    List<Map<String, Object>> getMoneyByCourse(@Param("start") String start, @Param("end") String end, @Param("courseId") String courseId);
}
