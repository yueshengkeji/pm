package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.mapper.DatabaseVersionMapper;
import com.yuesheng.pm.service.DatabaseVersionService;
import com.yuesheng.pm.util.DateUtil;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

@Service("databaseVersionService")
public class DatabaseVersionServiceImpl implements DatabaseVersionService {

    @Autowired
    private DatabaseVersionMapper databaseVersionMapper;

    @PostConstruct
    public void autoUpdate() {
        try {
            update();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int update() {
        eachVersionSql();
        return 1;
    }

    @Override
    public String queryVersion(){
        PageHelper.startPage(1,1);
        return databaseVersionMapper.queryNowVersion();
    }

    private void eachVersionSql() {
        //获取最新更新版本日期
        PageHelper.startPage(1, 1);
        String date = databaseVersionMapper.queryNowVersionDate();
        if (StringUtils.isBlank(date)) {
            date = "2015-01-01 00:00:00";
        } else {
            date += " 00:00:00";
        }
        long lastMillis = DateUtil.parse(date).getTime();
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("/")).getPath() + File.separator + "update";
        if (path.indexOf("/") == 0 && StringUtils.startsWith(System.getProperty("os.name"),"Windows")) {
            path = path.substring(1);
        }
        File sqlFolder = new File(path);
        if (!sqlFolder.isDirectory() || !sqlFolder.canRead()) {
            LogManager.getLogger("mylog").info("未找到升级目录：" + sqlFolder.getPath());
        } else {
            File[] files = sqlFolder.listFiles();
            if (!Objects.isNull(files)) {
                for (File file : files) {
                    if (file != null && file.isFile()) {
                        try {
                            FileTime ft = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class).creationTime();
                            long fileMillis = ft.toMillis();
                            // 文件创建时间大于最后更新时间，可以更新
                            if (fileMillis > lastMillis) {
                                //执行升级sql
                                execSql(file);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void execSql(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            //执行sql文件
            execSql(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }
    }

    private void markSqlFile(File file, StringBuffer sb) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("-- &update&");
        bw.newLine();
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private void execSql(BufferedReader br) throws IOException {
        String sqlText;
        StringBuffer sql = new StringBuffer();
        while ((sqlText = br.readLine()) != null) {
            if (StringUtils.startsWithIgnoreCase(sqlText, "--")) {
                continue;
            }
            if (StringUtils.trim(sqlText).equals("go")) {
                //sql语句结束，开始执行
                execSql(sql.toString());
                sql.delete(0, sql.length());
            } else {
                sql.append(sqlText);
                sql.append("\r\n");
                if (sqlText.contains(";")) {
                    //sql语句结束，开始执行
                    execSql(sql.toString());
                    sql.delete(0, sql.length());
                }
            }
        }
        if (sql.length() > 0) {
            execSql(sql.toString());
        }
        br.close();
    }

    private void execSql(String sql) {
        try {
            if (!StringUtils.containsIgnoreCase(sql, "delete from")
                    && !StringUtils.containsIgnoreCase(sql, "drop")
                    && !StringUtils.containsIgnoreCase(sql, "update ")) {
                databaseVersionMapper.execSql(sql);
            }
        } catch (Exception e) {
             LogManager.getLogger(DatabaseVersionServiceImpl.class).error("更新数据库失败：" + e.getMessage());
        }
    }

}
