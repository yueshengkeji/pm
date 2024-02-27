package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.MaterialMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * @author XiaoSong
 * @date 2016-08-06 材料服务实现
 */
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ProcurementMaterService procurementMaterService;
    @Autowired
    @Lazy
    private PutStorageMaterialService putStorageMaterialService;
    @Autowired
    private OutMaterChildService outMaterChildService;
    @Autowired
    private BackMaterChildService backMaterChildService;
    @Autowired
    private CheckMaterChildService checkMaterChildService;
    @Autowired
    private PlanMaterialService planMaterialService;
    @Autowired
    private PlanService planService;
    @Autowired
    private UnitService unitService;
    private int insertCount = 0;

    @Override
    public Material getMaterialByid(String id) {
        return materialMapper.getMaterialByid(id);
    }

    @Override
    public Integer updateMaterSum(Material material) {
        return materialMapper.updateMaterSum(material);
    }

    @Override
    public Integer updateMaterSums(List<Material> materials) {
        return materialMapper.updateMaterSums(materials);
    }

    @Override
    public Integer updateMaterSum_(Material material) {
        return materialMapper.updateMaterSum_(material);
    }

    @Override
    public List<Material> getMaterialByType(Map<String, Object> params) {
        return materialMapper.getMaterialByType(params);
    }

    @Override
    public Integer getMaterByTypeCount(Map<String, Object> params) {
        return materialMapper.getMaterialByTypeCount(params);
    }

    @Override
    public Material isExist(Material material) {
        Unit u = material.getUnit();
        PageHelper.startPage(1,1);
        Material newMater = materialMapper.isExist(material);
        //判断单位是否相同，相同则返回
        if (u != null && newMater != null) {
            if (u.getId().equals(newMater.getUnit().getId())) {
                return newMater;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void addMater(Material material) throws SQLException {
        try {
            materialMapper.addMater(material);
            unitService.addUnitToMater(material);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Folder folderExist(String id) {
        PageHelper.startPage(1,1);
        return materialMapper.folderExist(id);
    }

    @Override
    public void addFolder(Folder folder) {
        materialMapper.addFolder(folder);
    }

    @Override
    public String getCode() {

        String fix = DateUtil.format(new Date(), DateUtil.PATTER_IMAGE_DIRECTORY);
        String code = AESEncrypt.getFixLenthString(5);

        code = fix + code;
        Material m = getMaterialByid(code);
        if (Objects.isNull(m)) {
            return code;
        } else {
            return getCode();
        }
    }

    @Override
    public void updateMaterMoney(String id, Double price, Double money) {
        materialMapper.updateMaterMoney(id, price, money);
    }

    @Override
    public List<Folder> seekMaterialFolder(String text) {
        return materialMapper.seekMaterialFolder(text);
    }

    @Override
    public Folder getFolderById(String id) {
        return materialMapper.getFolderById(id);
    }

    @Override
    public int updateFolder(Folder folder) {
        return materialMapper.updateFolder(folder);
    }

    @Override
    public int updateMaterial(Material material) {
        if (material == null) {
            return -1;
        }
        if (material.getUnit() == null) {
            return -1;
        }
        return materialMapper.updateMaterial(material);
    }

    @Override
    public Map<String, String> deleteMaterial(String id) {
        Material m = getMaterialByid(id);
        Map<String, String> result = new HashMap<>(16);
        result.put("state", "-1");
        if (m == null) {
            result.put("msg", "不存在该材料");
            return result;
        }
        Material temp = planMaterialService.getMaterByMaterId(id);
        if (temp != null) {       //判断计划单
            result.put("msg", "材料已被计划单引用");
            return result;
        }
        temp = applyMaterialService.getMaterialByMaterId(id);
        if (temp != null) {       //判断申请单
            result.put("msg", "材料已被申请单引用");
            return result;
        }
        temp = procurementMaterService.getMaterByMaterId(id);
        if (temp != null) {       //判断采购单
            result.put("msg", "材料已被采购单引用");
            return result;
        }
        temp = putStorageMaterialService.getMaterialByMaterId(id);
        if (temp != null) {   //判断入库单
            result.put("msg", "材料已被入库单引用");
            return result;
        }
        temp = outMaterChildService.getMaterByMaterId(id);
        if (temp != null) {       //判断出库单
            result.put("msg", "材料已被出库单引用");
            return result;
        }
        temp = backMaterChildService.getMaterByMaterId(id);
        if (temp != null) {       //判断退料单
            result.put("msg", "材料已被退料单引用");
            return result;
        }
        temp = checkMaterChildService.getMaterByMaterId(id);
        if (temp != null) {       //判断盘点单
            result.put("msg", "材料已被盘点单引用");
            return result;
        }
        result.put("state", materialMapper.deleteMaterial(id) + "");
        result.put("msg", "ok");
        result.put("id", id);
        return result;
    }

    @Override
    public List<Folder> getFolderByParent(String parent) {
        return materialMapper.getFolderByParent(parent);
    }

    @Override
    public Map<String, Object> getMaterUseMsg(String id, String[] typeArray) {
        Map<String, Object> result = new HashMap<>(16);
        Map<String, BaseEntity> idMap = new HashMap<>(16);
        if (typeArray != null) {
            for (String s : typeArray) {
                switch (s) {
                    case "1":           //计划单信息
                        List<PlanMaterial> pm = planMaterialService.getMaterialByMaterId(id);
                        Plan plan = null;
                        for (PlanMaterial p : pm) {
                            plan = (Plan) idMap.get(p.getPlanId());
                            if (plan == null) {
                                plan = planService.getPlanById(p.getPlanId());
                            }
                            p.setPlan(plan);
                        }
                        result.put("plan", pm);
                        break;
                    case "2":       //申请单信息
                        break;
                    case "3":       //采购单信息
                        break;
                    case "4":       //入库单信息，先实现
                        List<StorageMaterial> psList = putStorageMaterialService.getMaterialMsgByMaterId(id);
                        result.put("put", psList);
                        break;
                    case "5":       //出库单信息，先实现
                        List<MaterOutChild> mocList = outMaterChildService.getMaterialUseByMaterId(id);
                        result.put("out", mocList);
                        break;
                    case "6":       //退料单信息，先实现
                        List<BackMaterChild> bmcList = backMaterChildService.getMaterUseByMaterId(id);
                        result.put("back", bmcList);
                        break;
                    case "7":       //盘点单信息，先实现
                        List<CheckMaterChild> cmcList = checkMaterChildService.getMaterUseByMaterId(id);
                        result.put("check", cmcList);
                        break;
                    default:
                        break;
                }
            }

        }
        return result;
    }

    @Override
    public Folder getFolderByMaterId(String materId) {
        return materialMapper.getFolderByMaterId(materId);
    }

    @Override
    public Material[] insert(Material[] material, Staff staff) {
        Material mater = null;
        for (int i = 0; i < material.length; i++) {
            mater = material[i];
            insert(staff, mater);
        }
        return material;
    }

    @Override
    public Material insert(Staff staff, final Material mater) {
        if (mater != null) {
//                去除单位名称所有空格
            mater.setName(StringUtils.trimToEmpty(mater.getName()));
            //判断单位是否存在,不存在添加
            Unit unit = mater.getUnit();
            if (unit == null) {
                mater.setId("-1");
                mater.setProperty("{\"state\":\"-1\",\"msg\":\"" + mater.getName() + "指定的材料单位不存在\"}");
                return mater;
            }
            if (StringUtils.isBlank(unit.getName())) {
                unit = null;
            } else if (StringUtils.isBlank(unit.getId())) {
                unit.setName(StringUtils.trimToEmpty(unit.getName()));
                unit = unitService.isExist(unit.getName());
                if (unit == null) {
                    unit = mater.getUnit();
                    unitService.addUnit(unit);
                } else {
                    mater.setUnit(unit);
                }
            } else {
                unit = unitService.getUnit(unit.getId());
                //单位不存在
                if (unit == null) {
                    unit = mater.getUnit();
                    unitService.addUnit(unit);
                } else {
                    mater.setUnit(unit);
                }
            }
            if (unit == null) {
                mater.setProperty("{\"state\":\"-1\",\"msg\":\"" + mater.getName() + "指定的材料单位有误\"}");
                return mater;
            }
            //mater 指向数据库中的对象，判断材料是否存在，不存在添加
            if (StringUtils.isBlank(mater.getModel())) {
                mater.setModel("");
            }
            mater.setUnit(unit);
            Material existMter = isExist(mater);
            if (existMter == null) {
                mater.setUnit(unit);
                if (mater.getFolderObj() != null && mater.getFolderObj().getId() != null && !"".equals(mater.getFolderObj().getId())) {
                    //已经指定材料目录
                    mater.setFolder(mater.getFolderObj().getId());
                } else {
                    //设置材料目录为系统自动生成文件夹
                    mater.setFolder(Constant.MATER_FOLDER_GENERATE);
                }
                mater.setStaffCoding(staff.getCoding());
                if (StringUtils.isBlank(mater.getDate())) {
                    mater.setDate(DateFormat.getDate());
                }
                insertMater(mater);
                if (mater.getId() == null) {
                    //该材料添加失败
                    mater.setName("材料添加失败：" + mater.getName());
                    mater.setProperty("{\"state\":\"-1\",\"msg\":\"" + mater.getName() + "该材料添加失败，请重试!\"}");
                }
            } else {
                mater.setId(existMter.getId());
                mater.setUnit(existMter.getUnit());
            }
        }
        return mater;
    }

    @Override
    public int insertFucai(Material material) {
        return materialMapper.insertFucai(material);
    }

    @Override
    public int updateFucaiPrice(String id, Double planPrice) {
        return materialMapper.updateFucaiPrice(id, planPrice);
    }

    @Override
    public Double getFucaiPrice(String id) {
        return materialMapper.getFuCaiPrice(id);
    }

    @Override
    public int deleteFucai(String id) {
        return materialMapper.deleteFucai(id);
    }

    @Override
    public List<Material> queryStorage(HashMap<String, Object> params) {
        return materialMapper.queryStorage(params);
    }

    @Override
    public int updateLastPutDate(Material material) {
        return materialMapper.updateLastPutDate(material);
    }

    @Override
    public int updateLastPro(Material m) {
        return materialMapper.updateLastPro(m);
    }

    private void insertMater(final Material mater) {
        try {
            mater.setId(getCode());
            addMater(mater);
            insertCount = 0;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            if (insertCount > 10) {
                mater.setId(null);
                return;
            } else {
                insertCount++;
                insertMater(mater);
            }
        }
    }
}
