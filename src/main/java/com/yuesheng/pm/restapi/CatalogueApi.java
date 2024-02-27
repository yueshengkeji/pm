package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CatalogueService;
import com.yuesheng.pm.service.CertificateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gui_lin
 * @Description 描述
 * 2022/1/10
 */
@Tag(name = "证书目录管理")
@RestController
@RequestMapping("/api/catalogue")
public class CatalogueApi {
    @Autowired
    CatalogueService catalogueService;

    @Autowired
    CertificateService certificateService;

    @Operation(description = "查询证书目录")
    @GetMapping
    public ResponseModel selectAllCatalogue(@Parameter(name="搜索字符串") String str){
        List<Catalogue> catalogueList = catalogueService.selectAllCatalogue(StringUtils.isBlank(str) ? null : str);
        for (Catalogue item : catalogueList){
            List<Catalogue> child = catalogueService.toChild(item);
            if (child.size() != 0){
                item.setChildren(child);
            }
        }
        return new ResponseModel(catalogueList);
    }

    @Operation(description = "添加证书目录")
    @PutMapping()
    public ResponseModel insertCatalogue(@RequestBody Catalogue catalogue){
        if (catalogueService.toCheck(catalogue)){
            return new ResponseModel(401,"创建失败，目录或已存在");
        }else {
            catalogueService.insertCatalogue(catalogue);
            return new ResponseModel("添加成功");
        }
    }

    @Operation(description = "删除证书目录")
    @DeleteMapping
    public ResponseModel deleteCatalogue(@RequestBody Catalogue catalogue){
        if (catalogueService.toChild(catalogue).size() != 0){
            return new ResponseModel(401,"不能删除,存在子目录");
        }else if (certificateService.selectCerByCat(catalogue).size() != 0){
            return new ResponseModel(401,"不能删除，此分类下存在证书");
        }else {
            catalogueService.deleteCatalogue(catalogue);
            return new ResponseModel("删除成功");
        }
    }

    @Operation(description = "修改证书目录")
    @PostMapping("/update")
    public ResponseModel updateCatalogue(@RequestBody Catalogue catalogue){
        catalogueService.updateCatalogue(catalogue);
        return new ResponseModel("修改成功");
    }

    @Operation(description = "可存放目录信息")
    @GetMapping("/all")
    public ResponseModel selectAll(){
        List<Catalogue> all = catalogueService.selectALL();
        return new ResponseModel(all);
    }

    @Operation(description = "根据id查询目录")
    @PostMapping("/byId")
    public ResponseModel getCatById(Integer id){
        return new ResponseModel(catalogueService.selectCatById(id));
    }
}
