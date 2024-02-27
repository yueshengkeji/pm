package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProDetail;
import com.yuesheng.pm.entity.ProDetailOwe;
import com.yuesheng.pm.mapper.ProDetailOweMapper;
import com.yuesheng.pm.service.ProDetailOweService;
import com.yuesheng.pm.service.ProDetailService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2017/2/16
 */
@Service("proDetailOweService")
public class ProDetailOweServiceImpl implements ProDetailOweService {
    @Autowired
    private ProDetailOweMapper proDetailOweMapper;

    @Autowired
    @Lazy
    private ProDetailService detailService;

    @Override
    public void addOwe(ProDetailOwe owe) {
        if (StringUtils.isBlank(owe.getOweDate())) {
            owe.setOweDate(DateFormat.getDate());
        }
        if (StringUtils.isBlank(owe.getDate())) {
            owe.setDate(DateFormat.getDate());
        }
        if (owe.getOweMoney() == null) {
            owe.setOweMoney(0.00);
        }
        if (StringUtils.isBlank(owe.getId())) {
            owe.setId(UUID.randomUUID().toString());
        }
        proDetailOweMapper.addOwe(owe);

        updateDetail(owe);
    }

    private void updateDetail(ProDetailOwe owe) {
        ProDetail update = new ProDetail();
        update.setId(owe.getMainId());
        if(owe.getType() == 0){
            //更新年初欠票金额
            update.setYearOwe(null);
            update.setYearBillFinance(owe.getOweMoney());
            detailService.updateYearOweAndBill(update);
        }else if(owe.getType() == 1) {
            //更新年初欠款金额
            update.setYearBillFinance(null);
            update.setYearOwe(owe.getOweMoney());
            detailService.updateYearOweAndBill(update);
        }

    }

    @Override
    public List<ProDetailOwe> getOweByDate(Map<String, Object> params) {
        return proDetailOweMapper.getOweByDate(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMoney(ProDetailOwe owe) {
        if (StringUtils.isBlank(owe.getId())) {
            addOwe(owe);
            return 1;
        }
        int row = proDetailOweMapper.updateMoney(owe);
        updateDetail(owe);
//        设置id为原id+history，作为更新日志记录
        owe.setId(owe.getId() + "{history" + System.currentTimeMillis() + "}");
//        日志类型
        owe.setType(3);
//        设置修改时间
        owe.setDate(DateFormat.getDate());
        addOwe(owe);
        return row;
    }

    @Override
    public ProDetailOwe getOweByDate(String date, String mainId, int type) {
        PageHelper.startPage(1,1);
        return proDetailOweMapper.getOweByDate2(date, mainId, type);
    }

    @Override
    public List<ProDetailOwe> getListByDate(String date, String mainId, int type) {
        return proDetailOweMapper.getListByDate(date, mainId, type);
    }

    @Override
    public int deleteByNotIn(String ids, String mainId,int type) {
        return proDetailOweMapper.deleteByNotIn(ids, mainId,type);
    }

    @Override
    public int clearHistory(String year) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("year", StringUtils.isBlank(year) ? DateUtil.getYear() : year);
        List<ProDetail> details = detailService.getDetailByYearV2(params);
        int rows = 0;
        for (int i = 0; i < details.size(); i++) {
            String mainId = details.get(i).getId();
            List<ProDetailOwe> owes = getListByDate(null, mainId, 0);
            List<ProDetailOwe> owes2 = getListByDate(null, mainId, 1);
            String ids = getIds(owes);
            if (StringUtils.isNotBlank(ids)) {
                rows += deleteByNotIn(ids, mainId,1);
            }
            ids = getIds(owes2);
            if (StringUtils.isNotBlank(ids)) {
                rows += deleteByNotIn(ids, mainId,1);
            }
        }
        return rows;
    }

    private String getIds(List<ProDetailOwe> owes) {
        StringBuffer sb = new StringBuffer();
        if (owes.size() > 5) {
            for (int j = 0; j < 5; j++) {
                sb.append("'");
                sb.append(owes.get(j).getId());
                sb.append("',");
            }
            return sb.substring(0, sb.length() - 1);
        } else {
            return null;
        }
    }
}
