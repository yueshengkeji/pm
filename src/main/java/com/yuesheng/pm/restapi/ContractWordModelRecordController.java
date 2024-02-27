package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractWordRecordService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ContractWordModelRecordController
 * @Description
 * @Author ssk
 * @Date 2024/2/4 0004 8:30
 */
@RestController
@RequestMapping("/api/contractWordModelRecord")
public class ContractWordModelRecordController {
    @Autowired
    private ContractWordRecordService contractWordRecordService;

    @PostMapping("/insert")
    public ResponseModel insert(@RequestBody ProZujin proZujin){
        return new ResponseModel(contractWordRecordService.insert(proZujin));
    }

    @GetMapping("/getByContractId")
    public ResponseModel getByContractId(@Parameter String contractId){
        return new ResponseModel(contractWordRecordService.selectLastOneByContractId(contractId));
    }
}
