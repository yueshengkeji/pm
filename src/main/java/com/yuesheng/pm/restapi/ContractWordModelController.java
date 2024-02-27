package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ContractWordModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractWordModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ContractWordModelController
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 13:26
 */
@RestController
@RequestMapping("/api/contractWordModel")
public class ContractWordModelController {
    @Autowired
    private ContractWordModelService contractWordModelService;

    @GetMapping("/list")
    public ResponseModel list(){
        return new ResponseModel(contractWordModelService.list());
    }

    @PostMapping("/insert")
    public ResponseModel insert(@RequestBody ContractWordModel contractWordModel){
        return new ResponseModel(contractWordModelService.insert(contractWordModel));
    }

    @PostMapping("/update")
    public ResponseModel update(@RequestBody ContractWordModel contractWordModel){
        return new ResponseModel(contractWordModelService.update(contractWordModel));
    }
}
