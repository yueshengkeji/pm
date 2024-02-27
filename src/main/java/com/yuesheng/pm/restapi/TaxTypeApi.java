package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.TaxType;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.TaxTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "税率类型管理")
@RestController
@RequestMapping("api/taxType")
public class TaxTypeApi {
    @Autowired
    private TaxTypeService taxTypeService;

    @Operation(description = "获取税率类型列表")
    @GetMapping
    public ResponseModel getList() {
        return new ResponseModel(taxTypeService.getTaxTypes());
    }

    @Operation(description = "根据税率类型id查询明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(taxTypeService.getTypeById(id));
    }

    @Operation(description = "添加税率类型")
    @PostMapping
    public ResponseModel addTaxType(@RequestBody TaxType taxType) {
        int state = taxTypeService.insert(taxType);
        if (state == -1) {
            return new ResponseModel(500, "名称不能为空");
        } else if (state == -2) {
            return new ResponseModel(500, "税率点不能为空");
        }
        return new ResponseModel(taxType);
    }
}
