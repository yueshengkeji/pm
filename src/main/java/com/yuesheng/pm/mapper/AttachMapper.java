package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Attach;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/11/16 附件mapper.
 *
 * @author XiaoSOng
 * @date 2016/11/16
 */
@Mapper
public interface AttachMapper {

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
     * 通过窗口主键id删除附件集合（采购合同专用）
     *
     * @param moduleId 窗口主键id
     * @return 删除的行数
     */
    int deleteByModoleId(String moduleId);


    /**
     * 添加附件到主表
     *
     * @param attach
     */
    void addAttachForFj(Attach attach);

    /**
     * 添加附件到关系表
     *
     * @param params {table：表名称，field:字段1，field:字段2，moduleId:模块主键id，id：附件主表id}
     */
    void addAttachRelation(Map<String, String> params);

    /**
     * 删除附件关系行
     *
     * @param params
     */
    void deleteAttchRelation(Map<String, String> params);

    /**
     * 获取附件集合
     *
     * @param result table = 表名，field=字段名，field2=left连接列名 ,moduleId=判断的值（窗口主键id）
     * @return
     */
    List<Attach> getAttachByRelation(Map<String, String> result);

    /**
     * 删除附件关系表
     *
     * @param tableName 表名称
     * @param field     字段名称
     * @param moduleId  主键
     */
    void deleteAttachRelation(@Param("table") String tableName,
                              @Param("field") String field,
                              @Param("moduleId") String moduleId);

    /**
     * 获取附件列表
     *
     * @param sd 开始时间
     * @param ed 截止时间
     * @return
     */
    List<Attach> getAttachByDate(@Param("startDate") String sd, @Param("endDate") String ed);

    /**
     * 查询附件表是否存在
     *
     * @param tableName 表名称
     * @return
     */
    String getTableInfo(@Param("tableName") String tableName);

    /**
     * 删除附件
     *
     * @param param
     * @return
     */
    int delete(HashMap<String, String> param);

    int deleteAttachRelationById(@Param("table") String fileTableName,
                                 @Param("field1") String field1,
                                 @Param("mainId") String mainId,
                                 @Param("field2") String field2,
                                 @Param("attachId") String attachId);

    /**
     * 更新附件pdf图片路径
     * @param attach
     * @return
     */
    int updatePdfImgs(Attach attach);
}
