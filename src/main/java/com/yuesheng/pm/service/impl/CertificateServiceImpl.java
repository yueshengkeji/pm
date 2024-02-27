package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.entity.Certificate;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.CertificateMapper;
import com.yuesheng.pm.service.CertificateService;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author gui_lin
 * @Description 证书管理实现
 * 2021/12/27
 */
@Service("CertificateService")
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateMapper certificateMapper;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private RedisService redisService;

    @Override
    public List<Certificate> selectAllCertificate(String str) {
        return certificateMapper.selectAllCertificate(str);
    }

    @Override
    public int insertCertificate(Certificate certificate) {
        try {
            certificate.setPushMsg(0);
            certificateMapper.insertCertificate(certificate);
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int deleteCertificate(Long id) {
        try {
            certificateMapper.deleteCertificate(id);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updateCertificate(Certificate certificate) {
        try {
            Certificate select = selectByCer(certificate);
            if (!select.getExpirationTime().equals(certificate.getExpirationTime())){
                certificate.setPushMsg(0);
            }
            certificateMapper.updateCertificate(certificate);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<Certificate> selectCertificateOfAuthorization(String str, String id) {
        return certificateMapper.selectCertificateOfAuthorization(str,id);
    }

    @Override
    public Boolean isBind(String staffId, Long cerId) {
        return !Objects.isNull(certificateMapper.isBind(staffId,cerId));
    }

    @Override
    public Boolean insert(Staff staff, Long cerId) {
        try {
            certificateMapper.insert(staff,cerId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void deletePerson(String staffId, Long cerId) {
        certificateMapper.deletePerson(staffId,cerId);
    }

    @Override
    public List<Staff> selectStaffByCertificateId(Long cerId) {
        return certificateMapper.selectStaffByCertificateId(cerId);
    }

    @Override
    public List<Certificate> selectCerByCat(Catalogue catalogue) {
        return certificateMapper.selectCerByCat(catalogue);
    }

    @Override
    public void checkPassNotify() {
        List<String> name = new ArrayList<>();
        List<Certificate> certificateList = selectAllCertificate(null);
        Staff staff = (Staff) redisService.getValue("sinkMsgStaff");
        String title = "更新证书消息提醒";
        String content = "";
        String status = "";
        String linkedUrl = "";
        certificateList.forEach(item -> {
            if (item.getExpirationTime() != null && item.getPushMsg() != null && item.getPushMsg() != 1) {
                int time = (int) ((item.getExpirationTime().getTime() - DateFormat.parseData(DateFormat.getDate()).getTime()) / (1000 * 3600 * 24));
                if (time <= 30 && 0 < time) {
                    name.add(item.getName());
                    item.setPushMsg(1);
                    updateCertificate(item);
                }
                if (time < 0) {
                    item.setState(0);
                    updateCertificate(item);
                }
            }
        });
        if (name.size() != 0) {
            for (int i = 0; i < 2 && i < name.size(); i++) {
                content = content.concat(name.get(i) + "、");
            }
            content = (String) content.subSequence(0, content.length() - 1);
            content = content.concat("等,共" + name.size() + "个证书即将到期，请登录系统更新证书");
            CompanyWxUtil.sendMsg(flowNotifyService.getWxUserIdOrWxOpenId(staff), title, content, status, linkedUrl);
        }
    }

    /**
     * 根据证书信息查询证书
     *
     * @param certificate
     * @return
     */
    private Certificate selectByCer(Certificate certificate) {
        return certificateMapper.selectByCer(certificate);
    }
}
