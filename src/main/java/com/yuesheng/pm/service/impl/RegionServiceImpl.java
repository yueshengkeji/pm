package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.City;
import com.yuesheng.pm.entity.Region;
import com.yuesheng.pm.mapper.RegionMapper;
import com.yuesheng.pm.service.RegionService;
import com.yuesheng.pm.util.AESEncrypt;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by 96339 on 2016/11/19.
 * 地区服务实现
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionMapper regionMapper;
    @Override
    public List<Region> getRegions(String name) {
        return regionMapper.getRegions(name);
    }

    @Override
    public Region getCityById(String id) {
        return regionMapper.getCityById(id);
    }

    @Override
    public void insert(Region city) {
        if (city.getId() == null || StringUtils.equals(city.getId(), "-1")) {
            city.setId(UUID.randomUUID().toString());
        }
        regionMapper.insert(city);
    }

    @Override
    public int update(Region region) {
        return regionMapper.update(region);
    }

    @Override
    public void convert() {
        List<City> cities = regionMapper.getCitys();
        for (City c : cities) {
            String name = c.getName();
            name = name.replace("市", "");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < name.length(); i++) {
                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
                // 控制大小写
                // UPPERCASE：大写 (ZHONG)
                // LOWERCASE：小写 (zhong)
                defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                // WITHOUT_TONE：无音标
                // WITH_TONE_NUMBER：1-4数字表示英标
                // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）
                defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(name.charAt(i), defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination | ArrayIndexOutOfBoundsException badHanyuPinyinOutputFormatCombination) {
                    sb.append("");
                }
            }
            c.setCoding(sb.toString());
            c.setId(AESEncrypt.getRandom8Id());
            c.setName(name.replaceAll(" ", ""));
            City city = regionMapper.selectByName(c.getName());
            if (city == null) {
                regionMapper.insert(c);
            }
        }
    }
}
