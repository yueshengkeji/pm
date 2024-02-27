package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.ProjectAuth;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.RequestPageModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.PlanMaterialService;
import com.yuesheng.pm.service.PlanService;
import com.yuesheng.pm.service.ProjectService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 材料计划单接口.
 *
 * @author xiaoSong
 * @date 2020-07-27
 */
@RestController
@RequestMapping("/api/plan")
@Tag(name = "材料计划单接口")
public class PlanApi {
    @Autowired
    private PlanService planService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private PlanMaterialService planMaterialService;
    @Autowired
    private ProjectService projectService;

    @Operation(description = "获取材料计划单集合")
    @GetMapping("getPlans")
    public ResponseModel getPlans(@Parameter(name = "分页请求数据模型", example = "userId='用户id(可选),offset=数据页位置（必选）,limit=数据大小（必选），order=排序方式（可选）,sort=排序名称（可选）,searchText=搜索字符串（可选），dataType=单据类型(可选)'") RequestPageModel requestPageModel,@SessionAttribute(Constant.SESSION_KEY) Staff staff) throws UnsupportedEncodingException {
        Map<String, Object> param = new HashMap<>();
        List<ProjectAuth> projectList = projectService.getProjectAuthByStaff(staff.getId());
        if(!projectList.isEmpty()){
            param.put("projectAuth",staff.getId());
        }

        param.put("staff", requestPageModel.getStaffId());
        param.put("order", MaterialController.isSort(requestPageModel.getSort(), requestPageModel.getOrder()));
        param.put("str", StringUtils.isBlank(requestPageModel.getSearchText()) ? null : requestPageModel.getSearchText());
        List<Plan> planList = planService.getPlans(param,requestPageModel.getOffset(), requestPageModel.getLimit());
        for (int x = 0; x < planList.size(); x++) {
            if (Objects.isNull(flowMessageService.getMessageByFrameId(planList.get(x).getId())) && planList.get(x).getAppMark() == 0) {
                planList.get(x).setAppMark((byte) -1);
            }
        }
        return new ResponseModel(planList);
    }

    @Operation(description = "添加计划单")
    @PutMapping
    public ResponseModel addPlan(@RequestBody Plan plan, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        plan.setStaff(staff);
        planService.insert(plan);
        return new ResponseModel(plan);
    }

    @Operation(description = "修改计划单")
    @PostMapping
    public ResponseModel updateplan(@RequestBody Plan plan, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (plan.getAppMark() == 1) {
            return new ResponseModel(401, null, "计划单已审核，不能修改！");
        } else if (!plan.getStaff().getId().equals(staff.getId())) {
            return new ResponseModel(401, null, "只能修改自己的单据哦！");
        }
        return new ResponseModel(planService.update(plan));
    }

    @Operation(description = "删除计划单")
    @DeleteMapping
    public ResponseModel deletePlan(@Parameter(name = "计划单id") String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return new ResponseModel(planService.delete(id, staff));
    }

    @Operation(description = "查询计划单条目总数，参数参见 /getPlans")
    @GetMapping("getTotal")
    public ResponseModel<Integer> getTotal(RequestPageModel model) {
        Map<String, Object> param = new HashMap<>();
        param.put("staff", model.getUserId());
        param.put("str", StringUtils.isBlank(model.getSearchText()) ? null : model.getSearchText());
        return ResponseModel.ok(planService.getPlansCount(param));
    }

    @Operation(description = "查询计划单明细")
    @GetMapping("getById/{id}")
    public ResponseModel getPlanById(@PathVariable String id) {
        Plan plan = planService.getPlanById(id);
        if (!Objects.isNull(plan) && plan.getMaterialList() == null) {
            plan.setMaterialList(planMaterialService.getMaterialsByPlan(id));
        }
        return new ResponseModel(plan);
    }
}
