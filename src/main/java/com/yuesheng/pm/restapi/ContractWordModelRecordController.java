package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.AdvertPlaceContract;
import com.yuesheng.pm.entity.PlaceUseContract;
import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractWordRecordService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "/租赁")
    public ResponseModel insert(@RequestBody ProZujin proZujin){
        return new ResponseModel(contractWordRecordService.insert(proZujin));
    }

    @GetMapping("/getByContractId")
    public ResponseModel getByContractId(String contractId){
        return new ResponseModel(contractWordRecordService.selectLastOneByContractId(contractId));
    }

    @PostMapping("/insertPlaceContract")
    @Operation(description = "多径类")
    public ResponseModel insertPlaceContract(@RequestBody PlaceUseContract placeUseContract){
        return new ResponseModel(contractWordRecordService.insertPlaceContract(placeUseContract));
    }

    @PostMapping("/insertAdvertPlaceContract")
    @Operation(description = "广告位租赁")
    public ResponseModel insertAdvertPlaceContract(@RequestBody AdvertPlaceContract advertPlaceContract){
        return new ResponseModel(contractWordRecordService.insertAdvertPlaceContract(advertPlaceContract));
    }
}
