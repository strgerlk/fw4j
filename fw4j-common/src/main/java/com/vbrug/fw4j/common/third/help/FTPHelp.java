package com.vbrug.fw4j.common.third.help;

import com.vbrug.fw4j.common.util.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * @author LK
 * @since 1.0
 */
public class FTPHelp {

    private static  final Logger logger = LoggerFactory.getLogger(FTPHelp.class);

    private String ip;

    private int port;

    private String userName;

    private String password;

    private FTPClient ftpClient;

    private FTPHelp(String ip, int port, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public static FTPHelp getInstance(String ip, int port) {
        return new FTPHelp(ip, port, null, null);
    }

    public static FTPHelp getInstance(String ip, int port, String userName, String password) {
        return new FTPHelp(ip, port, userName, password);
    }

    public FTPClient getFtpClient(){
        return this.ftpClient;
    }

    /**
     * 连接ftp
     */
    public void connect() throws Exception{
        ftpClient = new FTPClient();
        // 设置相关属性
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setConnectTimeout(6000);
        ftpClient.connect(ip, port);
        if (this.userName == null || "".equals(this.userName)) {
            ftpClient.login("anonymous", "password");
        } else {
            ftpClient.login(userName, password);
        }
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.disconnect();
            throw new Exception("ftp连接异常");
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public boolean existFile(String filePath) throws Exception {
        return ftpClient.listFiles(filePath).length > 0 ? true : false;
    }

    /**
     * 下载文件
     * @param downloadFilePath
     * @param targetFilePath
     * @throws Exception
     */
    public int download(String downloadFilePath, String targetFilePath) throws Exception{
        return this.download(downloadFilePath, targetFilePath, false);
    }

    /**
     * 下载文件
     * @param downloadFilePath
     * @param targetFilePath
     * @throws Exception
     */
    public int download(String downloadFilePath, String targetFilePath, boolean notExistsThrow) throws Exception{
        logger.debug(" start download file "+downloadFilePath);
        int    segmentIndex = downloadFilePath.lastIndexOf("/");
        String path         = downloadFilePath.substring(0, segmentIndex + 1);
        String fileName     = downloadFilePath.substring(segmentIndex + 1);
        // 判断文件夹是否存在
        notExistCreate(targetFilePath);
        ftpClient.changeWorkingDirectory(path);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        FTPFile ftpFile = Arrays.stream(ftpFiles).filter(x -> x.getName().equalsIgnoreCase(fileName)).findFirst().orElse(null);
        if (ftpFile == null ){
            if (notExistsThrow) {
                throw new Exception(downloadFilePath + " file not exists");
            } else {
                logger.debug(downloadFilePath + "file not exists");
                return 0;
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(targetFilePath));
            ftpClient.retrieveFile(ftpFile.getName(), fos);
            fos.flush();
        } finally {
            IOUtils.close(fos);
        }
        logger.debug("download file "+downloadFilePath);
        return 1;
    }

    /**
     * 文件夹
     * @param filePath
     */
    private void notExistCreate(String filePath) {
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            new File(filePath.substring(0, filePath.lastIndexOf("/") + 1)).mkdirs();
        }
    }

    public void disconnect() {
        if (ftpClient.isConnected())
            try {
                ftpClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
