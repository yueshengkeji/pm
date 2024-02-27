package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.WordModule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 96339 on 2017/6/3 办文模板mapper.
 * @author XiaoSong
 * @date 2017/06/03
 */
@Mapper
public interface WordModuleMapper {
    /**
     * 添加模板
     * @param module 模板对象
     */
    void insertModule(WordModule module);

    /**
     * 获取所有模板（不包括模板）
     * @return
     */
    List<WordModule> querySimples();

    /**
     * 获取所有模板 （包括数据byte[]）
     * @return
     */
    List<WordModule> queryAll();

    /**
     * 获取模板对象
     * @param id 主键
     * @return
     */
    WordModule queryById(String id);

    /**
     * 获取模板集合
     * @param folder 目录id
     * @return
     */
    List<WordModule> queryByFolder(String folder);

    /**
     * 获取模板集合
     * @param str 检索串
     * @return
     */
    List<WordModule> searchModule(@Param("str") String str);

    /**
     * 删除模板
     * @param id 模板主键
     * @return
     */
    int deleteModule(String id);

    /**
     * 获取模板目录对象
     * @param folderId 目录主键
     * @return
     */
    Folder queryFolder(@Param("folderId") Object folderId);

    /**
     * 添加办文模板目录
     * @param f 办文模板目录对象
     */
    void insertFolder(Folder f);

    /**
     * 获取办文模板集合
     *
     * @param afId 办文目录id
     * @return
     */
    List<WordModule> queryByAF(@Param("afId") String afId);

    /**
     * 更新模板
     *
     * @param module
     * @return
     */
    int update(WordModule module);
}
