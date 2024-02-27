package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProOtherPay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProOtherPay)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:02
 */
@Mapper
public interface ProOtherPayMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProOtherPay queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProOtherPay> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proOtherPay 实例对象
     * @return 对象列表
     */
    List<ProOtherPay> queryAll(ProOtherPay proOtherPay);

    /**
     * 新增数据
     *
     * @param proOtherPay 实例对象
     * @return 影响行数
     */
    int insert(ProOtherPay proOtherPay);

    /**
     * 修改数据
     *
     * @param proOtherPay 实例对象
     * @return 影响行数
     */
    int update(ProOtherPay proOtherPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 保存附件
     *
     * @param id     付款单id
     * @param fileId 文件id
     * @return
     */
    int saveFile(@Param("payId") String id, @Param("fileId") String fileId);

    /**
     * 删除附件
     *
     * @param fileId
     * @return
     */
    int deleteFileByFileId(@Param("fileId") String fileId);

    /**
     * 删除附件
     *
     * @param payId 付款单id
     * @return
     */
    int deleteFileByPayId(@Param("payId") String payId);

    /**
     * 查询附件列表
     *
     * @param payId 付款单id
     * @return
     */
    String[] queryFile(@Param("payId") String payId);

    /**
     * 查询指定范围内，付款金额合计
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param tagName   标签名称
     * @param state     审核状态
     * @return
     */
    Double getSumMoney(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("tagName") String tagName, @Param("state") Integer state);

    /**
     * 查询未支付列表
     * @param query
     * @return
     */
    List<ProOtherPay> queryNoPay(ProOtherPay query);
}