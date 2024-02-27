package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProDetailMoney;
import com.yuesheng.pm.mapper.ProDetailMoneyMapper;
import com.yuesheng.pm.service.ProDetailMoneyService;
import com.yuesheng.pm.service.ProDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by 96339 on 2017/2/16.
 */
@Service("proDetailMoneyService")
public class ProDetailMoneyServiceImpl implements ProDetailMoneyService {
    @Autowired
    private ProDetailMoneyMapper proDetailMoneyMapper;
    @Autowired
    @Lazy
    private ProDetailService detailService;

    @Override
    public void addMoney(ProDetailMoney money) {
        proDetailMoneyMapper.addMoney(money);
        updateDetailSubject(money);
    }

    @Override
    public void updateDetailSubject(ProDetailMoney detailMoney) {
        String subSeries = "";
        List<ProDetailMoney> detailSubject = getMoneyByMainId(detailMoney.getMainId());
        if (!Objects.isNull(detailSubject)) {
            StringBuffer sb = new StringBuffer();
            detailSubject.forEach(item -> {
                sb.append(item.getSeries());
                sb.append("+");
            });
            if (sb.length() > 0) {
                subSeries = sb.substring(0, sb.length() - 1);
            }
        }
        detailService.updateSubjects(detailMoney.getMainId(),subSeries);
    }

    @Override
    public int update(ProDetailMoney money) {
        int row = proDetailMoneyMapper.update(money);
        updateDetailSubject(money);
        return row;
    }

    @Override
    public List<ProDetailMoney> getMoneyByMainId(String mainId) {
        return proDetailMoneyMapper.getMoneyByMainId(mainId);
    }

    @Override
    public void deleteByMain(String mainId) {
        proDetailMoneyMapper.deleteByMain(mainId);
        ProDetailMoney money = new ProDetailMoney();
        money.setMainId(mainId);
        updateDetailSubject(money);
    }
}
