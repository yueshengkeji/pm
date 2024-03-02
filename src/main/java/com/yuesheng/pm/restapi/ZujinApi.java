package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Tag(name = "租金管理")
@RestController
@RequestMapping("api/zujin")
public class ZujinApi extends BaseApi {
    @Autowired
    private ProZujinService zujinService;

    @Autowired
    private ProZujinHouseService houseService;

    @Autowired
    private ProZujinHouseRService houseRService;
    @Autowired
    private ProZujinYtService ytService;

    @Autowired
    private ProDetailOweService oweService;

    @Autowired
    private ProBzjService bzjService;

    @Autowired
    private ProDetailMoneyService proDetailMoneyService;
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private TermService termService;
    @Autowired
    @Lazy
    private ConcatBillService billService;

    @Operation(description = "查询所有品牌")
    @GetMapping("brandList")
    public ResponseModel brandList(String searchText) {
        return ResponseModel.ok(zujinService.queryBrand(searchText));
    }

    @Operation(description = "查询租金合计")
    @GetMapping("moneyTotal")
    public ResponseModel moneyTotal() {
        Map<String, Object> result = zujinService.queryMoneyTotal(DateUtil.format(new Date(), "yyyy"));
        if (Objects.isNull(result)) {
            result = new HashMap<>();
        }
        Double money = zujinService.queryEarlyMoney(DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        result.put("earlyMoney", money);
        return new ResponseModel(result);
    }

    @Operation(description = "查询租金合计V2")
    @GetMapping("moneyTotalv2")
    public ResponseModel moneyTotal(Integer type) {
        Map<String, Object> result = zujinService.queryMoneyTotal(DateUtil.format(new Date(), "yyyy"), type);
        Double money = zujinService.queryEarlyMoney(DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        if (Objects.isNull(money)) {
            money = 0.0;
        }
        if (!Objects.isNull(result)) {
            result.put("earlyMoney", money);
        }
        return new ResponseModel(result);
    }

    @Operation(description = "导出租赁列表")
    @GetMapping("list/export")
    public ResponseModel export(String searchText,
                                Integer page,
                                Integer itemsPerPage,
                                String[] sortBy,
                                Boolean[] sortDesc,
                                String ytId,
                                Integer type,
                                Integer isSh) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("zlType", "0,1");
        params.put("ytId", ytId);
        params.put("type", type);
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取租赁合同列表")
    @GetMapping("list")
    public ResponseModel list(String searchText,
                              Integer page,
                              Integer itemsPerPage,
                              String[] sortBy,
                              Boolean[] sortDesc,
                              String ytId,
                              Integer type,
                              Integer isSh) {

        if (Objects.isNull(page)) {
            return ResponseModel.ok();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("zlType", "0,1");
        params.put("isSh", isSh);
        params.put("ytId", ytId);
        params.put("type", type);
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }


    @Operation(description = "导出过期租赁合同")
    @GetMapping("expire/export")
    public ResponseModel expireExport(String searchText,
                                      Integer page,
                                      Integer itemsPerPage,
                                      String[] sortBy,
                                      Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "0");
        params.put("zlType", "0,1");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    /**
     * 获取到期的账单列表
     *
     * @param searchText
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param sortDesc
     * @return
     */
    @Operation(description = "获取到期的账单列表")
    @GetMapping("expire/list")
    public ResponseModel expireList(String searchText,
                                    Integer page,
                                    Integer itemsPerPage,
                                    String[] sortBy,
                                    Boolean[] sortDesc) {
        // Order order = new Order();
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "0");
        params.put("zlType", "0,1");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }


    @Operation(description = "到期金额统计")
    @GetMapping("rageMoney/export")
    public ResponseModel rageMoneyExport(String searchText,
                                         Integer page,
                                         Integer itemsPerPage,
                                         String[] sortBy,
                                         Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("rage", "1");
        params.put("zlType", "0,1");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    /**
     * 获取到期的账单列表
     *
     * @param searchText
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param sortDesc
     * @return
     */
    @Operation(description = "到期账单列表")
    @GetMapping("rageMoney/list")
    public ResponseModel rageMoneyList(String searchText,
                                       Integer page,
                                       Integer itemsPerPage,
                                       String[] sortBy,
                                       Boolean[] sortDesc) {
        // Order order = new Order();
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("rage", "1");
        params.put("zlType", "0,1");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }


    @Operation(description = "保证金额导出")
    @GetMapping("bzjMoney/export")
    public ResponseModel bzjMoneyExport(String searchText,
                                        Integer page,
                                        Integer itemsPerPage,
                                        String[] sortBy,
                                        Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("bzj", "1");
        params.put("zlType", "0,1");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    @Operation(description = "已过期列表")
    @GetMapping("expireEarly/list")
    public ResponseModel expireEarlyList(String searchText,
                                         Integer page,
                                         Integer itemsPerPage,
                                         String[] sortBy,
                                         Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("startDateTime", DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "2");
        params.put("zlType", "0,1");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }

    @Operation(description = "财务金额列表")
    @GetMapping("cwMoneyEarly/list")
    public ResponseModel cwMoneyEarlyList(String searchText,
                                          Integer page,
                                          Integer itemsPerPage,
                                          String[] sortBy,
                                          Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("early", "1");
        params.put("zlType", "0,1");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }

    @Operation(description = "财务金额导出")
    @GetMapping("cwMoneyEarly/export")
    public ResponseModel cwMoneyEarlyExort(String searchText,
                                           String[] sortBy,
                                           Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("early", "1");
        params.put("zlType", "0,1");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    @GetMapping("expireEarly/export")
    public ResponseModel expireEarlyExport(String searchText,
                                           String[] sortBy,
                                           Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.rollDay(new Date(), 30), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("startDateTime", DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "2");
        params.put("zlType", "0,1");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }


    /**
     * 获取有保证金的账单列表
     *
     * @param searchText
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param sortDesc
     * @return
     */
    @Operation(description = "获取有保证金的账单列表")
    @GetMapping("bzjMoney/list")
    public ResponseModel bzjMoneyList(String searchText,
                                      Integer page,
                                      Integer itemsPerPage,
                                      String[] sortBy,
                                      Boolean[] sortDesc) {
        // Order order = new Order();
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("expire", "1");
        params.put("bzj", "1");
        params.put("zlType", "0,1");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }

    @Operation(description = "物业临时金额导出")
    @GetMapping("tempMoney/export")
    public ResponseModel tempMoneyExport(String searchText,
                                         Integer page,
                                         Integer itemsPerPage,
                                         String[] sortBy,
                                         Boolean[] sortDesc) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("zlType", "2");
        String fileName = getExcelModel(params, sortBy, sortDesc);
        return new ResponseModel(fileName);
    }

    /**
     * 获取到期的账单列表
     *
     * @param searchText
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param sortDesc
     * @return
     */
    @Operation(description = "获取到期的账单列表")
    @GetMapping("tempMoney/list")
    public ResponseModel tempMoneyList(String searchText,
                                       Integer page,
                                       Integer itemsPerPage,
                                       String[] sortBy,
                                       Boolean[] sortDesc) {
        // Order order = new Order();
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchText", searchText);
        params.put("endDatetime", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        params.put("zlType", "2");
        startPage(page, itemsPerPage == -1 ? 1000 : itemsPerPage, sortBy, sortDesc);
        Page<ProZujin> zujins = (Page<ProZujin>) zujinService.queryByParam(params);

        setItemDetail(zujins);
        params.clear();
        params.put("rows", zujins);
        params.put("total", zujins.getTotal());
        return new ResponseModel(params);
    }


    private String getExcelModel(HashMap<String, Object> params, String[] sortBy, Boolean[] sortDesc) {
        startPage(1, 10000, sortBy, sortDesc);
        List<ProZujin> zujins = zujinService.queryByParam(params);
        setItemDetail(zujins);
        String fileName = DateUtil.format(new Date(), "对账单-" + DateUtil.PATTERN_IMAGE_NAME) + ".xlsx";
        ArrayList<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setIndex(0);
        row.setCell(getHander());
        rows.add(row);
        int index = 1;
        for (int i = 0; i < zujins.size(); i++) {
            ProZujin zujin = zujins.get(i);
            final StringBuffer sb = new StringBuffer();
            zujin.getHouses().forEach(item -> {
                sb.append(item.getPwNumber());
                sb.append(",");
            });
            if (sb.length() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            zujin.setKj(zujin.getKjType().get(0));
            zujin.setHousesString(sb.toString());
            Row rowValue = new Row();
            List<Cell> cells = getCells(zujin);
            rowValue.setCell(cells);
            rowValue.setIndex(index);
            rows.add(rowValue);
            index++;
        }
        fileName = ExcelParse.writeExcel(rows, fileName);
        return fileName;
    }

    private List<Cell> getHander() {
        ArrayList<Cell> arrayList = new ArrayList<>();
        String[] names = new String[]{"合同编号", "租赁方", "品牌", "商铺位置", "面积", "应收租金", "目前应收", "财务已收", "会计科目", "科目余额"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (names[i].equals("收货人")) {
                cell.setWidth((float) 42.8 * 42 * 3);
            } else if (i == 0) {
                cell.setWidth((float) 42.8 * 42 * 3);
            } else if (i == 1) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 2) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            arrayList.add(cell);
        }
        return arrayList;
    }

    private List<Cell> getCells(ProZujin report) {
        String[] values = new String[]{"Series", "Company.Name",
                "Brand", "HousesString", "Acreage",
                "YearRental", "YsMoney", "CwMoney",
                "Kj.Series", "Kj.Money"};
        return EntityUtils.getCells(report, values);
    }

    private void setItemDetail(List<ProZujin> zujins) {
        zujins.forEach(item -> {
            setZujin(item);
        });
    }

    private void setZujin(ProZujin item) {
        item.setHouses(houseService.getHouseByZujin(item.getId()));
//        item.setYt(ytService.queryById(item.getCompanyTypeId()));
        item.setMoneyOwe(oweService.getOweByDate(DateFormat.getDate(), item.getId() + "", 0));
        item.setBillOwe(oweService.getOweByDate(DateFormat.getDate(), item.getId() + "", 1));
        List<ProDetailMoney> detail = proDetailMoneyService.getMoneyByMainId(item.getId() + "");
        ProDetailMoney detailMoney = null;
        Staff staff = staffService.getStaffById(item.getLastStaffId());
        if (!Objects.isNull(staff)) {
            item.setLastStaffName(staff.getName());
        }
        if (detail.isEmpty()) {
            detailMoney = new ProDetailMoney();
            detailMoney.setId(UUID.randomUUID().toString());
            detailMoney.setDate(DateUtil.format(DateUtil.getNowDate()));
            detailMoney.setMainId(item.getId() + "");
            detailMoney.setMoney(0d);
            detailMoney.setSeries("默认编号");
            detailMoney.setRemark("");
            proDetailMoneyService.addMoney(detailMoney);
            detail.add(detailMoney);
            //科目余额=期初欠租-期初未开票+开票情况合计数-收款情况合计数
            Double kjMoney = getKjTypeMoney(item);
            detailMoney.setMoney(kjMoney);
        }
        item.setKjType(detail);

        if (item.getYsMoney() > item.getCwMoney()) {
            //查询最后收款日期
            //查询最近30天内应收日期金额合计，
            Date endTime = DateUtil.rollDay(new Date(), 30);
            Double ysMoney = proPutDetailService.getProMoneySumByMain(item.getId() + "", DateUtil.format(endTime, DateUtil.PATTERN_CLASSICAL_SIMPLE));
            if (!Objects.isNull(ysMoney) && ysMoney > item.getCwMoney()) {
                //可以预警
                item.setYsMoneyEarly(true);
            }
        }
    }

    private Double getKjTypeMoney(ProZujin item) {
        if (item.getKpMoney() == null) {
            item.setKpMoney(0d);
        }
        if (item.getCwMoney() == null) {
            item.setCwMoney(0d);
        }
        return item.getMoneyOwe().getOweMoney() - item.getBillOwe().getOweMoney() + item.getKpMoney() - item.getCwMoney();
    }

    @Operation(description = "添加租金合同")
    @PutMapping
    public ResponseModel insert(@RequestBody ProZujin zujin,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        zujin = zujinService.insert(zujin, staff);
        return new ResponseModel(zujin);
    }

    @Operation(description = "更新租金合同")
    @PostMapping
    public ResponseModel update(@RequestBody ProZujin zujin, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        houseService.deleteByZujin(zujin.getId());
        houseService.insertHouseRelation(zujin.getHouses(), zujin);
        zujin.setLastStaffId(staff.getId());
        zujin.setLastDateTime(DateUtil.format(new Date()));
        return new ResponseModel(zujinService.update(zujin));
    }

    @Operation(description = "删除租金合同")
    @DeleteMapping
    public ResponseModel delete(Integer id) {
        houseService.deleteByZujin(id);
        return new ResponseModel(zujinService.deleteById(id));
    }

    @Operation(description = "修改租金金额")
    @PostMapping("updateMoney")
    public ResponseModel updateMoneyCount(@RequestBody ProZujin proZujin, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        proZujin.setLastStaffId(staff.getId());
        proZujin.setLastDateTime(DateUtil.format(new Date()));
        zujinService.updateMoneyCount(proZujin);
        Double money = getKjTypeMoney(proZujin);
        ProDetailMoney kj = proZujin.getKjType().get(0);
        kj.setMoney(money);
        proDetailMoneyService.update(kj);
        return new ResponseModel(zujinService.queryById(proZujin.getId()));
    }

    @Operation(description = "添加保证金")
    @PostMapping("bzj")
    public ResponseModel insertBzj(@RequestBody ProBzj bzj) {
        bzjService.insert(bzj);
        ProZujin zujin = zujinService.queryById(Integer.valueOf(bzj.getProDetailId()));
        if (Objects.isNull(zujin.getBzjMoney())) {
            zujin.setBzjMoney(0d);
        }
        if (Objects.isNull(zujin.getReturnBzjMoney())) {
            zujin.setReturnBzjMoney(0d);
        }
        if (bzj.getMoney() > 0) {
            zujin.setBzjType(bzj.getType());
            zujin.setBzjMoney(zujin.getBzjMoney() + bzj.getMoney());
            zujin.setReturnBzjMoney(null);
        } else {
            zujin.setBzjMoney(null);
            zujin.setBzjType(null);
            zujin.setReturnBzjMoney(zujin.getReturnBzjMoney() + bzj.getMoney());
        }
        zujinService.updateBzj(zujin);
        return new ResponseModel(bzj);
    }

    @Operation(description = "删除保证金")
    @DeleteMapping("deleteBzj/{id}")
    public ResponseModel deleteBzj(@PathVariable Integer id) {
        ProBzj bzj = bzjService.queryById(id);
        ProZujin zujin = zujinService.queryById(Integer.valueOf(bzj.getProDetailId()));
        if (bzj.getMoney() > 0) {
            if (!Objects.isNull(zujin.getBzjMoney()) && bzj.getMoney() != null) {
                zujin.setBzjMoney(zujin.getBzjMoney() - bzj.getMoney());
                zujin.setBzjType(null);
                zujinService.updateBzj(zujin);
            }
        } else {
            if (!Objects.isNull(zujin.getReturnBzjMoney()) && bzj.getMoney() != null) {
                zujin.setReturnBzjMoney(zujin.getReturnBzjMoney() - bzj.getMoney());
                zujin.setBzjType(null);
                zujinService.updateBzj(zujin);
            }
        }
        bzjService.deleteById(id);
        return new ResponseModel(bzj);
    }

    @Operation(description = "查询保证金列表")
    @GetMapping("bzj/{zujinId}")
    public ResponseModel listBzj(@PathVariable String zujinId) {
        ProBzj bzj = new ProBzj();
        bzj.setProDetailId(zujinId);
        return new ResponseModel(bzjService.queryAll(bzj));
    }

    @Operation(description = "通过商铺查询租赁合同")
    @GetMapping("byHouseId/{houseId}")
    public ResponseModel getByHouseId(@PathVariable Integer houseId) {
        ProZujinHouseR query = new ProZujinHouseR();
        query.setHouseId(houseId);
        query.setType((byte) 0);
        List<ProZujinHouseR> houseRS = houseRService.queryAll(query);
        if (houseRS.size() > 0) {
            query = houseRS.get(0);
            ProZujin zujin = zujinService.queryById(query.getZjId());
            setZujin(zujin);
            return new ResponseModel(zujin);
        } else {
            return new ResponseModel(500, "该商铺未登记租赁信息");
        }
    }

    @Operation(description = "通过id查询租赁合同明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable Integer id) {

        ProZujin zujin = zujinService.queryById(id);
        setZujin(zujin);
        zujin.setYt(ytService.queryById(zujin.getCompanyTypeId()));

        ProBzj bzj = new ProBzj();
        bzj.setProDetailId(id + "");
        zujin.setBzjList(bzjService.queryAll(bzj));

        Term term = new Term();
        term.setConcatId(id + "");
        zujin.setTermList(termService.queryByPage(term));

        return ResponseModel.ok(zujin);
    }

    @Operation(description = "查询合同账单")
    @GetMapping("billList")
    public ResponsePage billList(Integer page, Integer itemsPerPage,
                                 String companyName,
                                 String brand,
                                 String room,
                                 String name,
                                 String startDate,
                                 String endDate,
                                 String[] state,
                                 String concatType,
                                 String type) {

        HashMap<String, String> param = new HashMap<>();
        param.put("companyName", companyName);
        param.put("brand", brand);
        param.put("room", room);
        param.put("name", name);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("state", ArrayUtil.join(state, ",", "'"));
        param.put("concatType", concatType);
        param.put("type", type);
        startPage(page, itemsPerPage, "pay_end_date", "asc");
        Page<ConcatBill> cbList = (Page) billService.queryByParam(param);
        HashMap<String, ProZujin> pzMap = new HashMap<>();
        cbList.forEach(item -> {
            if (pzMap.containsKey(item.getConcatId())) {
                item.setConcat(pzMap.get(item.getConcatId()));
            } else if (StringUtils.isNotBlank(item.getConcatId())) {
                try {
                    ProZujin pz = (ProZujin) getById(Integer.valueOf(item.getConcatId())).getData();
                    item.setConcat(pz);
                } catch (Exception e) {
                    //ignore this error
                }
            }
        });

        return ResponsePage.ok(cbList);
    }

    @Operation(description = "导出合同账单")
    @GetMapping("exportBillList")
    public ResponseModel exportBillList(Integer page,
                                        String companyName,
                                        String brand,
                                        String room,
                                        String name,
                                        String startDate,
                                        String endDate,
                                        String[] state,
                                        String concatType,
                                        String type) {

        HashMap<String, String> param = new HashMap<>();
        param.put("companyName", companyName);
        param.put("brand", brand);
        param.put("room", room);
        param.put("name", name);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("state", ArrayUtil.join(state, ",", "'"));
        param.put("concatType", concatType);
        param.put("type", type);
        startPage(page, 5000, "pay_end_date", "asc");
        Page<ConcatBill> cbList = (Page) billService.queryByParam(param);
        String fileName = "租赁合同账单.xlsx";
        fileName = ExcelParse.writeExcel(cbList, fileName, new String[]{
                "State", "ArrearageDay", "Room", "Floor", "Brand", "Name",
                "PayEndDate", "Money", "PayMoney", "BackMoney", "SjMoney",
                "Arrearage", "ConcatType", "InvoiceState"
        }, ConcatBill.class);

        return ResponseModel.ok(fileName);
    }


    @Operation(description = "新增收款账单")
    @PutMapping("bill")
    public ResponseModel insertBill(@RequestBody ConcatBill bill) {
        billService.insert(bill);
        return ResponseModel.ok(bill);
    }

    @Operation(description = "修改收款账单")
    @PostMapping("bill")
    public ResponseModel updateBill(@RequestBody ConcatBill bill) {
        billService.update(bill);
        return ResponseModel.ok(bill);
    }
}
