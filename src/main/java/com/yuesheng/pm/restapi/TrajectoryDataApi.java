package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.TrajectoryData;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.TrajectoryDataService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;

import static com.yuesheng.pm.util.DateUtil.PATTERN_CLASSICAL_SIMPLE;

@Tag(name = "员工行程管理")
@RestController
@RequestMapping("api/tdc")
public class TrajectoryDataApi extends BaseApi {

    @Autowired
    private TrajectoryDataService trajectoryDataService;

    @Operation(description = "添加行程")
    @PutMapping
    public ResponseModel insert(@RequestBody TrajectoryData data, HttpSession httpSession) {
        Map<String, Object> result = new HashMap<>();
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        data.setBackDate(DateUtil.format(new Date(), PATTERN_CLASSICAL_SIMPLE));
        data.setStaff(staff);
        data.setState(0);
        data.setId(UUID.randomUUID().toString());
        trajectoryDataService.insert(data);
        result.put("data", data);
        return ResponseModel.ok(result);
    }

    @Operation(description = "查询行程")
    @GetMapping("{id}")
    public ResponseModel queryById(@PathVariable String id) {
        return ResponseModel.ok(trajectoryDataService.queryById(id));
    }

    @Operation(description = "查询行程列表")
    @GetMapping("/list")
    public ResponseModel queryByParam(String start, String end, String str) {
        Map<String, Object> param = new HashMap<>();
        param.put("start", start);
        param.put("end", end);
        param.put("str", str);
        List<TrajectoryData> result = trajectoryDataService.query(param);
        Integer count = trajectoryDataService.queryCount(param);
        param.clear();
        param.put("rows", result);
        param.put("total", count);
        return ResponseModel.ok(param);
    }

    @Operation(description = "删除行程")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        return ResponseModel.ok(trajectoryDataService.delete(id));
    }
}
