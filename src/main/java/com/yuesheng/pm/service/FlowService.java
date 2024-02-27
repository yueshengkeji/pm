package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-15 流程对象服务接口.
 *
 */
public interface FlowService {
    /**
     * 通过窗体代号获取该窗体绑定的流程集合
     * @param coding 窗体代码
     * @return 该窗体流程对象集合
     */
    List<Flow> getFlowByFrameCoding(String coding);

    /**
     * 根据流程id获取该流程所在目录编码
     * @param flowid 流程id
     * @return 目录编码
     */
    Map<String,String> getFlorderARemarkAName(String flowid);

    /**
     * 获取流程集合
     * @param folderId 办文目录id
     * @return
     */
    List<Flow> getFlowByAF(String folderId);

    /**
     * 获取流程对象
     * @param id 主键id
     * @return
     */
    Flow getFlowById(String id);

    /**
     * 获取流程根目录集合
     * @return
     */
    List<Folder> getRootFolder(String parent);

    /**
     * 获取流程集合
     * @param folderId 目录主键
     * @return
     */
    List<Flow> getFlowByFolder(String folderId);

    /**
     * 添加流程
     *
     * @param flow  流程对象
     * @param staff 添加职员信息
     */
    Integer insert(Flow flow, Staff staff);

    /**
     * 更新流程
     * @param flow
     */
    void update(Flow flow);

    /**
     * 添加流程权限
     * @param params {flowId:流程id,authType:人员类型,staffId:人员类型主键，type:1=修改，0=使用}
     */
    void insertAuth(Map<String, String> params);

    /**
     * 获取流程集合
     * @param name 流程名称
     * @return
     */
    List<Flow> getFLowByName(String name);

    /**
     * 删除流程
     * @param id
     */
    void delete(String id);

    /**
     * 获取流程目录
     * @param folderId 目录id
     * @return
     */
    Folder getFolderById(String folderId);

    /**
     * 更新流程目录
     * @param folder
     * @return
     */
    int updateFolder(Folder folder);

    /**
     * 添加流程目录
     * @param folder
     * @return
     */
    void insertFolder(Folder folder);

    /**
     * 检索流程目录
     *
     * @param str 检索串
     * @return
     */
    List<Folder> seekFolder(String str);

    /**
     * 获取所有流程
     *
     * @param name 检索流程名称
     * @return
     */
    List<Flow> getAllFLow(String name);

    /**
     * 获取所有流程目录
     *
     * @return
     */
    List<Folder> getFolderAll();

    /**
     * 更新流程最后使用时间
     * @param id 流程id
     * @param datetime 使用时间
     * @return
     */
    int updateLastUse(String id, String datetime);
}
