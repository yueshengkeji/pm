package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.*;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/otherPay")
public class ProOtherPayApi extends BaseApi {

    @Autowired
    private ProOtherPayService payService;

    @Autowired
    private FlowMessageService messageService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProOtherPayTagService tagService;

    @Autowired
    private FlowMessageService flowMessageService;

    @Autowired
    private FlowApproveService flowApproveService;

    @Autowired
    private StaffService staffService;

    @Operation(description = "新增其他付款")
    @PutMapping
    public ResponseModel insert(@RequestBody ProOtherPay proOtherPayApi, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proOtherPayApi.setStaff(staff);
        proOtherPayApi.setState(false);
        proOtherPayApi.setDatetime(DateUtil.getNowDate());
        if (proOtherPayApi.getCompany().getId() != null){
            proOtherPayApi.getCompany().setStaff(staff);
            companyService.updateCompany(proOtherPayApi.getCompany());
        }else {
            Company company = companyService.insert(proOtherPayApi.getCompany(), staff);
            if (StringUtils.isBlank(company.getId())) {
                return ResponseModel.error("供应商录入失败");
            }
            proOtherPayApi.setCompany(company);
        }


        if (StringUtils.isNotBlank(proOtherPayApi.getId())) {
            ProOtherPay pay = payService.queryById(proOtherPayApi.getId());
            if (!Objects.isNull(pay)) {
                payService.update(proOtherPayApi);
                return ResponseModel.ok(proOtherPayApi);
            }
        }
        payService.insert(proOtherPayApi);
        return ResponseModel.ok(proOtherPayApi);
    }

    @Operation(description = "修改其他付款")
    @PostMapping
    public ResponseModel update(@RequestBody ProOtherPay pay) {
        payService.update(pay);
        return ResponseModel.ok(pay);
    }

    @Operation(description = "删除其他付款")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        ProOtherPay proOtherPay = payService.queryById(id);
        if (!Objects.isNull(proOtherPay) && proOtherPay.getState()) {
            return ResponseModel.error("单据已审核，禁止删除");
        }
        messageService.deleteMessage(id);
        return ResponseModel.ok(payService.deleteById(id));
    }

    @Operation(description = "查询其他付款单列表")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             Boolean sortDesc,
                             String tagName,
                             String searchText,
                             String startDate,
                             String endDate,
                             Boolean ifMine,
                             @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ProOtherPay proOtherPay = new ProOtherPay();
        proOtherPay.setTitle(searchText);
        proOtherPay.setPayTypeTag(tagName);
        if (StringUtils.isNotBlank(startDate)) {
            proOtherPay.setDatetime(DateUtil.parse(startDate + " 00:00:00", DateUtil.PATTERN_CLASSICAL));
            proOtherPay.setRemark(endDate + " 23:59:59");
        }
        if (ifMine) {
            proOtherPay.setStaff(staff);
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return ResponsePage.ok((Page) payService.queryAll(proOtherPay));
    }

    @Operation(description = "导出付款单列表")
    @GetMapping("exportList")
    public ResponseModel exportList(Integer page,
                                    Integer itemsPerPage,
                                    String sortBy,
                                    Boolean sortDesc,
                                    String tagName,
                                    String searchText,
                                    String startDate,
                                    String endDate,
                                    Boolean ifMine,
                                    @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ProOtherPay proOtherPay = new ProOtherPay();
        proOtherPay.setTitle(searchText);
        proOtherPay.setPayTypeTag(tagName);
        if (StringUtils.isNotBlank(startDate)) {
            proOtherPay.setDatetime(DateUtil.parse(startDate + " 00:00:00", DateUtil.PATTERN_CLASSICAL));
            proOtherPay.setRemark(endDate + " 23:59:59");
        }
        if (ifMine) {
            proOtherPay.setStaff(staff);
        }
        List<ProOtherPay> proOtherPays = payService.queryAll(proOtherPay);
        if (proOtherPays.size() > 10000) {
            return ResponseModel.error("导出数据过大，请缩短日期范围,分批导出");
        }

        proOtherPays.forEach(item -> {
            String ZH = "";
            String JQ = "";
            String SP = "";
//            String YJ = "";
            FlowMessage message = flowMessageService.getMessageByFrameId(item.getId());
            if (!Objects.isNull(message)) {
                message.setStaff(staffService.getStaffById(message.getStaffId()));
                List<FlowApprove> flowApproveByMessageId = flowApproveService.getFlowApproveByMessageId(message.getId());
                for (int i = 0; i < flowApproveByMessageId.size(); i++) {
                    if (flowApproveByMessageId.get(i).getApproveState() == 5 || flowApproveByMessageId.get(i).getApproveState() == 6) {
                        if (flowApproveByMessageId.get(i).getAcceptUser() != null){
                            ZH += flowApproveByMessageId.get(i).getAcceptUser().getName() + "\r\n";
                        }
                    } else if (flowApproveByMessageId.get(i).getPo00421().contains("加签")) {
                        if (flowApproveByMessageId.get(i).getAcceptUser() != null){
                            JQ += flowApproveByMessageId.get(i).getAcceptUser().getName() + "-" + flowApproveByMessageId.get(i).getContent() + "\r\n";
                        }
                    } else {
                        if (flowApproveByMessageId.get(i).getAcceptUser() != null){
                            SP += flowApproveByMessageId.get(i).getAcceptUser().getName() + "-" + flowApproveByMessageId.get(i).getContent() + "\r\n";
                        }
                    }
                }
                item.setJQ(JQ);
                item.setSP(SP);
                item.setZH(ZH);
//                item.setYJ(YJ);
            }
        });

        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader2());
        rows.add(header);
        for (int i = 0; i < proOtherPays.size(); i++) {
            proOtherPays.get(i).setIndex(i + 1);
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell2(proOtherPays.get(i), row.getIndex()));
            rows.add(row);
        }

        String filename = DateUtil.format(new Date(), DateUtil.PATTERN_IMAGE_NAME) + "付款记录.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader2() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "标题", "发起人", "金额", "付款对象", "备注", "付款时间", "审批人", "知会", "加签"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 3 || i == 5 || i == 6) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 4 || i == 7) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell2(ProOtherPay proOtherPay, int index) {
        String[] headers = new String[]{"ProOtherPay.Index", "ProOtherPay.Title", "ProOtherPay.Staff.Name", "ProOtherPay.PayMoney", "ProOtherPay.Company.Name",
                "ProOtherPay.Remark", "ProOtherPay.PayDatetime", "ProOtherPay.SP", "ProOtherPay.ZH", "ProOtherPay.JQ"};
        ProOtherPayModel model = new ProOtherPayModel();
        model.setIndex(index);
        model.setProOtherPay(proOtherPay);
        return EntityUtils.getCells(model, headers);
    }

    @Operation(description = "查询指定时间范围内，付款金额合计")
    @GetMapping("getSumMoney")
    public ResponseModel getSumMoney(String startDate,
                                     String endDate,
                                     String tagName,
                                     Integer state) {
        if (StringUtils.isNotBlank(startDate)) {
            return ResponseModel.ok(payService.getSumMoney(startDate + " 00:00:00", endDate + " 23:59:59", tagName, state));
        } else {
            return ResponseModel.ok(payService.getSumMoney(null, null, tagName, state));
        }
    }

    @Operation(description = "根据id查询付款单")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(payService.queryById(id));
    }

    @Operation(description = "根据付款id查询付款类型")
    @GetMapping("payType/{id}")
    public ResponseModel gtePayType(@PathVariable Integer id) {
        return ResponseModel.ok(tagService.queryById(id + ""));
    }
}
