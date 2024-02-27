package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Article;

public class ArticleModel {
    private Article article;
    private String[] attachs;
    private String onUpFile;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String[] getAttachs() {
        return attachs;
    }

    public void setAttachs(String[] attachs) {
        this.attachs = attachs;
    }

    public String getOnUpFile() {
        return onUpFile;
    }

    public void setOnUpFile(String onUpFile) {
        this.onUpFile = onUpFile;
    }
}
