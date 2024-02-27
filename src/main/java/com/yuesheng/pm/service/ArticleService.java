package com.yuesheng.pm.service;


import com.yuesheng.pm.entity.Article;
import com.yuesheng.pm.entity.ArticleFolder;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ArticleModel;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/24 办文服务接口.
 */
public interface ArticleService {
    /**
     * 获取办文对象
     * @param id 办文id
     * @return
     */
    Article getSimpleById(String id);

    /**
     * 获取办文对象（包含数据流对象）
     * @param id id
     * @return
     */
    Article getArticle(String id);

    /**
     * 获取办文对象
     *
     * @param id 办文主键
     * @return
     */
    Article getDataById(String id);

    /**
     * 获取办文中单据集合
     * @param folder 办文目录主键
     * @return
     */
    List<Article> getArticleByFolder(String folder);

    /**
     * 获取办文集合
     * @param bounds 分页
     * @param params {folder:办文目录主键，searchText:检索串，begin:开始日期，end:截止日期，order:排序，staffId:职员id}
     * @return
     */
    List<Article> getArticleByParam(Integer pageNum,Integer pageSize ,Map<String, Object> params);

    /**
     * 添加办文对象
     * @param article
     */
    void insert(Article article);

    /**
     * 获取办文对象集合
     * @param folder 办文文件夹id
     * @param begin 开始时间
     * @param end 截止时间
     * @return
     */
    List<Article> getArticleByFD(String folder, String begin, String end);

    /**
     * 获取办文目录集合
     * @param parent 父节点主键
     * @return
     */
    List<ArticleFolder> getFolders(String parent);

    /**
     * 根据条件获取办文总条目
     * @param params
     * @return
     */
    int getCountByParam(Map<String, Object> params);

    /**
     * 删除办文单据
     * @param id 办文主键
     */
    void delete(String id);

    /**
     * 修改办文目录
     * @param af 办文目录对象
     */
    void updateAF(ArticleFolder af);

    /**
     * 添加办文目录
     * @param af
     */
    void addFolder(ArticleFolder af);

    /**
     * 获取办文目录
     * @param id 目录id
     * @return
     */
    ArticleFolder getFolderById(String id);

    /**
     * 更新办文对象
     *
     * @param article 办文
     */
    void updateArticle(Article article);

    /**
     * 添加我的文档
     *
     * @param article 文档对象
     * @param staff   职员对象
     * @return
     */
    Article insert(ArticleModel article, Staff staff);

    /**
     * 修改我的办文
     *
     * @param model
     * @param staff
     * @return
     */
    Article updateArticle(ArticleModel model, Staff staff);

    /**
     * 删除办文目录
     *
     * @param id 目录id
     * @return
     */
    int deleteFolder(String id);

    /**
     * 检索办文目录
     *
     * @param str
     * @return
     */
    List<ArticleFolder> searchFolder(String str);

    /**
     * 办文转html
     * @param id
     * @param isReload
     * @return
     */
    String docToHtml(String id, String isReload);

    /**
     *
     * @param id
     * @param staff
     * @return
     */
    Map<String,Object> delete(String id, Staff staff);

    void approve(FlowMessage msg);
}
