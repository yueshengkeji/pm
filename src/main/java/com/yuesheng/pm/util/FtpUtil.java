package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by 96339 on 2016/11/17.
 *
 * @author XiaoSong
 * @date 2016/11/17
 */
public class FtpUtil {
    /**
     * ftp登录用户名
     */
    private static String userName = WebParam.FTP_USER;
    /**
     * ftp登录密码
     */
    private static String userPassword = WebParam.FTP_PASSWD;
    /**
     * ftp地址:直接IP地址
     */
    private static String server = WebParam.FTP_ADDRESS;
    /**
     * 指定写入的目录
     */
    private static String path = WebParam.FTP_ROOT_FOLDER;

    /**
     * 向ftp写文件(数据)
     */
    public synchronized static void uploadFile(byte[] fileBytes, String fileName) throws IOException {
        // 指定写入的目录
        FTPClient ftpClient = loginFtp();
        ftpClient.enterLocalPassiveMode();
        InputStream is = null;
        try {
            // 1.输入流
            is = new ByteArrayInputStream(fileBytes);
            fileExists(fileName, ftpClient);
            fileName = spliteFileName(fileName);
            // 5.写操作
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, is);
        } finally {
            IOUtils.closeQuietly(is);
            closeFtp(ftpClient);
        }
    }

    private static String spliteFileName(String fileName) {
        return fileName.substring(fileName.contains(File.separator) ? fileName.indexOf(File.separator) + 1 : 0);
    }

    private static void fileExists(String fileName, FTPClient ftpClient) throws IOException {
        String folder = fileName.contains(File.separator) ? fileName.substring(0, fileName.indexOf(File.separator)) : "";
        //设置超时时间 ,1分钟
        /*ftpClient.setConnectTimeout(60000);*/
        // 4.指定写入的目录
        if (!ftpClient.changeWorkingDirectory(path + File.separator + folder)) {
            ftpClient.changeWorkingDirectory(path);
            //目录不存在
            ftpClient.makeDirectory(folder);
            ftpClient.changeWorkingDirectory(path + File.separator + folder);
        }
    }

    /**
     * 压缩图片
     *
     * @param localPath 目标路径
     * @param localName 目标名称
     * @param bais      图片流对象
     * @throws IOException
     */
    public synchronized static byte[] zipImage(String localPath, String localName, ByteArrayInputStream bais) throws IOException {
        File localFile = new File(localPath + localName);
        //如果文件存在，则直接return，无须从ftp服务器下载
        if (localFile.exists()) {
            FileInputStream fis = new FileInputStream(localFile);
            byte[] bytes = IOUtils.toByteArray(fis);
            fis.close();
            return bytes;
        }
        ImageCompress ic = ImageCompress.instanceImage();
        ic.setOutputDir(localPath);
        //图片大小1024时才压缩
        ic.setProportion(((bais.available() / 1024) > 1024));
        byte[] bytes = ic.compressPic(localName, bais);
        bais.close();
        return bytes;
    }

    /**
     * 获取文件流对象，用完调用closeFtp();
     *
     * @param fileName 文件名称
     * @return
     */
    private static byte[] getFileStream(String fileName) {
        FTPClient ftp = null;
        try {
            ftp = loginFtp();
            fileExists(fileName, ftp);
            fileName = spliteFileName(fileName);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//            String charSet = Charset.defaultCharset().displayName();
//            fileName = new String(fileName.getBytes(charSet), "iso-8859-1");
            FTPFile fs = ftp.mlistFile(fileName);
            //获取ftp服务器流对象
            return IOUtils.toByteArray(ftp.retrieveFileStream(fs.getName()));
        } catch (IOException e) {
            LoggerFactory.getLogger(FtpUtil.class).error("ftp IoException:", e);
        } catch (Exception e) {
            LoggerFactory.getLogger(FtpUtil.class).error("ftp exception:", e);
        } finally {
            closeFtp(ftp);
        }
        return null;
    }

    /**
     * 从FTP服务器获取文件流
     *
     * @param fileName ftp文件名
     * @return
     * @throws Exception
     */
    public static byte[] downloadFile(String fileName) throws Exception {
        FTPClient ftp = null;
        try {
            ftp = loginFtp();
            fileExists(fileName, ftp);
            fileName = spliteFileName(fileName);
            // 设置以二进制流的方式传输
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            return IOUtils.toByteArray(ftp.retrieveFileStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeFtp(ftp);
        }
        return new byte[0];
    }

    private static FTPClient loginFtp() throws IOException {
//        int reply;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        //1.连接服务器
        ftp.connect(server);
        //2.登录服务器 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftp.login(userName, userPassword);
        ftp.enterLocalPassiveMode();
        //4.指定要下载的目录
        ftp.changeWorkingDirectory(path);
//        if (!ftp.changeWorkingDirectory(path + "/" + folder)) {
//            ftp.changeWorkingDirectory(path);
//            //创建目录
//            if (!ftp.makeDirectory(folder)) {
//                //创建目录失败,切换到失败目录
//                ftp.changeWorkingDirectory(path + "/" + folder);
//            } else {
//                ftp.changeWorkingDirectory(folder);
//            }
//        }
        return ftp;
    }

    /**
     * 关闭ftp链接
     */
    public static void closeFtp(FTPClient ftp) {
        if (ftp != null) {
            try {
                ftp.logout();
            } catch (IOException e) {
                //忽略
            } finally {
                try {
                    ftp.disconnect();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 转换表单文件对象
     *
     * @param file
     * @param staff
     * @param id
     * @return
     */
    public static Attach multiParse(MultipartFile file, Staff staff, String id) {
        Attach attach = new Attach();
        try {
            String fileName;
            String datetime = DateUtil.getDatetime();
            String uploadDate = DateUtil.getDate();
            fileName = file.getOriginalFilename();
            fileName = getFileName(fileName, uploadDate);
            attach.setUploadUser(staff);
            attach.setUpdateDate(datetime);
            attach.setUploadDate(datetime);
            attach.setModuleId(id);
            attach.setId(UUID.randomUUID().toString());
            attach.setFileName(fileName);
            int lastIndex = fileName.lastIndexOf(".");
            lastIndex = lastIndex == -1 ? fileName.length() : lastIndex;
            attach.setName(fileName.substring(0, lastIndex));
            attach.setFileBytes(file.getBytes());
            attach.setPk00811(StringUtils.substring(fileName,lastIndex,fileName.length()));
            //添加附件到ftp服务器
            uploadFile(attach.getFileBytes(), attach.getName() + attach.getId() + (fileName.substring(lastIndex)));
            //添加附件到数据库
        } catch (IOException e) {
            LogManager.getLogger("mylog").error("文件上传异常："+e);
            attach = null;
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("文件上传异常："+e);
            attach = null;
        }
        return attach;
    }

    public static String getFileName(String fileName, String uploadDate) {
        return uploadDate.substring(0, 7) + File.separator + fileName.replaceAll(" ", "").replaceAll("#", "").replaceAll("&", "").replaceAll("@", "");
    }

    /**
     * 下载其他文件
     *
     * @param fileName
     * @param localPath
     * @param localName
     * @return
     */
    public static void downOtherFile(String fileName, String localPath, String localName) throws NullPointerException {
        File localFile = new File(localPath, localName);
        DataOutputStream dos = null;
        //如果文件存在，则直接return，无须从ftp服务器下载
        if (localFile.exists()) {
            return;
        }
        try {
            dos = new DataOutputStream(new FileOutputStream(localPath + localName));
            dos.write(getFileStream(fileName));
        } catch (IOException e) {
            LoggerFactory.getLogger(FtpUtil.class).error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(dos);
        }
    }

    /**
     * 从ftp删除文件
     *
     * @param fileName 文件名称
     */
    public static void deleteFile(String fileName) throws IOException {
        FTPClient ftp = loginFtp();
        fileExists(fileName, ftp);
        fileName = spliteFileName(fileName);
//        ftp.deleteFile(new String(fileName.getBytes(), "iso-8859-1"));
        ftp.deleteFile(fileName);
        closeFtp(ftp);
    }

    /**
     * 删除本地文件
     *
     * @param path 相对（项目）路径
     * @return
     */
    public static boolean deleteLocalFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 写入文件到本地
     *
     * @param file
     * @return
     */
    public static String uploadFileToLocal(MultipartFile file, String fileName) {
        FileOutputStream fos = null;
        String path = WebParam.assetsPath + "ftptemp"+File.separator + fileName;
        try {
            fos = new FileOutputStream(path);
            IOUtils.write(file.getBytes(), fos);
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException ignore) {
            return "";
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ignore) {

            }
        }
        return path;
    }
}
