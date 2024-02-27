package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Overtime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/5/10 加班申请单mapper.
 * @author XiaoSong
 * @date 2017/05/10
 */
@Mapper
public interface OvertimeMapper {
    /**
     * 添加加班申请单
     *
     * @param overtime
     */
    void insert(Overtime overtime);

    /**
     * 删除加班生清单
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 获取加班申请单
     *
     * @param id
     * @return
     */
    Overtime queryById(String id);

    /**
     * 获取加班申请单集合
     *
     * @param params {searchText:检索串,staffId:职员id,begin:开始时间，end:截止时间，order:排序方式}
     * @return
     */
    List<Overtime> getByParam(Map<String, Object> params);

    /**
     * 根据参数查询数据总数
     * @param params 参考 getByParam()
     * @return
     */
    int getCountByParam(Map<String,Object> params);

    /**
     * 更新请假单审批状态
     * @param o
     * @return
     */
    int approve(Overtime o);
}
