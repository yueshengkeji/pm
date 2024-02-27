package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Info;
import com.yuesheng.pm.entity.InfoState;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.InfoService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/systemInfo")
public class InfoApi extends BaseApi {
    @Autowired
    private InfoService infoService;

    @GetMapping("list")
    public ResponseModel list(String searchText,
                              Integer page,
                              Integer itemsPerPage) {
        HashMap<String, Object> result = new HashMap<>();
        Info info = new Info();
        info.setTitle(searchText);
        startPage(page, itemsPerPage, "sendTime", "desc");
        List<Info> is = infoService.queryList(info);
        for (Info i : is) {
            i.setCount(infoService.queryCount(i.getId()));
        }
        result.clear();
        result.put("total", ((Page) is).getTotal());
        result.put("rows", is);
        return new ResponseModel(result);
    }

    @GetMapping("{id}")
    public ResponseModel detail(@PathVariable String id) {
        return new ResponseModel(infoService.queryById(id));
    }

    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        infoService.deleteById(id);
        return new ResponseModel("success");
    }

    @PostMapping("read/{id}")
    public ResponseModel read(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {

        InfoState info = new InfoState();
        info.setMainId(id);
        info.setState(1);
        info.setReadDate(DateUtil.getDatetime());
        info.setStaffId(staff.getId());
        infoService.updateStateByInfo(info);

        return ResponseModel.ok(info);
    }

    @GetMapping("listByNoRead")
    public ResponseModel listByNoRead(HttpSession httpSession) {
        Staff s = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        List<InfoState> infos = infoService.queryInfoStates(s.getId());
        List<Info> infoList = new ArrayList<>();
        Map<String, Object> msg = new HashMap<>(16);
        msg.put("type", 3);
        for (InfoState is : infos) {
            Info i = infoService.queryById(is.getMainId());
            i.setIs(is);
            infoList.add(i);
        }
        return new ResponseModel(infoList);
    }


    @PutMapping("saveDesktopError")
    public ResponseModel saveDesktopError(String errorMsg, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (StringUtils.isNotBlank(errorMsg)) {
            MDC.put("params", errorMsg);
            MDC.put("userName", staff.getName());
            MDC.put("userId", staff.getId());
            Logger.getLogger("mylog").error(errorMsg);
            MDC.clear();
        }
        return ResponseModel.ok();
    }
}
