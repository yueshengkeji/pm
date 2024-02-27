package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Article;
import com.yuesheng.pm.entity.ProApplyDine;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.mapper.ProApplyDineMapper;
import com.yuesheng.pm.restapi.ProBidApi;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.ProApplyDineService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.poi.EncryptedDocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * (ProApplyDine)就餐申请表服务实现类
 *
 * @author xiaoSong
 * @since 2022-08-15 16:23:56
 */
@Service("proApplyDineService")
public class ProApplyDineServiceImpl implements ProApplyDineService {
    @Autowired
    private ProApplyDineMapper proApplyDineMapper;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ArticleService articleService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProApplyDine queryById(String id) {
        return this.proApplyDineMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProApplyDine> queryAllByLimit(int offset, int limit) {
        return this.proApplyDineMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proApplyDine 实例对象
     * @return 实例对象
     */
    @Override
    public ProApplyDine insert(ProApplyDine proApplyDine) {
        proApplyDine.setId(UUID.randomUUID().toString());
        proApplyDine.setDatetime(DateUtil.getDatetime());
        proApplyDine.setState(0);
        if (StringUtils.isBlank(proApplyDine.getDate())) {
            proApplyDine.setDate(DateUtil.getDate());
        }
        if (Objects.isNull(proApplyDine.getProject())) {
            proApplyDine.setProject(new Project());
        }
        this.proApplyDineMapper.insert(proApplyDine);
        return proApplyDine;
    }

    /**
     * 修改数据
     *
     * @param proApplyDine 实例对象
     * @return 实例对象
     */
    @Override
    public ProApplyDine update(ProApplyDine proApplyDine) {
        this.proApplyDineMapper.update(proApplyDine);
        return this.queryById(proApplyDine.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proApplyDineMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProApplyDine> queryAll(ProApplyDine q) {
        return this.proApplyDineMapper.queryAll(q);
    }

    @Override
    public void parseArticleToDine() {
        List<Article> articles = articleService.getArticleByFD(Constant.DINE_FOLDER, "2015-01-01", "2022-09-01");
        ProApplyDine dine = null;
        for (Article article : articles) {
            if (queryById(article.getId()) != null) {     //已存在，无须重新转换添加
                continue;
            }
            dine = getDineByArticle(article);
            if (dine == null || dine.getStaff() == null) {
                continue;
            }
            insert(dine);
        }
    }

    @Override
    public int approve(String id) {
        ProApplyDine d = queryById(id);
        if (!Objects.isNull(d)) {
            d.setState(1);
            update(d);
            return 1;
        }
        return 0;
    }

    private ProApplyDine getDineByArticle(Article article) {
        ProApplyDine dine = new ProApplyDine();
        Element node;

        Document document;
        try {
            try {
                String html = articleService.docToHtml(article.getId(), null);
                document = Jsoup.parse(html);
            } catch (EncryptedDocumentException e) {        //转换失败
                return queryById(article.getId());
            }
            Elements ns = document.getElementsByTag("table");
            node = ns.get(0).child(0);

            dine.setNote(node.child(2).child(1).text());

            String pn = node.child(3).child(1).text();
            if (StringUtils.isNotBlank(pn)) {
                pn = pn.replace("人", "");
                pn = pn.replace("/", "");
                pn = getString(pn);
                try {
                    dine.setPersonNum(Integer.valueOf(pn));
                } catch (NumberFormatException e) {

                }
            }
            String s = node.child(3).child(3).text();
            if (StringUtils.isNotBlank(s)) {
                s = getString(s);
                if (s.length() > 50) {
                    s = StringUtils.substring(s, 0, s.length() - 1);
                }
                dine.setStandard(s);
            }
            dine.setSection(article.getStaff().getSection());
            dine.setProject(article.getProject());
            dine.setStaff(article.getStaff());
            dine.setDatetime(article.getDate());
            if (article.getDate().length() > 10) {
                dine.setDate(article.getDate().substring(0, 10));
            } else {
                dine.setDate(article.getDate());
            }
            dine.setState(1);
            dine.setId(article.getId());
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger(ProBidApi.class).error(e.getLocalizedMessage());
            return null;
        }
        return dine;
    }

    private String getString(String pn) {
        pn = StringUtils.replace(pn, "\r", "");
        pn = StringUtils.replace(pn, "\n", "");
        pn = StringUtils.replace(pn, "\\s", "");
        pn = StringUtils.replace(pn, "\t", "");
        pn = StringUtils.replace(pn, " ", "");
        return pn;
    }
}