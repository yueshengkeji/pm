package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ContractType;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ContractTypeApi
 * @Description
 * @Author ssk
 * @Date 2022/8/1 0001 14:07
 */
@Tag(name = "合同类型")
@RestController
@RequestMapping("api/contractType")
public class ContractTypeApi {

    @Autowired
    private ContractTypeService contractTypeService;

    @Operation(description = "获取合同类型集合")
    @GetMapping("getContractTypes")
    public ResponseModel getContractTypes(){
        List<ContractType> typeByParent = contractTypeService.getTypeByParent("");
        return new ResponseModel(typeByParent);
    }

    @Operation(description = "根据父id获取合同类型集合")
    @GetMapping("getByFatherId")
    public ResponseModel getByFatherId(String parentId){
        List<ContractType> typeByParent = contractTypeService.getTypeByParent(parentId);
        if (typeByParent != null){
            for (int i = 0;i < typeByParent.size();i++){
                List<ContractType> typeByParent1 = contractTypeService.getTypeByParent(typeByParent.get(i).getId());
                if (typeByParent1.size() > 0){
                    typeByParent.get(i).setIfHaveChildren(true);
                }else {
                    typeByParent.get(i).setIfHaveChildren(false);
                }
            }
        }
        return new ResponseModel(typeByParent);
    }

    @Operation(description = "新增合同类型")
    @PostMapping("insertContractType")
    public ResponseModel insertContractType(@RequestBody ContractType contractType){
        int insert = contractTypeService.insert(contractType);
        return new ResponseModel(insert);
    }

    @Operation(description = "更新合同类型")
    @PostMapping("updateContractType")
    public ResponseModel updateContractType(@RequestBody ContractType contractType){
        int update = contractTypeService.update(contractType);
        return new ResponseModel(update);
    }

    @Operation(description = "删除合同类型")
    @DeleteMapping("deleteContractType")
    public ResponseModel deleteContractType(String id){
        int delete = contractTypeService.delete(id);
        return new ResponseModel(delete);
    }
}
