package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Entertain;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.EntertainService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName EntertainApi
 * @Description
 * @Author ssk
 * @Date 2023/5/6 0006 11:15
 */
@RestController
@RequestMapping("api/entertain")
@Tag(name = "招待")
public class EntertainApi extends BaseApi{
    @Autowired
    private EntertainService entertainService;

    @Operation(description = "新增")
    @PostMapping("insert")
    public ResponseModel insert(@RequestBody Entertain entertain, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        entertain.setStaff(staff);
        entertainService.insert(entertain);
        return new ResponseModel(entertain);
    }

    @Operation(description = "更新")
    @PostMapping("update")
    public ResponseModel update(@RequestBody Entertain entertain){
        return new ResponseModel(entertainService.update(entertain));
    }

    @Operation(description = "删除")
    @PostMapping("delete")
    public ResponseModel delete(@RequestBody Entertain entertain){
        return new ResponseModel(entertainService.delete(entertain.getId()));
    }

    @Operation(description = "查询单个")
    @GetMapping("selectById")
    public ResponseModel selectById(@Parameter String id){
        return new ResponseModel(entertainService.selectById(id));
    }

    @Operation(description = "列表")
    @GetMapping("list")
    public ResponseModel list(@Parameter(name="检索字符串") String str,
                              @Parameter(name="开始日期") String startDate,
                              @Parameter(name="截止日期") String endDate,
                              @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                              @Parameter(name = "数据当前索引位置", required = true) Integer page,
                              @Parameter(name="排序名称") String sortName,
                              @Parameter(name="排序方式：desc/asc") String sortOrder,
                              @Parameter(name="是否只看本人数据") Boolean ifMine,
                              @Parameter(name="是否只看某人数据") Boolean ifSomeone,
                              @Parameter(name="某人") String someone,
                              @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        if (ifSomeone){
            params.put("staff",ifSomeone ? someone : null);
        }
        if (ifMine){
            params.put("staff",ifMine ? staff.getId() : null);
        }

        startPage(page,itemsPerPage,sortName,sortOrder);
        Page<Entertain> list = (Page<Entertain>) entertainService.list(params);

        params.clear();
        params.put("list",list);
        params.put("total",list.getTotal());

        return new ResponseModel(params);
    }
}
