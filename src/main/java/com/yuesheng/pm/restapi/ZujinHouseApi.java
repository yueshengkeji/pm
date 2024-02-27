package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.entity.ProZujinHouseR;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Tag(name = "商铺管理")
@RestController
@RequestMapping("api/zujinHouse")
public class ZujinHouseApi {
    @Autowired
    private ProZujinHouseService houseService;
    @Autowired
    private ProZujinYtService ytService;
    @Autowired
    private ProHouseJsonService jsonService;
    @Autowired
    private ProZujinService zujinService;
    @Autowired
    private ProZujinHouseRService houseRService;

    @Operation(description = "查询商铺json地图")
    @GetMapping("/houseJson/{floor}")
    public ResponseModel getHouseJson(@PathVariable String floor) {
        return new ResponseModel(jsonService.queryById(floor));
    }

    @Operation(description = "更新商铺信息")
    @PostMapping
    public ResponseModel update(@RequestBody ProZujinHouse house) {
        return new ResponseModel(houseService.update(house));
    }

    @Operation(description = "添加商铺信息")
    @PutMapping
    public ResponseModel insert(@RequestBody ProZujinHouse house) {
        return new ResponseModel(houseService.insert(house));
    }

    @Operation(description = "查询商铺信息明细")
    @GetMapping("{id}")
    public ResponseModel getById(Integer id) {
        return new ResponseModel(houseService.queryById(id));
    }

    @Operation(description = "查询商铺列表")
    @GetMapping("list")
    public ResponseModel list(String pwNumber,
                              String floor,
                              Integer yetaiId,
                              String type,
                              Integer flag,
                              String searchText,
                              Integer page,
                              Integer itemsPerPage) {
        HashMap<String, Object> params = new HashMap<>(16);
        params.put("pwNumber", pwNumber);
        params.put("floor", floor);
        params.put("yetaiId", yetaiId);
        params.put("type", type);
        params.put("flag", flag);
        params.put("searchText", searchText);
        List<ProZujinHouse> houseList = houseService.queryByParam(params, page, itemsPerPage);
        Integer total = houseService.queryCountByParam(params);
        for (ProZujinHouse proZujinHouse : houseList) {
            if (!Objects.isNull(proZujinHouse)) {
                proZujinHouse.setYt(ytService.queryById(proZujinHouse.getYetaiId()));
            }
            if ("1".equals(proZujinHouse.getFlag())) {
                ProZujinHouseR query = new ProZujinHouseR();
                query.setHouseId(proZujinHouse.getId());
                query.setType((byte)0);
                List<ProZujinHouseR> rs = houseRService.queryAll(query);
                if (!rs.isEmpty()) {
                    ProZujin item = zujinService.queryById(rs.get(0).getZjId());
                    if (item != null && item.getYsMoney() != null) {
                        if (item.getCwMoney() == null || item.getYsMoney() > item.getCwMoney()) {
                            //欠租状态
                            proZujinHouse.setFlag("2");
                        }
                    }
                }
            }

        }
        params.clear();
        params.put("rows", houseList);
        params.put("total", total);
        return new ResponseModel(params);
    }

    @Operation(description = "查询楼层列表")
    @GetMapping("floors")
    public ResponseModel floors() {
        return new ResponseModel(houseService.queryFloor());
    }
}
