package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.WorkArticlePut;

/**
 * 办公用品入库
 */
public interface WorkArticlePutService {
    /**
     * 办公用品入库
     * @param article
     * @return
     */
    public int insert(WorkArticlePut article);

    int approveByFlow(FlowMessage msg);

    /**
     * 审核办公用品入库
     * @param article
     * @return
     */
    public int approve(WorkArticlePut article);

    int unApprove(WorkArticlePut articlePut);

    /**
     * 删除办公用品入库
     * @param id 主键
     * @return
     */
    public int delete(String id);
}
