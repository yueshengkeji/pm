package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.ProApplyDine;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.ProApplyDineService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.LogInterceptor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Tag(name = "就餐申请")
@RestController
@RequestMapping("api/applyDine")
public class ProApplyDineApi extends BaseApi {
    @Autowired
    private ProApplyDineService dineService;

    @Autowired
    private FlowMessageService flowMessageService;

    @Operation(description = "新增就餐申请")
    @PutMapping
    public ResponseModel<ProApplyDine> insert(@RequestBody ProApplyDine dine, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        dine.setStaff(staff);
        dine.setSection(staff.getSection());
        dine = dineService.insert(dine);
        return ResponseModel.ok(dine);
    }

    @Operation(description = "修改就餐申请状态")
    @PostMapping("updateState")
    public ResponseModel<ProApplyDine> updateState(String id, Integer state) {
        ProApplyDine d = dineService.queryById(id);
        if (!Objects.isNull(d)) {
            d.setState(state);
            dineService.update(d);
        }
        return ResponseModel.ok(id);
    }

    @Operation(description = "查询我的就餐申请")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             Boolean sortDesc,
                             String searchText,
                             String staffId) {
        ProApplyDine q = new ProApplyDine();
        q.setNote(searchText);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            q.setStaff(staff);
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<ProApplyDine> dineList = dineService.queryAll(q);
        return ResponsePage.ok((Page) dineList);
    }

    @Operation(description = "修改就餐申请")
    @PostMapping
    public ResponseModel update(@RequestBody ProApplyDine dine) {
        dineService.update(dine);
        return ResponseModel.ok(dine);
    }

    @Operation(description = "删除就餐申请")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        ProApplyDine d = dineService.queryById(id);
        if (!Objects.isNull(d) && d.getState() == 0) {
            dineService.deleteById(id);
            flowMessageService.deleteMessage(id);
            return ResponseModel.ok(id);
        } else {
            return ResponseModel.error("单据已审核");
        }
    }

    @Operation(description = "根据id查询就餐申请明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(dineService.queryById(id));
    }

    @Operation(description = "审批通过后通知接口")
    @PostMapping("{id}")
    @NoToken
    public ResponseModel approve(@PathVariable String id, HttpServletRequest request) {
        String ip = LogInterceptor.getIpAddr(request);
        if (StringUtils.equals(ip, "127.0.0.1")) {
            return ResponseModel.ok(dineService.approve(id));
        } else {
            return null;
        }
    }
}
