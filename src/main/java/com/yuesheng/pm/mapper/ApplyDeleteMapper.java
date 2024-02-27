package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ApplyDelete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019-11-25.
 * @author XiaoSong
 * @date 2019/11/25
 */
@Mapper
public interface ApplyDeleteMapper {
    /**
     * 申请删除订单
     *
     * @param applyDelete 删除订单记录对象
     */
    void applyDelete(ApplyDelete applyDelete);

    /**
     * 修改删除订单记录状态
     *
     * @param applyDelete 删除订单记录对象
     */
    void updateState(ApplyDelete applyDelete);

    /**
     * 查询所有申请删除中订单记录
     *
     * @return 未处理的删除订单记录信息
     */
    List<ApplyDelete> queryAll();

    /**
     * 查询申请删除订单列表
     *
     * @param ad 指定条件
     * @return
     */
    List<ApplyDelete> queryByParam(ApplyDelete ad);

    /**
     * 删除申请信息
     *
     * @param proId 订单id
     * @return
     */
    int delete(@Param("proId") String proId);
}
