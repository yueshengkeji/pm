package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Overtime;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/5/10 加班服务.
 */
public interface OvertimeService {
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
    List<Overtime> getByParam( Map<String, Object> params);

    /**
     * 根据参数查询数据总数
     * @param params 参考 getByParam()
     * @return
     */
    int getCountByParam(Map<String,Object> params);

    /**
     * 审核加班单
     * @param id
     * @return
     */
    int approve(String id);

    /**
     * 审核加班单
     * @param item
     * @return
     */
    int approve(Overtime item);

    /**
     * 更新审批状态
     * @param startDate 开始时间范围
     * @param endDate 截止时间范围
     * @return
     */
    int updateState(String startDate, String endDate);
}
