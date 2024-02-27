package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.OvertimeService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.FtpUtil;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.yuesheng.pm.util.WordToHtml.replaceAndGenerateWord;

@Tag(name = "加班单")
@RestController
@RequestMapping("api/overtimeForm")
public class OvertimeApi extends BaseApi {
    @Autowired
    private OvertimeService overtimeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private FlowMessageService flowMessageService;

    private static final Logger log = LoggerFactory.getLogger(OvertimeController.class);
    @Operation(description = "获取加班单")
    @GetMapping("getOvertimes")
    public ResponseModel getOvertimes(Integer pageSize,
                                      Integer pageNumber,
                                      String searchText,
                                      String sortName,
                                      String sortOrder,
                                      String staffId,
                                      String begin,
                                      String end,
                                      String approve){
        Map<String, Object> params = new HashMap<>(16);
        if (begin == null) {
            begin = "";
            end = "";
        }
        params.put("staffId", staffId);
        params.put("serachText", "".equals(searchText) ? null : searchText);
        params.put("begin", begin);
        params.put("end", end);
        params.put("approve", "".equals(approve) ? null : approve);
        startPage(pageNumber, pageSize, sortName, sortOrder);
        Page<Overtime> overtimes = (Page<Overtime>) overtimeService.getByParam(params);
        params.put("rows", overtimes);
        params.put("total", overtimes.getTotal());

        return new ResponseModel(params);
    }

    @Operation(description = "添加加班申请单")
    @PutMapping("insertOvertime")
    public ResponseModel insertOvertime(@RequestBody Overtime overtime, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        overtime.setId(UUID.randomUUID().toString());
        overtime.setStaff(staff);
        overtime.setDate(DateFormat.getDate());
        overtime.setEnd(overtime.getOvertimeEnd() + " " + overtime.getEnd());
        overtimeService.insert(overtime);
        /*
         * 兼容pm2，添加到sdpo009中
         */
        Article article = new Article();
        article.setId(overtime.getId());
        article.setCode(DateFormat.getDateForNumber());
        article.setName(overtime.getName());
        article.setTitle(overtime.getName());
        article.setData(new byte[0]);
        article.setStaff(overtime.getStaff());
        article.setDate(overtime.getDate());
        Project p = new Project();
        p.setId("");
        article.setProject(p);
        ArticleFolder folder = new ArticleFolder();
        folder.setId(Constant.OVERTIMR_FOLDER);
        article.setFolder(folder);
        articleService.insert(article);
        try {
            byte[] bytes = getDocByOvertime(overtime);
            FtpUtil.uploadFile(bytes, overtime.getId() + ".doc");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseModel(overtime);
    }

    /**
     * overtime转成doc文档
     *
     * @param overtime 加班单对象
     * @return
     */
    public static byte[] getDocByOvertime(Overtime overtime) throws IOException {
        String filepathString = WebParam.webRootPath + "assets/formviews/doctemp/overtime.doc";
        Map<String, String> map = new HashMap(16);
        map.put("${staff}", overtime.getStaff().getName());
        map.put("${section}", overtime.getStaff().getSection().getName());
        map.put("${overtime}", overtime.getOvertime());
        map.put("${begin_time}", overtime.getBegin() + "-" + overtime.getEnd());
        map.put("${remark}", overtime.getRemark());
        map.put("${hour}", overtime.getHour() + "");
        String temp = WebParam.webRootPath + "assets/formviews/doctemp/" + overtime.getId() + ".doc";
        if (replaceAndGenerateWord(filepathString, temp, map)) {
            FileInputStream fileInputStream = new FileInputStream(temp);
            byte[] result = org.apache.poi.util.IOUtils.toByteArray(fileInputStream);
            IOUtils.closeQuietly(fileInputStream);
            return result;
        } else {
            return null;
        }
    }

    @Operation(description = "删除加班单")
    @PutMapping("deleteOvertime")
    public ResponseModel deleteOvertime(@RequestBody Overtime overtime, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        FlowMessage fm = flowMessageService.getMessageByFrameId(overtime.getId());
//      已审核，不能删除 || 单据不属于该人员
        if (isApprove(fm, staff)) {
            return new ResponseModel("");
        }
//      删除消息
        flowMessageService.deleteMessage(overtime.getId());
//      删除办文word文档
        try {
            FtpUtil.deleteFile(overtime.getId() + ".doc");
        } catch (IOException e) {

        }
//      删除请假单对象
        overtimeService.delete(overtime.getId());
        return new ResponseModel(overtime.getId());
    }

    private boolean isApprove(FlowMessage fm, Staff staff) {
        return (fm != null && fm.getState() == 2) || (fm != null && !staff.getId().equals(fm.getStaffId()));
    }

    @Operation(description = "获取加班单对象")
    @GetMapping("getOverTime")
    public ResponseModel getOverTime(String id) {
        Overtime overtime = overtimeService.queryById(id);
        return new ResponseModel(overtime);
    }
}
