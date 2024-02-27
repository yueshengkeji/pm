package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.TravelApplication;
import com.yuesheng.pm.mapper.TravelApplicationMapper;
import com.yuesheng.pm.service.TravelApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出差申请
 *
 * @author ssk
 * @since 2022-3-2
 */
@Service("TravelApplicationService")
public class TravelApplicationServiceImpl implements TravelApplicationService {

    @Autowired
    private TravelApplicationMapper travelApplicationMapper;

    @Override
    public int insertTravelApplication(TravelApplication travelApplication) {
        return travelApplicationMapper.insertTravelApplication(travelApplication);
    }

    @Override
    public int deleteTravelApplication(String id) {
        return travelApplicationMapper.deleteTravelApplication(id);
    }

    @Override
    public int updateTravelApplication(TravelApplication travelApplication) {
        return travelApplicationMapper.updateTravelApplication(travelApplication);
    }

    @Override
    public List<TravelApplication> selectTravelApplicationByTraveller(String travellerId) {
        return travelApplicationMapper.selectTravelApplicationByTraveller(travellerId);
    }

    @Override
    public List<TravelApplication> selectByTraveller(Integer pageNum, Integer pageSize, String travellerId) {
        PageHelper.startPage(pageNum, pageSize, false);
        return travelApplicationMapper.selectByTraveller(travellerId);
    }

    @Override
    public Integer selectCountsByTraveller(String travellerId) {
        return travelApplicationMapper.selectCountsByTraveller(travellerId);
    }

    @Override
    public List<TravelApplication> selectTravelApplicationByPosition(String departmentId) {
        return travelApplicationMapper.selectTravelApplicationByPosition(departmentId);
    }

    @Override
    public List<TravelApplication> selectByPosition(Integer pageNum, Integer pageSize, String departmentId) {
        PageHelper.startPage(pageNum, pageSize, false);
        return travelApplicationMapper.selectByPosition(departmentId);
    }

    @Override
    public List<TravelApplication> selectByPositionDate(Integer pageNum, Integer pageSize, String departmentId, String dateSearch) {
        PageHelper.startPage(pageNum, pageSize);
        return travelApplicationMapper.selectByPositionDate(departmentId, dateSearch);
    }

    @Override
    public Integer selectCountsByPosition(String departmentId) {
        return travelApplicationMapper.selectCountsByPosition(departmentId);
    }

    @Override
    public Integer selectCountsByPositionDate(String departmentId, String dateSearch) {
        return travelApplicationMapper.selectCountsByPositionDate(departmentId, dateSearch);
    }

    @Override
    public List<TravelApplication> selectTravelApplicationByDateSearch(String dateSearch) {
        return travelApplicationMapper.selectTravelApplicationByDateSearch(dateSearch);
    }

    @Override
    public List<TravelApplication> selectByDateSearch(Integer pageNum,Integer pageSize, String dateSearch) {
        PageHelper.startPage(pageNum,pageSize,false);
        return travelApplicationMapper.selectByDateSearch( dateSearch);
    }

    @Override
    public List<TravelApplication> selectByDateSearchDepartment(Integer pageNum, Integer pageSize, String dateSearch, String departmentId) {
        PageHelper.startPage(pageNum, pageSize, false);
        return travelApplicationMapper.selectByDateSearchDepartment(dateSearch, departmentId);
    }

    @Override
    public Integer selectCountsByDateSearch(String dateSearch) {
        return travelApplicationMapper.selectCountsByDateSearch(dateSearch);
    }

    @Override
    public Integer selectCountsByDateSearchDepartment(String dateSearch, String departmentId) {
        return travelApplicationMapper.selectCountsByDateSearchDepartment(dateSearch, departmentId);
    }

    @Override
    public List<TravelApplication> selectTravelApplicationBySearch(String search) {
        return travelApplicationMapper.selectTravelApplicationBySearch(search);
    }

    @Override
    public List<TravelApplication> selectByDateAndDepartment(String dateSearch, String departmentId) {
        return travelApplicationMapper.selectByDateAndDepartment(dateSearch, departmentId);
    }

    @Override
    public TravelApplication selectById(String id) {
        return travelApplicationMapper.selectById(id);
    }

    @Override
    public void checkOa(String id) {
        TravelApplication ta = selectById(id);
        ta.setStatus(1);
        updateTravelApplication(ta);
    }
}
