package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.AdvertPlaceContract;
import com.yuesheng.pm.entity.ContractWordRecord;
import com.yuesheng.pm.entity.PlaceUseContract;
import com.yuesheng.pm.entity.ProZujin;

/**
 * @ClassName ContractWordRecordService
 * @Description
 * @Author ssk
 * @Date 2024/2/2 0002 15:11
 */
public interface ContractWordRecordService {
    ContractWordRecord selectLastOneByContractId(String contractId);
    int insert(ProZujin proZujin);

    int insertPlaceContract(PlaceUseContract placeUseContract);

    int insertAdvertPlaceContract(AdvertPlaceContract advertPlaceContract);
}
