package com.yuesheng.pm.restapi;

import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.SalesContractLogsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SalesContractLogsApi
 * @Description
 * @Author ssk
 * @Date 2022/6/27 0027 9:46
 */
@Tag(name = "销售合同日志")
@RestController
@RequestMapping("api/salesContractLogs")
public class SalesContractLogsApi {
    @Autowired
    private SalesContractLogsService salesContractLogsService;

    @Operation(description = "获取日志列表")
    @GetMapping("getLogs")
    public ResponseModel getLogs(String agreementID,Integer type){
        return new ResponseModel(salesContractLogsService.selectLogs(agreementID,type));
    }
}
