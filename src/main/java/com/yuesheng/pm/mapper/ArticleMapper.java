package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Article;
import com.yuesheng.pm.entity.ArticleFolder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/24 办文mapper.
 * @author XiaoSong
 * @date 2016/12/24
 */
@Mapper
public interface ArticleMapper {
    /**
     * 获取办文对象
     * @param id 办文id
     * @return
     */
    Article getSimpleById(@Param("id") String id);

    /**
     * 获取办文对象（包含数据流对象）
     * @param id id
     * @return
     */
    Article getArticleById(@Param("id") String id);

    /**
     * 获取办文数据
     * @param id 办文id
     * @return
     */
    Article getDataById(@Param("id") String id);


    /**
     * 获取办文集合
     * @param params {folder:办文目录主键，searchText:检索串，begin:开始日期，end:截止日期，order:排序，staffId:职员id}
     * @return
     */
    List<Article> getArticleByParam( Map<String, Object> params);

    /**
     * 获取办文中单据集合
     * @param folder 办文目录主键
     * @return
     */
    List<Article> getArticleByFolder(String folder);

    /**
     * 获取条目总数
     * @param params 参考getArticleByParam()
     * @return
     */
    int getCountByParam(Map<String,Object> params);

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
    List<Article> getArticleByFolderDate(@Param("folder") String folder, @Param("begin") String begin, @Param("end") String end);

    /**
     * 获取办文目录集合
     * @param parent 父节点主键
     * @return
     */
    List<ArticleFolder> getFolders(@Param("parent") String parent);

    /**
     * 删除办文单据
     * @param id 办文主键
     */
    void delete(String id);

    /**
     * 修改办文目录
     * @param af 办文目录对象
     */
    void updateArticleFolder(ArticleFolder af);

    /**
     * 添加办文目录
     * @param af
     */
    void addFolder(ArticleFolder af);

    /**
     * 获取目录对象
     * @param id 主键id
     * @return
     */
    ArticleFolder getFolderById(String id);

    /**
     * 更新办文对象
     *
     * @param article 办文
     */
    void updateArticle(Article article);

    int deleteFolder(String id);

    List<ArticleFolder> searchFolder(@Param("str") String str);
}
