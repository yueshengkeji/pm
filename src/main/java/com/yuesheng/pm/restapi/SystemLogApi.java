package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.SystemLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统日志")
@RestController
@RequestMapping("api/systemLog")
public class SystemLogApi extends BaseApi {

    @Autowired
    private SystemLogService logService;

    @Operation(description = "查询系统日志列表")
    @GetMapping("list")
    public ResponsePage list(@Parameter(name="检索字符串") String searchText,
                             @Parameter(name="请求方法过滤") String method,
                             @Parameter(name="日志类型过滤") String type,
                             @Parameter(name="日志时间") String datetime,
                             @Parameter(name="条目数") Integer page,
                             @Parameter(name="页码") Integer itemsPerPage,
                             @Parameter(name="排序列") String[] sortBy,
                             @Parameter(name="过滤用户名") String userName,
                             @Parameter(name="排序方式") Boolean[] sortDesc,
                             @Parameter(name="api标题") String title,
                             @Parameter(name="请求url") String url) {
        SystemLog systemLog = new SystemLog();
        systemLog.setParams(searchText);
        systemLog.setDatetime(datetime);
        systemLog.setType(type);
        systemLog.setMethod(method);
        systemLog.setUserName(userName);
        systemLog.setUrl(url);
        systemLog.setTitle(title);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<SystemLog> page1 = (Page<SystemLog>) logService.queryByParam(systemLog);
        return ResponsePage.ok(page1);
    }

    @Operation(description = "根据日期分组查询日志总数")
    @GetMapping("byDateGroup")
    public ResponseModel byDateGroup(){
        return ResponseModel.ok(logService.queryByDateGroup());
    }
}
