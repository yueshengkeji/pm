package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Contract;
import com.yuesheng.pm.entity.ContractType;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ContractModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ContractService;
import com.yuesheng.pm.service.PaymentDetailService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "采购合同管理")
@RestController
@RequestMapping("api/contract")
public class ContractApi extends BaseApi {

    @Autowired
    private ContractService contractService;

    @Autowired
    private PaymentDetailService paymentDetailService;

    @Operation(description = "获取采购合同类型")
    @GetMapping("type")
    public ResponseModel getContractType() {
        ContractType contractType = new ContractType();
        contractType.setId("20IBAN7E");
        contractType.setName("采购合同");
        return new ResponseModel(contractType);
    }

    @Operation(description = "根据供应单位id获取采购合同")
    @GetMapping("companyId")
    public ResponseModel getContractByCompany(@Parameter(name="单位id") String companyId,
                                              @Parameter(name="支付状态：1=已付清，2=未付清，null=所有") String isPay,
                                              @Parameter(name="检索合同名称") String contractName) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<>(16);
        params.put("companyId", companyId);
        params.put("contractName", contractName);
        params.put("isPay", isPay);
        List<Contract> cs = contractService.getContractByCompany(params, 0, 100);
        return new ResponseModel(cs);
    }

    @Operation(description = "根据供应单位id查询采购合同")
    @GetMapping("companyIdV2")
    public ResponseModel getContractByCompanyV2(@Parameter(name="单位id") String companyId,
                                                @Parameter(name="支付状态：1=已付清，2=未付清，null=所有") String isPay,
                                                @Parameter(name="检索合同名称") String contractName){
        Map<String, Object> params = new HashMap<>(16);
        params.put("companyId", companyId);
        params.put("contractName", contractName);
        params.put("isPay", isPay);
        List<Contract> cs = contractService.getContractByCompany(params);
        return ResponseModel.ok(cs);
    }

    @Operation(description = "根据供应单位id获取未付清的采购合同")
    @GetMapping("companyId/pay")
    public ResponseModel getNoPayContractByCompany(@Parameter(name="供应单位id") String companyId,
                                                   @Parameter(name="检索合同名称") String contractName) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("companyId", companyId);
        params.put("contractName", contractName);
        params.put("isPay", "2");
        List<Contract> cs = contractService.getContractByCompany(params, 0, 100);
        for (Contract c : cs) {
            if(Objects.isNull(c.getApplyMoney()) || c.getApplyMoney() == 0.0){
                c.setApplyMoney(paymentDetailService.getApplyPaymentMoneyByContract(c.getId()));
            }
        }
        return new ResponseModel(cs);
    }


    @Operation(description = "获取采购合同集合", summary = "rows=合同数据集合，total=合同总数")
    @GetMapping
    public ResponseModel getContract(@Parameter(name="开始日期") String startDate,
                                     @Parameter(name="截止日期") String endDate,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "1") Integer pageNumber,
                                     @Parameter(name="检索字符串") String searchText,
                                     @Parameter(name="排序名称，参考合同属性对应的数据库字段名称") String sortName,
                                     @Parameter(name="排序方式：desc/asc") String sortOrder,
                                     @Parameter(name="支付状态：0=已付清，1=付款中，2=未付款，null=所有") String payState,
                                     @Parameter(name="是否只获取我登记的合同") Boolean myContract,
                                     @Parameter(name="供应单位id") String partyB,
                                     @Parameter(name="合同状态") String state,
                                     @Parameter(name="合同最低金额限制") Double minPrice,
                                     @Parameter(name="合同最高金额限制") Double maxPrice,
                                     @SessionAttribute(Constant.SESSION_KEY) Staff staff
    ) {
        HashMap<String, Object> result = new HashMap<>();
        Page<Contract> contracts = list(startDate, endDate, pageSize, pageNumber, searchText, sortName, sortOrder, payState, myContract, partyB, state, minPrice, maxPrice, staff);
        result.put("rows", contracts);
        result.put("total", contracts.getTotal());
        return new ResponseModel(result);
    }

    @Operation(description = "导出excel", summary = "excel下载地址")
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(@Parameter(name="开始日期") String startDate,
                                     @Parameter(name="截止日期") String endDate,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "1") Integer pageNumber,
                                     @Parameter(name="检索字符串") String searchText,
                                     @Parameter(name="排序名称，参考合同属性对应的数据库字段名称") String sortName,
                                     @Parameter(name="排序方式：desc/asc") String sortOrder,
                                     @Parameter(name="支付状态：0=已付清，1=付款中，2=未付款，null=所有") String payState,
                                     @Parameter(name="是否只获取我登记的合同") Boolean myContract,
                                     @Parameter(name="供应单位id") String partyB,
                                     @Parameter(name="合同状态") String state,
                                     @Parameter(name="合同最低金额限制") Double minPrice,
                                     @Parameter(name="合同最高金额限制") Double maxPrice,
                                     @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return ResponseModel.error("请指定开始/截止时间");
        }
        String filePath = startDate + "到" + endDate + "采购合同列表.xlsx";
        Page<Contract> contracts = list(startDate, endDate, pageSize, pageNumber, searchText, sortName, sortOrder, payState, myContract, partyB, state, minPrice, maxPrice, staff);
        filePath = ExcelParse.writeExcel(contracts, filePath, new String[]{"Name", "PartyB.Name", "Price", "YetPay", "ApplyMoney", "Debt", "Staff.Name", "Date"}, Contract.class);
        return new ResponseModel(filePath);
    }

    private Page<Contract> list(String startDate,
                                String endDate,
                                Integer pageSize,
                                Integer pageNumber,
                                String searchText,
                                String sortName,
                                String sortOrder,
                                String payState,
                                Boolean myContract,
                                String partyB,
                                String state,
                                Double minPrice,
                                Double maxPrice,
                                Staff staff) {
        Map<String, Object> params = new HashMap<>(16);
        sortName = getSortName(sortName);
        // params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("str", StringUtils.isBlank(searchText) ? null : searchText);
        params.put("end", endDate);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        params.put("partyB", StringUtils.isBlank(partyB) ? null : partyB);
        params.put("state", StringUtils.isBlank(state) ? null : state);
        payState = "".equals(payState) ? null : payState;
        if (BooleanUtils.isTrue(myContract)) {
            params.put("staffCoding", staff.getCoding());
        }
        if (payState != null) {
            if ("0".equals(payState)) {
                //已付清

                params.put("payStateSql", "a.pd00448 >= a.pd00409");
            } else if ("1".equals(payState)) {
                //付款中
                params.put("payStateSql", "a.pd00409 > a.pd00448 AND a.pd00448 > 0");
            } else if ("2".equals(payState)) {
                //未付款
                params.put("payStateSql", "a.pd00448 = 0");
            } else {
                payState = null;
            }
            params.put("payState", payState);
        }
        startPage(pageNumber, pageSize, sortName, sortOrder);
        Page<Contract> contracts = (Page<Contract>) contractService.getContractByParam(params);
        for (Contract c : contracts) {
            if(Objects.isNull(c.getApplyMoney()) || c.getApplyMoney() == 0.0){
                c.setApplyMoney(paymentDetailService.getApplyPaymentMoneyByContract(c.getId()));
            }
            if (!Objects.isNull(c.getApplyMoney())) {
                c.setDebt(c.getPrice() - c.getApplyMoney());
            }
        }
        return contracts;
    }

    @Operation(description = "根据合同id获取合同对象")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable @Parameter(name="合同id") String id) {
        Contract contractById = contractService.getContractById(id);
        if (!Objects.isNull(contractById)) {
            contractById.setApplyMoney(paymentDetailService.getPayMoneyByContract(contractById.getId()));
        }
        return new ResponseModel(contractById);
    }

    @Operation(description = "添加采购合同")
    @PostMapping
    public ResponseModel addContract(@RequestBody ContractModel contractModel,
                                     @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        contractService.addContract(contractModel, staff);
        return new ResponseModel(contractModel.getContract());
    }

    @Operation(description = "删除采购合同")
    @DeleteMapping
    public ResponseModel deleteById(@Parameter(name="合同id") String id,
                                    @Parameter(name="是否强制删除，强制删除可删除已审核的单据,只有具备管理员角色的用户可以删除") Boolean force,
                                    @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        return new ResponseModel(contractService.delete(id, staff, force));
    }

    @Operation(description = "合同作废")
    @PostMapping("loseContract")
    public ResponseModel loseContract(String id) throws Exception {
        contractService.lose(id);
        return new ResponseModel(contractService.getContractById(id));
    }

    @Operation(description = "修改合同")
    @PutMapping
    public ResponseModel update(@RequestBody ContractModel contractModel,
                                @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Contract contract = contractModel.getContract();
        Map<String, Object> result = contractService.delete(contract.getId(), staff, false);
        if (result.get("state").toString().equals("0")) {
            contractService.addContract(contractModel, staff);
        } else {
            return new ResponseModel(500, result, "修改失败");
        }
        return new ResponseModel(contract);
    }

    @Operation(description = "获取指定日期内合同总金额（已审核）")
    @GetMapping("getContractMoney")
    public ResponseModel getContractMoney(String startDate, String endDate) {
        return ResponseModel.ok(contractService.getMoneyByDate(startDate, endDate));
    }

    private String getSortName(String sortName) {
        if (StringUtils.isBlank(sortName)) {
            return "pd00419";
        }
        switch (sortName) {
            case "name":
                return "pd00402";
            case "partyA.name":
                return "pd00404";
            case "partyB.name":
                return "pd00405";
            case "price":
                return "pd00409";
            case "staff.name":
                return "pd00421";
            case "serialNumber":
                return "pd00452";
            case "yetPay":
                return "pd00448";
            case "date":
            default:
                return "pd00419";
        }
    }
}
