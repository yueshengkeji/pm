package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.mapper.SystemConfigMapper;
import com.yuesheng.pm.service.SystemConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统配置主表(SystemConfig)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-07-07 14:40:20
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SystemConfig queryById(Integer id) {
        return this.systemConfigMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SystemConfig> queryAllByLimit(int offset, int limit) {
        return this.systemConfigMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    @Override
    public SystemConfig insert(SystemConfig systemConfig) {
        SystemConfig sc = queryByCoding(systemConfig.getCoding());
        if (Objects.isNull(sc)) {
            this.systemConfigMapper.insert(systemConfig);
        } else {
            sc.setValue(systemConfig.getValue());
            sc.setName(systemConfig.getName());
            sc.setParent(systemConfig.getParent());
            update(sc);
            return sc;
        }
        return systemConfig;
    }

    /**
     * 修改数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    @Override
    public SystemConfig update(SystemConfig systemConfig) {
        this.systemConfigMapper.update(systemConfig);
        return this.queryById(systemConfig.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        this.systemConfigMapper.deleteByParent(id);
        return this.systemConfigMapper.deleteById(id) > 0;
    }

    @Override
    public List<SystemConfig> queryAll() {
        return this.systemConfigMapper.queryAll(null);
    }

    @Override
    public SystemConfig queryByCoding(String coding) {
        SystemConfig config = new SystemConfig();
        config.setCoding(coding);
        List<SystemConfig> configs = this.systemConfigMapper.queryAll(config);
        if (configs.isEmpty()) {
            return null;
        }
        return configs.get(0);
    }

    @Override
    public List<SystemConfig> queryByParam(SystemConfig config) {
        return this.systemConfigMapper.queryAll(config);
    }

    @Override
    public List<SystemConfig> queryDetailByParent(Integer coding) {
        if (Objects.isNull(coding)) {
            return new ArrayList<>();
        }
        SystemConfig config = new SystemConfig();
        config.setParent(coding);
        return this.systemConfigMapper.queryAll(config);
    }

    @Override
    public String getValueByParentCoding(String coding, String parentCoding) {
        SystemConfig sc = queryByCoding(parentCoding);
        if (!Objects.isNull(sc)) {
            SystemConfig config = new SystemConfig();
            config.setParent(sc.getId());
            List<SystemConfig> configs = queryByParam(config);
            for (int i = 0; i < configs.size(); i++) {
                String temp = configs.get(i).getCoding();
                if (StringUtils.equals(coding, temp)) {
                    return configs.get(i).getValue();
                }
            }
            return null;
        } else {
            return "";
        }
    }
}