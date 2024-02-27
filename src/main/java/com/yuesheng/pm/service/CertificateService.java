package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.entity.Certificate;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * @author gui_lin
 * @Description 证书管理
 * 2021/12/27
 */
public interface CertificateService {
    /**
     * 获取全部证书
     * @param str 检索字符串
     * @return:
     */
    List<Certificate> selectAllCertificate(String str);

    /**
     * 上传证书
     * @param certificate 证书信息
     * @return
     */
    int insertCertificate(Certificate certificate);

    /**
     * 删除证书
     * @param id 证书id
     * @return
     */
    int deleteCertificate(Long id);

    /**
     * 更新证书
     * @param certificate 更新信息
     * @return
     */
    int updateCertificate(Certificate certificate);

    /**
     * 查询已授权证书
     * @return
     */
    List<Certificate> selectCertificateOfAuthorization(String str, String id);

    /**
     * 是否绑定
     * @param staffId 员工id
     * @param cerId 证书id
     * @return
     */
    Boolean isBind(String staffId, Long cerId);

    /**
     * 添加授权人员
     * @param staff 员工
     * @param cerId 证书id
     * @return
     */
    Boolean insert(Staff staff, Long cerId);

    /**
     * 撤回员工被授权的证书
     * @param staffId 员工id
     * @param cerId 证书id
     */
    void deletePerson(String staffId, Long cerId);

    /**
     * 查询以被授权此证书的员工
     * @param cerId 证书id
     * @return
     */
    List<Staff> selectStaffByCertificateId(Long cerId);

    /**
     * 根据分类目录查询证书
     *
     * @param catalogue 分类目录信息
     * @return
     */
    List<Certificate> selectCerByCat(Catalogue catalogue);

    /**
     * 检查证书过期提醒
     */
    void checkPassNotify();
}
