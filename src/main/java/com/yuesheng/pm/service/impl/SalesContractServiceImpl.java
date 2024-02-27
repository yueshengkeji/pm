package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.SalesFiles;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.mapper.SalesContractMapper;
import com.yuesheng.pm.model.Marker;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author ssk
 * @date 2021-12-17
 */
@Service("SalesContractService")
public class SalesContractServiceImpl implements SalesContractService, FileService {

    @Autowired
    public SalesContractMapper salesContractMapper;

    @Autowired
    private SalesContractFilesService filesService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private SystemConfigService configService;

    @Override
    public SalesContract selectByID(String agreementID) {
        return salesContractMapper.selectByID(agreementID);
    }

    @Override
    public List<SalesContract> selectByName(String agreementName) {
        return salesContractMapper.selectByName(agreementName);
    }

    @Override
    public List<SalesContract> selectByCompany(String companyName) {
        return salesContractMapper.selectByCompany(companyName);
    }

    @Override
    public List<SalesContract> selectByRegistrant(String registrant) {
        return salesContractMapper.selectByRegistrant(registrant);
    }

    @Override
    public List<SalesContract> selectAll() {
        return salesContractMapper.selectAll();
    }

    @Override
    public List<SalesContract> selectForPage(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectForPage();
    }

    @Override
    public Integer selectCounts() {
        return salesContractMapper.selectCounts();
    }

    @Override
    public List<SalesContract> selectByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return salesContractMapper.selectByDate(startDate,endDate);
    }

    @Override
    public List<SalesContract> selectByDate2(Integer pageNum,Integer pageSize,String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectByDate2(startDate,endDate);
    }

    @Override
    public Integer selectByDateCounts(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return salesContractMapper.selectByDateCounts(startDate,endDate);
    }

    @Override
    public List<SalesContract> selectByDateForPage(Integer pageNum,Integer pageSize, String dateSearch, List staffId) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectByDateForPage(startDate,endDate, staffId);
    }

    @Override
    public Integer selectByDateCounts(String dateSearch, List staffId) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return salesContractMapper.selectByDateForPageCounts(startDate,endDate, staffId);
    }

    @Override
    public List<SalesContract> selectByDateYear(String dateSearch) {
        return salesContractMapper.selectByDateYear(dateSearch);
    }

    @Override
    public int insertContract(SalesContract salesContract) {
        salesContract.setContractId(UUID.randomUUID().toString());
        salesContract.setCreateTime(DateUtil.getNowDate());
        return salesContractMapper.insertContract(salesContract);
    }

    @Override
    public int deleteContract(String agreementID) {
        return salesContractMapper.deleteContract(agreementID);
    }

    @Override
    public int updateContract(SalesContract salesContract) {
        return salesContractMapper.updateContract(salesContract);
    }

    @Override
    public List<SalesContract> selectBySearch(String search) {
        return salesContractMapper.selectBySearch(search);
    }

    @Override
    public List<SalesContract> selectByDuty(Integer pageNum,Integer pageSize, List staffId) {
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectByDuty( staffId);
    }

    @Override
    public Integer selectByDutyCounts(List staffId) {
        return salesContractMapper.selectByDutyCounts(staffId);
    }

    @Override
    public List<SalesContract> selectByDateAndStaffId(String dateSearch, String staffId, Integer pageNum,Integer pageSize) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectByDateAndStaffId(startDate,endDate, staffId);
    }

    @Override
    public Integer selectByDateAndStaffIdCounts(String dateSearch, String staffId) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return salesContractMapper.selectByDateAndStaffIdCounts(startDate,endDate, staffId);
    }

    @Override
    public List<SalesContract> selectByStaffId(Integer pageNum,Integer pageSize, String staffId) {
        PageHelper.startPage(pageNum,pageSize);
        return salesContractMapper.selectByStaffId( staffId);
    }

    @Override
    public Integer selectByStaffIdCounts(String staffId) {
        return salesContractMapper.selectByStaffIdCounts(staffId);
    }

    @Override
    public void updateState(String id)
    {
        updateState(id,1);
    }

    @Override
    public int updateState(String contractId, int state) {
        return salesContractMapper.updateState(contractId, state);
    }

    @Override
    public List<Marker> getProjectMap() {
        return salesContractMapper.getProjectMap();
    }

    @Override
    public int setProjectLngLat() {
        //获取百度地图ak
        SystemConfig baidu_ak = configService.queryByCoding("BAIDU_AK");
        if (ObjectUtils.isEmpty(baidu_ak)){
            return 0;
        }
        //获取工程地址
        List<Marker> projectMap = salesContractMapper.getProjectMap();
        for (Marker marker : projectMap){
            Map<String, Double> lndLat = baiduMapSearch(marker.getProjectBase(), baidu_ak.getValue());
            SalesContract salesContract = new SalesContract();
            salesContract.setContractId(marker.getId());
            salesContract.setLat(lndLat.get("lat"));
            salesContract.setLng(lndLat.get("lng"));
            salesContractMapper.updateContract(salesContract);
        }

        return 1;
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        List<SalesFiles> saleFiles = filesService.select(moduleId);
        ArrayList<Attach> attaches = new ArrayList<>();
        saleFiles.forEach(item -> {
            String url = item.getFileUrl();
            String name = item.getFileName();
            attaches.add(attachService.getById(attachService.findId(name, url)));
        });
        return attaches;
    }


    public static String baiduMapSearchURL = "https://api.map.baidu.com/place/v2/suggestion?";
    public Map<String,Double> baiduMapSearch(String addressStr,String ak){
        Map<String,Double> lngLat = new HashMap<>(8);
        lngLat.put("lng",0.0);
        lngLat.put("lat",0.0);

        Map<String,Object> params = new HashMap<>(8);
        params.put("query", addressStr);
        params.put("region", "中国");
        params.put("output", "json");
        params.put("ak", ak);

        StringBuffer queryString = new StringBuffer();
        queryString.append(baiduMapSearchURL);
        for (Map.Entry<?, ?> pair : params.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        String s = loadJSON(queryString.toString());
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray result = jsonObject.getJSONArray("result");
        if (result.size() > 0){
            JSONObject item = result.getJSONObject(0);
            lngLat.put("lng",item.getJSONObject("location").getDouble("lng"));
            lngLat.put("lat",item.getJSONObject("location").getDouble("lat"));
        }

        return lngLat;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

}
