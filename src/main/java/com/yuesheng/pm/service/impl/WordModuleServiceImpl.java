package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.WordModule;
import com.yuesheng.pm.mapper.WordModuleMapper;
import com.yuesheng.pm.service.WordModuleService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.WordToHtml;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by 96339 on 2017/6/3.
 */
@Service("wordModuleService")
public class WordModuleServiceImpl implements WordModuleService {
    @Autowired
    private WordModuleMapper wordModuleMapper;

    @Override
    public void insertModule(WordModule module) {
//        设置系统默认模板目录
        if (module.getFolderId() == null || "".equals(module.getFolderId())) {
            module.setFolderId(Constant.WORD_FOLDER_GENERATE);
        }
        module.setId(UUID.randomUUID().toString());
        wordModuleMapper.insertModule(module);
    }

    @Override
    public List<WordModule> querySimples() {
        return wordModuleMapper.querySimples();
    }

    @Override
    public List<WordModule> queryAll() {
        return wordModuleMapper.queryAll();
    }

    @Override
    public WordModule queryById(String id) {
        return wordModuleMapper.queryById(id);
    }

    @Override
    public List<WordModule> queryByFolder(String folder) {
        return wordModuleMapper.queryByFolder(folder);
    }

    @Override
    public List<WordModule> searchModule(String str) {
        return wordModuleMapper.searchModule(str);
    }

    @Override
    public Folder queryFolder(Object folderId) {
        return wordModuleMapper.queryFolder(folderId);
    }

    @Override
    public void insertFolder(Folder f) {
        wordModuleMapper.insertFolder(f);
    }

    @Override
    public List<WordModule> queryByAF(String afId) {
        return wordModuleMapper.queryByAF(afId);
    }

    @Override
    public int deleteModule(String id) {
        return wordModuleMapper.deleteModule(id);
    }

    @Override
    public int update(WordModule module) {
        return wordModuleMapper.update(module);
    }

    @Override
    public WordModule getWordModuleHtml(String id) {
        WordModule wm = queryById(id);
        if (wm == null) {
            wm = new WordModule();
            wm.setWordToHtml("");
            wm.setId("");
            wm.setName("没有模板");
        }
        if (StringUtils.isBlank(wm.getWordToHtml())) {
            try {
                String d = null;
                try {
                    d = WordToHtml.conver2Html(new ByteArrayInputStream(wm.getWord()));
                } catch (ParserConfigurationException | NullPointerException | TransformerException e) {
                    d = "";
                } catch (OfficeXmlFileException o) {
                    d = WordToHtml.getDocumentByDocx2(new ByteArrayInputStream(wm.getWord()));
                } catch (NotOLE2FileException e) {
                    d = WordToHtml.xmlDocToHtmlStr(wm.getWord());
                }
                wm.setWordToHtml(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        wm.setWord(null);
        return wm;
    }
}
