package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProZujinEnd;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProZujinEndService;
import com.yuesheng.pm.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProZujinEndApi
 * @Description
 * @Author ssk
 * @Date 2024/3/16 0016 10:55
 */
@Tag(name = "合同终止协议")
@RequestMapping("api/proZujinEnd")
@RestController
public class ProZujinEndApi extends BaseApi{
    @Autowired
    private ProZujinEndService proZujinEndService;

    @ApiOperation("新增")
    @PostMapping("/insert")
    public ResponseModel insert(@RequestBody ProZujinEnd proZujinEnd, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        proZujinEnd.setRecordStaff(staff);
        proZujinEndService.insert(proZujinEnd);
        return new ResponseModel(proZujinEnd);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public ResponseModel update(@RequestBody ProZujinEnd proZujinEnd, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        proZujinEnd.setUpdateStaff(staff);
        return new ResponseModel(proZujinEndService.update(proZujinEnd));
    }

    @ApiOperation("删除")
    @PostMapping("deleteById")
    public ResponseModel deleteById(@ApiParam String id){
        return new ResponseModel(proZujinEndService.delete(id));
    }

    @ApiOperation("列表")
    @GetMapping("list")
    public ResponseModel list(@ApiParam String searchStr,
                              @ApiParam Integer page,
                              @ApiParam Integer pageSize){
        Map<String,Object> params = new HashMap<>();
        params.put("searchStr",searchStr);

        startPage(page,pageSize,"create_date","desc");
        Page<ProZujinEnd> list = (Page<ProZujinEnd>) proZujinEndService.list(params);

        params.clear();
        params.put("list",list);
        params.put("total",list.getTotal());

        return new ResponseModel(params);
    }

    @ApiOperation("id查找")
    @GetMapping("selectById")
    public ResponseModel selectById(@ApiParam String id){
        return new ResponseModel(proZujinEndService.selectById(id));
    }
}
