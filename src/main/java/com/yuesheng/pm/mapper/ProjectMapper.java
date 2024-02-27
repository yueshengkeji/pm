package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.entity.ProjectAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-05 项目mapper.
 * @author XiaoSong
 * @date 2016/08/05
 */
@Mapper
public interface ProjectMapper {
    /**
     * 获取项目对象
     * @param id 项目id
     * @return
     */
    Project getProjectByid(String id);

    /**
     * 获取最近出库的20个项目
     * @param counts 常用统计信息对象集合
     * @return
     */
    List<Project> getProjectByLately(@Param("list") List<Count> counts);

    /**
     * 获取出库最多的20个项目
     * @return 项目id总数和使用次数集合
     */
    List<Count> getOutPrijectMax();


    /**
     * 检索项目集合
     * @param str 检索字符串
     * @return 检索到的集合
     */
    List<Project> seek(@Param("str") String str);

    /**
     * 根据合同id获取项目集合
     * @param contractId 合同id
     * @return 项目集合
     */
    List<Project> getProjectByCon(@Param("contractId") String contractId);

    /**
     * 添加项目
     * @param p 项目对象
     */
    void insert(Project p);

    /**
     * 获取项目目录对象
     * @param folderId 目录主键
     * @return
     */
    Folder getFolderById(@Param("folderId") String folderId);

    /**
     * 获取项目目录集合
     * @param str 检索串
     * @return
     */
    List<Folder> queryFolder(@Param("str") String str);

    /**
     * 获取所有项目集合
     * @return
     */
    List<Project> getAllProjects();

    /**
     * 获取计划单总条数
     * @param params 参考getPlans()方法参数
     * @return
     */
    int getProjectsCount(Map<String, Object> params);

    /**
     * 获取材料计划单集合
     * @param params str 检索字符串（可选）
     *               pageIndex 页码  pageNumber 数据数量 sortName  排序名称
     *               sortOrder 排序类型 ASC(升序) | DESC（降序）
     * @return
     */
    List<Project> getProjects(Map<String, Object> params);


    /**
     * 获取领用数量最多的10个项目
     *
     * @param map    参见ProjectService.getMaterialTopTen()
     * @param bounds 分页对象
     * @return
     */
    List<Map<String, Object>> getMaterialTopTen(Map<String, Object> map);

    /**
     * 删除项目
     *
     * @param id 项目id
     * @return
     */
    int delete(String id);

    /**
     * 获取项目目录
     *
     * @param name 目录名称
     * @return
     */
    Folder getFolderByName(@Param("name") String name);

    /**
     * 添加项目目录
     *
     * @param folder 目录对象
     * @return
     */
    int insertFolder(Folder folder);

    /**
     * 检索已审核项目
     *
     * @param str
     * @return
     */
    List<Project> seekByApprove(@Param("name") String str);

    /**
     * 通过项目名称查询项目
     *
     * @param name
     * @return
     */
    Project getProjectByName(@Param("name") String name);

    /**
     * 更新项目状态
     *
     * @param frameId 项目id
     * @param state   状态
     * @param datetime 审核时间
     * @return
     */
    int updateApprove(@Param("id") String frameId, @Param("state") String state,@Param("datetime") String datetime);

    /**
     * 修改项目
     *
     * @param project
     * @return
     */
    int update(Project project);

    /**
     * 添加项目授权
     * @param auth
     * @return
     */
    int insertProjectAuth(ProjectAuth auth);

    /**
     * 删除项目授权
     * @param id
     * @return
     */
    int deleteProjectAuth(@Param("id") String id);

    /**
     * 查询项目授权列表
     * @param projectId 项目id
     * @return
     */
    List<ProjectAuth> getProjectAuth(@Param("projectId") String projectId);

    /**
     * 查询员工已授权项目列表
     * @param staffId
     * @return
     */
    List<ProjectAuth> getProjectAuthByStaff(@Param("staffId") String staffId);

    /**
     * 查询指定员工授权的项目列表
     * @param str 检索字符串
     * @param staffId 员工id
     * @return
     */
    List<Project> seekByAuth(@Param("str") String str,@Param("staffId") String staffId);

    /**
     * 查询项目权限
     * @param id 项目id
     * @param staffId 员工id
     * @return
     */
    ProjectAuth getProjectAuthByPs(@Param("projectId") String id,@Param("staffId") String staffId);
}
