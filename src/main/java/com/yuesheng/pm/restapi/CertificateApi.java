package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.entity.Certificate;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CertificateService;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gui_lin
 * @Description 描述
 * 2021/12/28
 */
@Tag(name = "证书管理")
@RestController
@RequestMapping("/api/certificate")
public class CertificateApi {
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private RedisService redisService;

    @Operation(description = "查询所有证书")
    @GetMapping
    public ResponseModel selectAllCertificate(@Parameter(name="搜索字符串") String str){
        List<Certificate> certificateList = certificateService.selectAllCertificate(StringUtils.isBlank(str) ? null : str);
        return new ResponseModel(certificateList);
    }

    @Operation(description = "添加证书")
    @PutMapping
    public ResponseModel insertCertificate(@RequestBody Certificate certificate, HttpSession httpSession){
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        certificate.setUploadUserId(Long.parseLong(staff.getCoding()));
        certificate.setUploadUserName(staff.getName());
        certificate.setUploadTime(DateFormat.parseData(DateFormat.getDate()));
        certificate.setState(0);
        certificateService.insertCertificate(certificate);
        return new ResponseModel(certificate);
    }

    @Operation(description = "删除证书")
    @DeleteMapping
    public ResponseModel deleteCertificate(Long id){
        certificateService.deleteCertificate(id);
        return new ResponseModel("删除成功");
    }

    @Operation(description = "更新证书")
    @PostMapping("/update")
    public ResponseModel updateCertificate(@RequestBody Certificate certificate, HttpSession httpSession){
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        certificate.setUpdateUserId(Long.parseLong(staff.getCoding()));
        certificate.setUpdateUserName(staff.getName());
        certificate.setUpdateTime(DateFormat.parseData(DateFormat.getDate()));
        if (certificate.getStaffList() != null && certificate.getStaffList().size() != 0){
            certificate.setState(1);
        }
        certificateService.updateCertificate(certificate);
        return new ResponseModel(certificate);
    }

    @Operation(description = "查询授权证书")
    @GetMapping("/selectCof")
    public ResponseModel selectCertificateOfAuthorization(@Parameter(name="搜索字符串") String str, HttpSession httpSession){
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        List<Certificate> certificateList = certificateService.selectCertificateOfAuthorization(StringUtils.isBlank(str) ? null : str,staff.getId());
        return new ResponseModel(certificateList);
    }

    @Operation(description = "将证书授权给指定员工")
    @PostMapping("/insertStaff")
    public ResponseModel insertStaff(@RequestBody Certificate certificate){
        if (StringUtils.isBlank(String.valueOf(certificate.getId())) || certificate.getStaffList() == null){
            return new ResponseModel(401,"请指定证书id或职员集合");
        }else {
            for(Staff staff : certificate.getStaffList()){
                Boolean bind = certificateService.isBind(staff.getId(), certificate.getId());
                if (!bind){
                    certificateService.insert(staff, certificate.getId());
                }
            }
        }
        return new ResponseModel(200,"授权成功");
    }

    @Operation(description = "撤回员工被授权的证书")
    @DeleteMapping("/deleteStaff")
    public ResponseModel deleteStaff(String staffId, Long cerId){
        certificateService.deletePerson(staffId,cerId);
        return new ResponseModel(200,"删除成功");
    }

    @Operation(description = "查询以被授权此证书的员工")
    @GetMapping("/selectStaffByCer")
    public ResponseModel selectStaffByCer(Long cerId){
        return new ResponseModel(certificateService.selectStaffByCertificateId(cerId));
    }

    @Operation(description = "通过分类查询证书")
    @PostMapping("/selectCerByCat")
    public ResponseModel selectCerByCat(@RequestBody Catalogue catalogue){
        List<Certificate> list = certificateService.selectCerByCat(catalogue);
        return new ResponseModel(list);
    }

    @Operation(description = "查询已经过期的证书")
    @GetMapping("/overdue")
    public ResponseModel overdue(String str){
        List<Certificate> list = certificateService.selectAllCertificate(StringUtils.isBlank(str) ? null : str);
        List<Certificate> certificateList = new ArrayList<>();
        for (Certificate item : list){
            if (item.getExpirationTime() != null && item.getExpirationTime().getTime() <= DateFormat.parseData(DateFormat.getDate()).getTime()){
                certificateList.add(item);
            }
        }
        return new ResponseModel(certificateList);
    }

    @Operation(description = "查询临近过期证书")
    @GetMapping("/nearOverdue")
    public ResponseModel nearOverdue(String str){
        List<Certificate> list = certificateService.selectAllCertificate(StringUtils.isBlank(str) ? null : str);
        List<Certificate> certificateList = new ArrayList<>();
        for (Certificate item : list){
            if (item.getExpirationTime() != null){
                int time =(int) ((item.getExpirationTime().getTime() - DateFormat.parseData(DateFormat.getDate()).getTime()) / (1000*3600*24));
                if (time <= 30 && 0 < time) {
                    certificateList.add(item);
                }
            }
        }
        return new ResponseModel(certificateList);
    }

    @Operation(description = "设置接收临近过期证书人员")
    @PostMapping("/sinkMsg")
    public ResponseModel sinkMsg(@RequestBody Staff staff){
        if (redisService.existsKey("sinkMsgStaff")){
            redisService.deleteKey("sinkMsgStaff");
        }
        redisService.setKey("sinkMsgStaff", staff);
        return new ResponseModel("设置成功");
    }

    @Operation(description = "查询接收临近过期证书人员")
    @GetMapping("/querySinkMsg")
    public ResponseModel querySinkMsg(){
        Staff staff =(Staff) redisService.getValue("sinkMsgStaff");
        return new ResponseModel(staff);
    }
}
