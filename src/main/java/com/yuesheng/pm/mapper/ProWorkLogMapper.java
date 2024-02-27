package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWorkLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工作日志(ProWorkLog)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-06-25 14:52:55
 */
@Mapper
public interface ProWorkLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWorkLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 新增数据
     *
     * @param proWorkLog 实例对象
     * @return 影响行数
     */
    int insert(ProWorkLog proWorkLog);

    /**
     * 修改数据
     *
     * @param proWorkLog 实例对象
     * @return 影响行数
     */
    int update(ProWorkLog proWorkLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 通过筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<ProWorkLog> queryAll(Map<String, Object> param);

    /**
     * 通过筛选条件查询数据总数
     *
     * @param param 查询参数
     * @return 对象列表
     */
    Integer queryAllCount(Map<String, Object> param);

    /**
     * 更新考核表id
     * @param id
     * @param perId
     */
    void updatePerId(@Param("id") Long id,@Param("perId") String perId);

    void clearPerId(String perId);

    void updateScoreId(@Param("id") Long id,@Param("scoreId") String scoreId);
    /**
     * 更新评分
     * @param log
     */
    void updateScore(ProWorkLog log);

    /**
     * 查询
     * @param id
     * @return
     */
    List<ProWorkLog> queryByPerId(String id);

    List<ProWorkLog> queryByScoreId(String scoreId);

    /**
     * 清除考核信息审批标记
     * @param id
     */
    void updateEditByPer(@Param("perId") String id,@Param("type") Integer type);

    /**
     * 清除打分审批标记
     * @param scoreId
     * @return
     */
    int clearScore(String scoreId);
}
