package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ContractType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-11 合同类型mapper.
 * @author XiaoSong
 * @date 2016/08/11
 */
@Mapper
public interface ContractTypeMapper {
    /**
     * 根据字符串获取合同对象
     * @param str 合同名称
     * @return 合同对象
     */
    ContractType getTypeByStr(String str);

    /**
     * 根据类型id获取类型对象
     * @param id 类型id
     * @return 类型对象
     */
    ContractType getTypeById(String id);
    /**
     * 获取合同类型集合
     * @param parentId 父节点id
     * @return
     */
    List<ContractType> getTypeByParent(@Param("parentId") String parentId);

    /**
     * 检索合同类型集合
     * @param str 检索串
     * @return
     */
    List<ContractType> seek(@Param("str") String str);

    /**
     * 获取合同类型集合
     * @return
     */
    List<ContractType> getContractTypes();

    /**
     * 新增合同类型
     * @param contractType
     * @return
     */
    int insert(ContractType contractType);

    /**
     * 更新
     * @param contractType
     * @return
     */
    int update(ContractType contractType);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);
}
