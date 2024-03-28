package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "入库/出库签字管理")
@RestController
@RequestMapping("api/putSign")
public class PutSignApi extends BaseApi {

    @Autowired
    private ProPutSignService putSignService;

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ProStaffHwService staffHwService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private OutMaterChildService outMaterChildService;

    @Operation(description = "查询签字信息列表")
    @GetMapping("list")
    public ResponsePage list(String staffId, Integer type, Integer page, Integer itemsPerPage, String sortBy, String sortDesc, String pastDate) {

        ProPutSign putSign = new ProPutSign();
        if (StringUtils.isNotBlank(staffId)) {
            putSign.setStaffId(staffId);

        }
        if (StringUtils.isNotBlank(pastDate)) {
            putSign.setPastDate(pastDate);
        }

        putSign.setType(type);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProPutSign> signList = (Page<ProPutSign>) putSignService.queryListByParam(putSign);

        signList.forEach(sign -> {

            sign.setProcurement(procurementService.getProcurementById(sign.getProId()));

            sign.setPutStorage(JSON.parseObject(sign.getPutobj(), PutStorage.class));

            Staff staff = staffService.getStaffById(sign.getSignStaffId());

            if (!Objects.isNull((staff))) {

                staff.setHead(staffHwService.queryHeadByStaffId(staff.getId()));

                sign.setSignStaff(staff);
            }

        });

        return new ResponsePage(signList);
    }

    @Operation(description = "微信通知签字")
    @PostMapping("wxNotify")
    public ResponseModel wxNotify(@RequestBody ProPutSign sign, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {

        if (Objects.isNull(sign.getSignStaff())) {
            sign.setSignStaff(staffService.getStaffById(sign.getSignStaffId()));
        }

        Map<String, Object> param = new HashMap<>();

        param.put("title", "材料入库签单");

        param.put("mTitle", "等待" + sign.getSignStaff().getName() + "签字确认");

        param.put("content", "发送人：" + staff.getName());

        param.put("url", WebParam.VUETIFY_BASE + "/procurement/signPut/" + sign.getId());

        ArrayList<Staff> staffList = new ArrayList<>();

        staffList.add(sign.getSignStaff());

        flowNotifyService.msgNotify(staffList, param);

        return new ResponseModel("ok");
    }

    @Operation(description = "删除签字信息")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {

        ProPutSign sign = putSignService.queryById(id);
        if (Objects.isNull(sign)) {
            return new ResponseModel(0);
        } else if (sign.getType() == 0) {
            return new ResponseModel(putSignService.deleteById(id));
        } else {
            return new ResponseModel(500, "该单据已经签字或过期");
        }

    }

    @Operation(description = "通过入库单查询签字信息")
    @GetMapping("queryByPutId/{putId}")
    public ResponseModel getByPutId(@PathVariable String putId) {
        ProPutSign sign = new ProPutSign();
        sign.setPutId(putId);
        sign = putSignService.queryByParam(sign);
        if (!Objects.isNull(sign)) {
            Staff staff = staffService.getStaffById(sign.getSignStaffId());
            if (staff != null) {
                sign.setSignStaffName(staff.getName());
            }
        }
        return ResponseModel.ok(sign);
    }

    @Operation(description = "通过入库单明细行查询签字信息列表")
    @GetMapping("queryByPutMaterId")
    public ResponseModel getByPutMaterIds(String[] putMaterIds) {
        return ResponseModel.ok(putSignService.queryByMaterIds(putMaterIds));
    }

    @Operation(description = "查询我的签字信息列表")
    @GetMapping("myList")
    public ResponseModel myList(@SessionAttribute(Constant.SESSION_KEY) Staff staff,
                                String sortBy,
                                Boolean sortDesc,
                                Integer page,
                                Integer itemsPerPage) {
        ProPutSign putSign = new ProPutSign();
        putSign.setSignStaffId(staff.getId());
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<ProPutSign> signList = putSignService.queryListByParam(putSign);
        signList.forEach(sign -> {
            sign.setProcurement(procurementService.getProcurementById(sign.getProId()));
        });
        return ResponseModel.ok(signList);
    }

    @Operation(description = "确认入库签字")
    @PostMapping("sign")
    public ResponseModel sing(@RequestBody ProPutSign sign, @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        return ResponseModel.ok(putSignService.updatePutSign(sign, staff));
    }

    @Operation(description = "确认出库签字")
    @PostMapping("outSign")
    public ResponseModel outSign(@RequestBody ProPutSign sign, @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        return ResponseModel.ok(putSignService.updateOutSign(sign, staff));
    }

    @Operation(description = "通过签字单id查询入库签单")
    @GetMapping("put/{id}")
    public ResponseModel getPutById(@PathVariable String id) {
        ProPutSign pps = putSignService.queryById(id);
        if (pps == null || StringUtils.isBlank(pps.getPutobj())) {
            return ResponseModel.error("入库签单不存在或被撤回");
        }
        PutStorage ps = JSON.parseObject(pps.getPutobj(), PutStorage.class);
        if (Objects.isNull(ps)) {
            return ResponseModel.error("入库单信息为空,请联系发起人");
        }
        ps.setProcurement(procurementService.getProcurementById(ps.getProId()));
        for (StorageMaterial sm : ps.getMaterialList()) {
            sm.setMaterial(materialService.getMaterialByid(sm.getMaterial().getId()));
        }
        pps.setPutobj(JSON.toJSONString(ps));
        return ResponseModel.ok(pps);
    }

    @Operation(description = "通过签字单id查询出库签单")
    @GetMapping("out/{id}")
    public ResponseModel getOutById(@PathVariable String id) {
        ProPutSign pps = putSignService.queryById(id);
        if (Objects.isNull(pps)) {
            return ResponseModel.error("出库签单不存在或被撤回");
        }
        MaterOut mo = outMaterService.getOutMaterById(pps.getProId());
        if (Objects.isNull(mo)) {
            return ResponseModel.error("出库签单不存在或被撤回");
        }
        mo.setMaterOuts(outMaterChildService.getOutMatersByOutId(mo.getId()));
        pps.setPutobj(JSON.toJSONString(mo));
        return ResponseModel.ok(pps);
    }

    @Operation(description = "通过出库单/采购订单id查询签字信息")
    @GetMapping("{proId}")
    public ResponseModel getByProId(@PathVariable String proId) {
        ProPutSign query = new ProPutSign();
        query.setProId(proId);
        return ResponseModel.ok(putSignService.queryByParam(query));
    }

    @Operation(description = "通过出库单/采购订单id查询签字信息列表")
    @GetMapping("list/{proId}")
    public ResponseModel listByProId(@PathVariable String proId){
        ProPutSign query = new ProPutSign();
        query.setProId(proId);
        List<ProPutSign> pps = putSignService.queryListByParam(query);
        pps.forEach(sign->{
            sign.setSignStaff(staffService.getStaffById(sign.getSignStaffId()));
            sign.setStaff(staffService.getStaffById(sign.getStaffId()));
        });
        return ResponseModel.ok(pps);
    }
}
