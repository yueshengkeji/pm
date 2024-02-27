package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.mapper.WorkArticleMaterMapper;
import com.yuesheng.pm.model.WorkOutMaterModel;
import com.yuesheng.pm.service.WorkArticleMaterService;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author XiaoSong
 * @date 2016/12/29
 */
@Service("workArticleMaterService")
public class WorkArticleMaterServiceImpl implements WorkArticleMaterService {
    @Autowired
    private WorkArticleMaterMapper workArticleMaterMapper;

    @Override
    public List<MaterOutChild> getArticleMaterByArticleId(String articleId) {
        return workArticleMaterMapper.getArticleMaterByArticleId(articleId);
    }

    @Override
    public MaterOutChild getArticleMaterById(String id) {
        return workArticleMaterMapper.getArticleMaterById(id);
    }

    @Override
    public List<Material> queryMater(Map<String, Object> params,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return workArticleMaterMapper.queryMater(params);
    }

    @Override
    public List<Material> queryMater(Map<String, Object> params) {
        return workArticleMaterMapper.queryMaterV2(params);
    }

    @Override
    public int updateOutSum(Material m) {
        return workArticleMaterMapper.updateOutSum(m);
    }

    @Override
    public int updatePutSum(Material m) {
        return workArticleMaterMapper.updatePutSum(m);
    }

    @Override
    public int queryMaterCount(Map<String, Object> params) {
        return workArticleMaterMapper.queryMaterCount(params);
    }

    @Override
    public Folder queryFolder(String id) {
        return workArticleMaterMapper.queryFolder(id);
    }

    @Override
    public void insert(Material material) {
        if (material == null) {
            return;
        }
        Material m;
        if (!verify(material)) {
            return;
        }
        //判断该材料是否存在
        m = getMaterByParam(material);
        if (m != null)       //材料已存在
        {
            material.setId(m.getId());
            return;
        }
        //设置材料编号
        material.setId(AESEncrypt.getFixLenthString((WebParam.WORK_M_L <= 0 || WebParam.WORK_M_L > 9) ? 9 : WebParam.WORK_M_L));
        /*
        判断编码是否存在
         */
        m = getMaterById(material.getId());
        if (m != null) {
            do {
                material.setId(AESEncrypt.getFixLenthString((WebParam.WORK_M_L <= 0 || WebParam.WORK_M_L > 9) ? 9 : WebParam.WORK_M_L));
                m = getMaterById(material.getId());
            } while (m == null);
        }
        workArticleMaterMapper.insert(material);
    }

    @Override
    public Material getMaterById(String id) {
        return workArticleMaterMapper.getMaterById(id);
    }

    @Override
    public Material getMaterByParam(Material am) {
        PageHelper.startPage(1,1);
        return workArticleMaterMapper.getMaterByParam(am);
    }

    @Override
    public MaterOutChild isUse(String materId) {
        PageHelper.startPage(1,1);
        return workArticleMaterMapper.isUse(materId);
    }

    @Override
    public int deleteMater(String id) {

        return workArticleMaterMapper.deleteMater(id);
    }

    @Override
    public void updateAM(Material material) {
        workArticleMaterMapper.updateAM(material);
    }

    @Override
    public void insertArticle(MaterOutChild m) {
        workArticleMaterMapper.insertArticle(m);
    }

    @Override
    public int updateArticle(MaterOutChild m) {
        return workArticleMaterMapper.updateArticle(m);
    }

    @Override
    public int deleteArticle(String id) {
        return workArticleMaterMapper.deleteArticle(id);
    }

    @Override
    public int deleteArticleByMain(String mainId) {
        return workArticleMaterMapper.deleteArticleByMain(mainId);
    }

    @Override
    public int querysCount(Map<String, Object> param) {
        return workArticleMaterMapper.querysCount(param);
    }

    @Override
    public List<MaterOutChild> querys(Map<String, Object> param,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return workArticleMaterMapper.querys(param);
    }

    @Override
    public Double queryMaterStorage(String id) {
        return workArticleMaterMapper.queryMaterStorage(id);
    }

    @Override
    public Double queryNewPlace(String id) {
        return workArticleMaterMapper.queryNewPlace(id);
    }

    @Override
    public List<WorkOutMaterModel> queryAll(MaterOutChild materOutChild) {
        return workArticleMaterMapper.queryAll(materOutChild);
    }



    private boolean verify(final Material material) {
        if (material == null) {
            return false;
        }
        if (material.getName() == null) {
            return false;
        }
        if (material.getModel() == null) {
            material.setModel("");
        }
        if (material.getBrand() == null) {
            material.setBrand("");
        }
        if (material.getUnitName() == null)      //单位不能为null
        {
            if (material.getUnit() == null || material.getUnit().getName() == null) {
                return false;
            }else{
                material.setUnitName(material.getUnit().getName());
            }
        }
        if (material.getFolder() == null)        //单位目录不能为null
        {
            material.setFolder("D09SZ4$T");
        }
        if (material.getCostPrice() == null) {      //材料成本价格
            material.setCostPrice(0.0);
        }
        return true;
    }


}
