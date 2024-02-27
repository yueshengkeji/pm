package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProReport;
import com.yuesheng.pm.entity.Procurement;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.ProReportService;
import com.yuesheng.pm.service.ProcurementService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Tag(name = "采购报表股哪里")
@RestController
@RequestMapping("api/proReport")
public class ProReportApi extends BaseApi {
    @Autowired
    private ProReportService reportService;
    @Autowired
    private ProcurementService procurementService;

    @Operation(description = "导出采购报表")
    @GetMapping("export")
    public ResponseModel export(String searchText,
                                String staffName,
                                String companyName,
                                String projectName,
                                Integer itemsPerPage,
                                Integer page,
                                String startDate,
                                String putState,
                                String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        param.put("staffName", staffName);
        param.put("companyName", StringUtils.isBlank(companyName) ? null : companyName);
        param.put("projectName", StringUtils.isBlank(projectName) ? null : projectName);
        param.put("startDate", startDate + " 00:00:00");
        param.put("endDate", endDate + " 23:59:59");
        param.put("putState", putState);
        int total = reportService.queryAllCount(param);
        if (total > 20000) {
            return new ResponseModel(500, "导出的数据过大，请缩短数据时间区间，分量导出");
        }
        startPage(page, total + 1, "voucher_date", "desc");
        List<ProReport> reportList = reportService.queryAll(param);
        String fileName = DateUtil.format(new Date(), DateUtil.PATTERN_IMAGE_NAME) + ".xlsx";
        ArrayList<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setIndex(0);
        row.setCell(getHander());
        rows.add(row);

        int index = 1;
        for (int i = 0; i < reportList.size(); i++) {
            Row rowValue = new Row();
            List<Cell> cells = getCells(reportList.get(i));
            rowValue.setCell(cells);
            rowValue.setIndex(index);
            rows.add(rowValue);
            index++;
        }
        fileName = ExcelParse.writeExcel(rows, fileName);
        return new ResponseModel(fileName);
    }

    private List<Cell> getHander() {
        ArrayList<Cell> arrayList = new ArrayList<>();
        String[] names = new String[]{"日期", "供应商", "项目", "采购物料", "规格", "品牌", "单位", "数量", "成本价", "单价", "合计金额", "备注", "业务员", "是否到货", "收货人", "单号", "是否签订合同", "付款日期"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (names[i].equals("收货人")) {
                cell.setWidth((float) 42.8 * 42 * 3);
            } else if (i == 0) {
                cell.setWidth((float) 42.8 * 42 * 3);
            } else if (i == 1) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 2) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            arrayList.add(cell);
        }
        return arrayList;
    }

    private List<Cell> getCells(ProReport report) {
        String[] values = new String[]{"VoucherDate", "CompanyName",
                "ProjectName", "Material.Name", "Material.Model",
                "Material.Brand", "Material.Unit.Name", "Sum",
                "ApplyPrice", "ProPrice", "ProMoney", "Remark", "ProjectPersonName",
                "DhRemark", "AcceptPersonName", "Series", "ContractRemark", "PayDate"};
        return EntityUtils.getCells(report, values);
    }


    @Operation(description = "刷新采购报表数据")
    @PutMapping("refreshReport")
    public ResponseModel refreshReport(String startDate, String endDate) {
        refresh(startDate, endDate);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "查询采购报表")
    @GetMapping("list")
    public ResponsePage list(String searchText,
                             String staffName,
                             String companyName,
                             String projectName,
                             Integer itemsPerPage,
                             Integer page,
                             String startDate,
                             String putState,
                             String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        param.put("staffName", staffName);
        param.put("companyName", StringUtils.isBlank(companyName) ? null : companyName);
        param.put("projectName", StringUtils.isBlank(projectName) ? null : projectName);
        param.put("startDate", startDate + " 00:00:00");
        param.put("endDate", endDate + " 23:59:59");
        param.put("putState", putState);
        startPage(page, itemsPerPage, "voucher_date", "desc");
        List<ProReport> reportList = reportService.queryAll(param);
        return ResponsePage.ok((Page) reportList);
    }

    private void refresh(String startDate, String endDate) {
        Integer rows = reportService.deleteByDate(startDate, endDate);
        List<Procurement> list = procurementService.getProcurementByDate(startDate, endDate);
        if (!list.isEmpty()) {
            list.forEach(p -> {
                reportService.genByProcurement(p);
            });
        }
    }

}
