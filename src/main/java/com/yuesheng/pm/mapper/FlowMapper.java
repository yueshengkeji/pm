package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.Folder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-15 流程对象mapper.
 * @author XiaoSong
 * @date 2016/08/15
 */
@Mapper
public interface FlowMapper {
    /**
     * 通过窗体代码获取该窗体所有流程
     * @param coding 窗体代码
     * @return 流程对象集合
     */
    List<Flow> getFlowByFrameCoding(String coding);

    /**
     * 根据流程id获取该流程所在的目录编码
     * @param id 流程id
     * @return 流程文件夹编码
     */
    Map<String,String> getFlorderARemarkAName(String id);

    /**
     * 通过办文目录-----获取流程集合
     * @param folderId    办文目录id
     * @return
     */
    List<Flow> getFlowByAF(String folderId);

    /**
     * 获取流程对象
     * @param id 流程对象id
     * @return
     */
    Flow getFlowById(String id);

    /**
     * 获取流程根目录集合
     * @param parent 可指定上级目录id
     * @return
     */
    List<Folder> getRootFolder(String parent);

    /**
     * 获取流程集合
     * @param folderId 目录id
     * @return
     */
    List<Flow> getFlowByFolder(String folderId);

    /**
     * 添加流程
     * @param flow
     */
    void insert(Flow flow);

    /**
     * 更新流程
     * @param flow
     */
    void update(Flow flow);

    /**
     * 参考FlowService.insertAuth方法
     * @param params
     */
    void insertAuth(Map<String, String> params);

    /**
     * 获取流程集合
     * @param name  流程目录
     * @return
     */
    List<Flow> getFLowByName(@Param("name") String name);

    /**
     * 删除流程
     * @param id
     */
    void delete(String id);

    /**
     * 获取目录
     * @param folderId 目录id
     * @return
     */
    Folder getFolderById(@Param("folderId") String folderId);

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
    List<Folder> seekFolder(@Param("str") String str);

    /**
     * 获取所有流程
     *
     * @param name 检索流程名称
     * @return
     */
    List<Flow> getAllFlow(@Param("name") String name);

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
    int updateLastUse(@Param("id") String id,@Param("datetime") String datetime);
}
