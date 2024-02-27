package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProSend;

import java.util.List;
import java.util.Map;

/**
 * 发货申请单主体接口
 *
 * @author XiaoSong
 * @date 2019-12-09 10:35
 */
public interface ProSendService {

    /**
     * 添加发货申请单主体
     *
     * @param proSend
     */
    ProSend insert(ProSend proSend);

    /**
     * 修改发货申请单
     *
     * @param proSend
     * @return
     */
    int update(ProSend proSend);

    /**
     * 删除发货申请单
     *
     * @param id 主键
     * @return
     */
    int delete(String id);

    /**
     * 获取发货申请单
     *
     * @param id 主键
     * @return
     */
    ProSend queryById(String id);

    /**
     * 查询发货申请单主体
     *
     * @param params     {str:检索字符串,order:排序方式}
     * @return
     */
    List<ProSend> queryByParam(Map<String, Object> params,Integer pageNum,Integer pageSize);

    /**
     * 查询发货申请单总数
     *
     * @param params 参见 queryByParam() params
     * @return
     */
    Integer queryByParamCount(Map<String, Object> params);
}
