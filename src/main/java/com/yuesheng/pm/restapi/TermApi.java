package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Term;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "合同条款管理")
@RestController
@RequestMapping("api/term")
public class TermApi extends BaseApi{
    @Autowired
    private TermService termService;

    @Operation(description = "修改条款")
    @PostMapping
    public ResponseModel update(@RequestBody Term term){
        termService.update(term);
        return ResponseModel.ok(term);
    }

    @Operation(description = "删除条款")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id){
        return ResponseModel.ok(termService.deleteById(id));
    }
}
