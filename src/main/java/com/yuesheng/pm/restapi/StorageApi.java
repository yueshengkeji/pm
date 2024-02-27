package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.Storage;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.StorageService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "公司仓库管理")
@RestController
@RequestMapping("api/storage")
public class StorageApi {
    @Autowired
    public StorageService storageService;

    @Operation(description = "获取公司所有仓库")
    @GetMapping("list")
    public ResponseModel getStorages() {
        return new ResponseModel(storageService.getStorages());
    }

    @Operation(description = "新增仓库")
    @PutMapping
    public ResponseModel insert(@RequestBody Storage storage, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        storage.setStaff(staff);
        storageService.insert(storage);
        return ResponseModel.ok(storage);
    }
}
