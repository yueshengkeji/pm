package com.yuesheng.pm.restapi;

import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.util.NetRequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@Tag(name = "用友T3数据接口")
@RestController
@RequestMapping("api/yonYou")
public class YonYouApi extends BaseApi {

    @Operation(description = "获取用友T3财务数据")
    @GetMapping("getDataBySeries")
    public ResponseModel getDataBySeries(@Parameter(name="会计科目号") String leaveNumber,
                                         @Parameter(name="数据年份") String year) {
        LinkedHashMap param = new LinkedHashMap<>();
        param.put("leaveNumber", leaveNumber);
        param.put("year", year);
        return new ResponseModel(NetRequestUtil.sendGetRequest("http://192.168.2.254:8082/queryYuPayBillData", param));
    }

    @Operation(description = "获取用友T3收支备注")
    @GetMapping("getRemark")
    public ResponseModel getRemark(@Parameter(name="数据日期") String date,
                                   @Parameter(name="数据年份") String year,
                                   @Parameter(name="收支金额") String money,
                                   @Parameter(name="类型， 0=付款,1=收票") String type) {
        LinkedHashMap param = new LinkedHashMap<>();
        param.put("date", date);
        param.put("year", year);
        param.put("money", money);
        param.put("type", type);
        return new ResponseModel(NetRequestUtil.sendGetRequest("http://192.168.2.254:8082/queryYuPayBillData/getRemark", param));
    }
}
