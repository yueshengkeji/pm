package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Region;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.RegionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "地区管理")
@RestController
@RequestMapping("api/region")
public class RegionApi extends BaseApi {
    @Autowired
    private RegionService regionService;

    @Operation(description = "获取地区集合")
    @GetMapping
    public ResponseModel get(String searchText) {
        List<Region> regions;
        if (StringUtils.isBlank(searchText)) {
            startPage(1, 10, "pj01504", "asc");
            regions = regionService.getRegions(searchText);
        } else {
            regions = regionService.getRegions(searchText);
        }
        return new ResponseModel(200, regions, regions.toString());
    }

    @Operation(description = "添加地区")
    @PostMapping
    public ResponseModel add(@RequestBody Region region) {
        regionService.insert(region);
        return new ResponseModel(region);
    }

    @Operation(description = "修改地区")
    @PutMapping
    public ResponseModel update(@RequestBody Region region) {
        regionService.update(region);
        return new ResponseModel(region);
    }
}
