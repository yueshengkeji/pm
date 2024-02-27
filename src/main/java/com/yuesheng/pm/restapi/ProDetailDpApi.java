package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProDetailDpService;
import com.yuesheng.pm.service.ProDetailService;
import com.yuesheng.pm.service.ProPutDetailService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.StrUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "采购明细对账单到票明细")
@RestController
@RequestMapping("/api/proDetailDp")
public class ProDetailDpApi {
    @Autowired
    private ProDetailDpService proDetailDpService;
    @Autowired
    private ProDetailService proDetailService;
    @Autowired
    private ProPutDetailService proPutDetailService;

    @Operation(description = "查询到票登记信息")
    @GetMapping
    public ResponseModel getDp(@Parameter(name="项目名称") String project,
                               @Parameter(name="单位名称") String companyName,
                               @Parameter(name="入库单号") String searchText,
                               @Parameter(name="数据年份") String year,
                               @Parameter(name="数据月份") String month,
                               @Parameter(name="数据大小") Integer pageSize,
                               @Parameter(name="数据索引位置") Integer pageNumber,
                               @Parameter(name="排序类型（asc/desc）") String sortOrder,
                               @Parameter(name="排序名称(参考对象属性名称)") String sortName,
                               @Parameter(name="是否加载作废单据") Boolean destroyState) {
        Map<String, Object> result = new HashMap<>();
        result.put("project", StrUtils.emptyToNull(StrUtils.decodeStr(project)));
        result.put("companyName", StrUtils.emptyToNull(StrUtils.decodeStr(companyName)));
        result.put("date", year + "-" + (org.apache.commons.lang3.StringUtils.isBlank(month) ? "" : month));
        result.put("searchText", StrUtils.emptyToNull(StrUtils.decodeStr(searchText)));
        result.put("order", MaterialController.isSort(sortName, sortOrder));
        if (destroyState != null && destroyState) {
            result.put("state", "2");
        } else {
            result.put("state", "0,1");
        }
        List<ProDetailDP> dpList = proDetailDpService.getDpByParam(result, pageNumber, pageSize);
        for (ProDetailDP dp : dpList) {
            if (dp.getProject() == null || dp.getCompany() == null) {
                ProPutForDetail detail = proPutDetailService.getDetail(dp.getProDetailId());
                if (detail != null) {
                    Project p;
                    Company company;
                    if ((p = detail.getProject()) != null) {
                        dp.setProject(p);
                    } else {
                        p = new Project();
                        p.setName(detail.getProjectName());
                    }
                    ProDetail pd = proDetailService.getDetailById(detail.getMainId());
                    if (pd != null) {
                        if ((company = pd.getCompany()) != null) {
                            dp.setCompany(company);
                        } else {
                            company = new Company();
                            company.setName(pd.getComName());
                            dp.setCompany(company);
                        }
                    }
                    proDetailDpService.updateProDetailDp(dp);
                }
            }
        }
        Integer total = proPutDetailService.getDpCount(result);
        result.clear();
        result.put("total", total);
        result.put("rows", dpList);
        return new ResponseModel(result);
    }

    @Operation(description = "查询到票登记信息")
    @GetMapping("proDetailDpList/{detailCId}")
    public ResponseModel proDetailDpList(@PathVariable String detailCId) {
        return ResponseModel.ok(proDetailDpService.getDetailDp(detailCId));
    }

    @Operation(description = "添加到票信息")
    @PutMapping
    public ResponseModel insert(@RequestBody ProDetailDP dp, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ProDetailDP isUpdate = proDetailDpService.getProDetailDp(dp.getId());
        if (isUpdate != null) {
            proDetailDpService.updateProDetailDp(dp);
        } else {
            dp.setState((byte) 0);
            dp.setStaff(staff);
            proDetailDpService.insertProDetailDp(dp);
        }
        return new ResponseModel(dp);
    }

    @Operation(description = "修改单据状态")
    @PostMapping("/updateState")
    public ResponseModel update(@RequestBody ProDetailDP dp, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        dp.setApproveStaff(staff);
        dp.setApproveDate(DateUtil.format(new Date()));
        proDetailDpService.updateProDetailDp(dp);
        return new ResponseModel(dp);
    }

    @Operation(description = "查询付款/到票合计")
    @GetMapping("getMoneyCount/{detailCId}")
    public ResponseModel getMoneyCount(@PathVariable String detailCId) {
        return ResponseModel.ok(proPutDetailService.getMoneysByPayAndDp(detailCId));
    }
}
