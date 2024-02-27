package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.AttachMapper;
import com.yuesheng.pm.service.AttachService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.FtpUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;

/**
 * @author XiaoSong
 * @date 2016/11/17
 */
@Service("attachService")
public class AttachServiceImpl implements AttachService {
    @Autowired
    private AttachMapper attachMapper;

    @Override
    public Attach getById(String id) {
        return attachMapper.getById(id);
    }

    @Override
    public int deleteAttach(String id) {
        return attachMapper.deleteAttach(id);
    }

    @Override
    public int deleteByContractId(String moduleId) {
        return attachMapper.deleteByModoleId(moduleId);
    }


    @Override
    public void addAttachForFJ(Attach attach) {
        attachMapper.addAttachForFj(attach);
    }

    @Override
    public void addAttachRelation(Map<String, String> params) {
        attachMapper.addAttachRelation(params);
    }

    @Override
    public void deleteAttachRelation(Map<String, String> params) {
        attachMapper.deleteAttchRelation(params);
    }

    @Override
    public void insertAttch(@RequestParam("files") MultipartFile[] files, Map<String, String> params, Staff staff, String mainId) {
        Attach attach;
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }
            attach = FtpUtil.multiParse(file, staff, mainId);
            if (attach != null) {
                addAttachForFJ(attach);
                params.put("id", attach.getId());
                addAttachRelation(params);
            }
        }
    }

    @Override
    public List<Attach> getAttachByRelation(Map<String, String> result) {
        return attachMapper.getAttachByRelation(result);
    }

    @Override
    public int deleteAttach(String attachId, String mainId, String tn) {
        String fileTableName = getFileTableName(tn);
        String fix = "";
        String field1;
        String field2;
        if (StringUtils.isNotBlank(fileTableName)) {
            if (!StringUtils.containsIgnoreCase(fileTableName, "pro_")) {
                fix = StringUtils.substring(fileTableName, 2, fileTableName.length());
            } else {
                fix = fileTableName;
            }
            field1 = fix + "01";
            field2 = fix + "02";

            HashMap<String, String> param = new HashMap<>();
            param.put("table", fileTableName);
            param.put("field2", field2);
            param.put("field", field1);
            param.put("moduleId", mainId);
            param.put("attachId", attachId);
            try {
                int row = attachMapper.delete(param);
                deleteAttachRelationById(fileTableName, field1, mainId, field2, attachId);
                return row;
            } catch (Exception e) {
                //ignore this error
            }
        }
        return 0;
    }

    @Override
    public int updatePdfImgs(Attach attach) {
        return attachMapper.updatePdfImgs(attach);
    }

    private int deleteAttachRelationById(String fileTableName, String field1, String mainId, String field2, String attachId) {
        return attachMapper.deleteAttachRelationById(fileTableName, field1, mainId, field2, attachId);
    }

    @Override
    public int deleteAttachByModuleId(String id, String tableName) {
        if (StringUtils.isNotBlank(tableName)) {
            List<Attach> attaches = getAttachByModuleId(id, tableName);
            if (!attaches.isEmpty()) {
                //从FTP删除文件
                attaches.forEach(a -> {
                    try {
                        int index = a.getFileName().lastIndexOf(".");
                        index = index != -1 ? index : a.getFileName().length();
                        String fileName = a.getFileName().substring(0, index)
                                + a.getId() +
                                a.getFileName().substring(index);
                        FtpUtil.deleteFile(fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                String fileTableName = getFileTableName(tableName);
                String fix = "";
                String field1;
                String field2;
                if (StringUtils.isNotBlank(fileTableName)) {
                    if (!StringUtils.containsIgnoreCase(fileTableName, "pro_")) {
                        fix = StringUtils.substring(fileTableName, 2, fileTableName.length());
                    } else {
                        fix = fileTableName;
                    }

                    field1 = fix + "01";
                    field2 = fix + "02";

                    HashMap<String, String> param = new HashMap<>();
                    param.put("table", fileTableName);
                    param.put("field2", field2);
                    param.put("field", field1);
                    param.put("moduleId", id);
                    try {
                        int row = attachMapper.delete(param);
                        deleteAttachRelation(fileTableName, field1, id);
                        return row;
                    } catch (Exception e) {
                        //ignore this error
                    }
                }
            }

        }
        return 0;
    }

    @Override
    public List<Attach> getAttachByModuleId(String mainId, String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<>();
        }

        String fileTableName = getFileTableName(tableName);
        String field2;
        String fix;
        String field1;

        if (StringUtils.isNotBlank(fileTableName)) {
            if (!StringUtils.containsIgnoreCase(fileTableName, "pro_")) {
                fix = StringUtils.substring(fileTableName, 2, fileTableName.length());
            } else {
                fix = fileTableName;
            }

            field1 = fix + "01";
            field2 = fix + "02";

            HashMap<String, String> param = new HashMap<>();
            param.put("table", fileTableName);
            param.put("field2", field2);
            param.put("field", field1);
            param.put("moduleId", mainId);

            try {
                return attachMapper.getAttachByRelation(param);
            } catch (Exception e) {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    private String getFileTableName(String tableName) {
        String fileTableName = Constant.FILE_TABLE_MAP.get(tableName);
        String field1 = "";
        String field2 = "";
        String fix = "";
        if (StringUtils.isBlank(fileTableName)) {
            //不存在附件表，开始查询数据库Fj开头过的关系表
            fileTableName = tableName + "FJ";
            fileTableName = attachMapper.getTableInfo(fileTableName);
        }
        return fileTableName;
    }


    @Override
    public void deleteAttachRelation(String tableName, String fieldName, String moduleId) {
        attachMapper.deleteAttachRelation(tableName, fieldName, moduleId);
    }

    @Override
    public void deleteCatchByDaysAgo(Integer days) throws IOException {
        Date startDate = DateUtil.rollDay(new Date(), -days);
        String ed = DateUtil.format(startDate);
        String sd = DateUtil.format(DateUtil.rollDay(startDate, 1));
        int page = 1;
        int x = 1;
        for (; x <= page; x++) {
            PageHelper.startPage(x, 100);
            Page<Attach> attachPage = (Page<Attach>) attachMapper.getAttachByDate(sd, ed);
            if (x == 1) {
                page = attachPage.getPages();
            }
            attachPage.forEach(item -> {
                FileUtils.deleteQuietly(new File(WebParam.assetsPath + "ftptemp" + File.separator + item.getId() + ".JPEG"));
            });
        }

        File file = new File(WebParam.assetsPath + "ftptemp");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            deleteFiles(days, files);
            if (!Objects.isNull(files)) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFiles(days, f.listFiles());
                    }
                }
            }
        }
    }

    @Override
    public void clearTempAssets(Integer offsetDay) throws IOException {
        File file = new File(WebParam.assetsPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            deleteFiles(offsetDay, files);
        }
        deleteCatchByDaysAgo(offsetDay);
    }

    @Override
    public String findId(String name, String url) {
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(name)) {
            int idx = StringUtils.lastIndexOf(name, ".");
            if (idx != -1) {
                String replaceStr = StringUtils.substring(name, 0, idx);
                String fileId = StringUtils.replace(url, replaceStr, "");
                idx = StringUtils.lastIndexOf(fileId, ".");
                idx = idx == -1 ? fileId.length() : idx;
                fileId = StringUtils.substring(fileId, 0, idx);
                return fileId;
            }
        }
        return null;
    }

    private int deleteFiles(int offsetDay, File[] files) throws IOException {
        int delNum = 0;
        if (!Objects.isNull(files)) {
            for (File f : files) {
                if (deleteLongTimeFile(offsetDay, f)) {
                    delNum++;
                }
            }
        }
        return delNum;
    }

    private static boolean deleteLongTimeFile(Integer offsetDay, File f) throws IOException {
        if (f.isFile()) {
            FileTime ft = Files.readAttributes(Paths.get(f.getPath()), BasicFileAttributes.class).creationTime();
            int day = DateUtil.getOffsetDays(new Date(ft.toMillis()), new Date());
            if (day > offsetDay) {
                return FileUtils.deleteQuietly(f);
            }
        }
        return false;
    }
}
