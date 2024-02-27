package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.PlanMaterial;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.PlanMapper;
import com.yuesheng.pm.model.DateCount;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.PlanMaterialService;
import com.yuesheng.pm.service.PlanService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 96339 on 2016/12/19.
 */
@Service("planService")
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private PlanMaterialService planMaterialService;
    @Autowired
    private FlowMessageService flowMessageService;

    @Override
    public Plan getPlanById(String id) {
        return planMapper.getPlanById(id);
    }

    @Override
    public List<Plan> getPlansByProject(String projectId) {
        return planMapper.getPlansByProject(projectId);
    }

    @Override
    public List<Plan> searchPlan(String name) {
        return planMapper.searchPlan(name);
    }

    @Override
    public List<Plan> getPlans(Map<String, Object> params, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,false);
        return planMapper.getPlans(params);
    }

    @Override
    public int getPlansCount(Map<String, Object> params) {
        return planMapper.getPlansCount(params);
    }

    @Override
    @Transactional
    public void insert(final Plan plan) {
        plan.setDate(DateFormat.getDate());
        plan.setId(UUID.randomUUID().toString());
        if (plan.getBillsCode() == null) {
            plan.setBillsCode(DateFormat.getDateForNumber() + "" + System.currentTimeMillis());
        }
        if (StringUtils.isBlank(plan.getPlanDate())) {
            plan.setPlanDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        }
        if (StringUtils.isBlank(plan.getRemark())) {
            plan.setRemark("");
        }

        //默认任务为1，无任务
        plan.setTask("1");
        //审核日期为""
        plan.setApproveDate("");
        //未审核
        plan.setAppMark((byte) 0);
        if (plan.getMaterialList() != null) {
            for (PlanMaterial material : plan.getMaterialList()) {
//                材料单价是否为空
                if (materialPriceIsNull(material)) {
                    plan.setId("-1");
                    return;
                }
            }
        }
        planMapper.insert(plan);
        //计划单材料添加
        for (PlanMaterial material : plan.getMaterialList()) {
            insertPlanMater(plan, material);
        }

    }

    private void insertPlanMater(Plan plan, PlanMaterial material) {
        if (material != null) {
            if (material.getMaterial() == null || StringUtils.isBlank(material.getMaterial().getId())) {
                System.out.println("材料id为null ：" + material.getTaskId());
            }
            material.setProjectId(plan.getProject().getId());
            material.setPlanId(plan.getId());
            //任务id为1，无任务
            if (StringUtils.isBlank(material.getTaskId())) {
                material.setTaskId("1");
            }
            if (StringUtils.isBlank(material.getPlanDate())) {
                material.setPlanDate(plan.getPlanDate());
            }
            if (StringUtils.isBlank(material.getCnfStr())) {
                material.setCnfStr("");
            }
            material.setInDate(DateFormat.getDate());
            material.setApproveCode("");
            material.setApproveDate("");
            material.setId(UUID.randomUUID().toString());
            material.setTaxMoney(material.getMoney());
            material.setPrice(material.getPlanPrice());
            planMaterialService.insert(material);
        }
    }

    @Override
    public void delete(String id) {
        planMaterialService.deleteAll(id);
        planMapper.delete(id);
    }

    @Override
    public int update(Plan plan) {
        int rowState = planMapper.update(plan);
        List<PlanMaterial> planMaterials = plan.getMaterialList();
        if (planMaterials != null) {
            PlanMaterial material = null;
            for (int i = 0; i < planMaterials.size(); i++) {
                material = planMaterials.get(i);
                material.setProjectId(plan.getProject().getId());
                if (!materialExist(material)) {    //添加新材料
                    insertPlanMater(plan, material);
                    /*if (material.getPlanPrice() == null || material.getPlanPrice() <= 0) {
                        plan.setId("-1");
                        continue;
                    } else {
                        material.setId(UUID.randomUUID().toString());
                        material.setPlanId(plan.getId());
                        if (StringUtils.isBlank(material.getTaskId())) {
                            material.setTaskId("1");
                        }
                        material.setApproveCode("");
                        material.setApproveDate("");
                        material.setPrice(material.getPlanPrice());
                        material.setMoney(material.getTaxMoney());
                        planMaterialService.insert(material);
                    }*/
                } else {     //修改
                    if (material.getPlanPrice() == null || material.getPlanPrice() <= 0) {
                        plan.setId("-1");
                        continue;
                    } else {
                        if (StringUtils.isBlank(material.getTaskId())) {
                            material.setTaskId("1");
                        }
                        planMaterialService.update(material);
                    }
                }
            }
        }
        return rowState;
    }

    private boolean materialExist(PlanMaterial material) {
        return planMaterialService.getMaterialById(material.getId()) != null;
    }

    @Override
    public int deleteByProject(String projectId) {
        return planMapper.deleteByProject(projectId);
    }

    @Override
    public Map<String, String> delete(String id, Staff staff) {
        Map<String, String> result = new HashMap(16);
        Plan plan = getPlanById(id);
        if (plan == null) {
            result.put("type", "fail");
            result.put("msg", "单据不存在");
        } else if (!staff.getCoding().equals(plan.getStaff().getCoding())) {
            result.put("type", "fail");
            result.put("msg", "单据不属于您，无法删除");
        } else if (plan.getAppMark() == 1) {
            result.put("type", "fail");
            result.put("msg", "单据已审核，不能删除");
        } else {
            try {
                flowMessageService.deleteMessage(id);
                delete(id);
                result.put("type", "ok");
                result.put("msg", "删除成功");
            } catch (Exception e) {
                result.put("type", "fail");
                result.put("msg", e.getMessage());
            }
        }
        result.put("id", id);
        return result;
    }

    @Override
    public Double getPlanMoneyByProject(String projectId) {
        return planMaterialService.getPlanMoneyByProject(projectId);
    }

    @Override
    public int approve(FlowMessage msg){
        return approve(msg.getFrameId(),msg.getLastApproveUser());
    }

    @Override
    public int approve(String id, Staff staff) {
        Plan plan = getPlanById(id);
        if (!Objects.isNull(plan)) {
            plan.setAppMark((byte) 1);
            plan.setApproveDate(DateUtil.getDate());
            plan.setApprove(staff);

            planMapper.approve(plan);
            planMaterialService.approve(plan);

            List<PlanMaterial> planMaterials = planMaterialService.getMaterialsByPlan(id);
            approvePlanMaterials(planMaterials);
        }
        return 1;
    }

    private void approvePlanMaterials(List<PlanMaterial> planMaterials) {
        for (PlanMaterial item : planMaterials) {
            String key = item.getProjectId() + item.getTaskId() + item.getMaterial().getId() + item.getCnfStr();
            PlanMaterial pm = planMaterialService.getApproveMaterByKey(key);
            if (Objects.isNull(pm)) {
                //不存在，添加新材料
                planMaterialService.insertOk(item);
            } else {
                //存在，更新信息
                DateCount dc = planMaterialService.getMaterCount(key);
                Double avgPrice = 0.0;
                if (!Objects.isNull(dc)) {
                    if (dc.getDoubleCount() > 0 && dc.getDoubleMoney() > 0) {
                        avgPrice = dc.getDoubleCount() / dc.getDoubleMoney();
                    } else if (dc.getDoubleCount() == 0) {
                        //计划数量为0，删除材料信息
                        planMaterialService.deleteOk(key);
                        break;
                    }
                    //更新材料信息
                    pm.setPrice(avgPrice);
                    pm.setMoney(dc.getDoubleMoney());
                    pm.setPlanSum(dc.getDoubleCount());
                    pm.setId(key);
                    planMaterialService.updateOk(pm);
                } else {
                    //不存在，添加新材料
                    planMaterialService.insertOk(item);
                }

            }
        }
    }

    private boolean materialPriceIsNull(PlanMaterial material) {
        return material != null && (material.getPlanPrice() == null || material.getPlanPrice() <= 0);
    }
}
