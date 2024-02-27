package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.EntertainMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName EntertainServiceImpl
 * @Description
 * @Author ssk
 * @Date 2023/5/6 0006 10:02
 */
@Service("entertainService")
public class EntertainServiceImpl implements EntertainService, FrameStateCheckService {

    @Autowired
    private EntertainMapper entertainMapper;

    @Autowired
    private SystemConfigService systemConfig;

    @Autowired
    private StaffService staffService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private FlowApproveService flowApproveService;

    @Override
    public int insert(Entertain entertain) {
        entertain.setId(UUID.randomUUID().toString());
        entertain.setCreateTime(DateUtil.getDatetime());
        return entertainMapper.insert(entertain);
    }

    @Override
    public int update(Entertain entertain) {
        entertain.setUpdateTime(DateUtil.getDatetime());
        return entertainMapper.update(entertain);
    }

    @Override
    public int delete(String id) {
        return entertainMapper.delete(id);
    }

    @Override
    public List<Entertain> list(Map<String, Object> params) {
        return entertainMapper.list(params);
    }

    @Override
    public int updateState(int state, String id) {
        return entertainMapper.updateState(state, id);
    }

    @Override
    public Entertain selectById(String id) {
        return entertainMapper.selectById(id);
    }

    @Override
    public void oaSuccessChange(FlowMessage message) {
        Entertain entertain = selectById(message.getFrameId());
        if (!Objects.isNull(entertain)) {
            List<Staff> staffList = new ArrayList<>();
            if (StringUtils.equals(entertain.getEntertainCategory(), "0")) {
                //知会厨房管理员
                SystemConfig sc = systemConfig.queryByCoding("CHUFANG");
                if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
                    staffList.addAll(staffService.seek(sc.getValue()));
                }
            }
            if (entertain.getEntertainCar() != null && entertain.getEntertainCar() == 1) {
                //知会商务用车司机
                SystemConfig sc = systemConfig.queryByCoding("SHANGWU_CAR");
                if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
                    staffList.addAll(staffService.seek(sc.getValue()));
                }
            }
            if (StringUtils.contains(entertain.getEntertainTypes(), "2")) {
                //知会文员(公章)
                SystemConfig sc = systemConfig.queryByCoding("WENYUAN_GZ");
                if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
                    staffList.addAll(staffService.seek(sc.getValue()));
                }
            }

            //新增流程知会
            String[] staffIds = new String[staffList.size()];
            int x = 0;
            for (Staff staff : staffList) {
                staffIds[x] = staff.getId();
                x++;
            }
            List<FlowApprove> fa = flowApproveService.getFlowApproveByMessageId(message.getId());
            if (!fa.isEmpty()) {
                flowApproveService.inFromUser(staffIds, fa.get(fa.size() - 1).getId());
            }

            //推送微信通知
            HashMap<String, Object> result = new HashMap<>();
            result.put("title", "招待通知");
            result.put("mTitle", entertain.getStaff().getName() + "的招待通知");
            result.put("content", "请点击查看详情");
            result.put("url", WebParam.VUETIFY_BASE + "/approve/index/2");
            flowNotifyService.msgNotify(staffList, result);

        }
    }
}
