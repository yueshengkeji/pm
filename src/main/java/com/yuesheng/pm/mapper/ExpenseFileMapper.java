package com.yuesheng.pm.mapper;


import com.yuesheng.pm.entity.ExpenseFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ExpenseFileMapper
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:10
 */
@Mapper
public interface ExpenseFileMapper {
    int insert(ExpenseFile expenseFile);

    int delete(String id);

    List<ExpenseFile> selectByMark(String mark);

    /**
     * 通过url查询文件
     * @param fileUrl
     * @return
     */
    ExpenseFile selectByUrl(String fileUrl);

    /**
     * 删除附件
     * @param fileUrl 附件url
     * @return
     */
    int deleteByUrl(String fileUrl);
}
