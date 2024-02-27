package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ApplyMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016-06-20 采购申请单实现.
 */
@Service("applyService")
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private CityService cityService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    @Lazy
    private ProcurementMaterService proMaterService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public List<Apply> getApplyList() {
        return applyMapper.getApplyListByParams();
    }

    @Override
    public List<Apply> getApplysByDate(Integer index, Map map) {
        PageHelper.startPage(index, Constant.PAGESIZE);
        return applyMapper.getApplysByDate( map);
    }

    @Override
    public void updateState(String s, int state) {
        Map<String, Object> params = new HashMap(2);
        //状态put到参数中
        params.put("state", state);
        //申请单id put到参数中
        params.put("appId", s);
        applyMapper.updateState(params);
    }

    @Override
    public Apply getApplyById(String applyId) {
        return applyMapper.getApplyById(applyId);
    }


    @Override
    public List<Apply> getApplyList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("pageNumber").toString()),Integer.parseInt(params.get("pageSize").toString()),false);
        return applyMapper.getApplyListByParams(params);
    }

    @Override
    public Integer getApplyListCount(Map<String, Object> params) {
        return applyMapper.getApplyCount(params);
    }

    @Override
    public void setStatus(String id) {

        List<ApplyMaterial> applyMaterials = applyMaterialService.getByApplyId(id);
        boolean isNoPro = false;
        for (ApplyMaterial item : applyMaterials) {
            Double proSum = proMaterService.getProSumByApplyMaterId(item.getMajor());
            if (!Objects.isNull(proSum) && proSum > 0) {
                //更新已采购总数
                item.setySum(proSum);
            } else {
                isNoPro = true;
                item.setySum(0.0);
            }
            applyMaterialService.updateProSum(item);
        }

        //查询该申请单采购总数与申请总数
        Map<String, BigDecimal> sums = applyMaterialService.getMaterSums(id);
        BigDecimal applySum = sums.getOrDefault("applySum", new BigDecimal(0.0));
        BigDecimal ySum = sums.getOrDefault("ySum", new BigDecimal(0.0));
        int state;
        if (ySum.compareTo(new BigDecimal(0.0)) == 0) {
            //采购数为0，更新状态为未采购
            state = 0;
        } else if (applySum.compareTo(ySum) < 1) {
            if (isNoPro) {
                //有材料为采购，直接更新状态为部分采购
                state = 1;
            } else {
                applyMaterials = applyMaterialService.getByApplyId(id);
                state = 2;
                for (ApplyMaterial item : applyMaterials) {
                    if (item.getySum() < item.getSum()) {
                        //有材料未完全采购，更新状态未部分采购
                        state = 1;
                        break;
                    }
                }
            }
        } else {
            //更新采购状态为部分采购
            state = 1;
        }
        //更新申请订单状态
        updateState(id, state);
//        applyMapper.setStatus(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addApply(final Apply apply) {
        if (StringUtils.isBlank(apply.getId())) {
            apply.setId(UUID.randomUUID().toString());
        }
        apply.setPrepareDate(DateFormat.getDate());
        apply.setSection(apply.getStaff().getSection());
        apply.setStaffCoding(apply.getStaff().getCoding());
        if (StringUtils.isBlank(apply.getRemark())) {
            apply.setRemark("");
        }
        if (StringUtils.isBlank(apply.getDate())) {
            apply.setDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        }
        if (StringUtils.isBlank(apply.getUnitid())) {
            if (Objects.isNull(apply.getApplyUnit())) {
                Company company = new Company();
                company.setId("");
                apply.setApplyUnit(company);
            }
            apply.setUnitid(apply.getApplyUnit().getId());
        }
        City c = apply.getCity();
        //是否添加地址
        if (c != null) {
            if ((c = cityService.insert(c)) != null) {
                apply.setAddress(c.getId());
            } else {
                apply.setAddress("");
            }
        } else {
            apply.setAddress("");
        }

        if (StringUtils.isBlank(apply.getSerialNumber())) {
            apply.setSerialNumber(apply.getStaffCoding() + DateUtil.format(new Date(), DateUtil.PATTERN_IMAGE_NAME));
        } else {
            Apply temp = getBySeries(apply.getSerialNumber());
            if (!Objects.isNull(temp)) {
                apply.setSerialNumber("编号重复,自动生成:" + DateUtil.format(new Date(), DateUtil.PATTERN_IMAGE_NAME));
            }
        }
        applyMapper.addApply(apply);
    }

    @Override
    public void delete(String id) {
        //删除明细
        applyMaterialService.deleteByApply(id);
        //删除主单据
        applyMapper.delete(id);
    }

    @Override
    public void approve(String id, int state, String coding, String date) {
        HashMap param = new HashMap(16);
        param.put("id", id);
        param.put("state", state);
        param.put("coding", coding);
        param.put("date", date);
        param.put("approveTime", DateUtil.getDatetime());
        applyMapper.approve(param);
    }

    @Override
    public String verifySeries(String seriesNumber) {
        PageHelper.startPage(1,1);
        return applyMapper.verifySeries(seriesNumber);
    }

    @Override
    public void updateApproveTime(String id, String approveDate) {
        applyMapper.updateApproveTime(id, approveDate);
    }

    @Override
    public List<Apply> getApplyGroupByProject(String projectId) {
        return applyMapper.getApplyByProject(projectId);
    }

    @Override
    public int updateNotify(String id, String notifyDate, String staffCoding) {
        return applyMapper.updateNotify(id, notifyDate, staffCoding);
    }

    @Override
    public boolean notifyStaff() {
        String date = DateUtil.getDate();
        List<Apply> applies = applyMapper.queryByNotify(date);

        HashMap<String, StringBuffer> tempMap = new HashMap<>(2);
        applies.forEach(item -> {
            if (StringUtils.isNotBlank(item.getNotifyStaffCoding()) && !tempMap.containsKey(item.getNotifyStaffCoding())) {
                StringBuffer sb = new StringBuffer();
                sb.append(item.getSerialNumber());
                sb.append(";");
                tempMap.put(item.getNotifyStaffCoding(), sb);
            } else if (tempMap.containsKey(item.getNotifyStaffCoding())) {
                StringBuffer sb = tempMap.get(item.getNotifyStaffCoding());
                sb.append(item.getSerialNumber());
                sb.append(";");
                tempMap.put(item.getNotifyStaffCoding(), sb);
            }
        });

        List<Staff> notifyStaff = new ArrayList<>();
        HashMap<String, Object> msg = new HashMap<>(3);
        msg.put("title", "申请单采购通知");
        msg.put("url", "/managent/systemNotify/procurement/apply/list");
        tempMap.forEach((key, val) -> {
            Staff staff = staffService.getStaffByCoding(key);
            if (!Objects.isNull(staff)) {
                notifyStaff.add(staff);
                msg.put("content", val.toString());
                flowNotifyService.msgNotify(notifyStaff, msg);
                notifyStaff.clear();
            }
        });
        return true;
    }

    @Override
    public int notifyExpress(String id, String expressCode) {
        Apply apply = getApplyById(id);
        if (!Objects.isNull(apply)) {
            List<Staff> staffList = new ArrayList<>();
            staffList.add(apply.getStaff());
            Map<String, Object> param = new HashMap<>();
            param.put("title", "采购申请单已发货");
            param.put("mTitle", "《" + apply.getSerialNumber() + "》已发货");
            param.put("content", "快递单号：" + expressCode);
            param.put("url", "/managent/systemNotify/apply/list");
        }
        return 0;
    }

    @Override
    public List<Apply> getApplyListV2(HashMap<String, Object> params) {
        return applyMapper.getApplyListByParamsV2(params);
    }

    @Override
    public Apply getBySeries(String seriesNumber) {
        PageHelper.startPage(1,1);
        return applyMapper.getBySeries(seriesNumber);
    }

    @Override
    public void checkOa(FlowMessage flowMsg) {
        approve(flowMsg.getFrameId(), 1, flowMsg.getLastApproveUser().getCoding(), DateUtil.getDate());
        SystemConfig sc = systemConfigService.queryByCoding("NTF_PRO_USER");
        if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
            Staff staff = staffService.getStaffByUserName(sc.getValue());
            if (!Objects.isNull(staff)) {
                List<Staff> ns = new ArrayList<>();
                ns.add(staff);
                HashMap<String, Object> result = new HashMap<>();
                result.put("title", "采购任务提醒");
                result.put("mTitle", flowMsg.getTitle());
                result.put("content", "点击查看详情");
                result.put("url", WebParam.VUETIFY_BASE + "/procurement/apply/list");
                flowNotifyService.msgNotify(ns, result);
            }
        }
    }
}
