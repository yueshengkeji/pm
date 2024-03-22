package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.ProOtherPay;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.ProOtherPayMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * (ProOtherPay)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:02
 */
@Service("proOtherPayService")
public class ProOtherPayServiceImpl implements ProOtherPayService, FileService {
    @Autowired
    private ProOtherPayMapper proOtherPayMapper;
    @Autowired
    private AttachService attachService;
    @Autowired
    private FlowMessageService messageService;
    @Autowired
    private FlowApproveService approveService;
    @Autowired
    @Lazy
    private FlowNotifyService flowNotifyService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProOtherPay queryById(String id) {
        return this.proOtherPayMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProOtherPay> queryAllByLimit(int offset, int limit) {
        return this.proOtherPayMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proOtherPay 实例对象
     * @return 实例对象
     */
    @Override
    public ProOtherPay insert(ProOtherPay proOtherPay) {
        if (StringUtils.isBlank(proOtherPay.getId())) {
            proOtherPay.setId(UUID.randomUUID().toString());
        }
        this.proOtherPayMapper.insert(proOtherPay);
        saveFile(proOtherPay);
        return proOtherPay;
    }

    private void saveFile(ProOtherPay pay) {
        String[] filePath = pay.getFileId();
        if (!Objects.isNull(filePath)) {
            for (String id : filePath) {
                this.proOtherPayMapper.saveFile(pay.getId(), id);
            }
        }
    }

    /**
     * 修改数据
     *
     * @param proOtherPay 实例对象
     * @return 实例对象
     */
    @Override
    public ProOtherPay update(ProOtherPay proOtherPay) {
        this.proOtherPayMapper.update(proOtherPay);
        this.proOtherPayMapper.deleteFileByPayId(proOtherPay.getId());
        saveFile(proOtherPay);
        return this.queryById(proOtherPay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        this.proOtherPayMapper.deleteFileByPayId(id);
        return this.proOtherPayMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProOtherPay> queryAll(ProOtherPay proOtherPay) {
        return this.proOtherPayMapper.queryAll(proOtherPay);
    }

    @Override
    public Double getSumMoney(String startDate, String endDate, String tagName, Integer state) {
        return this.proOtherPayMapper.getSumMoney(startDate, endDate, tagName, state);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        String[] file = this.proOtherPayMapper.queryFile(moduleId);
        ArrayList<Attach> fileList = new ArrayList<>();
        if (file != null) {
            for (String id : file) {
                fileList.add(attachService.getById(id));
            }
        }
        return fileList;
    }

    @Override
    public void approve(String id) {
        ProOtherPay proOtherPay = new ProOtherPay();
        proOtherPay.setId(id);
        proOtherPay.setState(true);
        proOtherPay.setPayDatetime(DateUtil.getNowDate());
        approve(proOtherPay);
    }

    @Override
    public List<ProOtherPay> queryNoPay(ProOtherPay query) {
        return proOtherPayMapper.queryNoPay(query);
    }

    @Override
    public void approve(ProOtherPay item) {
        proOtherPayMapper.update(item);
    }

    @Override
    public void syncNoPay() {
        ProOtherPay query = new ProOtherPay();
        query.setState(null);
        List<ProOtherPay> payList = queryNoPay(query);
        payList.forEach(item -> {
            FlowMessage fm = messageService.getMessageByFrameId(item.getId());
            if (!Objects.isNull(fm) && fm.getState() == 2) {
                ProOtherPay proOtherPay = new ProOtherPay();
                proOtherPay.setId(item.getId());
                proOtherPay.setState(true);
                if (StringUtils.isBlank(fm.getDate())) {
                    approveService.queryLastApprove(fm);
                }
                proOtherPay.setPayDatetime(DateUtil.parse(fm.getDate(), DateUtil.PATTERN_CLASSICAL));
                approve(proOtherPay);
            }
        });
    }

    @Override
    public void notifyApplyStaff(String id) {
        ProOtherPay pay = queryById(id);
        Staff staff = pay.getStaff();
        ArrayList<Staff> staffs = new ArrayList<>();
        staffs.add(staff);
        Map<String, Object> msgMap = new HashMap<>(4);
        msgMap.put("title", "采购付款通知");
        msgMap.put("mTitle", pay.getTitle());
        msgMap.put("content", "已经发送到现金会计");
        msgMap.put("url", WebParam.VUETIFY_BASE + "/otherPay/pro?id=" + id);
        flowNotifyService.msgNotify(staff, msgMap);
    }
}