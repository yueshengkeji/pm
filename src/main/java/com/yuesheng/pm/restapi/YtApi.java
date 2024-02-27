package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProZujinYt;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProZujinYtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@Tag(name = "业态管理")
@RestController
@RequestMapping("api/yetai")
public class YtApi {
    @Autowired
    private ProZujinYtService ytService;

    @PutMapping
    public ResponseModel insert(@RequestBody ProZujinYt yt) {
        if (StringUtils.isNotBlank(yt.getName())) {
            ytService.insert(yt);
        }
        return new ResponseModel(yt);
    }

    @Operation(description = "更新业态")
    @PostMapping
    public ResponseModel update(@RequestBody ProZujinYt yt) {
        if (StringUtils.isNotBlank(yt.getName())) {
            ytService.update(yt);
        }
        return new ResponseModel(yt);
    }

    @Operation(description = "查询业态列表")
    @GetMapping("list")
    public ResponseModel list(String name) {
        ProZujinYt yt = new ProZujinYt();
        yt.setName(name);
        return new ResponseModel(ytService.queryAll(yt));
    }

    @Operation(description = "获取业态明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable Integer id) {
        return new ResponseModel(ytService.queryById(id));
    }
}
