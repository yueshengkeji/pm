package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProTaskProgressReport;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProTaskProgressReportMapper
 * @Description
 * @Author ssk
 * @Date 2022/9/14 0014 9:06
 */
@Mapper
public interface ProTaskProgressReportMapper {

    /**
     * 新增汇报
     * @param proTaskProgressReport
     * @return
     */
    int insert(ProTaskProgressReport proTaskProgressReport);

    /**
     * 删除汇报
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 获取所有任务汇报列表
     * @return
     */
    List<ProTaskProgressReport> selectAllForPage();

    /**
     * 计数
     * @return
     */
    Integer selectAllCounts();

    /**
     * 根据项目名称查询
     * @param projectName
     * @return
     */
    List<ProTaskProgressReport> selectByPro(String projectName);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProTaskProgressReport selectById(String id);

}
