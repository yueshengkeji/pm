package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.MyApproveMain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-18 【我的审批】超级主表 sdpo100.
 * @author XiaoSong
 * @date 2016/08/18
 */
@Mapper
public interface MyApproveMainMapper {
    /**
     * 添加【我的审批】超级主表
     * @param myApproveMain 【我的审批】超级主表对象
     */
    void addApproveMain(MyApproveMain myApproveMain);

    /**
     * 通过超级审批对象主键获取sdpo100超级审批对象
     * @param msgId sdpo100id
     * @return
     */
    MyApproveMain getMainByMsgId(String msgId);

    /**
     * 查询指定范围内数据
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     * @return
     */
    List<MyApproveMain> queryByParam(@Param("startDatetime") String startDatetime,
                                     @Param("endDatetime") String endDatetime);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") String ids);
}
