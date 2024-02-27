package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.PlanMaterial;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.PlanMaterialService;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/planMaterial")
@Tag(name = "材料计划单明细接口")
public class PlanMaterialApi extends BaseApi {
    @Autowired
    private PlanMaterialService planMaterialService;

    @Operation(description = "通过计划单主键获取计划单材料集合")
    @GetMapping("byPlanId")
    public ResponseModel getByPlanId(@Parameter(name="计划单id") String planId) {
        long st = System.currentTimeMillis();
        List<PlanMaterial> materialList = planMaterialService.getMaterialsByPlan(planId);
//        System.out.println("query materialList time :" + (double) (System.currentTimeMillis() - st) / 1000d + "s");
        /*materialList.forEach(pm -> {
            pm.setMaterial(materialService.getMaterialByid(pm.getMaterial().getId()));
        });*/
        System.out.println("query time :" + (double) (System.currentTimeMillis() - st) / 1000d + "s");
        return new ResponseModel(materialList);
    }

    @Operation(description = "删除计划单材料")
    @DeleteMapping
    public ResponseModel deleteMaterial(@Parameter(name="计划单明细行主键") String id) {
        return new ResponseModel(planMaterialService.delete(id));
    }

    @Operation(description = "通过项目id获取未申请的计划单材料集合")
    @GetMapping("byProjectId/{projectId}")
    public ResponsePage getByProjectId(@PathVariable String projectId,
                                       String searchText,
                                       Integer page,
                                       Integer itemsPerPage,
                                       String sortBy,
                                       Integer applyType,
                                       Boolean sortDesc) {
        sortBy = getSortName(sortBy);
        startPage(page, itemsPerPage, sortBy, Objects.isNull(sortDesc) ? true : sortDesc);
        return ResponsePage.ok((Page) planMaterialService.getMaterForApplyV2(projectId, searchText, applyType));
    }

    @Operation(description = "通过项目id获取所有计划材料列表")
    @GetMapping("allListByProjectId/{projectId}")
    public ResponsePage listByProject(String projectId,
                                      @RequestParam(defaultValue = "1") Integer state,
                                      Integer page,
                                      Integer itemsPerPage) {
        PlanMaterial pm = new PlanMaterial();
        pm.setProjectId(projectId);
        pm.setPm07017(state);
        startPage(page, itemsPerPage, "pm07011", "DESC");
        Page<PlanMaterial> planMaterials = (Page<PlanMaterial>) planMaterialService.getMaterByParam(pm);
        Double money = planMaterialService.getPlanMoneyByProject(projectId);
        if(Objects.isNull(money)){
            money = 0.0;
        }
        ResponsePage result = ResponsePage.ok(planMaterials);
        result.getData().put("money", money);
        return result;
    }
    @Operation(description = "导出项目所有计划材料列表")
    @GetMapping("exportAllByProjectId/{projectId}")
    public ResponseModel exportAllByProjectId(String projectId,
                                      @RequestParam(defaultValue = "1") Integer state,
                                      Integer page) {
        PlanMaterial pm = new PlanMaterial();
        pm.setProjectId(projectId);
        pm.setPm07017(state);
        startPage(page, 5000, "pm07011", "DESC");
        Page<PlanMaterial> planMaterials = (Page<PlanMaterial>) planMaterialService.getMaterByParam(pm);
        String fileName = "材料计划单.xlsx";
        fileName = ExcelParse.writeExcel(planMaterials,fileName,new String[]{
                "Material.Name","Material.Model","Material.Brand","PlanSum","Material.Unit.Name",
                "PlanPrice","TaxMoney"
        }, PlanMaterial.class);
        return ResponseModel.ok(fileName);
    }

    private String getSortName(String sortBy) {
        if (StringUtils.isNotBlank(sortBy)) {
            switch (sortBy) {
                case "applySum":
                    return "pm03505";
                case "planSum":
                    return "a.pm00604";
                case "material.brand":
                    return "pm00221";
                case "material.model":
                    return "pm00205";
                case "material.name":
                    return "pm00202";
                case "taskId":
                    return "a.pm00602";
                case "planDate":
                default:
                    return "pm03505 asc, a.pm00610";
            }
        } else {
            return "pm03505 asc, a.pm00610";
        }
    }

    @Operation(description = "通过计划单id,重新将材料导入到未采购表中")
    @GetMapping("reParse/{id}")
    public ResponseModel reParse(@PathVariable("id") String planId) {
        List<PlanMaterial> pmList = planMaterialService.getMaterialsByPlan(planId);
        List<PlanMaterial> ok = planMaterialService.getMaterForApply(pmList.get(0).getProjectId());
        pmList.forEach((item) -> {
            boolean isAdd = false;
            for (PlanMaterial item2 : ok) {
                if (item.getTaskId() != null) {
                    if (item.getTaskId().equals(item2.getTaskId())
                            && item.getMaterial().getId().equals(item2.getMaterial().getId())) {
                        isAdd = true;
                        break;
                    }
                } else {
                    if (item.getMaterial().getId().equals(item2.getMaterial().getId())) {
                        isAdd = true;
                        break;
                    }
                }
            }
            if (!isAdd) {
                /*
                机型2U盘位8VGA1路HDMI1路HDMI1(画面分割)1、4、8、9、16、32分割前智能支持后智能不支持主处理器工业级嵌入式微控制器操作系统嵌入式Linux实时操作系统操作界面WEB、本地GUI网络协议IPv4、IPv6、HTTP、NTP、DNS、ONVIF网络视频接入32路网络带宽接入200Mbps,储存128Mbps,转发128MbpsIPC分辨率4K/6M/5M/4M/3M/1080P/1.3M/720P解码能力2×4K/4×4M/8×1080P/16×720P视频输出1路VGA，1路HDMI，支持VGA/HDMI视频同源输出多路回放最大支持16路回放录像方式录像方式和优先级：手动录像>报警录像>动态检测录像>定时录像存储方式本机硬盘、网络等备份方式硬盘、外接USB存储设备视频压缩标准H.265/H.264/MPEG4/MJPEG音频压缩标准G.711A音频接口1路,支持IPC音频输入/1路,支持语音对讲输出报警接口16进4出，其中3路继电器输出，1路12V1Actrl输出硬盘接口8个内置SATA接口，支持10T、SSDUSB接口2个前置USB2.0接口/2个后置USB3.0接口网络接口2个千兆以太网口串行接口1个RS-232/1个RS-485指示灯1个系统运行指示灯，1个硬盘指示灯，1个网络指示灯，1个电源指示灯电源1个电源接口，AC90V~264V50+2%Hz功耗＜12W（不含硬盘）工作温度-10℃～+55℃工作湿度10℅～90℅尺寸（宽x深x高）2U(mm)：439.7×445.5×90.65重量6.6Kg（不含硬盘）安装方式台式安装前智能接入支持客流量统计，热度图，人脸检测，绊线入侵，区域入侵，物品遗留，物品搬移，快速移动，人员聚集，徘徊检测等前智能2.0功能
                */
                if (StringUtils.isNotBlank(item.getCnfStr()) && item.getCnfStr().length() > 200) {
                    item.setCnfStr(item.getCnfStr().substring(0, 200));
                }
                //添加到已计划材料
                planMaterialService.insertOk(item);
            }
        });
        return new ResponseModel(planId);
    }
}
