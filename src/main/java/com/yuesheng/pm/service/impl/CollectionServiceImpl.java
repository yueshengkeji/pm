package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.CollectionNotify;
import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.CollectionMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuesheng.pm.entity.Collection;
import java.util.*;

/**
 * @author ssk
 * @date 2021-12-17
 */
@Service("CollectionService")
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private SalesContractService salesContractService;

    @Autowired
    private CollectionNotifyService collectionNotifyService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private StaffService staffService;

    @Override
    public List<Collection> selectByDay(String dateSearch) {
        String start = dateSearch + " 00:00:00";
        String lastDayOfMonth = dateSearch + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return collectionMapper.selectByDay(startDate,endDate);
    }

    @Override
    public List<Collection> selectByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return collectionMapper.selectByDate(startDate,endDate);
    }

    @Override
    public Collection selectByMark(String pMark) {
        return collectionMapper.selectByMark(pMark);
    }

    @Override
    public List<Collection> selectByAgreementID(String agreementID) {
        return collectionMapper.selectByAgreementID(agreementID);
    }

    @Override
    public int insertCollection(Collection collection) {
        collection.setCreateTime2(DateUtil.getNowDate());
        return collectionMapper.insertCollection(collection);
    }

    @Override
    public int deleteCollection(Integer ID) {
        return collectionMapper.deleteCollection(ID);
    }

    @Override
    public int deleteCollectionByAgreementID(String agreementID) {
        return collectionMapper.deleteCollectionByAgreementID(agreementID);
    }

    @Override
    public int deleteCollectionByPMark(String pMark) {
        return collectionMapper.deleteCollectionByPMark(pMark);
    }


    @Override
    public int updateCollection(Collection collection) {
        return collectionMapper.updateCollection(collection);
    }

    @Override
    public Collection selectById(Integer id) {
        return collectionMapper.selectById(id);
    }

    @Override
    public Integer updateDate(Collection collection) {
        return collectionMapper.updateDate(collection);
    }

    @Override
    public List<Collection> selectToDay() {
        Date date = new Date();
        return collectionMapper.selectByDateStatus(date, "未审核");
    }

    @Override
    public boolean expireNotify() {
        //通知合同缓存
        HashMap<String, SalesContract> tempCache = new HashMap<>();
        //通知消息map
        HashMap<String, String> notifyMap = new HashMap<>();
        //获取过期数据
        List<Collection> expireList = selectToDay();
        //遍历推送数据
        expireList.forEach(item -> {
            SalesContract contract;
            if (!tempCache.containsKey(item.getAgreementID())) {

                contract = salesContractService.selectByID(item.getAgreementID());
                tempCache.put(item.getAgreementID(), contract);

                //    根据合同通知策略 发送通知消息
                CollectionNotify query = new CollectionNotify();
                query.setCollectId(item.getID() + "");
                query.setAgreementId(item.getAgreementID());
                query = collectionNotifyService.selectByCollect(query);
                if (computeNotify(query, contract)) {
                    //满足推送条件
                    String staffId = contract.getRegistrantId();
                    //相同的员工，多个合同，只推送一次
                    if (notifyMap.containsKey(staffId)) {
                        notifyMap.put(staffId, notifyMap.get(staffId) + "、" + contract.getAgreementName());
                    } else {
                        notifyMap.put(staffId, contract.getAgreementName());
                    }
                    if (Objects.isNull(query)) {
                        query = new CollectionNotify();
                        query.setCollectId(item.getID() + "");
                        query.setAgreementId(item.getAgreementID());
                    } else {
                        //记录收款推送记录
                        query.setNotifyDate(new Date());
                        query.setId(null);
                    }
                    query.setNotifyDate(DateUtil.getNowDate());
                    query.setNotifyMsg("收款账单通知消息");
                    collectionNotifyService.insert(query);
                }
            }
        });
        //遍历通知消息map,推送到企业微信
        sendNotify(notifyMap);
        return true;
    }

    @Override
    public List<Collection> selectAll() {
        return collectionMapper.selectAll();
    }

    @Override
    public List<Collection> selectBySearch(String str,String staffId) {
        return collectionMapper.selectBySearch(str,staffId);
    }



    @Override
    public List<Collection> selectByAgreementIDAndStatus(String agreementId, String status) {
        return collectionMapper.selectByAgreementIDAndStatus(agreementId,status);
    }

    @Override
    public List<Collection> selectByPDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return collectionMapper.selectByPDate(startDate,endDate);
    }

    @Override
    public List<Collection> collectList(Map<String, Object> params) {
        return collectionMapper.collectList(params);
    }

    @Override
    public Collection selectByBillMark(String billMark) {
        return collectionMapper.selectByBillMark(billMark);
    }

    private void sendNotify(HashMap<String, String> notifyMsg) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", "合同应收款提醒");
        param.put("content", "应收款合同列表如下:");
        param.put("url", WebParam.VUETIFY_BASE+"/salesContract/list/CollectionDetail");
        notifyMsg.forEach((key, val) -> {
            List<Staff> staffList = new ArrayList();
            staffList.add(staffService.getStaffById(key));
            param.put("mTitle", val);
            flowNotifyService.msgNotify(staffList, param);
            staffList.clear();
        });
    }

    /**
     * 计算合同通知策略是否满足
     *
     * @param query    通知记录
     * @param contract 销售合同
     * @return
     */
    private boolean computeNotify(CollectionNotify query, SalesContract contract) {
        Integer type = contract.getNotifyType();
        boolean flag = false;
        if (!Objects.isNull(type) && !Objects.isNull(query) && !Objects.isNull(query.getNotifyDate())) {
            Integer days = DateUtil.getOffsetDays(query.getNotifyDate(), new Date());
            if (type == 1 && days >= 1) {
                flag = true;
            } else if (type == 2 && days >= 7) {
                flag = true;
            } else if (type == 3 && days >= 31) {
                flag = true;
            } else if (type == 4 && days >= (31 * 3)) {
                flag = true;
            } else if (type == 5 && days >= (31 * 6)) {
                flag = true;
            } else if (type == 6 && days >= 365) {
                flag = true;
            }
        } else if (Objects.isNull(query)) {
            flag = true;
        }
        return flag;
    }
}
