package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProSubcontract;
import com.yuesheng.pm.entity.ProSubcontractFile;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CompanyService;
import com.yuesheng.pm.service.ProSubcontractService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProSubcontractApi
 * @Description
 * @Author ssk
 * @Date 2022/9/22 0022 10:51
 */
@Tag(name = "分包合同登记")
@RestController
@RequestMapping("api/proSubcontract")
public class ProSubcontractApi {
    @Autowired
    private ProSubcontractService proSubcontractService;
    @Autowired
    private CompanyService companyService;

    @Operation(description = "获取合同列表")
    @GetMapping("getSubcontract")
    public ResponseModel getSubcontract(Integer pageSize,
                                        Integer pageNumber,
                                        String searchText,
                                        String sortName,
                                        String sortOrder,
                                        Boolean ifMine,
                                        String searchYear,
                                        String approveStatus,
                                        Integer type,
                                        @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("searchYear",searchYear);
        params.put("type",type);
        params.put("approveStatus",approveStatus);
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("staffId", BooleanUtils.isTrue(ifMine) ? staff.getId() : null);
        params.put("searchText", "".equals(searchText) ? null : searchText);
        List<ProSubcontract> proSubcontracts = proSubcontractService.selectAll(pageNumber, pageSize, params);
        Integer total = proSubcontractService.selectAllCounts(params);

        params.clear();
        params.put("rows",proSubcontracts);
        params.put("total",total);

        return new ResponseModel(params);
    }

    @Operation(description = "新增合同登记")
    @PostMapping("insertSubcontract")
    public ResponseModel insertSubcontract(@RequestBody ProSubcontract proSubcontract, @SessionAttribute(Constant.SESSION_KEY)@Parameter(hidden = true)Staff staff){
        proSubcontract.setStaff(staff);
        if (proSubcontract.getPartyA().getId() == null){
            companyService.insert(proSubcontract.getPartyA(),staff);
        }
        if (proSubcontract.getPartyB().getId() == null){
            companyService.insert(proSubcontract.getPartyB(),staff);
        }
        proSubcontractService.insert(proSubcontract);
        return new ResponseModel(proSubcontract);
    }

    @Operation(description = "更新合同")
    @PostMapping("updateSubcontract")
    public ResponseModel updateSubcontract(@RequestBody ProSubcontract proSubcontract){
        proSubcontractService.update(proSubcontract);
        return new ResponseModel(proSubcontract);
    }

    @Operation(description = "删除合同")
    @PostMapping("deleteSubcontract")
    public ResponseModel deleteSubcontract(@RequestBody ProSubcontract proSubcontract){
        ProSubcontract proSubcontract1 = proSubcontractService.selectById(proSubcontract.getId());
        if (proSubcontract1.getState() == 1){
            return new ResponseModel(500,"该合同已审核，无法删除！");
        }else {
            return new ResponseModel(proSubcontractService.delete(proSubcontract.getId()));
        }
    }

    @Operation(description = "通过id获取合同")
    @GetMapping("getSubcontractById")
    public ResponseModel getSubcontractById(String id){
        return new ResponseModel(proSubcontractService.selectById(id));
    }

    @Operation(description = "绑定附件")
    @PostMapping("bindFile")
    public ResponseModel bindFile(@RequestBody List<ProSubcontractFile> list){
        for (int i = 0;i < list.size();i++){
            proSubcontractService.addFile(list.get(i));
        }

        return new ResponseModel("ok");
    }

    @Operation(description = "获取附件集合")
    @GetMapping("getFiles")
    public ResponseModel getFiles(String id){
        return new ResponseModel(proSubcontractService.getFiles(id));
    }

    @Operation(description = "删除附件")
    @PostMapping("deleteFile")
    public ResponseModel deleteFile(@RequestBody ProSubcontractFile proSubcontractFile){
        return new ResponseModel(proSubcontractService.deleteFile(proSubcontractFile.getAttachId()));
    }
}
