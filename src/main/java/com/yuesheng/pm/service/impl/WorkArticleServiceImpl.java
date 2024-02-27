package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.WorkArticleMapper;
import com.yuesheng.pm.service.WorkArticleMaterService;
import com.yuesheng.pm.service.WorkArticleService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by 96339 on 2016/12/29.
 */
@Service("workArticleService")
public class WorkArticleServiceImpl implements WorkArticleService {
    @Autowired
    private WorkArticleMapper workArticleMapper;
    @Autowired
    private WorkArticleMaterService workArticleMaterService;

    @Override
    public WorkArticle getWorkArticleById(String id) {
        return workArticleMapper.getWorkArticleById(id);
    }

    @Override
    public WorkArticle getSimpleById(String id) {
        return workArticleMapper.getSimpleById(id);
    }

    @Override
    public void insert(WorkArticle wa) {
        workArticleMapper.insert(wa);
        List<MaterOutChild> mocList = wa.getMaterOutList();
        if (mocList != null) {
            for (MaterOutChild m : mocList) {
                if (m != null) {
                    if (!verifyDetail(m)) {
                        continue;
                    }
                    m.setId(UUID.randomUUID().toString());
                    m.setMaterOutId(wa.getId());
                    if (StringUtils.isBlank(m.getMaterial().getUnitName())) {
                        m.getMaterial().setUnitName(m.getMaterial().getUnit().getName());
                    }
                    //添加子表明细到数据库
                    workArticleMaterService.insertArticle(m);
                    //更新办公用品库存数量
                    // Material m2 = workArticleMaterService.getMaterById(m.getMaterial().getId());
                    // if (m2 != null) {
                    //     if(m2.getStorageSum() == 0){
                    //         m2.setStorageSum(99.0);
                    //     }
                    //     m2.setStorageSum(m2.getStorageSum() - m.getSum());            //当前的库存 = 原来的库存 - 当前领用的数量
                    //     workArticleMaterService.updateAM(m2);
                    // }
                }
            }
        }
    }

    private boolean verifyDetail(final MaterOutChild m) {
        if (m.getStorageId() == null) {
            m.setStorageId("");
        }
        if (m.getSum() == null) {
            m.setSum(0.0);
        }
        if (m.getTaxPrice() == null) {
            m.setTaxPrice(0.0);
        }
        if (m.getTaxMoney() == null) {
            m.setTaxMoney(m.getSum() * m.getTaxPrice());
        }
        if (m.getRemark() == null) {
            m.setRemark("");
        }
        return true;
    }


    @Override
    public int delete(String id) {
        workArticleMaterService.deleteArticleByMain(id);
        return workArticleMapper.delete(id);
    }

    @Override
    public int update(WorkArticle wa) {
        List<MaterOutChild> mocList = wa.getMaterOutList();
        if (mocList != null) {
            for (MaterOutChild m : mocList) {
                if (m.getId() == null) {
                    //新增
                    if (verifyDetail(m)) {
                        m.setId(UUID.randomUUID().toString());
                        m.setMaterOutId(wa.getId());
                        workArticleMaterService.insertArticle(m);
                    }
                } else {
                    //更新
                    if (verifyDetail(m)) {
                        workArticleMaterService.updateArticle(m);
                    }
                }
            }
        }
        return workArticleMapper.update(wa);
    }

    @Override
    public List<WorkArticle> querys(Map<String, Object> param, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return workArticleMapper.querys(param);
    }

    @Override
    public int querysCount(Map<String, Object> param) {
        return workArticleMapper.querysCount(param);
    }

    @Override
    public WorkArticle insertByMater(List<MaterOutChild> materOutChild, Staff staff) {
        WorkArticle wa = new WorkArticle();
        materOutChild.forEach(item -> {

            if (StringUtils.isBlank(item.getMaterial().getId()) || StringUtils.indexOf(item.getMaterial().getId(), "-1") != -1) {
                // item.getMaterial().setId(UUID.randomUUID().toString());
                item.getMaterial().setPutSum(99999.0);
                item.getMaterial().setOutSum(0.0);
                insertWorkMater(item);
            }

            if (StringUtils.isNotBlank(item.getOutDate())) {
                wa.setOutDate(DateUtil.getDate());
            }
        });
        if (StringUtils.isBlank(wa.getOutDate())) {
            wa.setOutDate(DateUtil.getDate());
        }
        wa.setId(UUID.randomUUID().toString());
        wa.setApproveDate("");
        wa.setApproveStatus((byte) 0);
        wa.setDate(DateUtil.getDatetime());
        wa.setStaff(staff);
        wa.setSeries(staff.getName() + "-办公用品领用");
        wa.setRemark("");
        wa.setMaterOutList(materOutChild);
        wa.setType(0);
        insert(wa);
        return wa;
    }

    @Override
    public void approve(FlowMessage message) {

        WorkArticle wa = getWorkArticleById(message.getFrameId());
        List<MaterOutChild> mocList = workArticleMaterService.getArticleMaterByArticleId(message.getFrameId());

        mocList.forEach(item -> {
            Material m = workArticleMaterService.getMaterById(item.getMaterial().getId());
            if (m.getPutSum() < m.getOutSum()) {
                //入库数小于出库数量，提醒补充库存
                LogManager.getLogger("mylog").error("办公用品<" + m.getName() + ">，审批时" +
                        "入库数小于出库数量，提醒补充库存");
            } else {
                //更新领用库存总数
                m.setOutSum(m.getOutSum() + item.getSum());
                workArticleMaterService.updateOutSum(m);
            }

        });

        if(!Objects.isNull(wa)){
            wa.setApproveStaff(message.getLastApproveUser());
            wa.setApproveDate(DateUtil.getDate());
            wa.setApproveStatus((byte) 1);
            workArticleMapper.approve(wa);
        }
    }

    private int insertWorkMater(MaterOutChild item) {

        Material material = item.getMaterial();

        workArticleMaterService.insert(material);

        return 1;
    }
}
