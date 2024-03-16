package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.AdvertPlaceContract;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.AdvertPlaceContractService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AdvertPlaceContractApi
 * @Description
 * @Author ssk
 * @Date 2024/3/14 0014 9:53
 */
@Tag(name = "广告位租赁合同")
@RequestMapping("api/advertPlaceContract")
@RestController
public class AdvertPlaceContractApi extends BaseApi{
    @Autowired
    private AdvertPlaceContractService advertPlaceContractService;

    @Operation(description = "新增")
    @PostMapping("insert")
    public ResponseModel insert(@RequestBody AdvertPlaceContract advertPlaceContract,@SessionAttribute(Constant.SESSION_KEY) Staff staff){
        advertPlaceContract.setRecordStaff(staff);
        advertPlaceContract.setType(3);
        advertPlaceContractService.insert(advertPlaceContract);
        return new ResponseModel(advertPlaceContract);
    }

    @Operation(description = "更新")
    @PostMapping("update")
    public ResponseModel update(@RequestBody AdvertPlaceContract advertPlaceContract,@SessionAttribute(Constant.SESSION_KEY) Staff staff){
        advertPlaceContract.setUpdateStaff(staff);
        return new ResponseModel(advertPlaceContractService.update(advertPlaceContract));
    }

    @Operation(description = "删除")
    @PostMapping("deleteById")
    public ResponseModel deleteById(String id){
        return new ResponseModel(advertPlaceContractService.delete(id));
    }

    @Operation(description = "列表")
    @GetMapping("list")
    public ResponseModel list(String searchStr,
                              Integer page,
                              Integer pageSize){
        Map<String,Object> params = new HashMap<>();
        params.put("searchStr",searchStr);

        startPage(page,pageSize,"create_date","desc");
        Page<AdvertPlaceContract> list = (Page<AdvertPlaceContract>) advertPlaceContractService.list(params);

        params.clear();
        params.put("list",list);
        params.put("total",list.getTotal());

        return new ResponseModel(params);
    }

    @Operation(description = "id查找")
    @GetMapping("selectById")
    public ResponseModel selectById(String id){
        return new ResponseModel(advertPlaceContractService.selectById(id));
    }
}
