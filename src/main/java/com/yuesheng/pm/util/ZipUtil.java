package com.yuesheng.pm.util;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * 压缩指定文件夹内所有文件
     *
     * @return
     */
    private static void zip(ZipOutputStream output, File file, String childPath) {
        FileInputStream input = null;
        try {
            // 文件为目录
            if (file.isDirectory()) {
                // 得到当前目录里面的文件列表
                File list[] = file.listFiles();
                childPath = childPath + (childPath.length() == 0 ? "" : "/")
                        + file.getName();
                // 循环递归压缩每个文件
                for (File f : list) {
                    zip(output, f, childPath);
                }
            } else {
                // 压缩文件
                childPath = (childPath.length() == 0 ? "" : childPath + "/")
                        + file.getName();
                output.putNextEntry(new ZipEntry(childPath));
                input = new FileInputStream(file);
                int readLen = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                    output.write(buffer, 0, readLen);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public static byte[] zipFile(File path) throws Exception {
        // 输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 压缩输出流
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        zip(out, path, "");
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(outputStream);
        return outputStream.toByteArray();
    }

    public static boolean zipFile(File path, String outputFileName) throws Exception {
        // 输出流
        FileOutputStream outputStream = new FileOutputStream(outputFileName);
        // 压缩输出流
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        zip(out, path, "");
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(outputStream);
        return true;
    }

}
