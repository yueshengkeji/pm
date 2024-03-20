package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProBackMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * (ProBack)表服务实现类
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
@Service("proBackService")
public class ProBackServiceImpl implements ProBackService {
    @Autowired
    private ProBackMapper proBackMapper;
    @Autowired
    private ProBackMasterService masterService;
    @Autowired
    private ProPutDetailService putDetailService;
    @Autowired
    private ProDetailService detailService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    @Lazy
    private ProcurementMaterService procurementMaterService;
    @Autowired
    @Lazy
    private ProcurementService procurementService;
    @Autowired
    @Lazy
    private PutStorageService putStorageService;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProBack queryById(String id) {
        return this.proBackMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param proBack 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ProBack> queryByPage(ProBack proBack) {
        return this.proBackMapper.queryAllByLimit(proBack);
    }

    /**
     * 新增数据
     *
     * @param proBack 实例对象
     * @return 实例对象
     */
    @Override
    public ProBack insert(ProBack proBack) {
        proBack.setDatetime(DateUtil.getDatetime());
        proBack.setId(Constant.uuid());
        this.proBackMapper.insert(proBack);
        if(proBack.getState() == 1){
            setBackMater(proBack);
        }
        return proBack;
    }

    private void setBackMater(ProBack proBack) {
        List<ProBackMaster> backMaters = proBack.getMasterList();
        HashMap<String, ProPutForDetail> cache = new HashMap<>();

        backMaters.forEach(item -> {
            if (StringUtils.isBlank(item.getId())) {
                item.setProBackId(proBack.getId());
                item.setId(Constant.uuid());
                masterService.insert(item);
            } else {
                masterService.update(item);
            }

            ProMaterial pm = procurementMaterService.getMatersById(item.getProRowId());
            if (!Objects.isNull(pm)) {
                updateProSum(pm);

                if (BooleanUtils.isTrue(proBack.getToApply())) {
                    //异步更新申请单已采购数量和回退采购状态
                    executorService.execute(() -> {
                        if (StringUtils.isNotBlank(pm.getMajor())) {
                            ApplyMaterial am = applyMaterialService.getMaterById(pm.getMajor());
                            if (!Objects.isNull(am)) {
                                am.setySum(am.getySum() - item.getSum());
                                applyMaterialService.updateProSum(am);
                                applyService.checkStatus(am.getApplyid(), true);
                            }
                        }
                    });
                }
            }

            //对账单添加采购情况：负数金额
            Project p = item.getProject();
            if (!Objects.isNull(p)) {
                ProDetail pd = detailService.getDetailByCompany(proBack.getCompany().getId(), DateUtil.getYear(), detailService.getCompanyType(p.getFolderId()));
                if (!Objects.isNull(pd)) {
                    ProPutForDetail detail = cache.get(item.getProject().getId());
                    if (Objects.isNull(detail)) {
                        detail = new ProPutForDetail();
                        detail.setProMoney(0.0);
                        detail.setProDate(DateUtil.getDate());
                        detail.setProject(item.getProject());
                        detail.setMainId(pd.getId());
                        detail.setProId(proBack.getId());
                    }
                    detail.setProMoney(detail.getProMoney() + (-item.getMoney()));
                    detail.setStaff(proBack.getStaff());
                    cache.put(p.getId(), detail);
                }
            } else {
                //直接添加
                List<ProDetail> pds = detailService.getDetailByCompany(proBack.getCompany().getId(), DateUtil.getYear());
                if (!pds.isEmpty()) {
                    ProPutForDetail detail = cache.get("null");
                    if (Objects.isNull(detail)) {
                        detail = new ProPutForDetail();
                        detail.setProMoney(0.0);
                        detail.setProDate(DateUtil.getDate());
                        detail.setMainId(pds.get(0).getId());
                        detail.setProId(proBack.getId());
                    }
                    detail.setProMoney(detail.getProMoney() + (-item.getMoney()));
                    cache.put("null", detail);
                }
            }
        });

        cache.forEach((key, item) -> putDetailService.addDetail(item));

        updateProPutState(proBack);
    }

    private void updateProPutState(ProBack proBack) {
        Procurement p = procurementService.getProcurementById(proBack.getProId());
        if (!Objects.isNull(p)) {
            p.setMaterial(procurementMaterService.getProMatersByProId(proBack.getProId()));
            putStorageService.updateProState(p.getMaterial(), p, 1, 0);
        }
    }

    private void updateProSum(ProMaterial pm) {
        //更新订单材料行退库数量
        Double backSum = masterService.queryBackSum(pm.getId());
        if (Objects.isNull(backSum)) {
            backSum = 0.0;
        }
        pm.setBackSum(backSum);
        procurementMaterService.updateBackSum(pm);
    }

    private void backMater(ProBack back, boolean isDelete) {
        List<ProBackMaster> backMasterList = back.getMasterList();
        backMasterList.forEach(item -> {
            if (isDelete) {
                masterService.deleteById(item.getId());
            }
            ProMaterial pm = procurementMaterService.getMatersById(item.getProRowId());
            if (!Objects.isNull(pm)) {
                updateProSum(pm);
                if (BooleanUtils.isTrue(back.getToApply())) {
                    executorService.execute(() -> {
                        if (StringUtils.isNotBlank(pm.getMajor())) {
                            ApplyMaterial am = applyMaterialService.getMaterById(pm.getMajor());
                            if (!Objects.isNull(am)) {
                                am.setySum(am.getySum() + item.getSum());
                                applyMaterialService.updateProSum(am);
                                applyService.checkStatus(am.getApplyid(), false);
                            }
                        }
                    });
                }
            }

            putDetailService.deleteByProId(item.getId());
        });

        updateProPutState(back);
    }

    /**
     * 修改数据
     *
     * @param proBack 实例对象
     * @return 实例对象
     */
    @Override
    public ProBack update(ProBack proBack) {
        backMater(proBack, false);
        this.proBackMapper.update(proBack);
        if(proBack.getState() == 1){
            setBackMater(proBack);
        }
        return this.queryById(proBack.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        ProBack back = queryById(id);
        if (!Objects.isNull(back)) {
            List<ProBackMaster> masterList = masterService.queryByBack(id);
            back.setMasterList(masterList);
            backMater(back, true);
            return this.proBackMapper.deleteById(id) > 0;
        } else {
            return false;
        }
    }
}
