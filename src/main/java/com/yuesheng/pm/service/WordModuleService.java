package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.WordModule;

import java.util.List;

/**
 * Created by 96339 on 2017/6/3 办文模板服务.
 */
public interface WordModuleService {
    /**
     * 添加模板
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
    List<WordModule> searchModule(String str);

    /**
     * 获取模板目录
     * @param folderId 模板目录主键
     * @return
     */
    Folder queryFolder(Object folderId);

    /**
     * 添加办文模板目录对象
     * @param f
     */
    void insertFolder(Folder f);

    /**
     * 获取办文模板集合
     * @param afId 办文目录id
     * @return
     */
    List<WordModule> queryByAF(String afId);

    /**
     * 删除模板
     *
     * @param id 模板主键
     * @return
     */
    int deleteModule(String id);

    /**
     * 更新模板
     *
     * @param module
     * @return
     */
    int update(WordModule module);

    /**
     * 通过模板目录获取html模板
     *
     * @param id 目录id
     * @return
     */
    WordModule getWordModuleHtml(String id);
}
