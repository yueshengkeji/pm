package com.yuesheng.pm.util;

import com.jcraft.jsch.*;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by 96339 on 2016/11/17.
 *
 * @author XiaoSong
 * @date 2016/11/17
 */
public class FtpUtil {
    private static Logger logger = LogManager.getLogger(FtpUtil.class);

    /**
     * 向ftp写文件(数据)
     */
    public synchronized static void uploadFile(byte[] fileBytes, String fileName) throws IOException {
        // 指定写入的目录
        InputStream is = new ByteArrayInputStream(fileBytes);
        if (WebParam.SFTP) {
            ChannelSftp channelSftp = null;
            try {
                channelSftp = loginSFtp();
                fileExists(fileName, channelSftp);
                fileName = spliteFileName(fileName);
                channelSftp.put(is, fileName);
            } catch (SftpException e) {
                logger.error("uploadFile SftpException:" + e.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
                closeSftp(channelSftp);
            }
        } else {
            FTPClient ftpClient = loginFtp();
            ftpClient.enterLocalPassiveMode();
            try {
                // 1.输入流
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

    }

    private static void closeSftp(ChannelSftp channelSftp) {
        if (!Objects.isNull(channelSftp)) {
            Session session = null;
            try {
                session = channelSftp.getSession();
                session.disconnect();
                ;
            } catch (JSchException e) {

            }
            channelSftp.exit();
        }
    }

    private static void fileExists(String fileName, ChannelSftp channelSftp) {
        String folder = fileName.contains(File.separator) ? fileName.substring(0, fileName.indexOf(File.separator)) : "";
        //设置超时时间 ,1分钟
        /*ftpClient.setConnectTimeout(60000);*/
        // 4.指定写入的目录
        try {
            SftpATTRS temp = channelSftp.lstat(folder);
            if (Objects.isNull(temp) || !temp.isDir()) {
                channelSftp.mkdir(folder);
            }
            channelSftp.cd(folder);
        } catch (SftpException e) {
            try {
                channelSftp.mkdir(folder);
                channelSftp.cd(folder);
            } catch (SftpException e2) {
                logger.error("文件夹创建失败，sftp dir create error:" + "folder=" + folder + "error:;" + e2.getMessage());
            }
        }
    }

    private static String spliteFileName(String fileName) {
        return fileName.substring(fileName.contains(File.separator) ? fileName.indexOf(File.separator) + 1 : 0);
    }

    private static void fileExists(String fileName, FTPClient ftpClient) throws IOException {
        String path = WebParam.FTP_ROOT_FOLDER;
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
     * 获取文件流对象，用完调用closeFtp();
     *
     * @param fileName 文件名称
     * @return
     */
    private static byte[] getFileStream(String fileName) {

        InputStream is = null;
        if (WebParam.SFTP) {
            ChannelSftp channel = loginSFtp();
            fileExists(fileName, channel);
            fileName = spliteFileName(fileName);
            try {
                is = channel.get(fileName);
                return IOUtils.toByteArray(is);
            } catch (SftpException | IOException e) {
                logger.error("文件下载失败：filename:" + fileName + ";error::" + e.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
                closeSftp(channel);
            }
        } else {
            FTPClient ftp = null;
            try {
                ftp = loginFtp();
                fileExists(fileName, ftp);
                fileName = spliteFileName(fileName);
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                FTPFile fs = ftp.mlistFile(fileName);
                //获取ftp服务器流对象
                is = ftp.retrieveFileStream(fs.getName());
                return IOUtils.toByteArray(is);
            } catch (IOException e) {
                LoggerFactory.getLogger(FtpUtil.class).error("ftp IoException:", e);
            } catch (Exception e) {
                LoggerFactory.getLogger(FtpUtil.class).error("ftp exception:", e);
            } finally {
                IOUtils.closeQuietly(is);
                closeFtp(ftp);
            }
        }
        return new byte[0];
    }

    /**
     * 从FTP服务器获取文件流
     *
     * @param fileName ftp文件名
     * @return
     * @throws Exception
     */
    public static byte[] downloadFile(String fileName) throws Exception {
        return getFileStream(fileName);
    }

    private static FTPClient loginFtp() throws IOException {
//        int reply;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        //1.连接服务器
        ftp.connect(WebParam.FTP_ADDRESS);
        //2.登录服务器 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftp.login(WebParam.FTP_USER, WebParam.FTP_PASSWD);
        ftp.enterLocalPassiveMode();
        //4.指定要下载的目录
        ftp.changeWorkingDirectory(WebParam.FTP_ROOT_FOLDER);
        return ftp;
    }

    private static ChannelSftp loginSFtp() {

        try {
            JSch jSch = new JSch();
            Session session = null;
            session = jSch.getSession(WebParam.FTP_USER, WebParam.FTP_ADDRESS, 22);
            session.setPassword(WebParam.FTP_PASSWD);
            // 配置链接的属性
            Properties p = new Properties();
            p.put("StrictHostKeyChecking", "no");
            session.setConfig(p);

            // 进行sftp链接
            session.connect();

            // 获取通信通道
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            try {
                String path = WebParam.FTP_ROOT_FOLDER;
                SftpATTRS sftpATTRS = channel.lstat(path);
                if (Objects.isNull(sftpATTRS) || !sftpATTRS.isDir()) {
                    channel.mkdir(path);
                }
                channel.cd(path);
            } catch (SftpException e) {
                try {
                    channel.mkdir(WebParam.FTP_ROOT_FOLDER);
                    channel.cd(WebParam.FTP_ROOT_FOLDER);
                } catch (SftpException exception) {
                    logger.error("创建文件根目录失败：" + exception.getMessage());
                }
            }

            return channel;
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
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
            attach.setPk00811(StringUtils.substring(fileName, lastIndex, fileName.length()));
            //添加附件到ftp服务器
            uploadFile(attach.getFileBytes(), attach.getName() + attach.getId() + (fileName.substring(lastIndex)));
            //添加附件到数据库
        } catch (IOException e) {
            LogManager.getLogger("mylog").error("文件上传异常：" + e);
            attach = null;
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("文件上传异常：" + e);
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
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            //不存在，创建文件夹
            boolean mkdir = localFile.mkdir();
            if (!mkdir) {
                logger.error("创建临时文件夹失败：" + mkdir + "," + localPath);
            }
        }
        localFile = new File(localPath, localName);
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
        if (WebParam.SFTP) {
            ChannelSftp channelSftp = loginSFtp();
            fileExists(fileName, channelSftp);
            fileName = spliteFileName(fileName);
            try {
                channelSftp.rm(fileName);
            } catch (SftpException e) {
                logger.error("删除sftp文件失败：fileName:" + fileName + ",error:" + e.getMessage());
            }
        } else {
            FTPClient ftp = loginFtp();
            fileExists(fileName, ftp);
            fileName = spliteFileName(fileName);
            ftp.deleteFile(fileName);
            closeFtp(ftp);
        }
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
}
