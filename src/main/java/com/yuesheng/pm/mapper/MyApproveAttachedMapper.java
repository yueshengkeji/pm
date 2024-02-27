package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.MyApproveAttached;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17 【我的审批附表】mapper.
 *
 * @author XiaoSong
 * @date 2016/08/17
 */
@Mapper
public interface MyApproveAttachedMapper {
    /**
     * 添加我的审批附表对象
     *
     * @param attached 我的审批附表对象
     */
    void addApproveAttached(MyApproveAttached attached);

    /**
     * 修改【我的审批】数据
     *
     * @param attached 我的审批附表
     * @return 影响的行数
     */
    int updateAttached(MyApproveAttached attached);


    /**
     * 通过id获取审批附表对象
     *
     * @param id 附表id
     * @return 我的审批附表对象
     */
    MyApproveAttached getAttachedById(String id);

    /**
     * 通过sdpo006 id 获取附表集合
     *
     * @param approveId 主键id
     * @return 我的审批消息附表集合
     */
    List<MyApproveAttached> getAttchedByApprove(@Param("approveId") String approveId);

    /**
     * 更新审批步骤状态
     *
     * @param ma
     * @return
     */
    int updateState(MyApproveAttached ma);

    /**
     * 删除数据
     * @param approveId 主表数据主键
     * @return
     */
    int deleteByApproveId(@Param("approveId") String approveId);
}
