package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProReportMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 采购订单报表记录(ProReport)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-06-24 09:44:26
 */
@Service("proReportService")
public class ProReportServiceImpl implements ProReportService {
    @Autowired
    private ProReportMapper proReportMapper;
    @Autowired
    @Lazy
    private ProcurementService procurementService;
    @Autowired
    private ProcurementMaterService materService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    @Lazy
    private PaymentDetailService paymentDetailService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private PutStorageMaterialService storageMaterialService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProReport queryById(Long id) {
        return this.proReportMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProReport> queryAllByLimit(int offset, int limit) {
        return this.proReportMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proReport 实例对象
     * @return 实例对象
     */
    @Override
    public ProReport insert(ProReport proReport) {
        this.proReportMapper.insert(proReport);
        return proReport;
    }

    /**
     * 修改数据
     *
     * @param proReport 实例对象
     * @return 实例对象
     */
    @Override
    public ProReport update(ProReport proReport) {
        this.proReportMapper.update(proReport);
        return this.queryById(proReport.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.proReportMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProReport> queryAll(Map<String, Object> query) {
        return proReportMapper.queryAll(query);
    }

    @Override
    public int deleteByProId(String proId) {
        return proReportMapper.deleteByProId(proId);
    }

    @Override
    public int queryAllCount(Map<String, Object> param) {
        return proReportMapper.queryAllCount(param);
    }

    @Override
    public void genReport(String proId) {
        Procurement p = procurementService.getProcurementById(proId);
        genByProcurement(p);
    }

    @Override
    public void genByProcurement(Procurement p) {
        if (!Objects.isNull(p)) {
            List<ProMaterial> materials = p.getMaterial();
            if (Objects.isNull(materials) || materials.isEmpty()) {
                materials = materService.getProMatersByProId(p.getId());
            }
            Contract contract = p.getContract();
            List<PaymentDetail> paymentDetails = null;
            if (!Objects.isNull(contract)) {
                //查找付款记录
                paymentDetails = paymentDetailService.getDetailByContract(contract.getId());
            }
            for (ProMaterial mp : materials) {
                try {
                    mp.setApplyPrice(applyMaterialService.getMaterById(mp.getMajor()).getPlanPrice());
                } catch (NullPointerException e) {
                    mp.setApplyPrice(0.0);
                }
                ProReport old = queryByMaterialId(mp.getId());
                if (Objects.isNull(old)) {
                    insertByProMaterial(mp, p, paymentDetails);
                } else {
                    ProReport update = copyValue(mp, p, paymentDetails);
                    update.setId(old.getId());
                    update(update);
                }
            }
        }
    }

    @Override
    public Integer deleteByDate(String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("isNull", "yes");
        List<ProReport> reports = queryByProGroup(params);
        reports.forEach(report -> {
            if(report != null) {
                deleteByProId(report.getProId());
            }
        });
        return reports.size();
    }

    @Override
    public List<ProReport> queryByProGroup(Map<String, Object> params) {
        return proReportMapper.queryByProGroup(params);
    }

    @Override
    public void syncProcurementData() {
        String startDate = DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(), -1));
        String endDate = DateUtil.getDate();
        deleteByDate(startDate, endDate);
        List<Procurement> list = procurementService.getProcurementByDate(startDate, endDate);
        if (!list.isEmpty()) {
            list.forEach(p -> {
                genByProcurement(p);
            });
        }
    }

    private ProReport queryByMaterialId(String proMaterRowId) {
        PageHelper.startPage(1,1);
        return proReportMapper.queryByMaterId(proMaterRowId);
    }

    private void insertByProMaterial(ProMaterial mp, Procurement p, List<PaymentDetail> paymentDetails) {
        ProReport report = copyValue(mp, p, paymentDetails);
        insert(report);
    }

    private ProReport copyValue(ProMaterial mp, Procurement p, List<PaymentDetail> paymentDetails) {
        List<StorageMaterial> putMs = storageMaterialService.getMaterByProMater(mp.getId());
        ProReport report = new ProReport();
        BeanUtils.copyProperties(mp, report);
        if(StringUtils.isBlank(report.getProId())){
            report.setProId(p.getId());
        }
        if (putMs.isEmpty()) {
            report.setDhRemark("未入库");
        } else {
            Double putSum = 0.0;
            for (int i = 0; i < putMs.size(); i++) {
                putSum += putMs.get(i).getPutSum();
            }
            if (mp.getSum() < putSum) {
                report.setDhRemark("采购:" + mp.getSum() + ",实际入库:" + putSum);
            } else if (mp.getSum().equals(putSum)) {
                report.setDhRemark("完全入库");
            } else {
                report.setDhRemark("已入库:" + putSum);
            }
        }
        report.setCompanyName(p.getCompany().getName());
        report.setCompanyId(p.getCompany().getId());
        report.setMaterialId(mp.getMaterial().getId());
        report.setStaffId(p.getStaff().getId());
        report.setStaffName(p.getStaff().getName());
        report.setProMoney(mp.getMoneyTax());
        report.setProPrice(mp.getPriceTax());
        report.setVoucherDate(p.getVoucherDate());
        report.setProMaterialId(mp.getId());
        City city = p.getCity();
        if (city != null) {
            String name = city.getName();
            try {
                report.setAcceptPersonName(name.substring(name.length() - 15));
            } catch (Exception e) {
                report.setAcceptPersonName("格式不正确");
            }
        } else {
            report.setAcceptPersonName("-");
        }
        try {
            report.setProjectId(mp.getProject().getId());
            report.setProjectName(mp.getProject().getName());
            ApplyMaterial am = applyMaterialService.getMaterById(mp.getMajor());
            Apply apply = null;
            if (am != null) {
                apply = applyService.getApplyById(am.getApplyid());
            }
            if (apply != null) {
                report.setProjectPersonName(apply.getStaff().getName());
            } else {
                report.setProjectPersonName("");
            }
        } catch (Exception e) {
            report.setProjectId("-");
            report.setProjectName("-");
            report.setProjectPersonName("-");
        }
        Contract contract = p.getContract();
        if (!Objects.isNull(contract)) {
            report.setContractRemark("有合同");
        } else {
            report.setContractRemark("");
        }
        if (!Objects.isNull(paymentDetails) && paymentDetails.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            paymentDetails.forEach(paymentDetail -> {
                if (StringUtils.isNotBlank(paymentDetail.getApproveDate())) {
                    sb.append(paymentDetail.getApproveDate());
                    sb.append("-");
                    sb.append(paymentDetail.getApplyMoney());
                    sb.append(";");
                }
            });
            if (sb.length() > 1) {
                report.setPayDate(sb.substring(0, sb.length() - 1));
            }
        }
        return report;
    }
}