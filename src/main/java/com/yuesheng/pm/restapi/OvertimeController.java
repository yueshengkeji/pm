package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.OvertimeService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.*;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.yuesheng.pm.util.WordToHtml.replaceAndGenerateWord;

/**
 * Created by 96339 on 2017/5/10 加班申请单控制器.
 *
 * @author XiaoSong
 * @date 2017/05/10
 */
@Controller
@RequestMapping("/managent")
public class OvertimeController {
    private final String[] SPLIT_CHAR = new String[]{"-", "到", "至", "--", "~", "----", "——", "-----", "---", "----"};
    @Autowired
    private OvertimeService overtimeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private FlowMessageService flowMessageService;

    private static final Logger log = LoggerFactory.getLogger(OvertimeController.class);

    /**
     * 把原来的doc文档文件转换成overtime类型，并添加到数据库
     *
     * @return
     */
    @RequestMapping("/parseOvertime")
    @ResponseBody
    public Map<String, Object> parseOvertime(String begin, String end) {
        List<Article> articles = articleService.getArticleByFD(Constant.OVERTIMR_FOLDER, begin, end);
        Overtime overtime = null;
        for (Article article : articles) {
            if (overtimeService.queryById(article.getId()) != null) {     //已存在，无须重新转换添加
                continue;
            }
            overtime = getOvertimeByArticle(article);
            if (overtime == null) {
                continue;
            }
            overtimeService.insert(overtime);
        }
        Map<String, Object> result = new HashMap(16);
        ;
        result.put("state", "ok");
        return result;
    }

    /**
     * @param pageSize   页大小
     * @param pageNumber 当前页码
     * @param searchText 检索串
     * @param sortName   排序名称
     * @param sortOrder  排序方式
     * @param staffId    职员id(可选)
     * @param begin      开始时间
     * @param end        结束时间
     * @param approve    是否審核
     * @return
     */
    @RequestMapping("/getOvertimes")
    @ResponseBody
    public Map<String, Object> getOvertimes(Integer pageSize, Integer pageNumber, String searchText, String sortName, String sortOrder, String staffId, String begin, String end, String approve) {
        Map<String, Object> params = new HashMap<>(16);
        if (begin == null) {
            begin = "";
            end = "";
        }
        params.put("staffId", staffId);
        params.put("searText", "".equals(searchText) ? null : searchText);
        params.put("begin", begin);
        params.put("end", end);
        params.put("approve", "".equals(approve) ? null : approve);
        PageHelper.startPage(pageNumber, pageSize, sortName+" "+sortOrder);
        Page<Overtime> overtimes = (Page<Overtime>) overtimeService.getByParam(params);
        params.put("rows", overtimes);
        params.put("total", overtimes.getTotal());

        return params;
    }

    /**
     * 获取加班申请单对象
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOvertimeById")
    @ResponseBody
    public Overtime queryById(String id) {
        Overtime o = overtimeService.queryById(id);
        if (o == null) {      //兼容办文单据，转换doc为加班对象
            o = getOvertimeByArticle(articleService.getArticle(id));
            if (o == null) {
                return null;
            } else {
                overtimeService.insert(o);
            }
        }
        return o;
    }

    /**
     * 添加加班申请单
     *
     * @param overtime 加班单对象
     * @param session
     * @return
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping(value = "/addOvertime", method = RequestMethod.POST)
    @ResponseBody
    public Overtime addOvertime(Overtime overtime, HttpSession session) {
        overtime.setId(UUID.randomUUID().toString());
        overtime.setStaff((Staff) session.getAttribute(Constant.SESSION_KEY));
        overtime.setDate(DateFormat.getDate());
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
        return overtime;
    }

    /**
     * overtime对象转换成html视图
     *
     * @param overtime
     * @return
     */
    public static org.jsoup.nodes.Document getDocumentByOvertime(Overtime overtime) {
        org.jsoup.nodes.Document document = null;
        try {
            document = WordToHtml.getDocumentByHtml(new File(WebParam.webRootPath + "assets"+File.separator+"formviews"+File.separator+"doctemp"+File.separator+"overtime_to_word.html"));
            Element body = document.body();
            body.getElementById("section").text(overtime.getStaff().getSection().getName());
            body.getElementById("staff").text(overtime.getStaff().getName());
            body.getElementById("overtime").text(overtime.getOvertime());
            body.getElementById("hour").text(overtime.getHour() + "");
            body.getElementById("begin_end_time").text(overtime.getBegin() + "-" + overtime.getEnd());
            body.getElementById("remark").text(overtime.getRemark());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return document;
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

    private String[] getTime(String gn) {
        String[] temp = null;
        String chars = SPLIT_CHAR[0];
        int pc = 0;
        do {
            temp = parseTime(gn, chars);
            pc++;
            chars = SPLIT_CHAR[pc];
        } while (temp.length != 2 && pc <= 9);
        return temp;
    }

    private String[] parseTime(String gn, String chart) {
        String[] temp = null;
        temp = gn.split(chart);
        return temp;
    }

    /**
     * 加班单办文转换成实体
     *
     * @param article
     * @return
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    private Overtime getOvertimeByArticle(Article article) {
        Overtime overtime = new Overtime();
        Node node;
        Document document = null;
        try {
            try {
                document = WordToHtml.getDocument(new ByteArrayInputStream(FtpUtil.downloadFile(article.getId() + ".doc")));
            } catch (EncryptedDocumentException e) {        //转换失败
                return overtimeService.queryById(article.getId());
            }
            NodeList ns = document.getElementsByTagName("table");
            if (ns.getLength() > 1) {
                node = ns.item(0).getChildNodes().item(0);
            } else {
                //开始处理文档对象，转换成Overtime对象
                node = ns.item(0).getChildNodes().item(0);
            }
            NodeList list = node.getChildNodes();
            overtime.setStaff(article.getStaff());
            node = list.item(1);       //加班日期&加班时长
            String date = node.getChildNodes().item(1).getTextContent();
            String hour = node.getChildNodes().item(3).getTextContent().replace("。", ".");

            overtime.setDate(article.getDate().substring(0, 10));        //设置单据日期
            if (date.length() != 10) {        //日期格式不对，使用办文对象日期
                overtime.setOvertime(overtime.getDate());
            } else {
                overtime.setOvertime(date);
            }

            //加班开始-结束时间
            String[] temp = null;
            try {
                temp = getTime(list.item(2).getChildNodes().item(1).getTextContent());
            } catch (IndexOutOfBoundsException e) {
                temp = new String[]{"格式不正确", "格式不正确"};
            }
            //加班事由
            overtime.setRemark(list.item(3).getChildNodes().item(1).getTextContent());
            if (temp.length != 2) {
                overtime.setBegin("格式不正确");
                overtime.setEnd("格式不正确");
                overtime.setRemark(overtime.getRemark() + "-" + list.item(2).getChildNodes().item(1).getTextContent());
            } else {
                overtime.setBegin(temp[0].replace("(-|——)", ""));
                overtime.setEnd(temp[1].replace("(-|——)", ""));
            }
            String regEx = "[^(\\-)?\\d+(\\.\\d{1,2})?$]";
            Pattern p = Pattern.compile(regEx);
            hour = p.matcher(hour).replaceAll("").trim();
            overtime.setHour(Double.parseDouble(hour));
        } catch (NumberFormatException e) {
            overtime.setHour(0);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
        overtime.setName(article.getTitle());
        overtime.setId(article.getId());
        return overtime;
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/deleteOvertime/{id}")
    @ResponseBody
    public String deleteOvertime(@PathVariable String id, HttpSession session) {
        FlowMessage fm = flowMessageService.getMessageByFrameId(id);
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
//      已审核，不能删除 || 单据不属于该操作人员
        if (isApprove(fm, staff)) {
            return "";
        }
//      删除消息
        flowMessageService.deleteMessage(id);
//      删除办文word文档
        try {
            FtpUtil.deleteFile(id + ".doc");
        } catch (IOException e) {

        }
//      删除请假单对象
        overtimeService.delete(id);
        return id;
    }

    private boolean isApprove(FlowMessage fm, Staff staff) {
        return (fm != null && fm.getState() == 3) || (fm != null && !staff.getId().equals(fm.getStaffId()));
    }

}
