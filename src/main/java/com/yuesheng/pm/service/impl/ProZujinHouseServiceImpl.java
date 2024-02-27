package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.entity.ProZujinHouseR;
import com.yuesheng.pm.entity.ProZujinYt;
import com.yuesheng.pm.mapper.ProZujinHouseMapper;
import com.yuesheng.pm.service.ProHouseJsonService;
import com.yuesheng.pm.service.ProZujinHouseRService;
import com.yuesheng.pm.service.ProZujinHouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * (ProZujinHouse)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Service("proZujinHouseService")
public class ProZujinHouseServiceImpl implements ProZujinHouseService {
    @Autowired
    private ProZujinHouseMapper proZujinHouseMapper;
    @Autowired
    private ProZujinHouseRService proZujinHouseRService;
    @Autowired
    private ProHouseJsonService houseJsonService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProZujinHouse queryById(Integer id) {
        return this.proZujinHouseMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProZujinHouse> queryAllByLimit(int offset, int limit) {
        return this.proZujinHouseMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proZujinHouse 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinHouse insert(ProZujinHouse proZujinHouse) {
        if (StringUtils.isNotBlank(proZujinHouse.getFloor())
                && StringUtils.isNotBlank(proZujinHouse.getPwNumber())) {
            HashMap<String, Object> param = new HashMap<>();
            param.put("floor", proZujinHouse.getFloor());
            param.put("pwNumber", proZujinHouse.getPwNumber());
            PageHelper.startPage(1, 2, false);
            List<ProZujinHouse> house = this.proZujinHouseMapper.queryByParam(param);
            if (house.isEmpty()) {
                proZujinHouse.setFlag("0");
                this.proZujinHouseMapper.insert(proZujinHouse);
            } else {
                proZujinHouse.setId(house.get(0).getId());
                this.proZujinHouseMapper.update(proZujinHouse);
            }
        }
        return proZujinHouse;
    }

    /**
     * 修改数据
     *
     * @param proZujinHouse 实例对象
     * @return 实例对象
     */
    @Override
    public ProZujinHouse update(ProZujinHouse proZujinHouse) {
        if (StringUtils.isNotBlank(proZujinHouse.getFloor())
                && StringUtils.isNotBlank(proZujinHouse.getPwNumber())) {
            this.proZujinHouseMapper.update(proZujinHouse);
        }
        return this.queryById(proZujinHouse.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proZujinHouseMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProZujinHouse> queryByParam(HashMap<String, Object> params, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return this.proZujinHouseMapper.queryByParam(params);
    }

    @Override
    public Integer queryCountByParam(HashMap<String, Object> params) {
        return this.proZujinHouseMapper.queryCountByParam(params);
    }

    @Override
    public List<String> queryFloor() {
        return this.proZujinHouseMapper.queryFloor();
    }

    @Override
    public int insertHouseRelation(List<ProZujinHouse> houseList, ProZujin zujin) {


        houseList.forEach(item -> {
            ProZujinHouseR houseR = new ProZujinHouseR();
            houseR.setHouseId(item.getId());
            houseR.setZjId(zujin.getId());
            proZujinHouseRService.insert(houseR);
            if (zujin.getType() == 0 || zujin.getType() == 2) {
                item.setFlag("1");
                item.setPerson(zujin.getZlPerson());
                item.setBrandName(zujin.getBrand());
                //更新商铺已租
                update(item);
                //    更新geojson数据，绑定信息
                houseJsonService.bindBrand(item);
            }
        });


        return houseList.size();
    }

    @Override
    public List<ProZujinHouse> getHouseByZujin(Integer zujinId) {
        ProZujinHouseR query = new ProZujinHouseR();
        query.setZjId(zujinId);
        List<ProZujinHouseR> list = proZujinHouseRService.queryAll(query);
        ArrayList result = new ArrayList();
        list.forEach(item -> {
            result.add(queryById(item.getHouseId()));
        });
        return result;
    }

    @Override
    public int deleteByZujin(Integer zujinId) {
        List<ProZujinHouse> houses = getHouseByZujin(zujinId);
        for (ProZujinHouse house : houses) {
            house.setFlag("0");
            house.setBrandName("");
            house.setPerson("");
            update(house);
            //更新geojson数据，取消绑定
            houseJsonService.clearBrand(house);
        }
        return proZujinHouseRService.deleteByZujinId(zujinId);
    }

    @Override
    public int releaseByZujin(Integer zujinId) {
        List<ProZujinHouse> houses = getHouseByZujin(zujinId);
        ProZujinHouseR r = new ProZujinHouseR();
        r.setZjId(zujinId);
        for (ProZujinHouse house : houses) {
            house.setFlag("0");
            house.setBrandName("");
            house.setPerson("");
            update(house);

            r.setType((byte) 1);
            r.setHouseId(house.getId());
            proZujinHouseRService.updateType(r);
        }
        return 1;
    }

    @Override
    public int updateHouseYetai(ProZujinYt yt, Integer id) {
        ProZujinHouse house = this.queryById(id);
        if (!Objects.isNull(house)) {
            house.setYt(yt);
            this.update(house);
        }
        return 0;
    }
}
