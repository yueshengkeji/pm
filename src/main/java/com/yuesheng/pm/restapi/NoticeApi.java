package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Info;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.InfoService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/notice")
public class NoticeApi extends BaseApi {

    @Autowired
    private InfoService infoService;

    @GetMapping("list")
    public ResponsePage list(String searchText,
                             Integer page,
                             Integer itemsPerPage,
                             String[] sortBy,
                             Boolean[] sortDesc) {
        Info info = new Info();
        info.setTitle(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<Info> infoList = (Page) infoService.queryList(info);
        for (Info i : infoList) {
            i.setCount(infoService.queryCount(i.getId()));
        }
        return new ResponsePage(infoList);
    }

    @Operation(description = "更新通知消息")
    @PostMapping
    public ResponseModel update(@RequestBody Info info) {
        infoService.update(info);
        return new ResponseModel(info);
    }

    @Operation(description = "添加通知消息")
    @PutMapping
    public ResponseModel insert(@RequestBody Info info,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        info.setStaff(staff);
        infoService.insert(info);
        return new ResponseModel(info);
    }

    @Operation(description = "删除通知消息")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Info info = infoService.queryById(id);
        if (Objects.isNull(info)) {
            return new ResponseModel(500, "数据不存在");
        } else {
            if (StringUtils.equals(info.getStaff().getId(), staff.getId())) {
                infoService.deleteById(id);
                return new ResponseModel("操作成功");
            } else {
                return new ResponseModel(500, "数据不属于你，禁止修改");
            }
        }
    }
}
