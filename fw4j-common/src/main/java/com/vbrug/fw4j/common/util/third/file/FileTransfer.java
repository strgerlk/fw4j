package com.vbrug.fw4j.common.util.third.file;

/**
 * 文件传输接口
 * @author vbrug
 * @since 1.0.0
 */
public interface FileTransfer {

    /**
     * 建立连接
     */
    void connect() throws Exception;

    /**
     * 上传文件
     * @param localFilePath  本地文件路径
     * @param remoteFilePath 远程文件路径
     */
    void upload(String localFilePath, String remoteFilePath) throws Exception;

    /**
     * 下载文件
     * @param localFilePath  本地文件路径
     * @param remoteFilePath 远程文件路径
     */
    void download(String localFilePath, String remoteFilePath) throws Exception;

    /**
     * 关闭资源
     */
    void close();
}
