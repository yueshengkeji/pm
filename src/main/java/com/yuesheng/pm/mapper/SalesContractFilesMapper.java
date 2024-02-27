package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SalesFiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 销售合同附件
 * @author ssk
 * @since 2022-1-7
 */
@Mapper
public interface SalesContractFilesMapper {

    /**
     * 插入附件信息
     * @param salesFiles
     * @return
     */
    int insert(SalesFiles salesFiles);

    /**
     * 查询
     * @param mark
     * @return
     */
    List<SalesFiles> select(String mark);

    /**
     * 删除附件
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 删除已删除合同下附件
     * @param mark
     * @return
     */
    int deleteByMark(String mark);
}
