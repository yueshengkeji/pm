package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWorkLog;

import java.util.List;
import java.util.Map;

/**
 * 工作日志(ProWorkLog)表服务接口
 *
 * @author xiaoSong
 * @since 2021-06-25 14:52:55
 */
public interface ProWorkLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWorkLog queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWorkLog 实例对象
     * @return 实例对象
     */
    ProWorkLog insert(ProWorkLog proWorkLog);

    /**
     * 修改数据
     *
     * @param proWorkLog 实例对象
     * @return 实例对象
     */
    ProWorkLog update(ProWorkLog proWorkLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询工作日志
     *
     * @param param 查询参数
     * @return
     */
    List<ProWorkLog> queryByParam(Map<String, Object> param,Integer pageNum,Integer pageSize);

    /**
     * 通过筛选条件查询数据总数
     *
     * @param param 查询参数
     * @return 对象列表
     */
    Integer queryByParamCount(Map<String, Object> param);

    /**
     * 更新工作日记状态
     * @param log
     * @return
     */
    int updateState(ProWorkLog log);

    /**
     * 更新工作日记备注
     * @param log
     * @return
     */
    int updateRemark(ProWorkLog log);

    /**
     * 更新考核表id
     * @param id
     * @param perId
     */
    void updatePerId(Long id, String perId);

    void clearPerId(String perId);

    void updateScoreId(Long id,String scoreId);

    /**
     * 清除打分审批中标记
     * @param scoreId
     * @return
     */
    int clearScore(String scoreId);

    /**
     * 更新评分
     * @param log
     */
    void updateScore(ProWorkLog log);

    /**
     *
     * @param id
     * @return
     */
    List<ProWorkLog> queryByPerId(String id);

    List<ProWorkLog> queryByScoreId(String scoreId);

    void updateEditByPer(String id,Integer type);
}
