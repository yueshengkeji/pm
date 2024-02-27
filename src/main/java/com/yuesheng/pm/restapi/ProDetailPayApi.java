package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProDetailPay;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProDetailPayService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("api/detailPay")
public class ProDetailPayApi {
    @Autowired
    private ProDetailPayService payService;

    @Operation(description = "修改付款报表")
    @PostMapping
    public ResponseModel update(@RequestBody ProDetailPay pay, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ProDetailPay countPayMoney = payService.updatePayAndBillMoney(pay);
        payService.updateLastMsg(DateFormat.getDate(), staff, pay.getId());
        return new ResponseModel(countPayMoney);
    }

    @Operation(description = "新增付款报表")
    @PutMapping
    public ResponseModel insert(@RequestBody ProDetailPay pay, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (pay.getMainId() != null) {
            pay.setId(UUID.randomUUID().toString());
            pay.setDate(DateFormat.getDate());
            pay.setStaff(staff);
            payService.addPay(pay);
            return ResponseModel.ok(pay);
        } else {
            return ResponseModel.error("添加失败");
        }
    }

    @Operation(description = "查询付款报表")
    @GetMapping("list/{detailId}")
    public ResponseModel list(@PathVariable String detailId) {
        return ResponseModel.ok(payService.getPayDetails(detailId));
    }

    @Operation(description = "查询付款报表总数")
    @GetMapping("moneyCount/{detailId}")
    public ResponseModel moneyCount(@PathVariable String detailId) {
        return ResponseModel.ok(payService.getMoneySumByMain(detailId));
    }

    @Operation(description = "查询年度付款/收票金额合计")
    @GetMapping("getMoneySum/{year}")
    public ResponseModel getMoneySum(@PathVariable String year)
    {
        HashMap<String,Double> result = new HashMap<>();
        result.put("payMoney",payService.getPayMoney(year));
        result.put("billMoney",payService.getBillMoney(year));
        return ResponseModel.ok(result);
    }
}
