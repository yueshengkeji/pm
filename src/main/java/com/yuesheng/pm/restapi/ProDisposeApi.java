package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.ProDispose;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.RequestPageModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProDisposeService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "处置单管理")
@RestController
@RequestMapping("/api/dispose")
public class ProDisposeApi {
    @Autowired
    private ProDisposeService proDisposeService;

    @Operation(description = "添加处置申请单")
    @PutMapping
    private ResponseModel insert(@RequestBody ProDispose proDispose, @Parameter(hidden = true) HttpSession httpSession) {
        ResponseModel result = new ResponseModel(200);
        if (StringUtils.isBlank(proDispose.getTitle())) {
            result.setCode(401);
            result.setMsg("标题不能为空");
        } else if (StringUtils.isBlank(proDispose.getExecStaffId()) || StringUtils.isBlank(proDispose.getExecStaffName())) {
            result.setCode(401);
            result.setMsg("执行人不能为空");
        } else {
            proDispose.setStaffId(((Staff) httpSession.getAttribute(Constant.SESSION_KEY)).getId());
            result.setMsg("添加成功");
            result.setData(proDisposeService.insert(proDispose));
        }
        return result;
    }

    @PostMapping("updateState")
    @Operation(description = "修改处置单状态")
    private ResponseModel updateState(@Parameter(name="主键", required = true) String id, @Parameter(name="更新状态", required = true) Integer state) {
        ProDispose proDispose = new ProDispose();
        proDispose.setId(id);
        proDispose.setState(state);
        return new ResponseModel(200, proDisposeService.update(proDispose), "修改成功");
    }

    @PostMapping("updateDispose")
    @Operation(description = "更新处置单信息")
    private ResponseModel update(@RequestBody ProDispose dispose) {
        return new ResponseModel(proDisposeService.update(dispose));
    }

    @Operation(description = "上传处置单附件")
    @PutMapping("uploadFiles/{disposeId}")
    private ResponseModel uoloadttach(@RequestBody Attach[] attach, @PathVariable String disposeId) {
        if (attach != null) {
            Map<String, Object> param = new HashMap();
            param.put("table", "sdpd013");
            param.put("field", "pd01301");
            param.put("field2", "pd01302");
            param.put("moduleId", disposeId);
            //添加处理
            for (Attach file : attach) {
                param.put("id", file.getId());
            }
        }
        return new ResponseModel("ok");
    }

    @Operation(description = "获取处置单集合")
    @GetMapping
    private ResponseModel getProDispose(@Parameter(name = "请求参数，参见RequestPageModel模型") RequestPageModel requestPageModel) {
        return new ResponseModel(proDisposeService.queryAllByParam(requestPageModel));
    }
}
