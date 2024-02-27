package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.City;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Tag(name = "货运地址管理")
@RestController
@RequestMapping("api/address")
public class CityApi {
    @Autowired
    private CityService cityService;

    @Operation(description = "搜索货运地址")
    @GetMapping("search")
    public ResponseModel search(String searchText) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(searchText)) {
            searchText = URLDecoder.decode(searchText, "UTF-8");
        }
        return new ResponseModel(cityService.seek(searchText));
    }

    @Operation(description = "添加货运地址")
    @PutMapping
    public ResponseModel insert(@RequestBody City city) {
        return new ResponseModel(cityService.insert(city));
    }
}
