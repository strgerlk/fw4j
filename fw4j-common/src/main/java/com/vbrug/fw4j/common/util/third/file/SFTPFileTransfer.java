package com.vbrug.fw4j.common.util.third.file;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;
import java.util.Vector;

/**
 * SFTP工具类
 */
public class SFTPFileTransfer implements FileTransfer{

    private final String      hostIp;
    private final int         port;
    private final String      userName;
    private final String      password;
    private       ChannelSftp channel;
    private       Session     session;

    private static final Logger logger = LoggerFactory.getLogger(SFTPFileTransfer.class);

    public SFTPFileTransfer(String hostIp, int port, String userName, String password) {
        this.hostIp = hostIp;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 连接
     * @throws JSchException、SftpException 异常信息
     */
    @Override
    public void connect() throws JSchException, SftpException {
        JSch jsch = new JSch();
        session = new JSch().getSession(this.userName, this.hostIp, this.port);
        session.setPassword(this.password);
        session.setTimeout(60000);

        // 配置sftp信息
        Properties configTemp = new Properties();
        configTemp.put("StrictHostKeyChecking", "no");              // 取消确认校验
        session.setConfig(configTemp);

        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        channel.setFilenameEncoding("UTF-8");
    }

    /**
     * 上传文件
     * @param localFilePath  本地文件路径
     * @param remoteFilePath 远程文件路径
     * @throws Exception 异常
     */
    @Override
    public void upload(String localFilePath, String remoteFilePath) throws Exception {
        File file = new File(localFilePath);
        if (!file.exists())
            throw new Exception("file not exists: " + localFilePath);
        long startTimeMillis = System.currentTimeMillis();
        this.notExistsCreateRemote(remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/")));
        channel.put(localFilePath, remoteFilePath);
        logger.debug("upload file " + localFilePath + ", size " + (file.length() / 1024) + "kb, consume time " + (System.currentTimeMillis() - startTimeMillis) + "ms");
    }

    /**
     * 下载文件
     * @param remotePath 远程文件路径
     * @param localPath  本地文件路径
     * @throws SftpException 异常
     */
    @Override
    public void download(String remotePath, String localPath) throws SftpException {
        this.notExistsCreateLocal(localPath);
        channel.get(remotePath, localPath);
    }

    /**
     * 关闭资源
     */
    @Override
    public void close() {
        if (this.channel != null) {
            this.channel.disconnect();
        }
        if (this.session != null) {
            this.session.disconnect();
        }
    }

    /**
     * 递归删除文件
     * @param path 操作路径
     * @throws SftpException 异常信息
     */
    public void delete(String path) throws SftpException {
        if (path.substring(path.lastIndexOf("/") + 1).indexOf(".") > 0) {
            channel.rm(path);
            logger.debug("delete file: " + path);
        } else {
            Vector vector = channel.ls(path);
            for (Object obj : vector) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                if (".".equals(entry.getFilename()) || "..".equals(entry.getFilename()))
                    continue;
                delete(path + "/" + entry.getFilename());
            }
            channel.rmdir(path);
            logger.debug("delete folder: " + path);
        }
    }


    /**
     * 如果文件夹不存在，在本地创建
     * @param filePath 目标文件路径
     */
    private void notExistsCreateLocal(String filePath) {
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            new File(filePath.substring(0, filePath.lastIndexOf("/") + 1)).mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     * @param path 文件路径
     * @return 结果
     * @throws SftpException 异常
     */
    private boolean isExists(String path) throws SftpException {
        try {
            channel.ls(path);
        } catch (SftpException e) {
            if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id)
                return false;
            else throw e;
        }
        return true;
    }

    /**
     * 创建远程文件夹
     * @param path 文件路径
     */
    private void notExistsCreateRemote(String path) throws SftpException {
        if (!this.isExists(path)) {
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            if (!this.isExists(parentPath))
                this.notExistsCreateRemote(parentPath);
            channel.mkdir(path);
        }
    }

}

