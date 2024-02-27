package com.yuesheng.pm.restapi;

import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.model.Base64UploadModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.FtpUtil;
import com.yuesheng.pm.util.WordToHtml;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.poi.util.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Tag(name = "附件上传管理")
@RestController
@RequestMapping("/api/files")
public class AttacheApi {
    @Autowired
    private AttachController attachController;
    @Autowired
    private AttachService attachService;

    @Operation(description = "上传附件")
    @PostMapping
    public ResponseModel upload(@RequestParam("files") MultipartFile[] files, @Parameter(hidden = true) HttpSession httpSession, @Parameter(name = "单据id(可选，当第一次上传成功后，返回单据id,保存单据时需要用此id作为信息表主键，否则后期查找附件会失败)") String mainCoding) {
        return new ResponseModel(attachController.uploadFile(files, httpSession, mainCoding));
    }

    @Operation(description = "获取附件集合")
    @GetMapping
    public ResponseModel getAttache(@Parameter(name = "单据id(flowMessage.frameId)", required = true) String frameId,
                                    @Parameter(name = "窗口编号", required = false) String frameCoding,
                                    @Parameter(name = "是否移动端请求") Boolean mobile) {
        List<Attach> attachList = attachController.getAttachByModuleAnTable(frameId, frameCoding, Constant.getTableName(frameCoding), mobile);
        return new ResponseModel(attachList);
    }

    @Operation(description = "删除附件")
    @DeleteMapping
    public ResponseModel delete(@Parameter(name = "附件id") String id) {
        return new ResponseModel(attachController.deleteFile(id));
    }

    @Operation(description = "下载附件")
    @GetMapping("/downloadFile")
    @NoToken
    public ResponseEntity<byte[]> downloadFile(@Parameter(name = "文件所在ftp路径") String filePath,
                                               @Parameter(name = "下载的文件名称") String downloadFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            headers.setContentDispositionFormData("attachment", downloadFile);
//            buff用于存放循环读取的临时数据
            return new ResponseEntity(FtpUtil.downloadFile(filePath), headers, HttpStatus.CREATED);
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

    @GetMapping("/dispose/{id}")
    @ResponseBody
    public ResponseModel dispose(@PathVariable String id) {
        Attach a = attachService.getById(id);
        attachController.disposeAttach(a, false);
        return new ResponseModel(a);
    }

    @GetMapping(value = "/printPdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @NoToken
    public ResponseEntity<byte[]> printPdf(@Parameter(name = "文件所在ftp路径") String filePath) {
        try {
            return new ResponseEntity(FtpUtil.downloadFile(filePath), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(("下载失败"), HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/pdfToImages")
    @NoToken
    public ResponseModel pdfToImages(@Parameter(name = "文件所在ftp路径") String filePath) {
        ByteArrayInputStream bais = null;
        try {
            byte[] bytes = FtpUtil.downloadFile(filePath);
            bais = new ByteArrayInputStream(bytes);
            ArrayList<String> result = WordToHtml.pdfToImage(bais);
            return ResponseModel.ok(result);
        } catch (Exception e) {
            return ResponseModel.error("文件预览失败:" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(bais);
        }
    }

    @Operation(description = "通过base64上传")
    @PutMapping("uploadByBase64")
    public ResponseModel uploadByBase64(@RequestBody Base64UploadModel base64) {
        String url = DateUtil.getDate().substring(0, 7) + File.separator + base64.getFileName();
        StringBuffer sb = new StringBuffer(base64.getData());
        String temp = sb.substring(0, 50);
        int idx = temp.indexOf("base64");
        if (idx != -1) {
            sb.delete(0, idx + 7);
        }
        try {
            FtpUtil.uploadFile(Base64.getMimeDecoder().decode(sb.toString().getBytes()), url);
        } catch (Exception e) {
            try {
                FtpUtil.uploadFile(Base64.getMimeDecoder().decode(StringUtils.replaceChars(sb.toString(), "%2F", "/").replaceAll("=", "").getBytes()), url);
            } catch (IOException ex) {
                LogManager.getLogger("mylog").error(e.getMessage() + ";" + ex.getMessage() + ",uploadByBase64 error:" + base64.getData().substring(0, 20));
            }
        }
        return ResponseModel.ok(url);
    }

    @Operation(description = "pdf转图片")
    @PostMapping("uploadPdf")
    public ResponseModel uploadPdf(@RequestParam("file") MultipartFile[] file) {
        try {
            InputStream is = file[0].getInputStream();
            ArrayList<String> result = WordToHtml.pdfToImage(is);
            return ResponseModel.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseModel.ok();
    }
}
