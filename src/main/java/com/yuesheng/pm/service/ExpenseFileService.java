package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ExpenseFile;

import java.util.List;

/**
 * @ClassName ExpenseFile
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:09
 */
public interface ExpenseFileService {

    int insert(ExpenseFile expenseFile);

    int delete(String id);

    List<ExpenseFile> selectByMark(String mark);

    /**
     * 通过文件url查询
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
