package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProDisposeDetail;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProDisposeDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "处置单明细管理")
@RestController
@RequestMapping("/api/disposeDetail")
public class ProDisposeDetailApi {
    @Autowired
    private ProDisposeDetailService proDisposeDetailService;

    /**
     *
     *
     * @param disposeDetail
     * @return
     */
    @Operation(description = "添加处置单明细")
    @PutMapping
    public ResponseModel insert(@RequestBody ProDisposeDetail disposeDetail) {
        return new ResponseModel(proDisposeDetailService.insert(disposeDetail));
    }

    @Operation(description = "获取处置单明细集合")
    @GetMapping("{disposeId}")
    public ResponseModel getList(@Parameter(name="处置单主单据id") @PathVariable String disposeId) {
        ProDisposeDetail disposeDetail = new ProDisposeDetail();
        disposeDetail.setDisposeId(disposeId);
        return new ResponseModel(proDisposeDetailService.queryAll(disposeDetail));
    }
}
