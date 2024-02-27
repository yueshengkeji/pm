package com.yuesheng.pm.restapi;

import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractWordModelParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ContractWordModelParamsController
 * @Description
 * @Author ssk
 * @Date 2024/2/2 0002 11:12
 */
@RestController
@RequestMapping("/api/contractWordModelParams")
public class ContractWordModelParamsController {

    @Autowired
    private ContractWordModelParamsService contractWordModelParamsService;

    @GetMapping("/list")
    public ResponseModel list(){
        return new ResponseModel(contractWordModelParamsService.list());
    }
}
