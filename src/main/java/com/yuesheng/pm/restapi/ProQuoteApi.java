package com.yuesheng.pm.restapi;

import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ProQuoteModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "报价单管理")
@RestController
@RequestMapping("api/proQuote")
public class ProQuoteApi {
    @Autowired
    private ProQuoteService proQuoteService;
    @Autowired
    private ProEnquiryMaterService enquiryMaterService;
    @Autowired
    private ApplyMaterialService materialService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ProEnquiryService proEnquiryService;

    @Operation(description = "查询报价单列表")
    @GetMapping("list")
    public ResponseModel list(String enquiryId) {
        List<ProQuote> quoteList = proQuoteService.queryByEnquiry(enquiryId);
        quoteList.forEach(quote -> {
            quote.setEnquiryMater(enquiryMaterService.queryById(quote.getEnquiryMaterId()));
            ApplyMaterial am = materialService.getMaterById(quote.getEnquiryMater().getApplyMaterialId());
            quote.setApplyMaterial(am);
            if (!Objects.isNull(am)) {
                quote.setApply(applyService.getApplyById(am.getApplyid()));
            }
        });
        return new ResponseModel(quoteList);
    }

    @Operation(description = "获取询价单")
    @GetMapping("getQuote/{enquiryId}")
    @NoToken
    public ResponseModel getQuote(@PathVariable String enquiryId){
        ProEnquiry proEnquiry = proEnquiryService.queryEnquiry(enquiryId);
        if (proEnquiry == null || proEnquiry.isClose()) {
            return new ResponseModel(500,"当前询价单已关闭");
        }
        Map<String, Object> param = new HashMap<>(16);
        param.put("enquiryId", enquiryId);
        List<ProEnquiryMater> maters = enquiryMaterService.queryList(param);
        if (maters == null || maters.isEmpty()) {
            return new ResponseModel(500,"当前询价材料已采购完毕");
        }
        param.put("enquiry",proEnquiry);
        param.put("material",maters);
        return new ResponseModel(param);
    }

    @Operation(description = "添加询价单")
    @PostMapping("insertQuote")
    @NoToken
    public ResponseModel insertQuote(@RequestBody ProQuoteModel proQuoteList){
        Map<String, Object> result = new HashMap<>(16);
        for (ProQuote pq : proQuoteList.getProQuoteList()) {
            result.put("state", proQuoteService.insert(pq));
            if (StringUtils.isNotBlank(pq.getEnquiryMaterId())) {
                enquiryMaterService.updateLastDate(new Date(), pq.getEnquiryMaterId());
            }
        }
        return new ResponseModel(200);
    }
}
