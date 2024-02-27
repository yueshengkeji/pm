package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Catalogue;
import com.yuesheng.pm.mapper.CatalogueMapper;
import com.yuesheng.pm.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author gui_lin
 * @Description 证书目录管理实现
 * 2022/1/10
 */
@Service("CatalogueService")
public class CatalogueServiceImpl implements CatalogueService {
    @Autowired
    CatalogueMapper catalogueMapper;

    @Override
    public List<Catalogue> selectAllCatalogue(String str) {
        return catalogueMapper.selectAllCatalogue(str);
    }

    @Override
    public void insertCatalogue(Catalogue catalogue) {
        catalogueMapper.insertCatalogue(catalogue);
    }

    @Override
    public void deleteCatalogue(Catalogue catalogue) {
        catalogueMapper.deleteCatalogue(catalogue);
    }

    @Override
    public void updateCatalogue(Catalogue catalogue) {
        catalogueMapper.updateCatalogue(catalogue);
    }

    @Override
    public Boolean toCheck(Catalogue catalogue) {
        return !Objects.isNull(catalogueMapper.toCheck(catalogue));
    }

    @Override
    public List<Catalogue> toChild(Catalogue catalogue) {
        return catalogueMapper.toChild(catalogue);
    }

    @Override
    public List<Catalogue> selectALL() {
        return catalogueMapper.selectALL();
    }

    @Override
    public Catalogue selectCatById(Integer id) {
        return catalogueMapper.selectCatById(id);
    }
}
