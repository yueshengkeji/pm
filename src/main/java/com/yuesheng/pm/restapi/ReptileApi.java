package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Reptile;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ReptileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "爬虫数据")
@RestController
@RequestMapping("api/reptile")
public class ReptileApi extends BaseApi {

    @Autowired
    private ReptileService reptileService;

    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String keys,
                             String startDate,
                             String endDate,
                             String sortBy,
                             Boolean sortDesc){
        Reptile query= new Reptile();
        query.setKey(keys);
        query.setReptileDatetime(startDate);
        startPage(page,itemsPerPage,sortBy,sortDesc);
        return ResponsePage.ok((Page) reptileService.selectByParam(query));
    }

}
