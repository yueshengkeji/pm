package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.DownloadRecord;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.RecordModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.DownloadRecordService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gui_lin
 * @Description 描述
 * 2022/1/15
 */
@Tag(name = "证书下载记录管理")
@RestController
@RequestMapping("/api/downloadRecord")
public class DownloadRecordApi {
    @Autowired
    private DownloadRecordService downloadRecordService;

    @Operation(description = "查询证书下载记录")
    @GetMapping
    public ResponseModel selectAllDownloadRecord(@Parameter(name="搜索字符串") String str){
        List<DownloadRecord> recordList = downloadRecordService.selectAllDownloadRecord(StringUtils.isBlank(str) ? null : str);
        return new ResponseModel(recordList);
    }

    @Operation(description = "添加证书下载记录")
    @PutMapping
    public ResponseModel insertDownloadRecord(@RequestBody DownloadRecord downloadRecord, HttpSession httpSession){
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        downloadRecord.setCertificateId(downloadRecord.getId());
        downloadRecord.setRecordUserId(Long.parseLong(staff.getCoding()));
        downloadRecord.setRecordUserName(staff.getName());
        downloadRecord.setRecordTime(DateFormat.parseData(DateFormat.getDate()));
        downloadRecordService.insertDownloadRecord(downloadRecord);
        return new ResponseModel(downloadRecord);
    }

    @PostMapping("/export")
    public ResponseModel export(@RequestBody List<DownloadRecord> list){
        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < list.size(); i++){
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell(list.get(i), row.getIndex()));
            rows.add(row);
        }
        String filename = "证书下载记录.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "证书名称", "下载人员姓名", "下载时间", "用途说明"};
        for (int i = 0; i < names.length; i++){
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 4){
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(DownloadRecord record, int index) {
        String[] headers = new String[]{"Index", "Record.Name", "Record.RecordUserName",
                "Record.RecordTime", "Record.RecordText"};
        RecordModel model = new RecordModel();
        model.setIndex(index);
        model.setRecord(record);
        return EntityUtils.getCells(model, headers);
    }
}
