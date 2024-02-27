package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ArticleFolder;
import com.yuesheng.pm.entity.WordModule;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.WordModuleService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/articleFolder")
@RestController
public class ArticleFolderApi {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WordModuleService moduleService;

    @Operation(description = "通过id删除办文目录")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        articleService.deleteFolder(id);
        return new ResponseModel(id);
    }

    @Operation(description = "通过id获取办文目录")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(articleService.getFolderById(id));
    }

    @Operation(description = "检索办文目录")
    @GetMapping("search")
    public ResponseModel search(String str) {
        return new ResponseModel(articleService.searchFolder(str));
    }

    @Operation(description = "修改办文目录")
    @PostMapping
    public ResponseModel update(@RequestBody ArticleFolder folder) {
        articleService.updateAF(folder);
        return new ResponseModel(folder);
    }

    @Operation(description = "添加办文目录")
    @PutMapping
    public ResponseModel insert(@RequestBody ArticleFolder folder) {
        articleService.addFolder(folder);
        return new ResponseModel(folder);
    }

    @Operation(description = "更新办文模板")
    @PostMapping("updateWord")
    public ResponseModel updateWord(@RequestBody WordModule module) {
        moduleService.update(module);
        return new ResponseModel(module);
    }

    @Operation(description = "获取办文模板")
    @GetMapping("getModuleByFolder/{folderId}")
    public ResponseModel updateWord(@PathVariable String folderId) {
        return new ResponseModel(moduleService.getWordModuleHtml(folderId));
    }

    @Operation(description = "添加办文模板")
    @PutMapping("insertWord")
    public ResponseModel insertWord(@RequestBody WordModule module) {
        if (StringUtils.isBlank(module.getWordToHtml())) {
            return new ResponseModel(500, "模板内容不能为空");
        }
        if (StringUtils.isBlank(module.getName())) {
            return new ResponseModel(500, "模板名称不能为空");
        }
        module.setWord(new byte[10]);
        moduleService.insertModule(module);
        return new ResponseModel(module);
    }

    @Operation(description = "搜索办文模板列表")
    @GetMapping("searchModule")
    public ResponseModel searchModule(String moduleName) {
        return new ResponseModel(moduleService.searchModule(moduleName));
    }

    @Operation(description = "获取子目录列表")
    @GetMapping("listChild")
    public ResponseModel listChild(String parent) {
        List<ArticleFolder> folder = articleService.getFolders(parent);
        for (int i = 0; i < folder.size(); i++) {
            ArticleFolder f = folder.get(i);
            List<ArticleFolder> child = articleService.getFolders(f.getId());
            if (child.size() > 0) {
                f.setChildrenNode(true);
            } else {
                f.setChildrenNode(false);
            }
            if(StringUtils.isBlank(f.getModuleId())){
                WordModule wordModule = new WordModule();
                wordModule.setWordToHtml("<table style=\"width:100%\">\n" +
                        "    <tbody>\n" +
                        "        <tr class=\"firstRow\">\n" +
                        "            <td colspan=\"2\">\n" +
                        "                <strong>默认模版</strong>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td>\n" +
                        "                标题\n" +
                        "            </td>\n" +
                        "            <td>\n" +
                        "                内容\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"1\" rowspan=\"1\"></td>\n" +
                        "            <td colspan=\"1\" rowspan=\"1\"></td>\n" +
                        "        </tr>\n" +
                        "    </tbody>\n" +
                        "</table>");
                wordModule.setFolderId(f.getId());
                wordModule.setName("默认模版");
                wordModule.setPo01205((byte)0);
                wordModule.setPo01206(0);
                wordModule.setWord(wordModule.getWordToHtml().getBytes());
                f.setModuleId(UUID.randomUUID().toString());
                wordModule.setId(f.getModuleId());
                moduleService.insertModule(wordModule);
            }
        }
        return ResponseModel.ok(folder);
    }
}