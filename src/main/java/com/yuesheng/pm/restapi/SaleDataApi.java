package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.SaleData;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.SaleDataService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "商铺销售数据管理")
@RequestMapping("api/saleData")
@RestController
public class SaleDataApi extends BaseApi {
    @Autowired
    private SaleDataService saleDataService;

    @Operation(description = "添加销售数据")
    @PutMapping
    public ResponseModel insert(@RequestBody SaleData saleData, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        saleData.setStaff(staff);
        saleDataService.insert(saleData);
        return ResponseModel.ok(saleData);
    }

    @Operation(description = "批量添加销售数据")
    @PutMapping("export")
    public ResponseModel insert(@RequestBody SaleData[] saleData, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ArrayList<SaleData> arrayList = new ArrayList();
        for (int i = 0; i < saleData.length; i++) {
            SaleData sd = saleData[i];
            if (!Objects.isNull(sd)) {
                sd.setId(UUID.randomUUID().toString());
                sd.setDate(DateUtil.getDatetime());
                sd.setStaff(staff);
                arrayList.add(sd);
            }
        }
        saleDataService.insertByArray(arrayList);
        return ResponseModel.ok(saleData);
    }

    @Operation(description = "删除销售数据")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        saleDataService.deleteById(id);
        return ResponseModel.ok(id);
    }

    @Operation(description = "查询销售数据列表")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String searchText,
                             String saleStartDate,
                             String saleEndDate,
                             String sortBy,
                             String yetai,
                             String sortDesc) {
        HashMap<String, Object> query = new HashMap<>();
        query.put("searchText", searchText);
        query.put("saleStartDate", saleStartDate);
        query.put("saleEndDate", saleEndDate);
        query.put("yetai", yetai);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<SaleData> saleDataPage = (Page<SaleData>) saleDataService.queryAll(query);
        Double money = saleDataService.queryMoney(query);
        ResponsePage<SaleData> rp = ResponsePage.ok(saleDataPage);
        rp.getData().put("money", money);
        return rp;
    }

    @Operation(description = "查询销售数据按天统计")
    @GetMapping("moneyTotal")
    public ResponseModel moneyTotal(Integer day) {
        HashMap<String,List<DateGroupModel>> result = new HashMap();
        if (!Objects.isNull(day)) {
            String dayStr;
            if(day <= 9){
                dayStr = "0"+day;
            }else{
                dayStr = String.valueOf(day);
            }
            String date = DateUtil.format(new Date(), "yyyy-MM-");
            HashMap<String,Object> param = new HashMap<>();
            param.put("saleStartDate", date+"01");
            param.put("saleEndDate", date+dayStr);
            List<DateGroupModel> thanGroupModels = saleDataService.queryMoneyGroupSaleDate(param);
            String prevDate = DateUtil.format(DateUtil.getLastMonthStartTime(),"yyyy-MM-");
            param.put("saleStartDate", prevDate+"01");
            param.put("saleEndDate", prevDate+dayStr);
            List<DateGroupModel> prevGroupModels = saleDataService.queryMoneyGroupSaleDate(param);
            result.put("thanGroupModels",thanGroupModels);
            result.put("prevGroupModels",prevGroupModels);
        }

        return ResponseModel.ok(result);
    }


    @Operation(description = "查询销售排名")
    @GetMapping("topList")
    public ResponseModel topList(String startDate,String endDate){
        int day = DateUtil.getOffsetDays(startDate+" 00:00:00",endDate+" 23:59:59");
        if(day > 100){
            return ResponseModel.error("最多统计100天内数据，请缩短时间范围");
        }
        HashMap<String,List<DateGroupModel>> result = new HashMap<>();
        List<DateGroupModel> dayGroupModels = saleDataService.queryByDayTopList(startDate,endDate);
        List<DateGroupModel> dateGroupModels = saleDataService.queryByDateTopList(startDate,endDate);
        result.put("dayGroupModels",dayGroupModels);
        result.put("dateGroupModels",dateGroupModels);
        return ResponseModel.ok(result);
    }

}
