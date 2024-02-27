package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoSong on 2016/8/31 材料控制器.
 *
 * @author XiaoSong
 * @date 2016/08/31
 */
@Controller
@RequestMapping("/managent")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private OutMaterChildService outMaterChildService;
    @Autowired
    private PutStorageMaterialService putStorageMaterialService;
    @Autowired
    private ProjectService projectService;

    /**
     * 获取材料集合，{0：有库存的材料，-1：全部，分页对象}
     *
     * @param type       获取的材料类型
     * @param pageNumber 页码
     * @param str        检索材料
     * @return
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("/seekMaterialList")
    @ResponseBody
    public Map<String, Object> getMaterIal(String type, String str, Integer pageSize, Integer pageNumber, String sortName, String sortOrder, String storage, String folder) {
        Map<String, Object> params = new HashMap<>(16);
        //判断是否有排序
        // params.put("order", isSort(sortName, sortOrder));
        //获取材料集合
        params.put("type", type);
        params.put("str", str);
        params.put("storage", storage);
        params.put("folder", null != folder ? "'" + folder + "'" : null);
        PageHelper.startPage(pageNumber, pageSize, sortName + " " + sortOrder);
        List<Material> materials = materialService.getMaterialByType(params);       //获取材料
        Long count = ((Page) materials).getTotal();            //获取类型总数目

        /**
         * 遍历材料平均单价
         */
        Map<String, Object> result = null;
        BigDecimal bd = null;
        for (Material m : materials) {
            if (m != null) {
                result = putStorageMaterialService.getAvgByMaterId(m.getId());
                bd = (BigDecimal) result.get("priceSum");        //单价总和
                Integer putCount = (Integer) result.get("putCount");        //入库总和
                if (bd != null && putCount != null) {
                    m.setAvgPrice(bd.doubleValue() / putCount);       //设置平均单价
                }
            }
        }

        //清除map,重新添加返回的集合
        params.clear();
        params.put("rows", materials);
        params.put("total", count);
        return params;
    }

    /**
     * 根据仓库id获取该仓库中材料集合
     *
     * @param id 仓库id
     * @return 材料集合
     */
    @RequestMapping("/getMaterByStorage")
    @ResponseBody
    public Map<String, Object> getMaterByStorage(String id, Integer pageSize, Integer pageNumber,
                                                 String searchText) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.startPage(pageNumber, pageSize);
        Page<StorageMater> m = (Page<StorageMater>) storageService.getMaterByStorageId(id, StringUtils.isBlank(searchText) ? null : searchText);
        for (StorageMater sm : m) {
            if (sm != null && sm.getMaterial() != null) {
                //查询该材料最后一次入库记录，获取税率
                StorageMaterial putSm = putStorageMaterialService.getPutDetailByMaterId(sm.getMaterial().getId());
                if (putSm != null) {
                    sm.setPutDate(putSm.getPutDate());
                    sm.setTax(putSm.getTax());
                    // storageService.updateOrInsert(sm.getStorageId(),sm.getMaterial().getId(), DateUtil.parse(putSm.getPutDate()+" 00:00:00"));
                }
            }
        }
        result.put("rows", m);
        result.put("total", m.getTotal());
        return result;
    }

    /**
     * 检索材料目录
     *
     * @param text 检索串
     * @return
     */
    @RequestMapping("/seekMaterialFolder")
    @ResponseBody
    public List<Folder> seekMaterialFolder(String text) {
        return materialService.seekMaterialFolder(text);
    }

    /**
     * 获取材料目录
     *
     * @param materId 材料id
     * @return
     */
    @RequestMapping("/getFolderByMaterId")
    @ResponseBody
    public Folder getFolderByMaterId(String materId) {
        return materialService.getFolderByMaterId(materId);
    }

    /**
     * 修改材料
     *
     * @param m 材料對象
     * @return
     */
    @RequestMapping("/updateMaterial")
    @ResponseBody
    public Material updateMaterial(@RequestBody Material m) {
        if (materialService.updateMaterial(m) == -1) {
            return materialService.getMaterialByid(m.getId());
        }
        return m;
    }

    /**
     * 获取材料目录集合
     *
     * @param parent 上级目录id
     * @return
     */
    @RequestMapping("/getMaterFolder")
    @ResponseBody
    public List<Folder> getFolder(String parent) {
        return materialService.getFolderByParent(parent);
    }

    /**
     * 检索单位集合
     *
     * @param str 检索字符串
     * @return
     */
    @RequestMapping("/getMaterUnit")
    @ResponseBody
    public List<Unit> getMaterUnit(String str) {
        return unitService.seek(str);
    }

    /**
     * 删除材料
     *
     * @param id 材料id
     * @return
     */
    @RequestMapping("/deleteMaterById")
    @ResponseBody
    public Map<String, String> deleteMater(String id) {
        return materialService.deleteMaterial(id);
    }

    /**
     * 获取材料使用信息集合
     *
     * @param id        材料id
     * @param typeArray {1=计划单信息，2=申请单信息，3=采购单信息，4=入库单信息，5=出库单信息，6=退料单信息，7=盘点单信息}
     * @return
     */
    @RequestMapping("/getMaterUseMsg")
    @ResponseBody
    public Map<String, Object> getMaterUseMsg(String id, String[] typeArray) {
        return materialService.getMaterUseMsg(id, typeArray);
    }


    /**
     * 根据材料id获取材料对象
     *
     * @param id
     * @return
     */
    @RequestMapping("/getMaterialById/{id}")
    @ResponseBody
    public Material getMaterById(@PathVariable String id) {
        return materialService.getMaterialByid(id);
    }

    /**
     * 获取材料使用记录
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param type  类型：1=入库单，2=出库单
     * @return
     */
    @RequestMapping("/getMUH")
    @ResponseBody
    public List<MaterUseHistory> gteMaterUseHistory(String start, String end, Integer type, String projects) {
        return new ArrayList<>();
    }

    /**
     * 判断排序
     *
     * @param sortName  排序列名
     * @param sortOrder 排序类型
     */
    public static String isSort(String sortName, String sortOrder) {
        if (sortName != null && sortOrder != null) {
            StringBuffer buffer = new StringBuffer("ORDER BY ");
            buffer.append(sortName);
            buffer.append(" ");
            buffer.append(sortOrder);
            return buffer.toString();
        } else {
            return "";
        }
    }

    /**
     * 申请单导入材料 ，根据材料名称、型号、品牌、单位确定材料唯一性
     *
     * @param material 材料集合
     * @return
     */
    @RequestMapping("/importMater")
    @ResponseBody
    public Material[] materialIsExist(@RequestBody Material[] material, HttpSession session) {
        materialService.insert(material, (Staff) session.getAttribute(Constant.SESSION_KEY));
        return material;
    }

    /**
     * 导入材料 ，根据材料名称、型号、品牌、单位确定材料唯一性
     *
     * @param material 材料集合
     * @return
     */
    @RequestMapping("/importMaterByPlan")
    @ResponseBody
    public Material[] materialIsExistByPlan(@RequestBody Material[] material, HttpSession session) {
        materialService.insert(material, (Staff) session.getAttribute(Constant.SESSION_KEY));
        return material;
    }

    /**
     * 判断材料是否存在
     *
     * @param m 材料对象
     */
    @RequestMapping("/materIsExists")
    @ResponseBody
    public Material isExists(Material m) {
        if (m == null) {
            return m;
        }
        return materialService.isExist(m);
    }
}
