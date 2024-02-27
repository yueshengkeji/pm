package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProTaskProgressReport;
import com.yuesheng.pm.mapper.ProTaskProgressReportMapper;
import com.yuesheng.pm.service.ProTaskProgressReportService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ProTaskProgressReprtServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/9/14 0014 9:58
 */
@Service
public class ProTaskProgressReportServiceImpl implements ProTaskProgressReportService {

    @Autowired
    private ProTaskProgressReportMapper proTaskProgressReportMapper;

    @Override
    public int insert(ProTaskProgressReport proTaskProgressReport) {
        proTaskProgressReport.setId(UUID.randomUUID().toString());
        proTaskProgressReport.setCreateTime(DateUtil.getDatetime());
        return proTaskProgressReportMapper.insert(proTaskProgressReport);
    }

    @Override
    public int delete(String id) {
        return proTaskProgressReportMapper.delete(id);
    }

    @Override
    public List<ProTaskProgressReport> selectAllForPage(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,false);
        return proTaskProgressReportMapper.selectAllForPage();
    }

    @Override
    public Integer selectAllCounts() {
        return proTaskProgressReportMapper.selectAllCounts();
    }

    @Override
    public List<ProTaskProgressReport> selectByPro(String projectName) {
        return proTaskProgressReportMapper.selectByPro(projectName);
    }

    @Override
    public ProTaskProgressReport selectById(String id) {
        return proTaskProgressReportMapper.selectById(id);
    }
}
