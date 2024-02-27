package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Company;
import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.CompanyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-09 第三方单位服务接口.
 *
 */
public interface CompanyService {
    /**
     * 单位对象
     * @param id 单位id
     * @return 单位
     */
    Company getCompanyById(String id);

    /**
     * 获取供应单位
     * @return 供应单位集合
     */
    List<Company> getCompanyBySupply(Integer pageNum,Integer pageSize);

    /**
     * 获取相关单位总数
     * @param type 单位类型{1：供应商}
     * @return 单位总数
     */
    int count(int type);

    /**
     * 检索单位集合
     * @param name 单位名称
     * @return 单位集合
     */
    List<Company> seek(String name);

    /**
     * 获取常用的单位集合
     * @return
     */
    List<Count> getSumCount(Integer pageNum,Integer pageSize);

    List<Company> getCompanyByList(List<Count> count);

    /**
     * 获取常出库的20个单位
     * @return 单位集合
     */
    List<Count> getSumCountByOut();

    /**
     * 搜索所有单位对象
     * @param str 检索字符串
     * @return 单位集合
     */
    List<Company> seekAll(String str);

    /**
     * 获取供应单位总数
     * @return
     */
    int getSupplyCount();

    /**
     * 获取单位目录集合
     * @param str 检索串
     * @return
     */
    List<Folder> queryFolder(String str);

    /**
     * 添加单位
     * @param c 单位对象
     */
    void insert(Company c);

    /**
     * 获取最后添加单位对象
     * @param sc 职员编码
     * @return
     */
    Company getCompanyByLast(String sc);

    /**
     * 获取单位目录
     * @param folder 目录主键
     * @return
     */
    Folder queryFolderById(String folder);

    /**
     * 获取购买最多的单位集合
     *
     * @param start 开始日期
     * @param end   截止日期
     * @param size  数据大小
     * @return
     */
    List<CompanyModel> getCompanyByProMax(String start, String end, Integer size);

    /**
     * 获取单位集合
     *
     * @param params     {str:检索串，order:排序方式}
     * @param pageBounds 分页对象
     * @return
     */
    List<Company> getCompanyByParam(Map<String, Object> params,Integer pageNum,Integer pageSize);

    /**
     * 修改单位信息
     *
     * @param company 单位集合
     * @return
     */
    int updateCompany(Company company);

    /**
     * 根据条件，获取总条目数
     *
     * @param params 参见 getCompanyByParam()方法
     * @return
     */
    Integer getCompanyByParamCount(Map<String, Object> params);

    /**
     * 添加单位（自动校验）
     *
     * @param c
     * @param staff
     * @return
     */
    Company insert(Company c, Staff staff);

    /**
     * 通过单位名称查询单位信息
     *
     * @param companyName 单位名称
     * @return
     */
    Company getByName(String companyName);

    /**
     * 模糊查询材料供应商
     *
     * @param name  材料名称
     * @param model 型号
     * @param brand 品牌
     * @return
     */
    List<Company> getByMaterial(String name, String model, String brand);

    List<Company> seekAllV2(String s);
}
