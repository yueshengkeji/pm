package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin
public class BaseApi {
    public Page startPage(Integer page, Integer pageSize, String[] sortBy, Boolean[] sortDesc) {
        StringBuffer sortString = new StringBuffer();
        if (!Objects.isNull(sortBy)) {
            int x = 0;
            for (String name : sortBy) {
                sortString.append(humpToLine(name));
                sortString.append(" ");
                sortString.append((sortDesc[x] ? "desc" : "asc"));
                sortString.append(",");
            }
            if (sortString.length() > 0) {
                sortString = sortString.delete(sortString.length() - 1, sortString.length());
            }
        }
        if(Objects.isNull(page)){
            page = 1;
        }
        if (Objects.isNull(pageSize) || pageSize == -1) {
            pageSize = 1000;
        }
        PageHelper.clearPage();
        return PageHelper.startPage(page, pageSize, sortString.toString());
    }

    public Page startPage(Integer page, Integer pageSize, String sortBy, String sortDesc) {
        if(Objects.isNull(page)){
            page = 1;
        }
        if (Objects.isNull(pageSize) || pageSize == -1) {
            pageSize = 1000;
        }
        if (!Objects.isNull(sortBy)
                && !Objects.isNull(sortDesc)
                && !Objects.isNull(page)
                && !Objects.isNull(pageSize)) {
            PageHelper.clearPage();
            return PageHelper.startPage(page, pageSize, humpToLine(sortBy) + " " + sortDesc);
        } else if (!Objects.isNull(page)
                && !Objects.isNull(pageSize)) {
            PageHelper.clearPage();
            return PageHelper.startPage(page, pageSize);
        }else{
            return null;
        }
    }

    public Page startPage(Integer page, Integer pageSize, String sortBy, Boolean sortDesc) {
        if (!Objects.isNull(sortBy)
                && !Objects.isNull(sortDesc)
                && !Objects.isNull(page)
                && !Objects.isNull(pageSize)) {
            if (pageSize == -1) {
                pageSize = 1000;
            }
            PageHelper.clearPage();
            return PageHelper.startPage(page, pageSize, humpToLine(sortBy) + " " + (sortDesc ? "DESC" : "ASC"));
        } else if (!Objects.isNull(page)
                && !Objects.isNull(pageSize)) {
            if (pageSize == -1) {
                pageSize = 1000;
            }
            PageHelper.clearPage();
            return PageHelper.startPage(page, pageSize);
        }
        return null;
    }
    public Page startPage(Integer page, Integer itemsPerPage, String[] strings, Boolean[] booleans, boolean isCount) {
        Page p = startPage(page,itemsPerPage,strings,booleans);
        p.setCount(isCount);
        return p;
    }

    public Page startPage(Integer page, Integer itemsPerPage, String sortBy, String sortDesc, boolean isCount) {
        Page p = startPage(page, itemsPerPage, sortBy, sortDesc);
        p.setCount(isCount);
        return p;
    }


    public Page startPage(Integer page, Integer itemsPerPage, String sortBy, Boolean sortDesc, boolean isLoad) {
        Page p = startPage(page,itemsPerPage,sortBy,sortDesc);
        p.setCount(isLoad);
        return p;
    }
    public static String humpToLine(String str) {
        try {
            return str.replaceAll("[A-Z]", "_$0").toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }
}
