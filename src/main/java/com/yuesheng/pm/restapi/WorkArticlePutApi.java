package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.WorkArticlePut;
import com.yuesheng.pm.entity.WorkArticlePutDetail;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.WorkArticlePutDetailService;
import com.yuesheng.pm.service.WorkArticlePutService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/workArticlePut")
public class WorkArticlePutApi extends BaseApi {

    @Autowired
    private WorkArticlePutService putService;

    @Autowired
    private WorkArticlePutDetailService detailService;

    @Operation(description = "添加办公用品入库")
    @PutMapping
    public ResponseModel insert(@RequestBody WorkArticlePut put,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        put.setStaff(staff);
        //添加
        putService.insert(put);
        if (put.getId() != null) {
            put.setApprove(staff);
            put.setApproveDate(DateUtil.getDate());
            put.setApproveState(1);
            //自动审核
            putService.approve(put);
        }
        return new ResponseModel(put);
    }

    @Operation(description = "查询办公用品入库材料记录")
    @GetMapping("list")
    public ResponsePage list(String searchText,
                             String startDate,
                             String endDate,
                             Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             Boolean sortDesc) {
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<WorkArticlePutDetail> details = (Page) detailService.queryAll(searchText, startDate, endDate);
        return new ResponsePage(details);
    }
}
