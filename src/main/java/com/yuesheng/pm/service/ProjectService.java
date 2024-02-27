package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ProjectMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06 项目服务.
 */
public interface ProjectService{
    /**
     * 根据项目id获取项目对象
     * @param id 项目id
     * @return
     */
    Project getProjectByid(String id);

    /**
     * 获取最近出库的20个项目
     * @return 最近出库的20个项目
     */
    List<Project> getProjectByLaately();


    /**
     * 获取出库最多的20个项目
     * @return 项目id总数和使用次数集合
     */
    List<Count> getOutPrijectMax();

    /**
     * 检索项目
     * @param str 检索字符串
     * @return 项目集合
     */
    List<Project> seek(String str);

    /**
     * 根据合同id获取项目集合
     * @param contractId 合同id
     * @return 项目集合
     */
    List<Project> getProjectByCon(String contractId);
    /**
     * 获取项目材料使用信息
     *
     * @param id   项目id
     * @param type 材料使用类型
     * @return 材料与使用情况 modal
     */
    List<ProjectMaterial> getMaterUseMsg(String id,int type);

    /**
     * 添加项目
     * @param p 项目对象
     */
    void insert(Project p) throws Exception;

    /**
     * 获取项目目录对象
     * @param folderId 目录主键
     * @return
     */
    Folder getFolderById(String folderId);

    /**
     * 获取项目目录集合
     * @param str 检索串
     * @return
     */
    List<Folder> queryFolders(String str);

    /**
     * 获取所有项目集合数据
     * @return
     */
    List<Project> getAllProjects();

    /**
     * 根据条件获取数据条数
     * @param params 参考getProjects()参数
     * @return
     */
    int getProjectsCount(Map<String,Object> params);

    /**
     * 获取材料计划单集合
     * @param params str 检索字符串（可选）
     *               pageIndex 页码  pageNumber 数据数量 sortName  排序名称
     *               sortOrder 排序类型 ASC(升序) | DESC（降序）
     * @return
     */
    List<Project> getProjects(Map<String, Object> params, Integer pageNum,Integer pageSize);


    /**
     * 获取领用数量最多的10个项目
     *
     * @param bounds
     * @return
     */
    List<Map<String, Object>> getMaterialTopTen(String beginDate, String endDate, Integer pageNum,Integer pageSize);

    /**
     * 添加项目
     *
     * @param p     项目对象
     * @param files 文件数组
     * @param staff 添加人员
     */
    void insert(Project p, String[] files, Staff staff) throws Exception;

    /**
     * 删除项目
     *
     * @param id 项目id
     * @return
     */
    int delete(String id);

    /**
     * 通过项目id查询该项目有多少个项目经理下过采购申请单
     *
     * @param id 项目id
     * @return
     */
    List<Staff> getProjectManager(String id);

    /**
     * 检索已审核项目
     *
     * @param str
     * @return
     */
    List<Project> seekByApprove(String str);

    /**
     * 通过项目名称精确查找项目
     *
     * @param name
     * @return
     */
    Project getProjectByName(String name);

    int updateApprove(String id);

    /**
     * 更新项目审核状态
     *
     * @param frameId 窗口id
     * @param state   状态
     * @return
     */
    int updateApprove(String frameId, String state);

    /**
     * 同步项目耗材数据
     *
     * @param id 项目id
     * @return
     */
    List<ProjectMaterial> syncData(String id);

    /**
     * 任务通知
     *
     * @param projectId 项目id
     * @param name      任务名称
     * @param taskId    任务id
     */
    void notifyUser(String projectId, String name, String taskId);

    /**
     * 修改项目
     *
     * @param project
     * @return
     */
    int update(Project project) throws Exception;

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
     * @param staffId 员工id
     * @return
     */
    List<ProjectAuth> getProjectAuthByStaff(String staffId);

    /**
     * 查询指定员工授权的项目列表
     * @param str 检索字符串
     * @param staffId 员工id
     * @return
     */
    List<Project> seek(String str, String staffId);
}
