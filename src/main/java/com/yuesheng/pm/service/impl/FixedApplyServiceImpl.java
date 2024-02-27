package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.FixedApplyMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * (ProFixedApply)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-11-29 09:41:20
 */
@Service("proFixedApplyService")
public class FixedApplyServiceImpl implements FixedApplyService {

    @Autowired
    private FixedApplyMapper fixedApplyMapper;

    @Autowired
    private FixedAssetsService assetsService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ApplyMaterialService applyMaterialService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private ProcurementMaterService materService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public FixedApply queryById(String id) {
        return this.fixedApplyMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<FixedApply> queryAllByLimit(int offset, int limit) {
        return this.fixedApplyMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param fixedApply 实例对象
     * @return 实例对象
     */
    @Override
    public FixedApply insert(FixedApply fixedApply) {
        fixedApply.setId(UUID.randomUUID().toString());
        fixedApply.setState((byte) 0);
        this.fixedApplyMapper.insert(fixedApply);
        insertAssets(fixedApply);
        return fixedApply;
    }

    private void insertAssets(FixedApply fixedApply) {
        List<FixedAssets> assets = fixedApply.getAssetsList();
        if (assets != null) {
            Folder folder = new Folder();
            folder.setName("固定资产采购申请");
            for (FixedAssets asset : assets) {
                if (StringUtils.isBlank(asset.getModel())) {
                    asset.setModel("");
                }
                asset.setFixedId(fixedApply.getId());
                asset.setFolderObj(folder);
                asset.setSection(fixedApply.getSection().getName());
                asset.setPerson(fixedApply.getStaff().getName());
                asset.setState((byte) 0);
                asset.setDate(DateUtil.getDatetime());
                asset.setSeries("");
                asset.setProDate("");
                assetsService.insert(asset,fixedApply.getSection());
            }
        }
    }

    /**
     * 修改数据
     *
     * @param fixedApply 实例对象
     * @return 实例对象
     */
    @Override
    public FixedApply update(FixedApply fixedApply) {
        this.fixedApplyMapper.update(fixedApply);
        if (fixedApply.getAssetsList() != null) {
            //删除之前的资产明细
            assetsService.deleteByFixed(fixedApply.getId());
            //添加新的资产明细
            insertAssets(fixedApply);
        }
        return this.queryById(fixedApply.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        assetsService.deleteByFixed(id);
        return this.fixedApplyMapper.deleteById(id) > 0;
    }

    /**
     * 根据条件查询数据
     */
    @Override
    public List<FixedApply> queryByParam(FixedApply apply) {
        return this.fixedApplyMapper.queryAll(apply);
    }

    @Override
    public void approve(String id, String approveStaffCoding) {
        FixedApply fixed = this.queryById(id);
        if (!Objects.isNull(fixed)) {
            fixed.setState((byte) 1);
            update(fixed);

            Apply apply = new Apply();
            apply.setAddress("");
            apply.setId(fixed.getId());
            apply.setStaff(fixed.getStaff());
            apply.setSerialNumber(fixed.getTitle());
            apply.setPm03416(DateUtil.getDatetime());
            Project project = new Project();
            SystemConfig config = systemConfigService.queryByCoding(Constant.FIXED_PROJECT_CODING);
            if (!Objects.isNull(config)) {
                project.setId(config.getValue());
            } else {
                project.setId("");
            }
            apply.setProject(project);
            apply.setRemark(fixed.getReason());

            applyService.addApply(apply);
            //        添加申请单材料
            HashMap<String, Object> param = new HashMap<>();
            param.put("fixedId", fixed.getId());
            List<FixedAssets> assets = assetsService.queryByParam(param);

            int x = 1;
            for (FixedAssets material : assets) {
                if (assets != null) {
                    material.setState((byte) 1);
                    assetsService.update(material);

                    ApplyMaterial am = new ApplyMaterial();
                    am.setApplyid(apply.getId());
                    am.setMajor(material.getId());
                    am.setSerialNumber(x);
                    am.setName(material.getName());
                    am.setModel(material.getModel());
                    Unit unit = new Unit();
                    unit.setName("无");
                    am.setUnit(unit);
                    am.setBrand("无");
                    am.setSum(material.getHaveSum());
                    am.setPlanPrice(0.0);
                    am.setRemark(material.getRemark());
                    am.setCnfParam("");
                    am.setySum(0.0);
                    am.setPlanRowId(material.getId());
                    am.setTax(0.0);
                    am.setApplyDate(DateUtil.getDate());
                    am.setTaskId("");
                    Material m = materialService.insert(fixed.getStaff(), am);
                    if (StringUtils.isNotBlank(m.getId())) {
                        am.setId(m.getId());
                        applyMaterialService.addMater(am);
                        x++;
                    }
                }
            }

            //审核申请单
            applyService.approve(apply.getId(), 1, approveStaffCoding, DateUtil.getDate());
        }
    }

    @Override
    public void checkProcurementFixed() {
        String startDate = DateUtil.format(DateUtil.rollDay(DateUtil.getNowDate(), -1), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        String endDate = DateUtil.getDate();
        List<Procurement> procurements = procurementService.getProcurementByDate(startDate, endDate);
        procurements.forEach(item -> {
            List<ProMaterial> pms = materService.getProMatersByProId(item.getId());
            pms.forEach(item2 -> {
                if (!Objects.isNull(item2)) {
                    FixedAssets fa = assetsService.queryById(item2.getMajor());
                    if (!Objects.isNull(fa)) {
                        //更新固定资产采购日期
                        fa.setProDate(item.getPmDate());
                        assetsService.update(fa);
                    }
                }
            });
        });
    }

    @Override
    public void approve(FlowMessage msg){
        approve(msg.getFrameId(),msg.getLastApproveUser().getId());
    }
}