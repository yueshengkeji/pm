package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProStaffAccount;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ProStaffAccountService;
import com.yuesheng.pm.service.ProStaffBalanceHistoryService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/staffAccount")
public class ProStaffAccountApi extends BaseApi {

    @Autowired
    private ProStaffAccountService accountService;

    @Autowired
    private ProStaffBalanceHistoryService historyService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private SystemConfigService systemConfig;

    private String managerValue = "前台";

    @PostConstruct
    public void init() {
        SystemConfig sc = systemConfig.queryByCoding("AMOUNT");
        if (!Objects.isNull(sc)) {
            managerValue = sc.getValue();
        }
    }

    @Operation(description = "员工账户退款")
    @PostMapping("returnAmount")
    public ResponseModel returnAmount(@Validated @RequestBody ProStaffBalanceHistory balanceHistory,
                                      @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        if (StringUtils.contains(StringUtils.join(staff.getRoleName()), managerValue) || StringUtils.contains(StringUtils.join(staff.getRoleName()), "管理员")) {
            balanceHistory.setRemark(Constant.RETURN_BALANCE_STR);
            balanceHistory.setOperate(staff);
            balanceHistory.setType((byte) 2);
            return new ResponseModel(accountService.subtract(balanceHistory, staff));
        } else {
            return new ResponseModel(500, "无操作权限");
        }
    }

    @Operation(description = "员工账户充值")
    @PostMapping("rechargeAmount")
    public ResponseModel rechargeAmount(@Validated @RequestBody ProStaffBalanceHistory balanceHistory,
                                        @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        if (StringUtils.contains(StringUtils.join(staff.getRoleName()), managerValue) || StringUtils.contains(StringUtils.join(staff.getRoleName()), "管理员")) {
            balanceHistory.setRemark(Constant.BALANCE_STR);
            balanceHistory.setOperate(staff);
            return new ResponseModel(accountService.rechargeAmount(balanceHistory, staff));
        } else {
            return new ResponseModel(500, "无操作权限");
        }
    }

    @Operation(description = "查询充值记录")
    @GetMapping("listHistory")
    public ResponsePage history(String staffId,
                                Integer page,
                                Integer itemsPerPage,
                                String sortBy,
                                Boolean sortDesc,
                                String searchText,
                                String startDate,
                                String endDate) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) 0);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return new ResponsePage((Page) historyService.queryAll(query, startDate, endDate));
    }

    @Operation(description = "导出充值记录为excel")
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(String staffId,
                                     Integer page,
                                     Integer itemsPerPage,
                                     String sortBy,
                                     Boolean sortDesc,
                                     String searchText,
                                     String startDate,
                                     String endDate) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) 0);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, 5000, sortBy, sortDesc);
        List<ProStaffBalanceHistory> histories = historyService.queryAll(query, startDate, endDate);
        String fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-充值记录.xlsx";
        fileName = ExcelParse.writeExcel(histories,
                fileName,
                new String[]{"Staff.Name", "Money", "Type", "Datetime", "Remark"},
                ProStaffBalanceHistory.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "查询账户列表")
    @GetMapping("listAccount")
    public ResponseModel listAccount() {
        ProStaffAccount account = new ProStaffAccount();
        List<ProStaffAccount> accountList = accountService.queryAll(account);
        accountList.forEach(item -> {
            item.setStaff(staffService.getStaffById(item.getStaff().getId()));
        });
        return new ResponseModel(accountList);
    }

    @Operation(description = "导出余额为excel")
    @GetMapping("exportExcelBalance")
    public ResponseModel exportExcelBalance() {
        ProStaffAccount account = new ProStaffAccount();
        List<ProStaffAccount> accountList = accountService.queryAll(account);

        accountList.forEach(item -> {
            item.setStaff(staffService.getStaffById(item.getStaff().getId()));
            item.setSection(item.getStaff().getSection());
        });
        Double totalBalance = 0.00;
        for (int i = 0; i < accountList.size(); i++) {
            totalBalance += accountList.get(i).getBalance();
        }

        ProStaffAccount p = new ProStaffAccount();
        Staff s = new Staff();
        s.setName("汇总");
        p.setStaff(s);
        p.setBalance(totalBalance);

        accountList.add(p);

        String fileName = "截至" + DateUtil.getDate() + "员工余额统计表.xlsx";
        fileName = ExcelParse.writeExcel(accountList,
                fileName,
                new String[]{"Staff.Name", "Section.Name", "Balance"},
                ProStaffAccount.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "查询指定员工账户列表")
    @GetMapping("byStaffId/{staffId}")
    public ResponseModel getByStaffId(@PathVariable String staffId) {
        return new ResponseModel(accountService.queryByStaffId(staffId));
    }

}
