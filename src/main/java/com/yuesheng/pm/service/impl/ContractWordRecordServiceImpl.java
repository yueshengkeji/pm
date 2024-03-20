package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ContractWordRecordMapper;
import com.yuesheng.pm.service.ContractWordModelParamsService;
import com.yuesheng.pm.service.ContractWordModelService;
import com.yuesheng.pm.service.ContractWordRecordService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ContractWordRecordServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/2/2 0002 15:11
 */
@Service
public class ContractWordRecordServiceImpl implements ContractWordRecordService {
    @Autowired
    private ContractWordRecordMapper contractWordRecordMapper;
    @Autowired
    private ContractWordModelService contractWordModelService;
    @Autowired
    private ContractWordModelParamsService contractWordModelParamsService;

    @Override
    public int insert(ProZujin proZujin) {
        ContractWordModel contractWordModel = contractWordModelService.selectByType(proZujin.getType());
        if (ObjectUtils.isEmpty(contractWordModel)) {
            return 0;
        }

        ContractWordRecord contractWordRecord = new ContractWordRecord();
        contractWordRecord.setId(UUID.randomUUID().toString());
        contractWordRecord.setRecordTime(DateUtil.getDatetime());
        contractWordRecord.setRichText(getRichText(proZujin, contractWordModel));
        contractWordRecord.setContractId(proZujin.getId() + "");
        contractWordRecord.setType(proZujin.getType());
        return contractWordRecordMapper.insert(contractWordRecord);
    }

    @Override
    public ContractWordRecord selectLastOneByContractId(String contractId) {
        return contractWordRecordMapper.selectLastOneByContractId(contractId);
    }

    public String getRichText(ProZujin proZujin, ContractWordModel contractWordModel) {
        String richText = contractWordModel.getRichText();
        String paramsArr = contractWordModel.getParamsArr();
        if (paramsArr.equals("")) {
            return richText;
        }
        String[] arr = paramsArr.split(",");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            ids.add(Integer.valueOf(arr[i]));
        }
        List<ContractWordModelParams> contractWordModelParams = contractWordModelParamsService.listByIds(ids);

        if (contractWordModelParams.size() > 0) {
            for (int i = 0; i < contractWordModelParams.size(); i++) {
                String replace = contractWordModelParams.get(i).getMarkName().replace("#", "");
                Object replaceValue = "";
                try {
                    if (replace.equals("payType")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        replaceValue = (int) field.get(proZujin) == 0 ? "年付" : (int) field.get(proZujin) == 1 ? "季度" : "月付";
                    } else if (replace.equals("zlType")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        replaceValue = (int) field.get(proZujin) == 0 ? "固定铺位" : (int) field.get(proZujin) == 1 ? "机动" : "临时收费";
                    } else if (replace.equals("yt")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        ProZujinYt proZujinYt = (ProZujinYt) field.get(proZujin);
                        replaceValue = proZujinYt.getName();
                    } else if (replace.equals("houses")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        List<ProZujinHouse> list = (List<ProZujinHouse>) field.get(proZujin);
                        String str = "";
                        if (list != null && list.size() > 0) {
                            for (int j = 0; j < list.size(); j++) {
                                if (j == list.size() - 1) {
                                    str = str + list.get(j).getPwNumber() + "";
                                } else {
                                    str = str + list.get(j).getPwNumber() + ",";
                                }

                            }
                        }
                        replaceValue = str;
                    } else if (replace.equals("houses.floor")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<ProZujinHouse> list = (List<ProZujinHouse>) field.get(proZujin);
                        String str = "";
                        if (list != null && list.size() > 0) {
                            for (int j = 0; j < list.size(); j++) {
                                if (j == list.size() - 1) {
                                    str = str + list.get(j).getFloor() + "";
                                } else {
                                    str = str + list.get(j).getFloor() + ",";
                                }

                            }
                        }
                        replaceValue = str;
                    } else if (replace.equals("moneyOwe")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        ProDetailOwe proDetailOwe = (ProDetailOwe) field.get(proZujin);
                        if (ObjectUtils.isNotEmpty(proDetailOwe)) {
                            replaceValue = String.valueOf(proDetailOwe.getOweMoney());
                        }
                    } else if (replace.equals("billOwe")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        ProDetailOwe billOwe = (ProDetailOwe) field.get(proZujin);
                        if (ObjectUtils.isNotEmpty(billOwe)) {
                            replaceValue = billOwe.getOweMoney();
                        }
                    } else if (replace.equals("startDatetime") || replace.equals("endDatetime") || replace.equals("planDate") || replace.equals("openDate")) {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        replaceValue = String.valueOf(field.get(proZujin)).replaceFirst("-", "年").replaceFirst("-", "月");
                    } else if (replace.equals("startDatetime.endDatetime")) {
                        String startDatetime = replace.split("\\.")[0];
                        String endDatetime = replace.split("\\.")[1];
                        Field field = ProZujin.class.getDeclaredField(startDatetime);
                        field.setAccessible(true);
                        String startDatetimeValue = String.valueOf(field.get(proZujin));
                        Field field2 = ProZujin.class.getDeclaredField(endDatetime);
                        field2.setAccessible(true);
                        String endDatetimeValue = String.valueOf(field2.get(proZujin));
                        LocalDate localDateStart = LocalDate.parse(startDatetimeValue);
                        LocalDate localDateEnd = LocalDate.parse(endDatetimeValue);
                        Period period = localDateStart.until(localDateEnd);
                        int months = period.getYears() * 12 + period.getMonths();
                        if (period.getDays() == 30) {
                            months = months + 1;
                        }
                        replaceValue = String.valueOf(months);
                    } else if (replace.equals("termList.筹备期装修管理费") || replace.equals("termList.营运期装修管理费") || replace.equals("termList.装修建筑垃圾清运费")
                            || replace.equals("termList.临时水费") || replace.equals("termList.临时电费")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals(replace.split("\\.")[1])) {
                                    replaceValue = String.valueOf(termList.get(j).getPrice());
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("bzjList.装修保证金") || replace.equals("bzjList.履约保证金") || replace.equals("bzjList.质保保证金")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<ProBzj> bzjList = (List<ProBzj>) field.get(proZujin);
                        if (bzjList != null && bzjList.size() > 0) {
                            for (int j = 0; j < bzjList.size(); j++) {
                                if (bzjList.get(j).getType().equals(replace.split("\\.")[1])) {
                                    replaceValue = String.valueOf(bzjList.get(j).getMoney());
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("bzjList.装修保证金.大写") || replace.equals("bzjList.履约保证金.大写") || replace.equals("bzjList.质保保证金.大写")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<ProBzj> bzjList = (List<ProBzj>) field.get(proZujin);
                        if (bzjList != null && bzjList.size() > 0) {
                            for (int j = 0; j < bzjList.size(); j++) {
                                if (bzjList.get(j).getType().equals(replace.split("\\.")[1])) {
                                    replaceValue = dealMoney(String.valueOf(bzjList.get(j).getMoney()));
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("brandCompany.name") || replace.equals("brandCompany.relationP") || replace.equals("brandCompany.telephoneP") || replace.equals("brandCompany.emailP")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        Company company = (Company) field.get(proZujin);
                        if (company != null) {
                            if (replace.split("\\.")[1].equals("name")) {
                                replaceValue = company.getName();
                            }
                            if (replace.split("\\.")[1].equals("relationP")) {
                                replaceValue = company.getRelationP();
                            }
                            if (replace.split("\\.")[1].equals("telephoneP")) {
                                replaceValue = company.getTelephoneP();
                            }
                            if (replace.split("\\.")[1].equals("emailP")) {
                                replaceValue = company.getEmailP();
                            }
                        }
                    } else if (replace.equals("termList.固定租金") || replace.equals("termList.固定租金(优惠阶段)") || replace.equals("termList.提成租金") || replace.equals("termList.提成固定较高租金")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        List<Term> termListZJ = new ArrayList<>();
                        List<Term> termListYHZJ = new ArrayList<>();
                        List<Term> termListTCZJ = new ArrayList<>();
                        List<Term> termListZJCompare = new ArrayList<>();
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getType().equals("regular")) {
                                    termListZJ.add(termList.get(j));
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getType().equals("regularPreferential")) {
                                    termListYHZJ.add(termList.get(j));
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getType().equals("commission")) {
                                    termListTCZJ.add(termList.get(j));
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getType().equals("compare")) {
                                    termListZJCompare.add(termList.get(j));
                                }
                            }
                        }
                        if (replace.split("\\.")[1].equals("固定租金")) {
                            replaceValue = dealGDZJ(termListZJ);
                        }
                        if (replace.split("\\.")[1].equals("固定租金(优惠阶段)")) {
                            replaceValue = dealYHZJ(termListYHZJ);
                        }
                        if (replace.split("\\.")[1].equals("提成租金")) {
                            replaceValue = dealTCZJ(termListTCZJ);
                        }
                        if (replace.split("\\.")[1].equals("提成固定较高租金")) {
                            replaceValue = dealCompareZJ(termListZJCompare);
                        }
                    } else if (replace.equals("termList.租金方式")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        String zjType = "";
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("租金")) {
                                    if (termList.get(j).getType().equals("regular") || termList.get(j).getType().equals("regularPreferential")) {
                                        zjType = zjType + "一 ";
                                        break;
                                    }
                                    if (termList.get(j).getType().equals("commission")) {
                                        zjType = zjType + "二 ";
                                        break;
                                    }
                                    if (termList.get(j).getType().equals("compare")) {
                                        zjType = zjType + "三 ";
                                        break;
                                    }
                                }
                            }
                        }
                        replaceValue = zjType;
                    } else if (replace.equals("termList.管理费单价")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("管理费")) {
                                    replaceValue = String.valueOf(termList.get(j).getPrice());
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("termList.管理费单价.大写")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("管理费")) {
                                    replaceValue = dealMoney(String.valueOf(termList.get(j).getPrice()));
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("termList.管理费")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("管理费")) {
                                    replaceValue = String.valueOf(termList.get(j).getMoney());
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("termList.管理费.大写")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null && termList.size() > 0) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("管理费")) {
                                    replaceValue = dealMoney(String.valueOf(termList.get(j).getMoney()));
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("termList.首期租金开始日期") || replace.equals("termList.首期租金截止日期") || replace.equals("termList.首期租金金额") || replace.equals("termList.首期租金金额大写")
                            || replace.equals("termList.首期物业服务费开始日期") || replace.equals("termList.首期物业服务费截止日期") || replace.equals("termList.首期物业服务费金额") || replace.equals("termList.首期物业服务费金额大写")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<Term> termList = (List<Term>) field.get(proZujin);
                        if (termList != null) {
                            for (int j = 0; j < termList.size(); j++) {
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getFirstStartDate() != null && replace.split("\\.")[1].equals("首期租金开始日期")) {
                                    replaceValue = DateUtil.format(termList.get(j).getFirstStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月");
                                    break;
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getFirstEndDate() != null && replace.split("\\.")[1].equals("首期租金截止日期")) {
                                    replaceValue = DateUtil.format(termList.get(j).getFirstEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月");
                                    break;
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getFirstMoney() != null && replace.split("\\.")[1].equals("首期租金金额")) {
                                    replaceValue = String.valueOf(termList.get(j).getFirstMoney());
                                    break;
                                }
                                if (termList.get(j).getName().equals("租金") && termList.get(j).getFirstMoney() != null && replace.split("\\.")[1].equals("首期租金金额大写")) {
                                    replaceValue = dealMoney(String.valueOf(termList.get(j).getFirstMoney()));
                                    break;
                                }
                                if (termList.get(j).getName().equals("管理费") && termList.get(j).getFirstStartDate() != null && replace.split("\\.")[1].equals("首期物业服务费开始日期")) {
                                    replaceValue = DateUtil.format(termList.get(j).getFirstStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月");
                                    break;
                                }
                                if (termList.get(j).getName().equals("管理费") && termList.get(j).getFirstEndDate() != null && replace.split("\\.")[1].equals("首期物业服务费截止日期")) {
                                    replaceValue = DateUtil.format(termList.get(j).getFirstEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月");
                                    break;
                                }
                                if (termList.get(j).getName().equals("管理费") && termList.get(j).getFirstMoney() != null && replace.split("\\.")[1].equals("首期物业服务费金额")) {
                                    replaceValue = String.valueOf(termList.get(j).getFirstMoney());
                                    break;
                                }
                                if (termList.get(j).getName().equals("管理费") && termList.get(j).getFirstMoney() != null && replace.split("\\.")[1].equals("首期物业服务费金额大写")) {
                                    replaceValue = dealMoney(String.valueOf(termList.get(j).getFirstMoney()));
                                    break;
                                }
                            }
                        }
                    } else if (replace.equals("tgfList.times") || replace.equals("tgfList.price") || replace.equals("tgfList.money") || replace.equals("tgfList.voucher")
                            || replace.equals("tgfList.liquidatedDamages") || replace.equals("tgfList.firstBar") || replace.equals("tgfList.secondBar") || replace.equals("tgfList.otherBar")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        List<ProZujinPromotion> tgfList = (List<ProZujinPromotion>) field.get(proZujin);
                        if (tgfList != null && tgfList.size() > 0) {
                            //暂时只取第一个
                            if (replace.split("\\.")[1].equals("times")) {
                                replaceValue = String.valueOf(tgfList.get(0).getTimes());
                            }
                            if (replace.split("\\.")[1].equals("price")) {
                                replaceValue = String.valueOf(tgfList.get(0).getPrice());
                            }
                            if (replace.split("\\.")[1].equals("money")) {
                                replaceValue = String.valueOf(tgfList.get(0).getMoney());
                            }
                            if (replace.split("\\.")[1].equals("voucher")) {
                                replaceValue = String.valueOf(tgfList.get(0).getVoucher());
                            }
                            if (replace.split("\\.")[1].equals("liquidatedDamages")) {
                                replaceValue = String.valueOf(tgfList.get(0).getLiquidatedDamages());
                            }
                            if (replace.split("\\.")[1].equals("firstBar")) {
                                replaceValue = String.valueOf(tgfList.get(0).getFirstBar());
                            }
                            if (replace.split("\\.")[1].equals("secondBar")) {
                                replaceValue = String.valueOf(tgfList.get(0).getSecondBar());
                            }
                            if (replace.split("\\.")[1].equals("otherBar")) {
                                replaceValue = String.valueOf(tgfList.get(0).getOtherBar());
                            }
                        }
                    } else if (replace.equals("#receivedCompany.emailP#")) {
                        String s = replace.split("\\.")[0];
                        Field field = ProZujin.class.getDeclaredField(s);
                        field.setAccessible(true);
                        Company company = (Company) field.get(proZujin);
                        if (company != null) {
                            if (replace.split("\\.")[1].equals("emailP")) {
                                replaceValue = company.getEmailP();
                            }
                        }
                    } else {
                        Field field = ProZujin.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        replaceValue = field.get(proZujin);
                    }
                    richText = richText.replace(contractWordModelParams.get(i).getMarkName(), String.valueOf(replaceValue != null ? replaceValue : ""));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return richText;
    }


    //固定租金转化
    public String dealGDZJ(List<Term> termListZJ) {
        String res = "";

        String trRichText = "<tr style=\"height:45px\">\n" +
                "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【#startDate#】日至【#endDate#】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"181\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span>#price#</span></td>\n" +
                "                </p>\n" +
                "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "        </tr>\n";
        if (termListZJ == null || termListZJ.size() == 0) {
            return "<table width=\"492\">\n" +
                    "    <tbody>\n" +
                    "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                    "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"181\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元/平方米）</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr style=\"height:45px\">\n" +
                    "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;</span><span style=\"font-family:微软雅黑;font-size:12px\">&nbsp;</span><span style=\";font-family:微软雅黑;font-size:12px\">】日</span>\n" +
                    "                </p>\n" +
                    "            </td>" +
                    "            <td width=\"181\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "        </tr>" +
                    "    </tbody>\n" +
                    "</table>";
        }
        for (int i = 0; i < termListZJ.size(); i++) {
            res = res + trRichText.replace("#startDate#", DateUtil.format(termListZJ.get(i).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#endDate#", DateUtil.format(termListZJ.get(i).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#price#", String.valueOf(termListZJ.get(i).getPrice()));
        }
        String tableRT = "<table width=\"492\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"181\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元/平方米）</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                res +
                "    </tbody>\n" +
                "</table>";

        return tableRT;
    }

    //优惠租金转化
    public String dealYHZJ(List<Term> termListYHZJ) {
        String res = "";
        String tableRT = "<table width=\"491\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:40px\" class=\"firstRow\">\n" +
                "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">优惠阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元</span><span style=\";font-family:微软雅黑;font-size:12px\">/平方米</span><span style=\"font-family:微软雅黑;font-size:12px\">）</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr style=\"height:59px\">\n" +
                "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【#startDate#】日至【#endDate#】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: rgb(0, 0, 0); border-top: none; border-bottom-width: 1px; border-bottom-color: rgb(0, 0, 0);\">" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span>#price#</span>" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\" valign=\"top\" rowspan=\"#表行数#\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">租金优惠期间，乙方除享受租金优惠外，本合同及其附件中规定由乙方履行的一切义务和约束，均不予免除。</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>";

        String trRichText = "<tr style=\"height:45px\">\n" +
                "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【#startDate#】日至【#endDate#】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"181\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span>#price#</td><span>\n" +
                "                </p>\n" +
                "        </tr>\n";

        if (termListYHZJ == null || termListYHZJ.size() == 0) {
            return "<table width=\"491\">\n" +
                    "    <tbody>\n" +
                    "        <tr style=\"height:40px\" class=\"firstRow\">\n" +
                    "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">优惠阶段</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元</span><span style=\";font-family:微软雅黑;font-size:12px\">/平方米</span><span style=\"font-family:微软雅黑;font-size:12px\">）</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"123\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr style=\"height:59px\">\n" +
                    "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;</span><span style=\"font-family:微软雅黑;font-size:12px\">&nbsp;</span><span style=\";font-family:微软雅黑;font-size:12px\">】日</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: rgb(0, 0, 0); border-top: none; border-bottom-width: 1px; border-bottom-color: rgb(0, 0, 0);\"></td>\n" +
                    "            <td width=\"123\" valign=\"top\" rowspan=\"2\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">租金优惠期间，乙方除享受租金优惠外，本合同及其附件中规定由乙方履行的一切义务和约束，均不予免除。</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr style=\"height:59px\">\n" +
                    "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;</span><span style=\"font-family:微软雅黑;font-size:12px\">&nbsp;</span><span style=\";font-family:微软雅黑;font-size:12px\">】日</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: rgb(0, 0, 0); border-top: none; border-bottom-width: 1px; border-bottom-color: rgb(0, 0, 0);\"></td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";
        }

        if (termListYHZJ != null && termListYHZJ.size() == 1) {
            return tableRT.replace("#startDate#", DateUtil.format(termListYHZJ.get(0).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#endDate#", DateUtil.format(termListYHZJ.get(0).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#price#", String.valueOf(termListYHZJ.get(0).getPrice()))
                    .replace("#表行数#", "1");
        }
        for (int i = 1; i < termListYHZJ.size(); i++) {
            res = res + trRichText.replace("#startDate#", DateUtil.format(termListYHZJ.get(i).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#endDate#", DateUtil.format(termListYHZJ.get(i).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#price#", String.valueOf(termListYHZJ.get(i).getPrice()));
        }

        String tableRes = "<table width=\"491\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:40px\" class=\"firstRow\">\n" +
                "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">优惠阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元</span><span style=\";font-family:微软雅黑;font-size:12px\">/平方米</span><span style=\"font-family:微软雅黑;font-size:12px\">）</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr style=\"height:59px\">\n" +
                "            <td width=\"186\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【" + DateUtil.format(termListYHZJ.get(0).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月") + "】日至【" +
                DateUtil.format(termListYHZJ.get(0).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月") + "】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: rgb(0, 0, 0); border-top: none; border-bottom-width: 1px; border-bottom-color: rgb(0, 0, 0);\">" +
                "                <p style=\";text-align:center\">\n" +
                termListYHZJ.get(0).getPrice() +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\" valign=\"top\" rowspan=\"" + String.valueOf(termListYHZJ.size()) + "\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">租金优惠期间，乙方除享受租金优惠外，本合同及其附件中规定由乙方履行的一切义务和约束，均不予免除。</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                res +
                "    </tbody>\n" +
                "</table>";
        return tableRes;
    }

    //提成租金转化
    public String dealTCZJ(List<Term> termListTCZJ) {
        String res = "";
        String trRichText = "        <tr style=\"height:45px\">\n" +
                "            <td width=\"182\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【#startDate#】日至【#endDate#】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">" +
                "                <p style=\";text-align:center\">\n" +
                "                   <span>#money#</span>" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "        </tr>\n";

        if (termListTCZJ == null || termListTCZJ.size() == 0) {
            return "<table width=\"492\">\n" +
                    "    <tbody>\n" +
                    "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                    "            <td width=\"182\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">固定扣点</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr style=\"height:45px\">\n" +
                    "            <td width=\"182\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";
        }

        for (int i = 0; i < termListTCZJ.size(); i++) {
            res = res + trRichText.replace("#startDate#", DateUtil.format(termListTCZJ.get(i).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#endDate#", DateUtil.format(termListTCZJ.get(i).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#money#", String.valueOf(termListTCZJ.get(i).getMoney()));
        }

        String tableRes = "<table width=\"492\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                "            <td width=\"182\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"187\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">固定扣点</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"124\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                res +
                "    </tbody>\n" +
                "</table>";
        return tableRes;
    }

    //固定提成较高租金转化
    public String dealCompareZJ(List<Term> termListCompare) {
        String res = "";
        String tableRT = "<table width=\"495\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元/平方米/月）</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">固定扣点</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr style=\"height:45px\">\n" +
                "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>";

        String trRichText = "<tr style=\"height:45px\">\n" +
                "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p>\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【#startDate#】日至【#endDate#】日</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span>#price#</span>" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">#money#</td>\n" +
                "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                "           </tr>\n";

        if (termListCompare == null || termListCompare.size() == 0) {
            return "<table width=\"495\">\n" +
                    "    <tbody>\n" +
                    "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                    "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元/平方米/月）</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">固定扣点</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p style=\";text-align:center\">\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr style=\"height:45px\">\n" +
                    "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-left-width: 1px; border-left-color: windowtext; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                    "                <p>\n" +
                    "                    <span style=\";font-family:微软雅黑;font-size:12px\">自【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日至【 &nbsp;&nbsp;&nbsp;】年【 &nbsp;】月【 &nbsp;】日</span>\n" +
                    "                </p>\n" +
                    "            </td>\n" +
                    "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top: none; border-bottom-width: 1px; border-bottom-color: windowtext;\"></td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";
        }

        for (int i = 0; i < termListCompare.size(); i++) {
            res = res + trRichText.replace("#startDate#", DateUtil.format(termListCompare.get(i).getStartDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#endDate#", DateUtil.format(termListCompare.get(i).getEndDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE).replaceFirst("-", "年").replaceFirst("-", "月"))
                    .replace("#price#", String.valueOf(termListCompare.get(i).getPrice()))
                    .replace("money", String.valueOf(termListCompare.get(i).getMoney()));
        }

        return "<table width=\"495\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"height:25px\" class=\"firstRow\">\n" +
                "            <td width=\"184\" valign=\"center\" style=\"padding: 0px 7px; border-width: 1px; border-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">计租阶段</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"162\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">月租金标准（元/平方米/月）</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"69\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">固定扣点</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"80\" valign=\"center\" style=\"padding: 0px 7px; border-left: none; border-right-width: 1px; border-right-color: windowtext; border-top-width: 1px; border-top-color: windowtext; border-bottom-width: 1px; border-bottom-color: windowtext;\">\n" +
                "                <p style=\";text-align:center\">\n" +
                "                    <span style=\";font-family:微软雅黑;font-size:12px\">备注</span>\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                res +
                "    </tbody>\n" +
                "</table>";
    }


    //金额大写
    public static String dealMoney(String inputStr) {
        StringBuilder resultStrBuld = new StringBuilder();
        String[] split = inputStr.split("\\.");
        String intStr = split[0];
        //@@@@@@@@@@处理整数部分
        try {
            //四位间隔的大单位
            String BigUnit[] = {"亿", "万", "元"};
            //四位之间的小单位
            String smallUnit[] = {"千", "百", "十", ""};
            String[] upNum = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
            if (intStr.matches("^[0]+$")) {
                //如果全是输入的 0 返回
                if (split.length == 2 && split[1].matches("^[0]+$")) {
                    return "零元零角零分";
                }
            }
            //去掉以整数部分为0开头的情况 eg:0000456 return 456
            intStr = cutFrontZeros(intStr);
            //按照四位进行处理进行转换，length是处理的次数
            int length = intStr.length() / 4;
            if ((intStr.length() % 4) > 0) {
                //有余数次数+1
                length++;
            }
            //获取截取的前索引
            int indexS = 0;
            int indexE = 0;
            //处理四位间隔数据
            for (int i = 0; i < length; i++) {
                //取大单位的索引
                int Bindex = 0;
                if (length < 3) {
                    Bindex = i + (3 - length);
                } else {
                    Bindex = i;
                }
                //分割索引
                if (i == 0 && ((intStr.length() % 4) > 0)) {
                    indexE = intStr.length() % 4;
                } else {
                    indexE = indexE + 4;
                }
                String substrNum = intStr.substring(indexS, indexE);
                //处理四位之间数据
                for (int j = 0; j < substrNum.length(); j++) {
                    //判断截取字符串之后是否全是0
                    boolean subStrAllzero = false;
                    //substrNum后面全是0
                    if (substrNum.substring(j, substrNum.length()).matches("^[0]+$")) {
                        subStrAllzero = true;
                    }
                    //判断这一位是否是0
                    boolean thisCharIs = false;
                    if ("0".equals(String.valueOf(substrNum.charAt(j)))) {
                        thisCharIs = true;
                    } else {
                        thisCharIs = false;
                    }
                    //取小单位索引
                    int Sindex = 0;
                    if (substrNum.length() != 4) {
                        Sindex = Sindex + (4 - substrNum.length() + j);
                    } else {
                        Sindex = j;
                    }
                    /*拼接数字对应汉字
                        如果后面全是0拼接单位，并结束循环(需要剔除0000这种情况)
                        否则拼接大写汉字,如果上一位且这一位是零不拼接汉字
                        */
                    if (subStrAllzero) {
                        if (j != 0 || i == (length - 1)) {
                            resultStrBuld.append(BigUnit[Bindex]);
                        }
                        break;
                    } else { //if((!lastCharIs || !thisCharIs))
                        if (resultStrBuld.toString().endsWith("零") && thisCharIs) {

                        } else {
                            resultStrBuld.append(upNum[Integer.parseInt(String.valueOf(substrNum.charAt(j)))]);
                        }
                    }
                    /*
                     * 拼接单位
                     *   如果是最后一位，拼接大单位
                     *   否则拼接小单位
                     *       如果上一位是零则不拼接单位，等非零情况拼接单位
                     * */
                    if ((j == (substrNum.length() - 1))) {
                        resultStrBuld.append(BigUnit[Bindex]);
                    } else {
                        if (!resultStrBuld.toString().endsWith("零")) {
                            resultStrBuld.append(smallUnit[Sindex]);
                        }
                    }
                }
                //重置字符串截取的开始索引。
                indexS = indexE;
            }
            //@@@@@@@@@@处理整数部分 END

            //@@@@@@@@@@处理小数部分
            if (split.length == 2) {
                String point = split[1];
                if (point.length() == 1) {
                    resultStrBuld.append(upNum[Integer.parseInt(String.valueOf(point.charAt(0)))]).append("角").append("零分");
                } else {
                    resultStrBuld.append(upNum[Integer.parseInt(String.valueOf(point.charAt(0)))]).append("角").append(upNum[Integer.parseInt(String.valueOf(point.charAt(1)))]).append("分");
                }
            } else {
                resultStrBuld.append("整");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
        return resultStrBuld.toString();
    }

    public static String cutFrontZeros(String str) {
        //如果全为整数部分全为0，保留一个0
        if ("".equals(str.replaceAll("0", ""))) {
            return "0";
        }
        String s = str;
        if (str.startsWith("0")) {
            s = cutFrontZeros(str.substring(1, str.length()));
        }
        return s;
    }

    /*********************************场地使用合同***********************************/

    @Override
    public int insertPlaceContract(PlaceUseContract placeUseContract) {
        ContractWordModel contractWordModel = contractWordModelService.selectByType(placeUseContract.getType());
        if (ObjectUtils.isEmpty(contractWordModel)) {
            return 0;
        }
        ContractWordRecord contractWordRecord = new ContractWordRecord();
        contractWordRecord.setId(UUID.randomUUID().toString());
        contractWordRecord.setRecordTime(DateUtil.getDatetime());
        contractWordRecord.setRichText(getRichTextPlaceContract(placeUseContract, contractWordModel));
        contractWordRecord.setContractId(placeUseContract.getId());
        contractWordRecord.setType(placeUseContract.getType());
        return contractWordRecordMapper.insert(contractWordRecord);
    }

    public String getRichTextPlaceContract(PlaceUseContract placeUseContract, ContractWordModel contractWordModel) {
        String richText = contractWordModel.getRichText();
        String paramsArr = contractWordModel.getParamsArr();
        if (paramsArr.equals("")) {
            return richText;
        }
        String[] arr = paramsArr.split(",");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            ids.add(Integer.valueOf(arr[i]));
        }

        List<ContractWordModelParams> contractWordModelParams = contractWordModelParamsService.listByIds(ids);
        if (contractWordModelParams.size() > 0) {
            for (int i = 0; i < contractWordModelParams.size(); i++) {
                String replace = contractWordModelParams.get(i).getMarkName().replace("#", "");
                Object replaceValue = "";
                try {
                    if (replace.equals("partB.name") || replace.equals("partB.idNumber") || replace.equals("partB.address") || replace.equals("partB.telephoneP")
                            || replace.equals("partB.openAccount") || replace.equals("partB.bankNumber")) {
                        String s = replace.split("\\.")[0];
                        Field field = PlaceUseContract.class.getDeclaredField(s);
                        field.setAccessible(true);
                        Company company = (Company) field.get(placeUseContract);
                        if (company != null) {
                            if (replace.split("\\.")[1].equals("name")) {
                                replaceValue = company.getName();
                            }
                            if (replace.split("\\.")[1].equals("idNumber")) {
                                replaceValue = company.getIdNumber();
                            }
                            if (replace.split("\\.")[1].equals("address")) {
                                replaceValue = company.getAddress();
                            }
                            if (replace.split("\\.")[1].equals("telephoneP")) {
                                replaceValue = company.getTelephoneP();
                            }
                            if (replace.split("\\.")[1].equals("openAccount")) {
                                replaceValue = company.getOpenAccount();
                            }
                            if (replace.split("\\.")[1].equals("bankNumber")) {
                                replaceValue = company.getBankNumber();
                            }
                        }
                    } else if (replace.equals("electricType.1") || replace.equals("electricType.2") || replace.equals("electricType.3") || replace.equals("electricType.4")) {
                        String s = replace.split("\\.")[0];
                        Field field = PlaceUseContract.class.getDeclaredField(s);
                        field.setAccessible(true);
                        int electricType = (int) field.get(placeUseContract);
                        if ((replace.equals("electricType.1") && electricType == 0) || (replace.equals("electricType.2") && electricType == 1)
                                || (replace.equals("electricType.3") && electricType == 2) || (replace.equals("electricType.4") && electricType == 3)) {
                            replaceValue = "√";
                        } else {
                            replaceValue = "□";
                        }
//                        if (replace.equals("electricType.2") && electricType == 1){
//                            replaceValue = "√";
//                        } else {
//                            replaceValue = "□";
//                        }
//                        if (replace.equals("electricType.3") && electricType == 2){
//                            replaceValue = "√";
//                        } else {
//                            replaceValue = "□";
//                        }
//                        if (replace.equals("electricType.4") && electricType == 3){
//                            replaceValue = "√";
//                        } else {
//                            replaceValue = "□";
//                        }
                    } else if (replace.equals("electricPrice.1") || replace.equals("electricPrice.2") || replace.equals("electricPrice.3") || replace.equals("electricPrice.4")) {
                        String s = replace.split("\\.")[0];
                        Field field = PlaceUseContract.class.getDeclaredField(s);
                        field.setAccessible(true);
                        Field field2 = PlaceUseContract.class.getDeclaredField("electricType");
                        field2.setAccessible(true);
                        String electricPrice = (String) field.get(placeUseContract);
                        int electricType = (int) field2.get(placeUseContract);
                        if (replace.equals("electricPrice.1")) {
                            replaceValue = "实时电价";
                        }
                        if (replace.equals("electricPrice.2")) {
                            replaceValue = "实时电价";
                        }
                        if (electricType == 2 && !electricPrice.equals("实时电价") && replace.equals("electricPrice.3")) {
                            replaceValue = electricPrice;
                        }
                        if (electricType == 3 && !electricPrice.equals("实时电价") && replace.equals("electricPrice.4")) {
                            replaceValue = electricPrice;
                        }
                    } else {
                        Field field = PlaceUseContract.class.getDeclaredField(replace);
                        field.setAccessible(true);
                        replaceValue = field.get(placeUseContract);
                    }
                    richText = richText.replace(contractWordModelParams.get(i).getMarkName(), String.valueOf(replaceValue != null ? replaceValue : ""));

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return richText;
    }

    /*********************************广告位合同***********************************/
    @Override
    public int insertAdvertPlaceContract(AdvertPlaceContract advertPlaceContract) {
        ContractWordModel contractWordModel = contractWordModelService.selectByType(advertPlaceContract.getType());
        if (ObjectUtils.isEmpty(contractWordModel)) {
            return 0;
        }
        ContractWordRecord contractWordRecord = new ContractWordRecord();
        contractWordRecord.setId(UUID.randomUUID().toString());
        contractWordRecord.setRecordTime(DateUtil.getDatetime());
        contractWordRecord.setRichText(getRichTextAdvertPlaceContract(advertPlaceContract, contractWordModel));
        contractWordRecord.setContractId(advertPlaceContract.getId());
        contractWordRecord.setType(advertPlaceContract.getType());
        return contractWordRecordMapper.insert(contractWordRecord);
    }

    public String getRichTextAdvertPlaceContract(AdvertPlaceContract advertPlaceContract, ContractWordModel contractWordModel) {
        String richText = contractWordModel.getRichText();
        String paramsArr = contractWordModel.getParamsArr();
        if (paramsArr.equals("")) {
            return richText;
        }
        String[] arr = paramsArr.split(",");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            ids.add(Integer.valueOf(arr[i]));
        }

        List<ContractWordModelParams> contractWordModelParams = contractWordModelParamsService.listByIds(ids);
        if (contractWordModelParams.size() > 0) {
            for (int i = 0; i < contractWordModelParams.size(); i++) {
                String replace = contractWordModelParams.get(i).getMarkName().replace("#", "");
                Object replaceValue = "";
                try {
                    Field field = AdvertPlaceContract.class.getDeclaredField(replace);
                    field.setAccessible(true);
                    replaceValue = field.get(advertPlaceContract);
                    richText = richText.replace(contractWordModelParams.get(i).getMarkName(), String.valueOf(replaceValue != null ? replaceValue : ""));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return richText;
    }


    /*********************************广告位合同***********************************/
    @Override
    public int insertProZujinEnd(ProZujinEnd proZujinEnd) {
        ContractWordModel contractWordModel = contractWordModelService.selectByType(proZujinEnd.getType());
        if (ObjectUtils.isEmpty(contractWordModel)) {
            return 0;
        }
        ContractWordRecord contractWordRecord = new ContractWordRecord();
        contractWordRecord.setId(UUID.randomUUID().toString());
        contractWordRecord.setRecordTime(DateUtil.getDatetime());
        contractWordRecord.setRichText(getRichTextProZujinEnd(proZujinEnd, contractWordModel));
        contractWordRecord.setContractId(proZujinEnd.getId());
        contractWordRecord.setType(proZujinEnd.getType());
        return contractWordRecordMapper.insert(contractWordRecord);
    }

    public String getRichTextProZujinEnd(ProZujinEnd proZujinEnd,ContractWordModel contractWordModel){
        String richText = contractWordModel.getRichText();
        String paramsArr = contractWordModel.getParamsArr();
        if (paramsArr.equals("")) {
            return richText;
        }
        String[] arr = paramsArr.split(",");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            ids.add(Integer.valueOf(arr[i]));
        }

        List<ContractWordModelParams> contractWordModelParams = contractWordModelParamsService.listByIds(ids);
        if (contractWordModelParams.size() > 0) {
            for (int i = 0; i < contractWordModelParams.size(); i++) {
                String replace = contractWordModelParams.get(i).getMarkName().replace("#", "");
                Object replaceValue = "";
                try {
                    Field field = ProZujinEnd.class.getDeclaredField(replace);
                    field.setAccessible(true);
                    replaceValue = field.get(proZujinEnd);
                    richText = richText.replace(contractWordModelParams.get(i).getMarkName(), String.valueOf(replaceValue != null ? replaceValue : ""));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return richText;
    }
}
