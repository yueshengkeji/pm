package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProEnquiry;
import com.yuesheng.pm.entity.ProEnquiryMater;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "询价单管理")
@RestController
@RequestMapping("api/enquiry")
public class ProEnquiryApi extends BaseApi {

    @Autowired
    private ProEnquiryMaterService enquiryMaterService;

    @Autowired
    private ProEnquiryService proEnquiryService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ProQuoteService proQuoteService;


    @Operation(description = "发布询价单")
    @PutMapping
    public ResponseModel insert(@RequestBody ProEnquiry proEnquiry, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proEnquiry.setStaffId(staff.getId());
        int success = proEnquiryService.insert(proEnquiry);
        if (success <= 0 || proEnquiry.getId() == null) {
            return new ResponseModel(500, "添加询价单失败");
        }
        for (ProEnquiryMater pem : proEnquiry.getProEnquiryMaterList()) {
            pem.setEnquiryId(proEnquiry.getId());
            enquiryMaterService.insert(pem);
        }
        return new ResponseModel(proEnquiry);
    }

    /**
     * 查询询价单集合
     *
     * @param searchText 模糊查询字符串
     * @param applyId    申请单id
     * @param isClose    是否已关闭（0=未关闭，1=已关闭）
     * @param staffId    职员id
     * @param startDate  开始日期
     * @param endDate    截止日期
     * @param sortName   排序名称
     * @param sortOrder  排序类型
     * @param pageSize   数据大小
     * @param pageNumber 数据索引位置
     * @return 询价单map:{rows:数据集合,total:总条目数}
     */
    @GetMapping("/list")
    public ResponsePage queryEnquiry(String searchText,
                                     String applyId,
                                     String isClose,
                                     String staffId,
                                     String startDate,
                                     String endDate,
                                     String sortName,
                                     String sortOrder,
                                     Integer pageSize,
                                     Integer pageNumber) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("order", MaterialController.isSort(sortName, sortOrder));
        param.put("str", "".equals(searchText) ? null : searchText);
        param.put("applyId", applyId);
        param.put("isClose", isClose);
        param.put("staffId", staffId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        startPage(pageNumber,pageSize,sortName,sortOrder);
        Page<ProEnquiry> enquiryList = (Page)proEnquiryService.queryEnquiryList(param);
        for (ProEnquiry enquiry : enquiryList) {
//            询价人员信息
            enquiry.setStaff(staffService.getStaffById(enquiry.getStaffId()));
//            申请单信息
            enquiry.setApply(applyService.getApplyById(enquiry.getApplyId()));
            enquiry.setQuoteCount(0);
//            报价单位总数统计
            List<Integer> integers = proQuoteService.queryCount(enquiry.getId());
            for (int i = 0; i < integers.size(); i++) {
                if (integers.get(i) != null) {
                    enquiry.setQuoteCount(enquiry.getQuoteCount() + integers.get(i));
                }
            }

        }
        int total = proEnquiryService.queryEnquiryCount(param);
        param.clear();
        param.put("rows", enquiryList);
        param.put("total", total);
        return ResponsePage.ok(enquiryList);
    }

    /**
     * 关闭/开启询价单
     *
     * @param state 0=开启，1=关闭
     * @param id    询价单主单据
     * @return 操作状态码（state）,操作提示（msg)
     */
    @Operation(description = "关闭/开启询价单")
    @PostMapping("/closeEnquiry")
    public ResponseModel closeEnquiry(Integer state, String id) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("state", proEnquiryService.closeEnquiry(state, id));
        result.put("msg", "操作成功");
        return ResponseModel.ok(result);
    }

}
