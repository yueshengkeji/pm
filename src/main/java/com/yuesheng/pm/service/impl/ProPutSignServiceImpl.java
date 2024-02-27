package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.MaterOut;
import com.yuesheng.pm.entity.ProPutSign;
import com.yuesheng.pm.entity.PutStorage;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProPutSignMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 入库签字表(ProPutSign)表服务实现类
 *
 * @author makejava
 * @since 2020-06-05 11:07:57
 */
@Service("proPutSignService")
public class ProPutSignServiceImpl implements ProPutSignService {
    @Autowired
    private ProPutSignMapper proPutSignDao;
    @Autowired
    @Lazy
    private PutStorageService putStorageService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private FlowNotifyService flowNotifyService;

    @Override
    public ProPutSign queryById(String id) {
        return this.proPutSignDao.queryById(id);
    }

    @Override
    public List<ProPutSign> queryAllByLimit(int offset, int limit) {
        return this.proPutSignDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ProPutSign insert(ProPutSign proPutSign) {
        this.proPutSignDao.insert(proPutSign);
        return proPutSign;
    }

    @Override
    public ProPutSign update(ProPutSign proPutSign) {
        this.proPutSignDao.update(proPutSign);
        return this.queryById(proPutSign.getId());
    }

    @Override
    public boolean deleteById(String id) {
        return this.proPutSignDao.deleteById(id) > 0;
    }

    @Override
    public ProPutSign queryByParam(ProPutSign proPutSign) {
        List<ProPutSign> proPutSigns = this.proPutSignDao.queryAll(proPutSign);
        if (proPutSigns.size() > 0) {
            ProPutSign result = proPutSigns.get(0);
            return queryById(result.getId());
        } else {
            return null;
        }
    }

    @Override
    public List<ProPutSign> queryListByParam(ProPutSign pps) {
        List<ProPutSign> ppsList = this.proPutSignDao.queryAll(pps);
        for (ProPutSign putSign : ppsList) {
            ProPutSign temp = queryById(putSign.getId());
            putSign.setSignImg(temp.getSignImg());
            putSign.setPutobj(temp.getPutobj());
        }
        return ppsList;
    }

    @Override
    public Map<String, Object> queryByMaterIds(String... putMaterIds) {
        ProPutSign queryParam;
        HashMap<String, Object> queryHistory = new HashMap<>(16);
        int x = 0;
        for (String id : putMaterIds) {
            PutStorage ps = putStorageService.getPutStorageByDetailId(id);
            if (ps != null) {
                if (queryHistory.containsKey(ps.getId())) {
                    continue;
                }
                queryParam = new ProPutSign();
                queryParam.setPutId(ps.getId());
                queryParam = queryByParam(queryParam);
                if (queryParam != null) {
                    queryParam.setPutobj("{\"putSerial\":\"" + ps.getPutSerial() + "\"}");
                    queryHistory.put(ps.getId(), queryParam);
                    x++;
                }
            }
        }
        queryHistory.put("length", x);
        return queryHistory;
    }

    @Override
    public Map<String, Object> updatePutSign(ProPutSign proPutSign, Staff staff) throws Exception {
        ProPutSign sign = queryById(proPutSign.getId());
        Map<String, Object> result = new HashMap<>();
        if (sign == null) {
            throw new Exception("签字单据不存在，请查看最新信息");
        } else if (sign.getType() > 0) {
            throw new Exception("签字单据不存在，请查看最新信息");
        } else if (!StringUtils.equals(sign.getSignStaffId(), staff.getId())) {
            throw new Exception("签字人员与实际签字人员信息不符");
        }

        proPutSign.setSignDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        proPutSign.setSignStaffId(staff.getId());
        if (proPutSign.getType() == 1) {
            PutStorage ps = proPutSign.getPutStorage();
            ps.setId(UUID.randomUUID().toString());
            result.put("state", putStorageService.addPutStorageSelect(ps, staffService.getStaffById(proPutSign.getStaffId())));
            if (Integer.parseInt(result.get("state").toString()) > 0) {
                proPutSign.setPutId(ps.getId());
                result.put("msg", update(proPutSign));
            }
        } else {
            result.put("state", update(proPutSign));
        }
        return result;
    }

    @Override
    public ProPutSign updateOutSign(ProPutSign outSign, Staff staff) throws Exception {
        ProPutSign sign = queryById(outSign.getId());
        if (sign == null) {
            throw new Exception("签字单据不存在，请查看最新信息");
        } else if (sign.getType() > 0) {
            throw new Exception("签字单据不存在，请查看最新信息");
        }
        outSign.setSignDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        outSign.setSignStaffId(staff.getId());
        if (outSign.getType() == 1) {
            MaterOut mo = outMaterService.getOutMaterById(outSign.getProId());
            if (Objects.isNull(mo)) {
                throw new Exception("出库单不存在，请联系仓库");
            }
//            outMaterService.approve(mo,mo.getStaff());
            update(outSign);

            List<Staff> staffSign = new ArrayList<>();
            staffSign.add(mo.getStaff());
            HashMap<String, Object> param = new HashMap<>();
            param.put("title", "材料出库签单完成");
            param.put("mTitle", staff.getName() + "已经签字确认");
            param.put("content", "");
            param.put("url", WebParam.VUETIFY_BASE + "/storage/out/list");
            flowNotifyService.msgNotify(staffSign, param);
        } else {
            update(outSign);
        }
        return outSign;
    }
}