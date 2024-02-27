package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Staff;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/11/17 系统通用模块 上传/下载附件服务.
 */
public interface AttachService {

    /**
     * 通过附件id获取附件对象
     *
     * @param id 附件id
     * @return
     */
    Attach getById(String id);

    /**
     * 删除附件
     *
     * @param id 附件id
     * @return 影响的行数
     */
    int deleteAttach(String id);

    /**
     * 通过窗口主键id删除附件集合(采购合同专用)
     *
     * @param moduleId 窗口主键id
     * @return 删除的行数
     */
    int deleteByContractId(String moduleId);

    /**
     * 添加有附件表的附件信息
     *
     * @param attach
     */
    void addAttachForFJ(Attach attach);

    /**
     * 添加附件到关系表
     *
     * @param params {table：表名称，field:字段1（对应模块主键id，一般是01），field2:字段2（sdpk008附件表主键位置，一般是02），moduleId:模块主键id，id：附件主表id}
     */
    void addAttachRelation(Map<String, String> params);

    /**
     * 删除附件关系行
     *
     * @param params DELETE  FROM ${table} WHERE ${field}=#{moduleId};
     */
    void deleteAttachRelation(Map<String, String> params);

    /**
     * 添加附件到数据库
     *
     * @param files  文件数组
     * @param params 要添加到附件表的关系表配置，参见addAttachRelation方法
     * @param staff  附件上传人员
     * @param mainId 主单据id
     */
    void insertAttch(MultipartFile[] files, Map<String, String> params, Staff staff, String mainId);

    /**
     * 获取附件对象集合
     *
     * @param result table = 表名，field=字段名，field2=left连接列名 ,moduleId=判断的值（窗口主键id）
     * @return
     */
    List<Attach> getAttachByRelation(Map<String, String> result);

    /**
     * 删除附件，主单据 mainId
     *
     * @param id        主单据id
     * @param tableName 主单据表名称
     */
    int deleteAttachByModuleId(String id, String tableName);

    /**
     * 获取附件集合
     *
     * @param mainId    主单据id
     * @param tableName 当前主单据表名称
     * @return
     */
    List<Attach> getAttachByModuleId(String mainId, String tableName);

    /**
     * 删除附件
     *
     * @param attachId 附件id
     * @param mainId   主单据id
     * @param tn       主单据主表名称
     */
    void deleteAttachRelation(String attachId, String mainId, String tn);

    /**
     * 删除指定日期前的图片缓存
     *
     * @param days
     */
    void deleteCatchByDaysAgo(Integer days) throws IOException;

    /**
     * 清除系统临时文件
     *
     * @param minutes 文件创建时间距离当前多少分钟，超过该分钟数的文件被清除
     */
    void clearTempAssets(Integer minutes) throws IOException;

    /**
     * tongguo 通过文件名称和url查找id
     *
     * @param name
     * @param url
     * @return
     */
    String findId(String name, String url);

    /**
     * 删除附件
     * @param attachId 附件id
     * @param mainId 表单id
     * @param tn 主标名称
     * @return
     */
    int deleteAttach(String attachId, String mainId, String tn);

    /**
     * 更新pdf转换的image图片路径
     * @param attach
     * @return
     */
    int updatePdfImgs(Attach attach);
}
