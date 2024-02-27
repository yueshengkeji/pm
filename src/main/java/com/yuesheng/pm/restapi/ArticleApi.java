package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Article;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ArticleModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/article")
public class ArticleApi extends BaseApi {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取办文集合
     *
     * @param sortName   排序名称
     * @param sortOrder  排序升序|降序
     * @param searchText 检索串
     * @param pageSize   页面大小
     * @param pageNumber 页码
     * @param folder     目录id
     * @param staffs     职员id集合
     * @return
     */
    @Operation(description = "获取办文列表")
    @GetMapping("list")
    public ResponseModel<Article> list(String sortName,
                                       String sortOrder,
                                       String searchText,
                                       Integer pageSize,
                                       Integer pageNumber,
                                       String folder,
                                       String staffs) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("searchText", "".equals(searchText) ? null : searchText);
        params.put("folder", folder);
        params.put("staffId", staffs);
        List<Article> articles = articleService.getArticleByParam(pageNumber, pageSize, params);
        int count = articleService.getCountByParam(params);
        params.clear();
        params.put("rows", articles);
        params.put("total", count);
        return ResponseModel.ok(params);
    }

    @Operation(description = "删除办文")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return ResponseModel.ok(articleService.delete(id, staff));
    }

    @Operation(description = "获取办文对象")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(articleService.getSimpleById(id));
    }

    @Operation(description = "获取办文html原始内容")
    @GetMapping("parseArticle/{id}")
    public ResponseModel parseArticle(@PathVariable String id, String isReload) {
        String contentHtml = articleService.docToHtml(id, isReload);
        return ResponseModel.ok(contentHtml);
    }

    /**
     * @param model {article:文档对象，attachs:附件集合}
     * @return
     */
    @Operation(description = "添加办文")
    @PutMapping
    public ResponseModel addArticle(@RequestBody ArticleModel model,
                                    @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return ResponseModel.ok(articleService.insert(model, staff));
    }

    @Operation(description = "修改办文")
    @PostMapping
    public ResponseModel updateArticle(@RequestBody ArticleModel model, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Article article = articleService.updateArticle(model, staff);
        return ResponseModel.ok(article);
    }
}
