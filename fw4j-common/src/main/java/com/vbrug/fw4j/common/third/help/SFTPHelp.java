package com.vbrug.fw4j.common.third.help;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;
import java.util.Vector;

public class SFTPHelp {

    private String hostIp;

    private int port;

    private String userName;

    private String password;

    private ChannelSftp channel;

    private Session session;

    private static final Logger logger = LoggerFactory.getLogger(SFTPHelp.class);

    public SFTPHelp(String hostIp, int port, String userName, String password) {
        this.hostIp = hostIp;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void connect() throws JSchException, SftpException {
        JSch jsch = new JSch();
        session = new JSch().getSession(this.userName, this.hostIp, this.port);
        session.setPassword(this.password);
        session.setTimeout(60000);

        // 配置sftp信息
        Properties configTemp = new Properties();
        configTemp.put("StrictHostKeyChecking", "no");
        session.setConfig(configTemp);

        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        channel.setFilenameEncoding("UTF-8");
    }

    public void close() {
        if (this.channel != null) {
            this.channel.disconnect();
        }
        if (this.session != null) {
            this.session.disconnect();
        }
    }

    public void upload(String localFilePath, String targetFilePath) throws  Exception{
        File file = new File(localFilePath);
        if (!file.exists())
            throw new Exception("file not exists: "+localFilePath);
        long startTimeMillis = System.currentTimeMillis();
        this.notExistsCreateRemote(targetFilePath.substring(0, targetFilePath.lastIndexOf("/")));
        channel.put(localFilePath, targetFilePath);
        logger.debug("upload file "+localFilePath + ", size "+(file.length()/1024)+ "kb, consume time "+ (System.currentTimeMillis()-startTimeMillis)+"ms");
    }

    /**
     * 文件夹
     * @param path
     */
    private void notExistsCreateRemote(String path) throws SftpException {
        if (!this.isExists(path)){
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            if (!this.isExists(parentPath))
                this.notExistsCreateRemote(parentPath);
            channel.mkdir(path);
        }
    }

    private boolean isExists(String path) throws SftpException {
        try {
            channel.ls(path);
        } catch (SftpException e) {
            if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id)
                return false;
            else throw  e;
        }
        return true;
    }

    public void download(String remotePath, String localPath) throws SftpException {
        this.notExistsCreateLocal(localPath);
        channel.get(remotePath, localPath);
    }

    /**
     * 文件夹
     * @param filePath
     */
    private void notExistsCreateLocal(String filePath) {
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            new File(filePath.substring(0, filePath.lastIndexOf("/") + 1)).mkdirs();
        }
    }

    public void delete(String path) throws SftpException {
        if (path.substring(path.lastIndexOf("/") + 1).indexOf(".") > 0 ) {
            channel.rm(path);
            logger.debug("delete file: "+path);
        } else {
            Vector vector = channel.ls(path);
            for (Object obj : vector) {
                ChannelSftp.LsEntry entry =  (ChannelSftp.LsEntry)obj;
                if (".".equals(entry.getFilename()) || "..".equals(entry.getFilename()) )
                    continue;
                delete(path+"/"+entry.getFilename());
            }
            channel.rmdir(path);
            logger.debug("delete folder: "+path);
        }
    }
}

