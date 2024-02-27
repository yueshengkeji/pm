package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.entity.Certificate;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gui_lin
 * @Description 证书mapper
 * 2021/12/27
 */
@Mapper
public interface CertificateMapper {
    /**
     * 获取全部证书
     * @param: []
     * @return:
     */
    List<Certificate> selectAllCertificate(String str);

    /**
     * 上传证书
     * @param certificate 证书信息
     * @return
     */
    void insertCertificate(Certificate certificate);

    /**
     * 删除证书
     * @param id 证书id
     * @return
     */
    void deleteCertificate(Long id);

    /**
     * 更新证书
     * @param certificate 更新信息
     * @return
     */
    void updateCertificate(Certificate certificate);

    /**
     * 查询已授权证书
     * @return
     */
    List<Certificate> selectCertificateOfAuthorization(@Param("str") String str,@Param("id") String id);

    /**
     * 是否绑定
     * @param staffId 员工id
     * @param cerId 证书id
     * @return
     */
    Boolean isBind(@Param("staffId") String staffId,@Param("cerId") Long cerId);

    /**
     * 添加授权人员
     * @param staff 员工
     * @param cerId 证书id
     */
    void insert(@Param("staff")Staff staff,@Param("cerId") Long cerId);

    /**
     * 撤回员工被授权的证书
     * @param staffId 员工id
     * @param cerId 证书id
     */
    void deletePerson(@Param("staffId") String staffId,@Param("cerId") Long cerId);

    /**
     * 查询以被授权此证书的员工
     * @param cerId 证书id
     * @return
     */
    List<Staff> selectStaffByCertificateId(Long cerId);

    /**
     * 根据分类目录查询证书
     * @param catalogue 分类目录信息
     * @return
     */
    List<Certificate> selectCerByCat(Catalogue catalogue);

    Certificate selectByCer(Certificate certificate);
}
