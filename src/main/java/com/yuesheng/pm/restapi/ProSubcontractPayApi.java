package com.yuesheng.pm.restapi;

import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Company;
import com.yuesheng.pm.entity.ProSubcontractPay;
import com.yuesheng.pm.entity.ProSubcontractPayFile;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CompanyService;
import com.yuesheng.pm.service.ProSubcontractPayService;
import com.yuesheng.pm.service.ProSubcontractService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ProSubcontractPayApi
 * @Description
 * @Author ssk
 * @Date 2022/9/16 0016 9:45
 */
@Tag(name = "分包合同付款")
@RestController
@RequestMapping("api/proSubcontractPay")
public class ProSubcontractPayApi {

    @Autowired
    private ProSubcontractPayService proSubcontractPayService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProSubcontractService proSubcontractService;

    @Operation(description = "获取分包合同付款集合")
    @GetMapping("getProSubcontractPays")
    public ResponseModel getProSubcontractPays(Integer pageSize,
                                               Integer pageNumber,
                                               String searchText,
                                               String sortName,
                                               String sortOrder,
                                               Boolean ifMine,
                                               String searchYear,
                                               String approveStatus,
                                               String projectName,
                                               @SessionAttribute(Constant.SESSION_KEY) Staff staff
                                               ){
        Map<String, Object> params = new HashMap<>(16);
        params.put("searchYear",searchYear);
        params.put("approveStatus",approveStatus);
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("staffId", BooleanUtils.isTrue(ifMine) ? staff.getId() : null);
        params.put("searchText", "".equals(searchText) ? null : searchText);
        params.put("projectName",projectName);
        List<ProSubcontractPay> proSubcontractPays = proSubcontractPayService.selectAll(pageNumber, pageSize, params);
        Integer total = proSubcontractPayService.selectAllCounts(params);

        params.clear();
        params.put("rows",proSubcontractPays);
        params.put("total",total);

        return new ResponseModel(params);
    }
    @Operation(description = "导出分包合同付款集合")
    @GetMapping("exportProSubcontractPays")
    public ResponseModel exportProSubcontractPays(Integer pageNumber,
                                               String searchText,
                                               String sortName,
                                               String sortOrder,
                                               Boolean ifMine,
                                               String searchYear,
                                               String approveStatus,
                                               String projectName,
                                               @SessionAttribute(Constant.SESSION_KEY) Staff staff
                                               ){
        Map<String, Object> params = new HashMap<>(16);
        params.put("searchYear",searchYear);
        params.put("approveStatus",approveStatus);
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("staffId", BooleanUtils.isTrue(ifMine) ? staff.getId() : null);
        params.put("searchText", "".equals(searchText) ? null : searchText);
        params.put("projectName",projectName);
        Integer total = proSubcontractPayService.selectAllCounts(params);
        if(!Objects.isNull(total) && total > 5000){
            return ResponseModel.error("数据过大，请缩小数据范围");
        }
        List<ProSubcontractPay> proSubcontractPays = proSubcontractPayService.selectAll(pageNumber, total, params);

        String fileName = "付款记录.xlsx";
        fileName = ExcelParse.writeExcel(proSubcontractPays,fileName,new String[]{
                "ContractName","PayMoney","ContractMoney","Project.Name","PayInfo",
                "CompanyName","CompanyBlank","CompanyNumber","EarlyMoney","AccountMoney",
                "Remark","DateTime","Staff.Name"
        }, ProSubcontractPay.class );

        return new ResponseModel(fileName);
    }

    @Operation(description = "新增分包付款")
    @PostMapping("insertProSubcontractPay")
    public ResponseModel insertProSubcontractPay(@RequestBody ProSubcontractPay proSubcontractPay,@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        proSubcontractPay.setStaff(staff);
        if (proSubcontractPay.getCompanyId() == null){
            Company company = new Company();
            company.setName(proSubcontractPay.getCompanyName());
            company.setOpenAccount(proSubcontractPay.getCompanyBlank());
            company.setBankNumber(proSubcontractPay.getCompanyNumber());
            companyService.insert(company,staff);
            proSubcontractPay.setCompanyId(company.getId());
        }else {
            Company company = new Company();
            company.setId(proSubcontractPay.getCompanyId());
            company.setName(proSubcontractPay.getCompanyName());
            company.setOpenAccount(proSubcontractPay.getCompanyBlank());
            company.setBankNumber(proSubcontractPay.getCompanyNumber());
            companyService.updateCompany(company);
        }

        proSubcontractPayService.insert(proSubcontractPay);
        return new ResponseModel(proSubcontractPay);
    }

    @Operation(description = "更新分包付款")
    @PostMapping("updateProSubcontractPay")
    public ResponseModel updateProSubcontractPay(@RequestBody ProSubcontractPay proSubcontractPay){
        proSubcontractPayService.update(proSubcontractPay);
        return new ResponseModel(proSubcontractPay);
    }

    @Operation(description = "删除分包付款")
    @PostMapping("deleteProSubcontractPay")
    public ResponseModel deleteProSubcontractPay(@RequestBody ProSubcontractPay proSubcontractPay){
        ProSubcontractPay proSubcontractPay1 = proSubcontractPayService.selectById(proSubcontractPay.getId());
        if (proSubcontractPay1.getState() == 1){
            return new ResponseModel(500,"该付款已审核，无法删除！");
        }else {
            return new ResponseModel( proSubcontractPayService.delete(proSubcontractPay.getId()));
        }

    }

    @Operation(description = "根据id查询分包付款")
    @GetMapping("getById")
    public ResponseModel getById(String id){
        return new ResponseModel(proSubcontractPayService.selectById(id));
    }

    @Operation(description = "绑定附件")
    @PostMapping("bindFile")
    public ResponseModel bindFile(@RequestBody List<ProSubcontractPayFile> list){
        for (int i = 0;i < list.size();i++){
            proSubcontractPayService.addFile(list.get(i));
        }
        return new ResponseModel("ok");
    }

    @Operation(description = "删除附件")
    @PostMapping("deleteFile")
    public ResponseModel deleteFile(@RequestBody ProSubcontractPayFile proSubcontractPayFile){
        return new ResponseModel(proSubcontractPayService.deleteFile(proSubcontractPayFile.getAttachId()));
    }

    @Operation(description = "根据id获取附件")
    @GetMapping("getFiles")
    public ResponseModel getFiles(String id) {
        return new ResponseModel(proSubcontractPayService.getFiles(id));
    }

    @Operation(description = "审批通过回调请求,仅支持127.0.0.1调用，兼容老版本系统")
    @PostMapping("updateState/{id}")
    @NoToken
    public ResponseModel updateState(@PathVariable String id) {
        return ResponseModel.ok(proSubcontractPayService.updateState(id, 1));
    }

    @Operation(description = "检索分包合同")
    @GetMapping("getSubContracts")
    public ResponseModel getSubContracts(String str){
        return new ResponseModel(proSubcontractService.getSubcontract(str));
    }

    @Operation(description = "根据合同id获取分包付款集合")
    @GetMapping("getPayByContractId")
    public ResponseModel getPayByContractId(String contractId){
        return new ResponseModel(proSubcontractPayService.getByContractId(contractId));
    }

}
