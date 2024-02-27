package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProDetailMoney;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProDetailMoneyService;
import com.yuesheng.pm.util.DateFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "会计科目")
@RestController
@RequestMapping("api/detailMoney")
public class ProDetailMoneyApi extends BaseApi {

    @Autowired
    private ProDetailMoneyService proDetailMoneyService;

    @Operation(description = "新增会计科目")
    @PutMapping("owe")
    public ResponseModel insert(@RequestBody ProDetailMoney money) {
        if (money.getMainId() != null) {
            money.setDate(DateFormat.getDateTime());
            money.setId(UUID.randomUUID().toString());
            proDetailMoneyService.addMoney(money);
            return ResponseModel.ok(money);
        } else {
            return ResponseModel.error("录入失败");
        }
    }

    @Operation(description = "修改会计科目")
    @PostMapping
    public ResponseModel update(@RequestBody ProDetailMoney money) {
        proDetailMoneyService.update(money);
        return ResponseModel.ok(money);
    }

    @Operation(description = "获取会计科目列表")
    @GetMapping("list")
    public ResponseModel list(String detailId) {
        return ResponseModel.ok(proDetailMoneyService.getMoneyByMainId(detailId));
    }
}
