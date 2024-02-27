package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.MyApprove;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17 【我的审批】mapper.
 * @author XiaoSOng
 * @date 2016/08/17
 */
@Mapper
public interface MyApproveMapper {
    /**
     * 添加我的审批消息
     * @param myApprove 我的审批对象
     */
    void addMyApprove(MyApprove myApprove);

    /**
     * 通过id获取【我的审批消息】对象
     * @param id 【我的审批】消息对象
     * @return 审批消息对象
     */
    MyApprove getMessageById(String id);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(String id);

    List<MyApprove> queryByParam(@Param("startDatetime") String startDatetime,
                                 @Param("endDatetime") String endDatetime);
}
