package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.ProHouseJson;
import com.yuesheng.pm.entity.ProZujinHouse;
import com.yuesheng.pm.mapper.ProHouseJsonMapper;
import com.yuesheng.pm.service.ProHouseJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProHouseJson)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-08-19 10:55:26
 */
@Service("proHouseJsonService")
public class ProHouseJsonServiceImpl implements ProHouseJsonService {
    @Autowired
    private ProHouseJsonMapper proHouseJsonMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param key 主键
     * @return 实例对象
     */
    @Override
    public ProHouseJson queryById(String key) {
        return this.proHouseJsonMapper.queryById(key);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProHouseJson> queryAllByLimit(int offset, int limit) {
        return this.proHouseJsonMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proHouseJson 实例对象
     * @return 实例对象
     */
    @Override
    public ProHouseJson insert(ProHouseJson proHouseJson) {
        this.proHouseJsonMapper.insert(proHouseJson);
        return proHouseJson;
    }

    /**
     * 修改数据
     *
     * @param proHouseJson 实例对象
     * @return 实例对象
     */
    @Override
    public ProHouseJson bindBrand(ProHouseJson proHouseJson) {
        this.proHouseJsonMapper.update(proHouseJson);
        return this.queryById(proHouseJson.getKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param key 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String key) {
        return this.proHouseJsonMapper.deleteById(key) > 0;
    }

    @Override
    public int bindBrand(ProZujinHouse house) {
        ProHouseJson json = this.queryById(house.getFloor());

        if (json == null) {
            return -1;
        }

        JSONObject jo = JSON.parseObject(json.getJson());
        JSONArray features = jo.getJSONArray("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject jo2 = (JSONObject) features.get(i);
            JSONObject tempObj = jo2.getJSONObject("properties");
            String roomName;
            if (tempObj != null && (roomName = tempObj.getString("name")) != null) {
                if (roomName.equals(house.getPwNumber())) {
                    roomName += "\r\n" + house.getBrandName();
                    tempObj.put("name", roomName);
                    break;
                }
            }
        }
        json.setJson(jo.toJSONString());
        this.bindBrand(json);
        return 1;
    }

    @Override
    public int clearBrand(ProZujinHouse house) {
        ProHouseJson json = this.queryById(house.getFloor());
        if (json == null) {
            return -1;
        }
        JSONObject jo = JSON.parseObject(json.getJson());
        JSONArray features = jo.getJSONArray("features");
        for (int i = 0; i < features.size(); i++) {
            JSONObject jo2 = (JSONObject) features.get(i);
            JSONObject tempObj = jo2.getJSONObject("properties");
            String roomName;
            if (tempObj != null && (roomName = tempObj.getString("name")) != null) {
                if (roomName.contains(house.getPwNumber())) {
                    roomName = house.getPwNumber();
                    tempObj.put("name", roomName);
                    break;
                }
            }
        }
        json.setJson(jo.toJSONString());
        this.bindBrand(json);
        return 1;
    }
}