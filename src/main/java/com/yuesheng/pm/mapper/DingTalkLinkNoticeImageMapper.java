package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DingTalkLinkNoticeImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName DingTalkLinkNoticeImageMapper
 * @Description
 * @Author ssk
 * @Date 2022/9/9 0009 15:07
 */
@Mapper
public interface DingTalkLinkNoticeImageMapper {

    /**
     * 新增图片
     * @param dingTalkLinkNoticeImage
     * @return
     */
    int insert(DingTalkLinkNoticeImage dingTalkLinkNoticeImage);

    /**
     * 获取钉钉消息图片信息列表
     * @return
     */
    List<DingTalkLinkNoticeImage> selectAll();

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 启用图片
     * @param id
     * @return
     */
    int update(String id);

    /**
     * 初始化图片使用状态
     * @return
     */
    int resetStatus();

    /**
     * 获取已使用的图
     * @return
     */
    DingTalkLinkNoticeImage selectUsed();

    /**
     * 获取第一个图
     * @return
     */
    DingTalkLinkNoticeImage selectFirst();
}
