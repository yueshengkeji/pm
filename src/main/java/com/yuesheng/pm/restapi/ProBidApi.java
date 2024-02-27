package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProBid;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ProBidCount;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.ProBidService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "投标盖章申请管理")
@RestController
@RequestMapping("api/proBid")
public class ProBidApi extends BaseApi {

    @Autowired
    private ProBidService proBidService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private FlowMessageService flowMessageService;

    @Operation(description = "通过主键获取投标信息")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(proBidService.queryById(id));
    }

    @Operation(description = "删除投标信息")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        flowMessageService.deleteMessageById(id);
        return new ResponseModel(proBidService.deleteById(id));
    }


    @Operation(description = "获取投标信息列表")
    @GetMapping("list")
    public ResponsePage queryByParam(Integer page,
                                     Integer itemsPerPage,
                                     String sortBy,
                                     Boolean sortDesc,
                                     String searchText,
                                     Integer state,
                                     Integer bzjState,
                                     String busPersonId,
                                     String staffId) {
        ProBid bid = new ProBid();
        if (Objects.isNull(state)) {
            bid.setState(-1);
        } else {
            bid.setState(state);
        }
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            bid.setStaff(staff);
        }
        if (StringUtils.isNotBlank(busPersonId)) {
            Staff staff = new Staff();
            staff.setId(busPersonId);
            bid.setBusPerson(staff);
        }
        bid.setBzjState(bzjState);
        bid.setProjectName(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<ProBid> bids = proBidService.query(bid);
        bids.forEach(item -> {
            item.setCourseName(flowMessageService.getFlowMsgStateStr(item.getId()));
        });
        return new ResponsePage((Page) bids);
    }

    @Operation(description = "新增投标盖章申请")
    @PutMapping()
    public ResponseModel insert(@RequestBody ProBid proBid, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proBid.setStaff(staff);
        return new ResponseModel(proBidService.insert(proBid));
    }

    @Operation(description = "修改投标盖章申请")
    @PostMapping()
    public ResponseModel update(@RequestBody ProBid proBid, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proBid.setStaff(staff);
        return new ResponseModel(proBidService.update(proBid));
    }


    @Operation(description = "更新单据状态")
    @PostMapping("{id}/{state}")
    public ResponseModel updateState(@PathVariable String id, @PathVariable Integer state) {
        proBidService.updateState(id, state);
        return new ResponseModel(id);
    }

    @Operation(description = "更新投标反馈结果")
    @PostMapping("updateResult")
    public ResponseModel updateResult(@RequestBody ProBid proBid, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proBid.setStaff(staff);
        proBidService.update(proBid);
        return new ResponseModel(proBid);
    }

    @Operation(description = "转换word文档为投标盖章申请数据")
    @GetMapping("parse")
    public ResponseModel parse(String begin, String end) {
        proBidService.parse(begin,end);
        Map<String, Object> result = new HashMap(16);
        result.put("state", "ok");
        return new ResponseModel(result);
    }

    @Operation(description = "获取统计信息")
    @GetMapping("getCountInfo")
    public ResponseModel getCountInfo(ProBidCount count){
        return new ResponseModel(proBidService.queryCountInfo(count));
    }

}
