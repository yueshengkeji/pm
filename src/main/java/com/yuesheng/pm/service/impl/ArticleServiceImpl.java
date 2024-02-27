package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.sun.istack.NotNull;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.ArticleMapper;
import com.yuesheng.pm.model.ArticleModel;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * @author XiaoSong
 * @date 2016/12/24
 */
@Service("ArticleService")
@DependsOn("databaseVersionService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private AttachService attachService;

    @Autowired
    private FlowMessageService flowMessageService;

    @Override
    public Article getSimpleById(String id) {
        return articleMapper.getSimpleById(id);
    }

    @Override
    public Article getArticle(String id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public Article getDataById(String id) {
        return articleMapper.getDataById(id);
    }

    @Override
    public List<Article> getArticleByFolder(String folder) {
        return articleMapper.getArticleByFolder(folder);
    }

    @Override
    public List<Article> getArticleByParam(Integer pageNum,Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum,pageSize);
        return articleMapper.getArticleByParam( params);
    }

    @Override
    public void insert(Article article) {
        if (article.getData() == null) {
            article.setData(new byte[1]);
        }
        articleMapper.insert(article);
    }

    @Override
    public List<Article> getArticleByFD(String folder, String begin, String end) {
        return articleMapper.getArticleByFolderDate(folder, begin, end);
    }

    @Override
    public List<ArticleFolder> getFolders(String parent) {

        return articleMapper.getFolders(parent);
    }

    @Override
    public int getCountByParam(Map<String, Object> params) {
        return articleMapper.getCountByParam(params);
    }

    @Override
    public void delete(String id) {
        articleMapper.delete(id);
    }

    @Override
    public void updateAF(ArticleFolder af) {
        if (verifyFolder(af)) {
            articleMapper.updateArticleFolder(af);
        }
    }

    @Override
    public void addFolder(ArticleFolder af) {
        af.setId(AESEncrypt.getRandom8Id());
        if (verifyFolder(af)) {
            articleMapper.addFolder(af);
        }
    }

    /**
     * 验证办文目录是否合法
     *
     * @param af
     * @return
     */
    private boolean verifyFolder(@NotNull final ArticleFolder af) {
        ArticleFolder parentAf = null;
        if (af.getId() == null) {
            return false;
        }
        if (af.getName() == null) {
            return false;
        }
        if (af.getParent() != null) {
            af.setParentId(af.getParent().getId());
        }
        if (af.getParentId() != null) {
            parentAf = getFolderById(af.getParentId());
        } else {
            af.setParentId("");
        }
        if (parentAf != null) {
//            设置根元素id = 所有父元素id+当前元素id
            af.setRootId(parentAf.getRootId() + af.getId());
        } else {
            af.setRootId(af.getId());
        }
        if (af.getModuleId() == null) {
            af.setModuleId("");
        }
        return true;
    }

    @Override
    public ArticleFolder getFolderById(String id) {
        return articleMapper.getFolderById(id);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public Article insert(ArticleModel model, Staff staff) {
        Article article = model.getArticle();
        String[] attachs = model.getAttachs();
        article.setStaff(staff);
        article.setDate(DateFormat.getDate());
        article.setCode(DateFormat.getDateForNumber());
        article.setTitle(article.getName());
        if (article.getId() == null || "".equals(article.getId())) {
            article.setId(UUID.randomUUID().toString());
        } else {
            Article temp = getArticle(article.getId());
            if (temp != null) {
//                已存在
                return temp;
            }
        }
        Project p = article.getProject();
        if (Objects.isNull(p)) {
            p = new Project();
            p.setId("");
        }
        article.setProject(p);
        //导出为word文档
        DocumentInputStream dis = WordToHtml.exportWord(article.getContent());
        if (dis != null) {
//            转换成功
            //写入文档到ftp服务器
            try {
                FtpUtil.uploadFile(IOUtils.toByteArray(dis), article.getId() + ".doc");
                //写入缓存html到本地

            } catch (IOException e) {
                //写入失败
                LoggerFactory.getLogger(ArticleServiceImpl.class).error(e.getLocalizedMessage());
            }
            //添加附件
            addAttach(article, attachs);
            //设置办文html数据，并添加办文对象到数据库
            article.setData(article.getContent().getBytes());
            insert(article);
        } else {      //转换失败
            System.out.println("转换失败");
        }
        article = getArticle(article.getId());
        return article;
    }

    @Override
    public Article updateArticle(ArticleModel model, Staff staff) {
        Article article = model.getArticle();
        String onUpFile = "0";
        try {
            onUpFile = model.getOnUpFile();
        } catch (Exception e) {
            // ignore error
        }
        if (article == null) {
            return null;
        }
        if (StringUtils.isBlank(article.getId())) {
            return article;
        }
        if (article.getProject() == null) {
            Project p = new Project();
            p.setId("");
            article.setProject(p);
        }

        String[] attachs = model.getAttachs();
        //导出为word文档
        DocumentInputStream dis = WordToHtml.exportWord(article.getContent());
        if ("1".equals(onUpFile)) {
            //附件处理
            addAttach(article, attachs);
        } else if (dis != null) {
            //写入文档到ftp服务器之前先删除之前的记录
            try {
                FtpUtil.deleteFile(article.getId() + ".doc");
                FtpUtil.uploadFile(IOUtils.toByteArray(dis), article.getId() + ".doc");
            } catch (IOException e) {
                //写入失败
                LoggerFactory.getLogger(ArticleServiceImpl.class).error(e.getLocalizedMessage());
            }
            article.setData(article.getContent().getBytes());
            updateArticle(article);
//            更新办文对象
            try {
//                写入缓存到本机
                WordToHtml.writeStrToFile(WebParam.assetsPath + "formviews" + File.separator + "doctemp" + File.separator + article.getId() + ".html",
                        article.getContent().getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {

            }
            //附件处理
            addAttach(article, attachs);
        }
        article = getArticle(article.getId());
        return article;
    }

    @Override
    public int deleteFolder(String id) {
        return articleMapper.deleteFolder(id);
    }

    @Override
    public List<ArticleFolder> searchFolder(String str) {
        return articleMapper.searchFolder(str);
    }

    @Override
    public String docToHtml(String id, String isReload) {

        String contentHtml = null;
        File file = new File(WebParam.assetsPath + "formviews" + File.separator + "doctemp" + File.separator + id + ".html");
        if (isReload == null && file.exists()) {     //存在，无须转换,直接读取字节流
            try {
                byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
                contentHtml = new String(bytes, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {      //ftp下载doc文件，并转换成html
            byte[] fileBytes = {};
            try {
                InputStream stream = new ByteArrayInputStream(FtpUtil.downloadFile(id + ".doc"));
                contentHtml = WordToHtml.convertToHtml(stream, file.getPath());
            } catch (EncryptedDocumentException e) {
                try {
                    fileBytes = FtpUtil.downloadFile(id + ".doc");
                    disposeOvertime(id, fileBytes);
                } catch (Exception e1) {

                }
            } catch (NotOLE2FileException e) {      //poi操作过的html转成的doc格式,直接转成html
                try {
                    fileBytes = FtpUtil.downloadFile(id + ".doc");
                    contentHtml = disposePoiDoc(id, fileBytes);
                } catch (Exception e1) {

                }
            } catch (Exception e) {
                LoggerFactory.getLogger(ArticleServiceImpl.class).error(e.getMessage());
            }
        }
        return contentHtml;
    }

    @Override
    public Map<String, Object> delete(String id, Staff staff) {
        FlowMessage fm = flowMessageService.getMessageByFrameId(id);
        Map<String, Object> result = new HashMap<>(16);
        result.put("id", id);
        if (fm != null) {
//            审批已完成
            if (fm.getState() == 2) {
                result.put("state", "-1");
                result.put("msg", "审批已完成，不能删除");
                return result;
            } else if (!fm.getStaffId().equals(staff.getId())) {
                result.put("state", "-1");
                result.put("msg", "单据不属于您，不能删除");
                return result;
            }
        }
        //删除审批消息
        flowMessageService.deleteMessage(id);
        //删除附件
        try {
            FtpUtil.deleteFile(id + ".doc");
            attachService.deleteAttachByModuleId(id, "sdpo009");
        } catch (IOException e) {
            LoggerFactory.getLogger(e.getClass()).error(e.getLocalizedMessage());
        }
        result.put("state", "0");
        //删除办文单据
        try {
            delete(id);
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public void approve(FlowMessage msg) {
//        暂时无需求，无需实现
    }


    /**
     * 处理html转成word后，再反转报错的问题
     *
     * @param id        文档主键
     * @param fileBytes 文档字节
     * @return doc转成功的html字符串
     */
    private String disposePoiDoc(String id, byte[] fileBytes) {
        String contentHtml = "";
        if (fileBytes.length > 0) {
            contentHtml = WordToHtml.xmlDocToHtmlStr(fileBytes);
            try {
                WordToHtml.writeStrToFile(WebParam.webRootPath + "assets" + File.separator + "formviews" + File.separator + "doctemp" + File.separator + id + ".html", contentHtml.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {

            }
        }
        return contentHtml;
    }

    /**
     * 处理加班单异常
     *
     * @param id        加班单id
     * @param fileBytes doc文档数据
     */
    private void disposeOvertime(String id, byte[] fileBytes) {
        try {
            String temp = WordToHtml.getDocumentByDocx(new ByteArrayInputStream(fileBytes));
            WordToHtml.writeFile(temp, WebParam.webRootPath + "assets" + File.separator + "formviews" + File.separator + "doctemp" + File.separator + id + ".html");
        } catch (Exception e1) {
            LoggerFactory.getLogger(ArticleServiceImpl.class).error(e1.getLocalizedMessage());
        }
    }

    /**
     * 添加办文附件到数据库
     * 8a3b2d7b-5edf-4942-9a03-5266f196de47
     *
     * @param article 办文对象
     * @param attachs 附件主键集合
     */
    private void addAttach(Article article, String[] attachs) {
        if (attachs != null) {
//            attachService.deleteAttachByModuleId(article.getId(), "sdpo009");
            Map<String, String> ap = new HashMap<>(16);
            ap.put("table", "sdpo010");
//            主键列
            ap.put("field", "po01001");
//            附件主表列
            ap.put("field2", "po01002");
            ap.put("moduleId", article.getId());
            for (String id : attachs) {
                ap.put("id", id);
                List<Attach> attaches = attachService.getAttachByRelation(ap);
                if (attaches.isEmpty() && StringUtils.isNotBlank(id)) {
                    attachService.addAttachRelation(ap);
                }
            }
        }
    }
}
