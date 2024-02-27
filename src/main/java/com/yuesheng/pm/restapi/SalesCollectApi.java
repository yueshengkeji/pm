package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.SalesContractLogs;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.*;
import com.yuesheng.pm.service.CollectionService;
import com.yuesheng.pm.service.SalesContractLogsService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yuesheng.pm.entity.Collection;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/salesCollect")
public class SalesCollectApi extends BaseApi {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private SalesContractLogsService salesContractLogsService;

    @Operation(description = "更新预收款时间")
    @PostMapping("updateDate")
    public ResponseModel updateDate(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {

        Collection temp = collectionService.selectById(collection.getID());
        if (Objects.isNull(temp)) {
            return new ResponseModel(500, null, "收款信息不存在");
        } else if (StringUtils.equals(temp.getStatus(), "已审核")) {
            return new ResponseModel(500, null, "收款信息已审核，禁止修改");
        } else {
            collectionService.updateDate(collection);

            //日志
            SalesContractLogs salesContractLogsC = new SalesContractLogs();
            salesContractLogsC.setAgreementID(collection.getAgreementID());
            salesContractLogsC.setType(1);
            salesContractLogsC.setModifyType(2);
            salesContractLogsC.setModifyStaff(staff);
            salesContractLogsC.setModifyMSG(staff.getName() + "修改了预收款时间，编号为:" + collection.getCollectID()
                    + ",原预收款时间为:" + DateFormat.parseString(temp.getpDate()) + ",现预收款时间为:" + DateFormat.parseString(collection.getpDate()));
            salesContractLogsService.insertLogs(salesContractLogsC);
            return new ResponseModel(collection);
        }
    }

    @Operation(description = "触发通知任务")
    @PostMapping("onNotify")
    public ResponseModel onNotify() {
        return new ResponseModel(collectionService.expireNotify());
    }

    @Operation(description = "更新收款金额")
    @PostMapping("updateCollectMoney")
    public ResponseModel updateCollectMoney(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {

        Collection temp = collectionService.selectById(collection.getID());
        if (Objects.isNull(temp)) {
            return new ResponseModel(500, null, "收款信息不存在");
        } else if (StringUtils.equals(temp.getStatus(), "已审核")) {
            return new ResponseModel(500, null, "收款信息已审核，禁止修改");
        } else {
            if (collection.getStatus().equals("已回款")) {
                collection.setStatus("已审核");
                collection.setStatusNumber(1);
            } else if (collection.getStatus().equals("未回款")) {
                collection.setStatus("未审核");
                collection.setStatusNumber(0);
            }
            int i = collectionService.updateCollection(collection);
            if (i == 1) {

                //日志
                SalesContractLogs salesContractLogsC = new SalesContractLogs();
                salesContractLogsC.setAgreementID(collection.getAgreementID());
                salesContractLogsC.setType(1);
                salesContractLogsC.setModifyType(2);
                salesContractLogsC.setModifyStaff(staff);
                salesContractLogsC.setModifyMSG(staff.getName() + "修改了收款金额，编号为:" + collection.getCollectID() + "，原收款金额为:" + collection.getCollectMoneyPast()
                        + "现收款金额为:" + collection.getCollectMoney());
                salesContractLogsService.insertLogs(salesContractLogsC);
            }

            return new ResponseModel(collection);
        }
    }

    @Operation(description = "固定资产处理")
    @PostMapping("fixedAssetsStatusChange")
    public ResponseModel fixedAssetsStatusChange(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Collection temp = collectionService.selectById(collection.getID());
        if (Objects.isNull(temp)) {
            return new ResponseModel(500, null, "收款信息不存在");
        } else {
            if (collection.getStatus().equals("已回款")) {
                collection.setStatus("已审核");
                collection.setStatusNumber(1);
            } else if (collection.getStatus().equals("未回款")) {
                collection.setStatus("未审核");
                collection.setStatusNumber(0);
            }
            int i = collectionService.updateCollection(collection);
            if (i == 1) {
                String str = null;
                if (collection.getFixedAssetsStatus() == 0) {
                    str = "未处理";
                } else if (collection.getFixedAssetsStatus() == 1) {
                    str = "已处理";
                }
                //日志
                SalesContractLogs salesContractLogsC = new SalesContractLogs();
                salesContractLogsC.setAgreementID(collection.getAgreementID());
                salesContractLogsC.setType(1);
                salesContractLogsC.setModifyType(2);
                salesContractLogsC.setModifyStaff(staff);
                salesContractLogsC.setModifyMSG(staff.getName() + "将编号为:" + collection.getCollectID() + "的固定资产处理状态修改为" + str);
                salesContractLogsService.insertLogs(salesContractLogsC);
            }
        }
        return new ResponseModel(collection);
    }

    @GetMapping("collectList")
    @Operation(description = "收款列表")
    public ResponseModel collectList(@Parameter(name="检索字符串") String search,
                                     @Parameter(name="开始日期") String startDate,
                                     @Parameter(name="截止日期") String endDate,
                                     @Parameter(name="筛选") String choice,
                                     @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                     @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                     @Parameter(name="排序名称") String sortName,
                                     @Parameter(name="排序方式：desc/asc") String sortOrder,
                                     @Parameter(name="是否个人") Boolean ifMine,
                                     @Parameter(name="时间类型") String dateType,
                                     @SessionAttribute(Constant.SESSION_KEY) Staff staff
    ) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("search", StringUtils.isBlank(search) ? null : search);
        params.put("startDate", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("endDate", StringUtils.isBlank(startDate) ? null : endDate);
        if (ifMine) {
            params.put("staff", ifMine ? staff.getId() : null);
        }
        params.put("date", new Date());
        params.put("choice", StringUtils.isBlank(choice) ? null : choice);
        if (dateType.equals("收款时间")) {
            params.put("dateType", "create_time");
        } else if (dateType.equals("登记时间")) {
            params.put("dateType", "create_time2");
        } else if (dateType.equals("预收款时间")) {
            params.put("dateType", "pDate");
        }

        List<Collection> collections1 = collectionService.collectList(params);
        BigDecimal myAmountHadTotal = BigDecimal.valueOf(0);
        BigDecimal myAmountWouldTotal = BigDecimal.valueOf(0);
        if (collections1 != null) {
            for (int i = 0; i < collections1.size(); i++) {
                if (collections1.get(i).getStatusNumber() == 0) {
                    myAmountWouldTotal = myAmountWouldTotal.add(collections1.get(i).getCollectMoney());
                } else if (collections1.get(i).getStatusNumber() == 1) {
                    myAmountHadTotal = myAmountHadTotal.add(collections1.get(i).getCollectMoney());
                }
            }
        }

        startPage(page, itemsPerPage, sortName, sortOrder);
        Page<Collection> collections = (Page<Collection>) collectionService.collectList(params);

        HashMap<String, Object> result = new HashMap<>();
        result.put("collections", collections);
        result.put("total", collections.getTotal());
        result.put("myAmountHadTotal", myAmountHadTotal);
        result.put("myAmountWouldTotal", myAmountWouldTotal);
        return new ResponseModel(result);
    }

    @GetMapping("exportCollectList")
    @Operation(description = "导出收款列表")
    public ResponseModel exportCollectList(@Parameter(name="检索字符串") String search,
                                           @Parameter(name="开始日期") String startDate,
                                           @Parameter(name="截止日期") String endDate,
                                           @Parameter(name="筛选") String choice,
                                           @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                           @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                           @Parameter(name="排序名称") String sortName,
                                           @Parameter(name="排序方式：desc/asc") String sortOrder,
                                           @Parameter(name="是否个人") Boolean ifMine,
                                           @Parameter(name="时间类型") String dateType,
                                           @SessionAttribute(Constant.SESSION_KEY) Staff staff
    ) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("search", StringUtils.isBlank(search) ? null : search);
        params.put("startDate", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("endDate", StringUtils.isBlank(startDate) ? null : endDate);
        if (ifMine) {
            params.put("staff", ifMine ? staff.getId() : null);
        }
        params.put("date", new Date());
        params.put("choice", StringUtils.isBlank(choice) ? null : choice);
        if (dateType.equals("收款时间")) {
            params.put("dateType", "create_time");
        } else if (dateType.equals("登记时间")) {
            params.put("dateType", "create_time2");
        } else if (dateType.equals("预收款时间")) {
            params.put("dateType", "pDate");
        }

        List<Collection> collections = collectionService.collectList(params);
        for (int i = 0; i < collections.size(); i++) {
            collections.get(i).setIndex(i + 1);
        }


        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader2());
        rows.add(header);
        for (int i = 0; i < collections.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell2(collections.get(i), row.getIndex()));
            rows.add(row);
        }
        String filename = startDate + "~" + endDate + "收款明细报表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader2() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "合同名称", "收款金额", "预收款日期", "收款日期", "审核状态", "登记人", "备注"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 3 || i == 5 || i == 4) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 7) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell2(Collection collection, int index) {
        String[] headers = new String[]{"Collection.Index", "Collection.AgreementName", "Collection.CollectMoney", "Collection.pDate", "Collection.CreateTime",
                "Collection.Status", "Collection.Registrant", "Collection.Remark"};
        CollectModel model = new CollectModel();
        model.setIndex(index);
        model.setCollection(collection);
        return EntityUtils.getCells(model, headers);
    }

    public String formatMarkTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }
}
