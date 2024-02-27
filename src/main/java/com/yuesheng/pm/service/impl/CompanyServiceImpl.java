package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.CompanyExtraMapper;
import com.yuesheng.pm.mapper.CompanyMapper;
import com.yuesheng.pm.model.CompanyModel;
import com.yuesheng.pm.service.CompanyService;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.DateFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author XiaoSong
 * @date 2016-08-09
 * 相关单位服务实现
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyExtraMapper companyExtraMapper;

    @Override
    public Company getCompanyById(String id) {
        return companyMapper.getCompanyById(id);
    }

    @Override
    public List<Company> getCompanyBySupply(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return companyMapper.getCompanyBySupply();
    }

    @Override
    public int count(int type) {
        return companyMapper.count(type);
    }

    @Override
    public List<Company> seek(String name) {
        return companyMapper.seek(name);
    }

    @Override
    public List<Count> getSumCount(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return companyMapper.getSumCount();
    }

    @Override
    public List<Company> getCompanyByList(List<Count> count) {
        if (count == null) {
            count = new ArrayList<>();
        }
        if (count.size() <= 0) {
            count = null;
        }
        return companyMapper.getCompanyByList(count);
    }

    @Override
    public List<Count> getSumCountByOut() {
        PageHelper.startPage(1, 20);
        return companyMapper.getSumCountByOut();
    }

    @Override
    public List<Company> seekAll(String str) {
        if (StringUtils.isBlank(str)) {
            List<Count> count = getSumCount(0, 20);
            if (!count.isEmpty()) {
                List<Company> companies = getCompanyByList(count);
                return companies;
            }
        }
        return companyMapper.seekAll(str);
    }

    @Override
    public int getSupplyCount() {
        return companyMapper.getSupplyCount();
    }

    @Override
    public List<Folder> queryFolder(String str) {
        PageHelper.startPage(1, 10);
        return companyMapper.queryFolder(str);
    }

    @Override
    public void insert(Company c) {
        companyMapper.insert(c);
    }

    @Override
    public Company getCompanyByLast(String sc) {
        PageHelper.startPage(1, 1);
        return companyMapper.getCompanyByLast(sc);
    }

    @Override
    public Folder queryFolderById(String folder) {
        return companyMapper.queryFolderById(folder);
    }

    @Override
    public List<CompanyModel> getCompanyByProMax(String start, String end, Integer size) {
        PageHelper.startPage(1, size);
        return companyMapper.getCompanyByProMax(start, end);
    }

    @Override
    public List<Company> getCompanyByParam(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return companyMapper.getCompanyByParam(params);
    }

    @Override
    public int updateCompany(Company company) {
        CompanyExtra companyExtra = new CompanyExtra();
        companyExtra.setId(company.getId());
        companyExtra.setPhone(company.getPhone());
        companyExtra.setStreet(company.getStreet());
        companyExtra.setJurisdiction(company.getJurisdiction());
        companyExtraMapper.update(companyExtra);
        return companyMapper.updateCompany(company);
    }

    @Override
    public Integer getCompanyByParamCount(Map<String, Object> params) {
        return companyMapper.getCompanyByParamCount(params);
    }

    @Override
    public Company insert(Company c, Staff staff) {
        if (verify(c)) {
            c.setLogDate(DateFormat.getDate());
            c.setId(UUID.randomUUID().toString());
            c.setStaff(staff);
            Company temp = getByName(c.getName());
            if (!Objects.isNull(temp)) {
                //已存在该单位
                c.setId(temp.getId());
                return temp;
            }
            insert(c);
            CompanyExtra companyExtra = new CompanyExtra();
            companyExtra.setId(c.getId());
            companyExtra.setJurisdiction(c.getJurisdiction());
            companyExtra.setStreet(c.getStreet());
            companyExtra.setPhone(c.getPhone());
            companyExtraMapper.insert(companyExtra);
        }
        return c;
    }

    @Override
    public Company getByName(String companyName) {
        PageHelper.startPage(1, 1);
        return companyMapper.getByName(companyName);
    }

    @Override
    public List<Company> getByMaterial(String name, String model, String brand) {
        return companyMapper.getByMaterial(name, model, brand);
    }

    @Override
    public List<Company> seekAllV2(String s) {
        return companyMapper.seekAll(s);
    }

    /**
     * 验证单位对象是否合格
     *
     * @param c 单位对象
     * @return
     */
    private boolean verify(Company c) {
        if (c == null) {
            return false;
        }
        if (c.getName() == null || "".equals(c.getName())) {
            return false;
        }
        c.setName(StringUtils.replaceChars(c.getName(), " ", ""));
        if (c.getoFolder() == null) {
            Folder folder = new Folder();
            folder.setId("10YA81JP");
            c.setoFolder(folder);
        }
        c.setFolder(c.getoFolder().getId());

        if (c.getBankNumber() == null) {
            c.setBankNumber("");
        }
        if (c.getAddress() == null) {
            c.setAddress("");
        }
        if (c.getRelationP() == null) {
            c.setRelationP("");
        }
        if (c.getTelephoneP() == null) {
            c.setTelephoneP("");
        }
        if (c.getOpenAccount() == null) {
            c.setOpenAccount("");
        }
        if (c.getAddress() == null) {
            c.setAddress("");
        }
        if (c.getEmailP() == null) {
            c.setEmailP("");
        }
        if (c.getTel() == null) {
            c.setTel("");
        }
        if (c.getBankNumber2() == null) {
            c.setBankNumber2("");
        }
        if (c.getOpenAccount2() == null) {
            c.setOpenAccount2("");
        }
        if (c.getUnitNumber() == null) {
            c.setUnitNumber(DateFormat.getDateForNumber() + AESEncrypt.getFixLenthString(5));
        }
        if (StringUtils.isBlank(c.getLineNum())) {
            c.setLineNum("");
        }
        return true;
    }

}
