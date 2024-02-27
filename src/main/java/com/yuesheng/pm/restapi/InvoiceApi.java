package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Invoice;
import com.yuesheng.pm.entity.InvoiceFile;
import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.InvoiceModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.InvoiceFileService;
import com.yuesheng.pm.service.InvoiceService;
import com.yuesheng.pm.service.SalesContractService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import com.yuesheng.pm.util.LogInterceptor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Tag(name = "销售合同开票接口")
@RestController
@RequestMapping("api/invoice")
public class InvoiceApi extends BaseApi{

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private SalesContractService salesContractService;

    @Autowired
    private InvoiceFileService invoiceFileService;

    @Operation(description = "获取开票信息")
    @GetMapping("{id}")
    public ResponseModel getById(@Parameter(name="开票id") @PathVariable String id) {
        HashMap<String, Object> result = new HashMap();
        Invoice invoice = invoiceService.selectById(id);
        if (!Objects.isNull(invoice)) {
            result.put("invoice", invoice);
            result.put("contract", salesContractService.selectByID(invoice.getAgreementID()));
            return new ResponseModel(result);
        } else {
            return ResponseModel.ok();
        }
    }

    @Operation(description = "开票审核通过接口")
    @PostMapping("{id}")
    @NoToken
    public ResponseModel approve(@PathVariable String id, HttpServletRequest request) {
        String ip = LogInterceptor.getIpAddr(request);
        if (StringUtils.equals(ip, "127.0.0.1")) {
            invoiceService.approve(id);
            return ResponseModel.ok();
        } else {
            return null;
        }
    }

    @Operation(description = "获取开票详情")
    @GetMapping("getInvoiceItem")
    public ResponseModel getInvoiceItem(String id){
        return new ResponseModel(invoiceService.selectById(id));
    }

    @Operation(description = "获取附件")
    @GetMapping("getInvoiceFiles")
    public ResponseModel getInvoiceFiles(String id){
        return new ResponseModel(invoiceFileService.select(id));
    }

    @Operation(description = "删除开票附件")
    @PostMapping("deleteInvoiceFile")
    public ResponseModel deleteInvoiceFile(@RequestBody InvoiceFile invoiceFile){
        int delete = invoiceFileService.delete(invoiceFile.getId());
        return new ResponseModel(delete);
    }

    @Operation(description = "开票列表")
    @GetMapping("list")
    public ResponseModel list(@Parameter(name="检索字符串") String search,
                              @Parameter(name="开始日期") String startDate,
                              @Parameter(name="截止日期") String endDate,
                              @Parameter(name="单位名称") String company,
                              @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                              @Parameter(name = "数据当前索引位置", required = true) Integer page,
                              @Parameter(name="排序名称") String sortName,
                              @Parameter(name="排序方式：desc/asc") String sortOrder,
                              @Parameter(name="是否个人") Boolean ifMine,
                              @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String,String> params = new HashMap<>(16);
        Page<Invoice> invoices = null;
        params.put("search",StringUtils.isBlank(search) ? null : search);
        params.put("startDate",StringUtils.isBlank(startDate) ? null : startDate);
        params.put("endDate",StringUtils.isBlank(startDate) ? null : endDate);
        params.put("company",StringUtils.isBlank(company) ? null : company);
        if (ifMine){
            params.put("staff",ifMine ? staff.getId() : null);
        }
        if (params.get("company") != null & params.get("company") != ""){
            sortName = "a.create_time";
            startPage(page,itemsPerPage,sortName,sortOrder);
            invoices = (Page<Invoice>)invoiceService.getByCompany(params);
        }else {
            startPage(page,itemsPerPage,sortName,sortOrder);
            invoices = (Page<Invoice>)invoiceService.list(params);
        }
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(salesContract.getTax());
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("invoices",invoices);
        result.put("total",invoices.getTotal());
        return new ResponseModel(result);
    }

    @Operation(description = "导出")
    @GetMapping("export")
    public ResponseModel export(@Parameter(name="检索字符串") String search,
                                @Parameter(name="开始日期") String startDate,
                                @Parameter(name="截止日期") String endDate,
                                @Parameter(name="单位名称") String company,
                                @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                @Parameter(name="排序名称") String sortName,
                                @Parameter(name="排序方式：desc/asc") String sortOrder,
                                @Parameter(name="是否个人") Boolean ifMine,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String,String> params = new HashMap<>(16);
        List<Invoice> invoices = null;
        params.put("search",StringUtils.isBlank(search) ? null : search);
        params.put("startDate",StringUtils.isBlank(startDate) ? null : startDate);
        params.put("endDate",StringUtils.isBlank(startDate) ? null : endDate);
        params.put("company",StringUtils.isBlank(company) ? null : company);
        if (ifMine){
            params.put("staff",ifMine ? staff.getId() : null);
        }
        if (params.get("company") != null & params.get("company") != ""){
            invoices = invoiceService.getByCompany(params);
        }else {
            invoices = invoiceService.list(params);
        }
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(invoices.get(i).getTax() != "" && invoices.get(i).getTax() != null ? invoices.get(i).getTax().split("/")[1] : salesContract.getTax());
            invoices.get(i).setCompanyAddress(salesContract.getCompanyAddress());
            invoices.get(i).setProjectBase(salesContract.getProjectBase());
        }
        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader2());
        rows.add(header);
        for (int i = 0; i < invoices.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell2(invoices.get(i), row.getIndex()));
            rows.add(row);
        }
        String filename = startDate + "至" + endDate + "财务销售开票明细报表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader2() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "合同名称", "项目基地", "单位名称", "单位地址", "合同金额", "开票内容", "开票金额", "税率(%)", "开票时间", "开票单位"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 4 || i == 6) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell2(Invoice invoice, int index) {
        String[] headers = new String[]{"Invoice.Index", "Invoice.AgreementName", "Invoice.ProjectBase", "Invoice.Company", "Invoice.CompanyAddress",
                "Invoice.AgreementMoney", "Invoice.InvoiceContent", "Invoice.InvoiceMoney", "Invoice.Tax", "Invoice.CreateTime", "Invoice.InvoiceCompany"};
        InvoiceModel model = new InvoiceModel();
        model.setIndex(index);
        model.setInvoice(invoice);
        return EntityUtils.getCells(model, headers);
    }
}
