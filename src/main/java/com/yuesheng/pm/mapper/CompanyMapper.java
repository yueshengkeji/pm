package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Company;
import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.model.CompanyModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-09 相关单位mapper.
 * @author XiaoSong
 * @date 2016/08/09
 */
@Mapper
public interface CompanyMapper {
    /**
     * 供应商 单位对象
     * @param id 单位id
     * @return 供应商单位对象
     */
    Company getCompanyById(String id);

    /**
     * 根据单位id，获取单位信息
     *
     * @param id
     * @return
     */
    Company getCompanyByIdAll(String id);
    /**
     * 获取供应单位
     * @return 供应单位集合
     */
    List<Company> getCompanyBySupply();

    /**
     * 获取相关单位总数
     * @param type 单位类型
     * @return 单位总数
     */
    int count(int type);

    /**
     * 检索单位集合
     * @param name 单位简称
     * @return
     */
    List<Company> seek(String name);

    /**
     * 获取常用的供应商
     * @return 供应商集合
     */
    List<Count> getSumCount();

    /**
     * 获取单位集合
     * @param count 常用的单位统计
     * @return
     */
    List<Company> getCompanyByList(List<Count> count);

    /**
     * 获取常用的出库单位集合
     * @return 单位集合
     */
    List<Count> getSumCountByOut();

    /**
     * 检索所有单位对象
     * @param str 检索字符串
     * @return 单位集合
     */
    List<Company> seekAll(@Param("name") String str);

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
    List<Folder> queryFolder(@Param("str") String str);

    /**
     * 添加单位
     * @param c
     */
    void insert(Company c);

    /**
     * 获取最后添加的单位对象
     * @param sc 职员编码
     * @return
     */
    Company getCompanyByLast(@Param("sc") String sc);

    /**
     * 获取单位目录
     * @param folder 目录主键
     * @return
     */
    Folder queryFolderById(@Param("folderId") String folder);

    /**
     * 后去购买最多的单位集合
     *
     * @param start 开始日期
     * @param end   截止日期
     * @return
     */
    List<CompanyModel> getCompanyByProMax(@Param("start") String start, @Param("end") String end);

    /**
     * 获取单位集合
     *
     * @param params     {str:检索串，order:排序方式}
     * @return
     */
    List<Company> getCompanyByParam(Map<String, Object> params);

    /**
     * 根据条件，获取总条目数
     *
     * @param params 参见 getCompanyByParam()方法
     * @return
     */
    Integer getCompanyByParamCount(Map<String, Object> params);

    /**
     * 修改单位对象
     *
     * @param company 单位对象
     * @return 影响的行数
     */
    int updateCompany(Company company);

    /**
     * 通过单位名称查询单位信息
     *
     * @param name 单位名称
     * @return
     */
    Company getByName(@Param("name") String name);

    /**
     * 模糊查询材料供应商
     *
     * @param name  材料名称
     * @param model 型号
     * @param brand 品牌
     * @return
     */
    List<Company> getByMaterial(@Param("name") String name, @Param("model") String model, @Param("brand") String brand);
}
