package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.FixedAssets;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.mapper.FixedAssetsMapper;
import com.yuesheng.pm.service.FixedAssetsService;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.PinyinUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author XiaoSong
 * @date 2017/12/20
 */
@Service("fixedAssetsService")
public class FixedAssetsServiceImpl implements FixedAssetsService {
    @Autowired
    private FixedAssetsMapper fixedAssetsMapper;
    @Autowired
    private SystemConfigService configService;

    @Override
    public int insert(FixedAssets fa) {
        Folder f = fa.getFolderObj();
        Folder temp;
        if (f == null) {
            return -1;
        }
        if (f.getId() == null || "".equals(f.getId())) {
            temp = getType(f.getName());
            //判断目录是否存在，不存在添加
            if (temp == null) {
                f.setId(UUID.randomUUID().toString());
                f.setRootId(f.getId());
                boolean typeSuccess = insertType(f);
                if (typeSuccess) {
                    //添加成功
                    fa.setFolderObj(f);
                } else {
                    //添加目录失败
                    return -2;
                }
            } else {
                //存在目录
                fa.setFolderObj(temp);
            }
        }
        if (StringUtils.isBlank(fa.getId())) {
            fa.setId(UUID.randomUUID().toString());
        }
        fixedAssetsMapper.insert(fa);
        return 1;
    }

    @Override
    public int insert(FixedAssets fa, Section section) {
        if (StringUtils.isBlank(fa.getSeries())) {
            SystemConfig sc = configService.queryByCoding("FIXED_SERIES");
            if (!Objects.isNull(sc)) {
                String fix = sc.getValue() + "-" + PinyinUtils.getPinYinHeadChar(section.getName()+"-");
                Integer size = querySeriesCount(fix);
                if (Objects.isNull(size)) {
                    size = 0;
                }
                fa.setSeries(fix + (size + 1));
            }
        }
        return this.insert(fa);
    }

    private Integer querySeriesCount(String fix) {
        return fixedAssetsMapper.querySeriesCount(fix);
    }

    @Override
    public boolean insertType(Folder f) {
        if (f.getName() == null || "".equals(f.getName())) {
            return false;
        }
        fixedAssetsMapper.insertType(f);
        return true;
    }

    @Override
    public Folder getType(String name) {
        PageHelper.startPage(1,1);
        return fixedAssetsMapper.getType(name);
    }

    @Override
    public List<FixedAssets> getFixedAssetsByMultiId(String[] ids) {
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put("array", ids);
        return fixedAssetsMapper.getFixedAssetsByMultiId(map);
    }

    @Override
    public int delete(String id) {
        return fixedAssetsMapper.delete(id);
    }

    @Override
    public int update(FixedAssets fa) {
        return fixedAssetsMapper.update(fa);
    }

    @Override
    public FixedAssets queryById(String id) {
        return fixedAssetsMapper.queryById(id);
    }

    @Override
    public List<FixedAssets> queryByParam(Map<String, Object> param) {
        return fixedAssetsMapper.queryByParam(param);
    }

    @Override
    public int queryByParamCount(Map<String, Object> param) {
        return fixedAssetsMapper.queryByParamCount(param);
    }

    @Override
    public int deleteByFixed(String fixedId) {
        return fixedAssetsMapper.deleteByFixed(fixedId);
    }
}
