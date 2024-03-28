package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.service.FileService;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.util.*;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.util.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by XiaoSong on 2016/11/17  附件控制器.
 *
 * @author XiaoSong
 * @date 2016/11/17
 */
@Controller
@RequestMapping("/managent")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 附件处理（此方法待优化）
     *
     * @param attaches
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    private ArrayList disposeAttach(List<Attach> attaches, Boolean mobile) {
        ArrayList<Attach> result = new ArrayList<>();
        for (int i = 0; i < attaches.size(); i++) {
            Attach a = attaches.get(i);
            if (disposeAttach(a, mobile)) {
                result.add(a);
            }
        }
        return result;
    }

    public boolean disposeAttach(Attach attach, Boolean mobile) {
        if (Objects.isNull(attach)) {
            return false;
        }
        BufferedInputStream bis = null;
        int index;
        String hz;
        String fileName;
        index = attach.getFileName().lastIndexOf(".");
        index = index != -1 ? index : attach.getFileName().length();
        hz = attach.getFileName().substring(index);
//        if (StringUtils.isBlank(attach.getPk00811())) {
//
//        } else {
//            hz = attach.getPk00811();
//        }
        try {
            fileName = attach.getName() + attach.getId() + hz;
            attach.setShowPath(URLEncoder.encode(fileName, "UTF-8").replace("+", "%20").replace("%2F", "/"));

            /*if (".png".equalsIgnoreCase(hz) || ".jpg".equalsIgnoreCase(hz) || ".jpeg".equalsIgnoreCase(hz) ||
                    ".gif".equalsIgnoreCase(hz) || ".bmp".equalsIgnoreCase(hz)) {
                //忽略图片处理
            } else *//*if (".pdf".equalsIgnoreCase(hz)) {
//                    pdf处理，下载到磁盘缓存
                if (fileIsExists(attach)) {
                    return true;
                }
                bis = getBufferedInputStream(fileName, attach);
                byte[] bytes = WordToHtml.writeFile(bis, resourcePath, attach.getShowPath());
                attach.setSize(bytes.length);
            } else */
            if (BooleanUtils.isTrue(mobile) && StringUtils.equalsIgnoreCase(hz, ".pdf")) {
                //移动端请求，并且是pdf图片，查询pdf是否有转过
                if (StringUtils.isBlank(attach.getPdfImgPathStr())) {
                    //未处理过pdf转图片，开始转换
                    bis = getBufferedInputStream(fileName, attach);
                    ArrayList<String> result = WordToHtml.pdfToImage(bis);
                    attach.setPdfImgPath(result);

                    attach.setPdfImgPathStr(result.stream().collect(Collectors.joining("?")));
                    attachService.updatePdfImgs(attach);
                } else {
                    String[] imgPaths = StringUtils.split(attach.getPdfImgPathStr(), "?");
                    attach.setPdfImgPath(Arrays.asList(imgPaths));
                }
            } else if (".doc".equalsIgnoreCase(hz) || ".docx".equalsIgnoreCase(hz)) {
//                    word处理
                if (htmlFileIsExists(attach)) {
                    return true;
                }
                bis = getBufferedInputStream(fileName, attach);
                byte[] bytes = disposeWord(fileName, attach, bis);
                attach.setSize(bytes.length);
            } else if (".xls".equalsIgnoreCase(hz) || ".xlsx".equalsIgnoreCase(hz)) {
//                    excel处理
                if (htmlFileIsExists(attach)) {
                    return true;
                }

                bis = getBufferedInputStream(fileName, attach);
                disposeExcel(fileName, attach, bis);
            } else if (StringUtils.equalsIgnoreCase(hz, ".txt")) {
                //文本处理
                InputStream is = getBufferedInputStream(fileName, attach);
                String content = org.apache.commons.io.IOUtils.toString(is, StandardCharsets.UTF_8);
                org.apache.commons.io.IOUtils.closeQuietly(is);
                attach.setContent(content);
            }
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.getLogger(AttachController.class).error(e.getLocalizedMessage());
        } catch (NullPointerException e) {
            //文件下载失败|不存在
            return false;
        } catch (Exception e) {
            //文件不存在
            e.printStackTrace();
            return false;
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(bis);
        }
        return true;
    }

    private BufferedInputStream getBufferedInputStream(String fileName, Attach attach) throws Exception {
        return new BufferedInputStream(new ByteArrayInputStream(FtpUtil.downloadFile(fileName)));
    }

    private boolean htmlFileIsExists(final Attach attach) throws IOException {
        File f = new File(WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator, attach.getId() + ".html");
        if (f.exists()) {
            //预览文档文件存在，使用系统缓存
            FileInputStream fis = new FileInputStream(f);
            String temp = new String(IOUtils.toByteArray(fis), "UTF-8");
            attach.setContent(temp);
            attach.setShowPath(attach.getId() + ".html");
            fis.close();
            return true;
        } else {
            return false;
        }
    }

    private boolean fileIsExists(final Attach attach) throws IOException {
        File f = new File(WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator, attach.getShowPath());
        FileInputStream fis;
        if (f.exists()) {
            fis = new FileInputStream(f);
            // attach.setFileBytes(IOUtils.toByteArray(fis));
            attach.setSize(fis.available());
            IOUtils.closeQuietly(fis);
            return true;
        } else {
            return false;
        }
    }

    private byte[] disposeExcel(String fileName, Attach attach, InputStream is) throws UnsupportedEncodingException {
        String resourcePath = WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator;
        attach.setShowPath(attach.getId() + ".html");
        String temp;
        FileInputStream fis = null;
        byte[] bytes = new byte[0];
        try {
            File f = new File(resourcePath + attach.getShowPath());
            if (f.exists() && !f.isDirectory()) {
                fis = new FileInputStream(f);
                temp = new String(IOUtils.toByteArray(fis));
            } else {
                bytes = IOUtils.toByteArray(is);
                temp = ExcelToHtml.xlsToHtml(new HSSFWorkbook(new ByteArrayInputStream(bytes)));
                WordToHtml.writeFile(temp, resourcePath + attach.getShowPath(), "UTF-8");
            }
        } catch (OfficeXmlFileException oe) {
            try {
                temp = ExcelToHtml.readExcelToHtml(new ByteArrayInputStream(bytes), false, resourcePath + attach.getShowPath());
                WordToHtml.writeFile(temp, resourcePath + attach.getShowPath(), "UTF-8");
            } catch (Exception e) {
                downOther(fileName, attach);
                return new byte[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
            downOther(fileName, attach);
            return new byte[0];
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {

                }
            }
        }
        attach.setContent(temp);
        attach.setSize(bytes.length);
        return bytes;
    }

    private byte[] disposeWord(String fileName, Attach attach, InputStream is) throws UnsupportedEncodingException {
        String resourcePath = WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator;
        FileInputStream fis = null;
        byte[] tempBytes = new byte[0];
        try {
            tempBytes = IOUtils.toByteArray(is);
            attach.setShowPath(attach.getId() + ".html");
            File f = new File(resourcePath + attach.getShowPath());
            String temp;
            if (f.exists() && !f.isDirectory()) {
                fis = new FileInputStream(f);
                temp = new String(IOUtils.toByteArray(fis));
            } else {
                temp = WordToHtml.convert2Html(new ByteArrayInputStream(tempBytes), resourcePath + attach.getShowPath());
            }
            attach.setContent(temp);
        } catch (Exception e) {
            if (e instanceof OfficeXmlFileException) {
                try {
                    String temp = WordToHtml.getDocumentByDocx2(new ByteArrayInputStream(IOUtils.toByteArray(new ByteArrayInputStream(tempBytes))));
                    WordToHtml.writeFile(temp, resourcePath + attach.getShowPath(), "UTF-8");
                    attach.setContent(temp);
                } catch (Exception e1) {
                    downOther(fileName, attach);
                }
            } else {
                downOther(fileName, attach);
            }
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    is.close();
                } catch (IOException e) {

                }
            }
        }
        return tempBytes;
    }

    /**
     * 处理其他文件
     *
     * @param fileName
     * @param attach
     * @throws UnsupportedEncodingException
     */
    private void downOther(String fileName, Attach attach) throws UnsupportedEncodingException {
        String resourcePath = WebParam.webRootPath + "assets" + File.separator + "ftptemp" + File.separator;
        attach.setShowPath(URLEncoder.encode(fileName, "UTF-8").replace("+", "%20").replace("/", "%2F"));
        FtpUtil.downOtherFile(fileName, resourcePath, attach.getShowPath());
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(resourcePath + attach.getShowPath());
            attach.setSize(fileInputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(fileInputStream);
        }

    }

    /**
     * 通过附件id获取附件对象
     *
     * @param id 附件id
     * @return
     */
    @RequestMapping("/getAttachById")
    @ResponseBody
    public Attach getAttachById(String id) {
        return attachService.getById(id);
    }

    /**
     * 删除附件
     *
     * @param id 附件id
     * @return 影响的行数
     */
    @RequestMapping("/deleteAttachById")
    @ResponseBody
    public int deleteAttach(String id) {
        return attachService.deleteAttach(id);
    }

    /**
     * 删除附件集合
     *
     * @param moduleId 窗口主键id
     * @return 影响的行数
     */
    @RequestMapping("/deleteAttachByModule")
    public int deleteAttachByModule(String moduleId) {
        return attachService.deleteByContractId(moduleId);
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @return
     */
    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName, @RequestParam("downloadName") String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            headers.setContentDispositionFormData("attachment", name);
//            buff用于存放循环读取的临时数据
            return new ResponseEntity(FtpUtil.downloadFile(fileName), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            headers.setContentDispositionFormData("attachment", "fileError.text");
            LoggerFactory.getLogger(AttachController.class).error(e.getLocalizedMessage());
            try {
                return new ResponseEntity(("文件下载失败，请将异常信息发给研发部小宋，以便解决问题，谢谢。error：" + e.getMessage()).getBytes("UTF-8"), headers, HttpStatus.CREATED);
            } catch (UnsupportedEncodingException e1) {
                return new ResponseEntity(("File download error" + e.getMessage()).getBytes(), headers, HttpStatus.CREATED);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @return
     */
    @RequestMapping("/prevFile")
    public ResponseEntity<byte[]> prevFile(@RequestParam("fileName") String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf;charset=UTF-8");
        headers.add("Accept-Ranges", "bytes");
        try {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            return new ResponseEntity(FtpUtil.downloadFile(fileName), headers, HttpStatus.OK);
        } catch (Exception e) {
            headers.add("Content-Type", "text/html;charset=UTF-8");
            headers.remove("Accept-Ranges");
            headers.add("Content-Language", "zh-CN");
            try {
                return new ResponseEntity(("文件预览失败，异常信息：" + e.getMessage()).getBytes("UTF-8"), headers, HttpStatus.OK);
            } catch (UnsupportedEncodingException e1) {
                //
                return new ResponseEntity(("file prev error,msg：" + e.getMessage()).getBytes(), headers, HttpStatus.OK);
            }
        }
    }

    /**
     * 获取附件集合，流程消息id || 主表名称任选1，根据主表名称获取更快
     * GetAttListForModule
     *
     * @param moduleId 表单id
     * @param tn       主表名称
     * @return
     */
    @RequestMapping("/getAttachByModuleAnTable")
    @ResponseBody
    public List<Attach> getAttachByModuleAnTable(String moduleId,
                                                 String frameCoding,
                                                 String tn,
                                                 Boolean mobile) {
        if (StringUtils.isBlank(tn) || StringUtils.equals(tn, "undefined")) {
//            attaches = attachService.getAttachByModuleId(moduleId, tn);
            tn = Constant.getTableName(frameCoding);
        }
        List<Attach> attaches = attachService.getAttachByModuleId(moduleId, tn);
        if (attaches.isEmpty()) {
            //查询第三方实现接口
            Menu menu = menuService.getMenuByCoding(frameCoding);
            if (!Objects.isNull(menu) && StringUtils.isNotBlank(menu.getBeanName())) {
                FileService o = WebParam.webApplicationContext.getBean(menu.getBeanName() + "Service", FileService.class);
                attaches = o.getByService(moduleId);
            }
        }
        attaches = disposeAttach(attaches, mobile);
        return attaches;
    }

    /**
     * 上传文件
     *
     * @param files
     * @param session
     * @return
     */
    @RequestMapping("/uploadFiles")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("files") MultipartFile[] files, HttpSession session, String mainCoding) {
        Map<String, Object> params = new HashMap<>(16);
        List<Attach> pathArr = new ArrayList<>();
        Attach attach;
        String mainId;
        if (mainCoding != null && !"".equals(mainCoding)) {
            mainId = mainCoding;
        } else {
            mainId = UUID.randomUUID().toString();
        }
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }
//            上传到ftp服务器
            attach = FtpUtil.multiParse(file, staff, mainId);
            if (attach != null) {
                int index = attach.getFileName().lastIndexOf(".");
                index = index != -1 ? index : attach.getFileName().length();
                if (attach.getFileName().length() >= 100) {
                    attach.setFileName(attach.getFileName().substring(0, 80) + attach.getFileName().substring(index));
                }
                //上传到本地
//                    FtpUtil.uploadFileToLocal(file, URLEncoder.encode(attach.getName() + attach.getId() + (attach.getFileName().substring(index)), "UTF-8"));
                if (StringUtils.containsIgnoreCase(file.getContentType(), "image")) {
                    FileCatchRunnable fcr = new FileCatchRunnable(attach);
                    taskExecutor.execute(fcr);
                }
                pathArr.add(attach);
                attachService.addAttachForFJ(attach);
            } else {
                continue;
            }
        }
        params.put("mainId", mainId);
        params.put("files", pathArr);
        return params;
    }

    /**
     * 删除邮件审批临时附件
     *
     * @param approveId 审批表id
     * @return
     */
    @RequestMapping("deleteApproveHtml")
    @ResponseBody
    public int deleteApproveHtml(String approveId, HttpSession httpSession) {
        FtpUtil.deleteLocalFile(WebParam.EMAIL_FOLDER + "\\" + approveId + ".html");
//        删除后该session过期
        httpSession.invalidate();
        return 1;
    }

    /**
     * 删除文件
     *
     * @param attachId 文件路径
     */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public String deleteFile(String attachId) {
        Attach attach = attachService.getById(attachId);
        if (attach == null) {
            return "-1";
        }
        int index = attach.getFileName().lastIndexOf(".");
        index = index != -1 ? index : attach.getFileName().length();
        String path = attach.getName() + attach.getId() +
                (attach.getFileName().substring(index));
        try {
            FtpUtil.deleteFile(path);
        } catch (IOException e) {

        }
        try {
            FtpUtil.deleteLocalFile(WebParam.assetsPath + "ftptemp" + File.separator +
                    URLEncoder.encode(path, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.getLogger(AttachController.class).error(e.getLocalizedMessage());
        }
        attachService.deleteAttach(attachId);
        return "1";
    }

    /**
     * 删除附件关系表
     *
     * @param attachId 附件主键id
     * @param mainId   窗口主键id
     * @param tn       主表名称
     * @return
     */
    @RequestMapping("/deleteARelation")
    @ResponseBody
    public String deleteRelation(@RequestParam String attachId, @RequestParam String mainId, @RequestParam String tn) {
        attachService.deleteAttach(attachId, mainId, tn);
        return "1";
    }
}