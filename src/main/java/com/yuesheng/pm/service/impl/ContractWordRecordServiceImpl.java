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
        if (ObjectUtils.isEmpty(contractWordModel)){
            return 0;
        }

        ContractWordRecord contractWordRecord = new ContractWordRecord();
        contractWordRecord.setId(UUID.randomUUID().toString());
        contractWordRecord.setRecordTime(DateUtil.getDatetime());
        contractWordRecord.setRichText(getRichText(proZujin,contractWordModel));
        contractWordRecord.setContractId(proZujin.getId());
        contractWordRecord.setType(proZujin.getType());
        return contractWordRecordMapper.insert(contractWordRecord);
    }

    @Override
    public ContractWordRecord selectLastOneByContractId(String contractId) {
        return contractWordRecordMapper.selectLastOneByContractId(contractId);
    }

    public String getRichText(ProZujin proZujin,ContractWordModel contractWordModel){
        String richText = contractWordModel.getRichText();
        String paramsArr = contractWordModel.getParamsArr();
        if (paramsArr.equals("")){
            return richText;
        }
        String[] arr = paramsArr.split(",");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0;i < arr.length;i++){
            ids.add(Integer.valueOf(arr[i]));
        }
        List<ContractWordModelParams> contractWordModelParams = contractWordModelParamsService.listByIds(ids);

        if (contractWordModelParams.size() > 0){
            for (int i = 0;i < contractWordModelParams.size();i++){
                String replace = contractWordModelParams.get(i).getMarkName().replace("#", "");
                Object replaceValue = "";
                try {
                    Field field = ProZujin.class.getDeclaredField(replace);
                    field.setAccessible(true);
                    if (replace.equals("payType")){
                        replaceValue = (int)field.get(proZujin) == 0 ? "年付" : (int)field.get(proZujin) == 1 ? "季度" : "月付";
                    } else if (replace.equals("zlType")){
                        replaceValue = (int)field.get(proZujin) == 0 ? "固定铺位" : (int)field.get(proZujin) == 1 ? "机动" : "临时收费";
                    } else if (replace.equals("yt")){
                        ProZujinYt proZujinYt = (ProZujinYt) field.get(proZujin);
                        replaceValue = proZujinYt.getName();
                    } else if (replace.equals("houses")){
                        List<ProZujinHouse> list = (List<ProZujinHouse>) field.get(proZujin);
                        String str = "";
                        if (list != null && list.size() > 0){
                            for (int j = 0;j < list.size();j++){
                                if (j == list.size() - 1){
                                    str = str + list.get(j).getPwNumber() + "";
                                }
                                str = str + list.get(j).getPwNumber() + ",";
                            }
                        }

                        replaceValue = str;
                    } else if (replace.equals("moneyOwe")){
                        ProDetailOwe proDetailOwe = (ProDetailOwe) field.get(proZujin);
                        if (ObjectUtils.isNotEmpty(proDetailOwe)){
                            replaceValue = String.valueOf(proDetailOwe.getOweMoney());
                        }
                    } else if (replace.equals("billOwe")){
                        ProDetailOwe billOwe = (ProDetailOwe) field.get(proZujin);
                        if (ObjectUtils.isNotEmpty(billOwe)){
                            replaceValue = billOwe.getOweMoney();
                        }
                    } else {
                        replaceValue = String.valueOf(field.get(proZujin));
                    }
                    richText = richText.replace(contractWordModelParams.get(i).getMarkName(), String.valueOf(replaceValue != null ? replaceValue : ""));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return richText;
    }


}
