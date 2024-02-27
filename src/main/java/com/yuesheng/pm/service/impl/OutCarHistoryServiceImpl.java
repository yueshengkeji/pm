package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.mapper.OutCarHistoryMapper;
import com.yuesheng.pm.model.OutCarHistoryGroup;
import com.yuesheng.pm.service.OutCarHistoryService;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * (OutCarHistory)表服务实现类
 *
 * @author xiaosong
 * @since 2023-03-29 14:54:25
 */
@Service("outCarHistoryService")
public class OutCarHistoryServiceImpl implements OutCarHistoryService {
    @Autowired
    private OutCarHistoryMapper outCarHistoryMapper;
    @Autowired
    private SystemConfigService configService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutCarHistory queryById(String id) {
        return this.outCarHistoryMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param outCarHistory 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<OutCarHistory> queryByPage(OutCarHistory outCarHistory, PageRequest pageRequest) {
        long total = this.outCarHistoryMapper.count(outCarHistory);
        return new PageImpl<>(this.outCarHistoryMapper.queryAllByLimit(outCarHistory, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param outCarHistory 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarHistory insert(OutCarHistory outCarHistory) {
        outCarHistory.setId(UUID.randomUUID().toString());
        outCarHistory.setDatetime(DateUtil.getDatetime());
        outCarHistory.setIsParkingCost(0);
        this.outCarHistoryMapper.insert(outCarHistory);
        return outCarHistory;
    }

    /**
     * 修改数据
     *
     * @param outCarHistory 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarHistory update(OutCarHistory outCarHistory) {
        this.outCarHistoryMapper.update(outCarHistory);
        return this.queryById(outCarHistory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.outCarHistoryMapper.deleteById(id) > 0;
    }

    @Override
    public List<OutCarHistory> queryAll(OutCarHistory q) {
        return this.outCarHistoryMapper.queryAll(q);
    }

    @Override
    public List<OutCarHistoryGroup> queryGroupProject(OutCarHistory q) {
        return this.outCarHistoryMapper.queryGroupProject(q);
    }

    @Override
    public List<OutCarHistory> queryByNoProject(OutCarHistory q) {
        return this.outCarHistoryMapper.queryByNoProject(q);
    }

    @Override
    public void recoverImgToSmartKm(String startDate, String endDate) {
        OutCarHistory q = new OutCarHistory();
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            startDate = DateUtil.getDate() + " 00:00:00";
            endDate = DateUtil.getDate() + " 23:59:59";
        }
        q.setStartTime(startDate);
        q.setEndTime(endDate);
        List<OutCarHistory> outCarHistories = queryAll(q);
//        outCarHistories.forEach(this::updateSmartKm);
    }

    @Override
    public void updateSmartKm(OutCarHistory och) {
        // 定义Tesseract OCR引擎实例
        /*Tesseract tesseract = new Tesseract();
        ByteArrayInputStream bais = null;
        ByteArrayInputStream bais2 = null;
        try {
            SystemConfig sc = configService.queryByCoding("TESSDATA");
            if (!Objects.isNull(sc)) {
                // 设置Tesseract数据训练文件的路径，可以根据实际情况进行修改
                tesseract.setDatapath("/opt/homebrew/share/tessdata/");
                // 读取图片并进行文字识别
                byte[] startImg = FtpUtil.downloadFile(och.getStartImg());
                if (startImg.length > 0) {
                    bais = new ByteArrayInputStream(startImg);
                    String startKm = tesseract.doOCR(ImageIO.read(bais));
                    // 输出识别的文字结果
                    LogManager.getLogger("mylog").info("开始里程数据：" + startKm);
                }

                byte[] endImg = FtpUtil.downloadFile(och.getEndImg());
                if (endImg.length > 0) {
                    bais2 = new ByteArrayInputStream(endImg);
                    String endKm = tesseract.doOCR(ImageIO.read(bais2));
                    LogManager.getLogger("mylog").info("结束里程数据：" + endKm);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(bais);
            IOUtils.closeQuietly(bais2);
        }*/
    }
}
