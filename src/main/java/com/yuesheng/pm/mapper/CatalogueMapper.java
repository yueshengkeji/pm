package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Catalogue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gui_lin
 * @Description 描述
 * 2022/1/10
 */
@Mapper
public interface CatalogueMapper {
    /**
     * 获取全部证书目录
     * @return
     */
    List<Catalogue> selectAllCatalogue(@Param("str") String str);

    /**
     * 添加证书目录
     * @param catalogue 目录信息
     */
    void insertCatalogue(Catalogue catalogue);

    /**
     * 删除目录
     * @param catalogue 目录
     */
    void deleteCatalogue(Catalogue catalogue);

    /**
     * 更新证书目录
     * @param catalogue 目录信息
     */
    void updateCatalogue(Catalogue catalogue);

    /**
     * 查询目录是否存在
     * @param catalogue 目录信息
     * @return
     */
    Boolean toCheck(Catalogue catalogue);

    /**
     * 检测是否存在子目录
     * @param catalogue 目录信息
     * @return
     */
    List<Catalogue> toChild(Catalogue catalogue);

    /**
     * 查询全部
     * @return
     */
    List<Catalogue> selectALL();

    /**
     * 根据id查询目录
     * @param id
     * @return
     */
    Catalogue selectCatById(Integer id);
}
