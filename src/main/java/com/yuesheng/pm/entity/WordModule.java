package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/6/3 办文模板 sdpo012.
 *
 * @author XiaoSong
 * @date 2017/06/03
 */
public class WordModule extends BaseEntity {
    /**
     * 目录id  sdpo013
     */
    private String folderId;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板数据
     */
    private byte[] word;
    /**
     * 默认0
     */
    private byte po01205;
    /**
     * 默认0
     */
    private int po01206;
    /**
     * word文档数据转成html字符串
     */
    private String wordToHtml;

    public String getWordToHtml() {
        return wordToHtml;
    }

    public void setWordToHtml(String wordToHtml) {
        this.wordToHtml = wordToHtml;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getWord() {
        return word;
    }

    public void setWord(byte[] word) {
        this.word = word;
    }

    public byte getPo01205() {
        return po01205;
    }

    public void setPo01205(byte po01205) {
        this.po01205 = po01205;
    }

    public int getPo01206() {
        return po01206;
    }

    public void setPo01206(int po01206) {
        this.po01206 = po01206;
    }
}
