package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.WorkArticle;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.model.WorkOutMaterModel;
import com.yuesheng.pm.service.SectionService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.service.WorkArticleMaterService;
import com.yuesheng.pm.service.WorkArticleService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "办公用品管理")
@RestController
@RequestMapping("api/workArticle")
public class WorkArticleApi extends BaseApi {

    @Autowired
    private WorkArticleService workArticleService;

    @Autowired
    private WorkArticleMaterService materService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private SectionService sectionService;

    @Operation(description = "新增办公单据")
    @PutMapping
    public ResponseModel insert(@RequestBody MaterOutChild[] materOutChild, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        WorkArticle wa = workArticleService.insertByMater(Arrays.asList(materOutChild), staff);
        return new ResponseModel(wa);
    }

    @Operation(description = "查询我的办公用品")
    @GetMapping("myList")
    public ResponsePage myList(Integer page,
                               Integer itemsPerPage,
                               String sortBy,
                               String sortDesc,
                               String searchText,
                               @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        MaterOutChild materOutChild = new MaterOutChild();
        materOutChild.setPm02110(staff.getId());
        materOutChild.setPm02112(searchText);
        sortBy = getSortName(sortBy);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<WorkOutMaterModel> materOutChildren = (Page) materService.queryAll(materOutChild);
        setStaff(materOutChildren);
        return new ResponsePage(materOutChildren);
    }

    private String getSortName(String sortBy) {
        if (StringUtils.isNotBlank(sortBy)) {
            switch (sortBy) {
                case "materOut.material.name":
                    return "po20102";
                case "materOut.sum":
                    return "po20507";
                case "materOut.material.unit.name":
                    return "po20105";
                case "materOut.outDate":
                default:
                    return "po20408";
            }
        } else {
            return "po20408";
        }
    }

    @Operation(description = "导出办公用品领用记录")
    @GetMapping("export")
    public ResponseModel export(String sortBy,
                                String sortDesc,
                                String searchText,
                                String staffId,
                                String sectionId,
                                String startDate,
                                String endDate) throws Exception {
        MaterOutChild materOutChild = getQueryParam(searchText, staffId, sectionId, startDate, endDate);
        if (Objects.isNull(materOutChild)) {
            throw new Exception("未查询到数据,导出失败");
        } else {
            if (StringUtils.isBlank(materOutChild.getOutDate())) {
                throw new Exception("请指定查询日期范围");
            } else {
                startPage(1, 5000, sortBy, sortDesc);
                List<WorkOutMaterModel> outChildList = materService.queryAll(materOutChild);
                String fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-办公用品领用记录.xlsx";
                fileName = ExcelParse.writeExcel(outChildList,
                        fileName,
                        new String[]{"MaterOut.Material.Name", "MaterOut.Sum", "MaterOut.Material.Unit.Name", "MaterOut.OutDate", "Staff.Name"},
                        WorkOutMaterModel.class);
                return new ResponseModel(fileName);
            }
        }
    }

    @Operation(description = "查询办公用品领用记录")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             String sortDesc,
                             String searchText,
                             String staffId,
                             String sectionId,
                             String startDate,
                             String endDate) {
        MaterOutChild materOutChild = getQueryParam(searchText, staffId, sectionId, startDate, endDate);
        if (Objects.isNull(materOutChild)) {
            return new ResponsePage(new Page());
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<WorkOutMaterModel> materOutChildren = (Page) materService.queryAll(materOutChild);
        setStaff(materOutChildren);
        return new ResponsePage(materOutChildren);
    }

    private void setStaff(Page<WorkOutMaterModel> materOutChildren) {
        materOutChildren.forEach(item -> {
            Staff staff1 = item.getStaff();
            if (!Objects.isNull(staff1)) {
                item.setStaff(staffService.getStaffById(staff1.getId()));
            }
        });
    }

    private MaterOutChild getQueryParam(String searchText, String staffId, String sectionId, String startDate, String endDate) {
        MaterOutChild materOutChild = new MaterOutChild();
        if (StringUtils.isNotBlank(staffId)) {
            materOutChild.setPm02110(staffId);
        }
        if (StringUtils.isNotBlank(sectionId)) {
            List<Staff> staffList = sectionService.getStaffList(sectionId);
            StringBuffer sb = new StringBuffer();
            staffList.forEach(item -> {
                if (!Objects.isNull(item)) {
                    sb.append("'");
                    sb.append(item.getId());
                    sb.append("',");
                }
            });
            if (sb.length() > 0) {
                materOutChild.setPm02109(sb.substring(0, sb.length() - 1));
            } else {
                return null;
            }
        }
        //日期范围
        materOutChild.setPm02112(startDate);
        materOutChild.setOutDate(endDate);
        //搜索字符串
        materOutChild.setRemark(searchText);
        return materOutChild;
    }

    @Operation(description = "通过主键获取办公用品信息")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(workArticleService.getWorkArticleById(id));
    }

    @Operation(description = "获取仓库中的办公用品列表")
    @GetMapping("getByStorage")
    public ResponseModel getByStorage(@Parameter(name="模糊查询字符串") String searchText,
                                      @RequestParam(defaultValue = "po20101") String sortName,
                                      @RequestParam(defaultValue = "DESC") String sortOrder,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer itemsPerPage,
                                      @Parameter(name="如果传任意值，则加载有库存的材料") String type,
                                      @Parameter(name="指定库存最大值") Double maxNumber,
                                      @RequestParam(defaultValue = "0.0") @Parameter(name="指定库存最小值") Double minNumber) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        params.put("type", type);
        params.put("maxNumber", maxNumber);
        params.put("minNumber", minNumber);
        startPage(page, itemsPerPage, sortName, sortOrder);
        List<Material> mocList = materService.queryMater(params);
        for (Material m : mocList) {
            if (m != null) {
                m.setFolderObj(materService.queryFolder(m.getFolder()));
                m.setPlanPrice(materService.queryNewPlace(m.getId()));
            }
        }
        int count = materService.queryMaterCount(params);
        params.clear();
        params.put("rows", mocList);
        params.put("total", count);
        return new ResponseModel(params);
    }

    @Operation(description = "删除办公用品领用")
    @DeleteMapping("{id}/{materId}")
    public ResponseModel delete(@PathVariable String id,@PathVariable String materId) {
        WorkArticle wa = workArticleService.getWorkArticleById(id);
        if (!Objects.isNull(wa) && wa.getApproveStatus() == 0) {
            materService.deleteArticle(materId);
            return ResponseModel.ok(id);
        } else {
            return ResponseModel.error("单据已审核或已被删除");
        }
    }

}
