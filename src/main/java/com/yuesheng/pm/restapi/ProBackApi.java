package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProBack;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ProBackMasterService;
import com.yuesheng.pm.service.ProBackService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "采购退库管理")
@RestController
@RequestMapping("api/proBack")
public class ProBackApi extends BaseApi {
    @Autowired
    private ProBackService backService;
    @Autowired
    private ProBackMasterService masterService;

    @Operation(description = "查询采购退库记录")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String proId,
                             String search,
                             String sortBy,
                             Boolean sortDesc) {
        ProBack query = new ProBack();
        query.setTitle(search);
        query.setProId(proId);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProBack> backs = (Page<ProBack>) backService.queryByPage(query);
        return ResponsePage.ok(backs);
    }

    @Operation(description = "添加采购退库")
    @PutMapping
    public ResponseModel insert(@RequestBody ProBack back, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        back.setStaff(staff);
        backService.insert(back);
        return ResponseModel.ok(back);
    }

    @Operation(description = "修改采购退库")
    @PostMapping
    public ResponseModel update(@RequestBody ProBack back, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        back.setStaff(staff);
        backService.update(back);
        return ResponseModel.ok(back);
    }

    @Operation(description = "删除采购退库")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        return ResponseModel.ok(backService.deleteById(id));
    }

    @Operation(description = "查询退库单详情")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        ProBack pb = backService.queryById(id);
        pb.setMasterList(masterService.queryByBack(id));
        return ResponseModel.ok(pb);
    }

}
