package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProDispose;
import com.yuesheng.pm.mapper.ProDisposeMapper;
import com.yuesheng.pm.model.RequestPageModel;
import com.yuesheng.pm.service.ProDisposeDetailService;
import com.yuesheng.pm.service.ProDisposeService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 处置单主表(ProDispose)表服务实现类
 *
 * @author makejava
 * @since 2020-06-28 10:28:57
 */
@Service("proDisposeService")
public class ProDisposeServiceImpl implements ProDisposeService {
    @Autowired
    private ProDisposeMapper proDisposeMapper;
    @Autowired
    private ProDisposeDetailService proDisposeDetailService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProDispose queryById(String id) {
        return this.proDisposeMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProDispose> queryAllByLimit(int offset, int limit) {
        return this.proDisposeMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proDispose 实例对象
     * @return 实例对象
     */
    @Override
    public ProDispose insert(ProDispose proDispose) {
        if (StringUtils.isBlank(proDispose.getDisposeDate())) {
            proDispose.setDisposeDate(DateUtil.format(new Date()));
        }
        if (StringUtils.isBlank(proDispose.getId())) {
            proDispose.setId(UUID.randomUUID().toString());
        }
        proDispose.setDate(DateUtil.format(new Date()));
        this.proDisposeMapper.insert(proDispose);
        if (proDispose.getDisposeDetailList() != null) {
            proDispose.getDisposeDetailList().forEach(item -> {
                item.setDisposeId(proDispose.getId());
                proDisposeDetailService.insert(item);
            });
        }
        return proDispose;
    }

    /**
     * 修改数据
     *
     * @param proDispose 实例对象
     * @return 实例对象
     */
    @Override
    public ProDispose update(ProDispose proDispose) {
        this.proDisposeMapper.update(proDispose);
        return this.queryById(proDispose.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proDisposeMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProDispose> queryAllByParam(RequestPageModel requestPageModel) {
        Map<String, Object> param = new HashMap<>();
        param.put("searchText", requestPageModel.getSearchText());
        param.put("offset", requestPageModel.getOffset());
        param.put("limit", requestPageModel.getLimit());
        PageHelper.startPage(requestPageModel.getOffset(), requestPageModel.getLimit());
        return this.proDisposeMapper.queryAllByParam(param);
    }
}