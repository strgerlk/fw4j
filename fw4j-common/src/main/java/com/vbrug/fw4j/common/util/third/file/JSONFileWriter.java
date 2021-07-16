package com.vbrug.fw4j.common.util.third.file;

import com.vbrug.fw4j.common.util.JacksonUtils;

import java.io.*;
import java.util.Map;

/**
 * JSON文件写入
 * @author vbrug
 * @since 1.0.0
 */
public class JSONFileWriter implements FileWriter {

    private final BufferedWriter    bw;
    private final JSONFileConfigure configure;

    public JSONFileWriter(String filePath, JSONFileConfigure configure) throws Exception {
        this(new File(filePath), configure);
    }

    public JSONFileWriter(File file, JSONFileConfigure configure) throws Exception {
        this.bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), configure.getFileEncoding()));
        this.configure = configure;
    }

    /**
     * 写入一行数据
     */
    @Override
    public void writeLine(Map<String, String> valueMap) throws IOException {
        bw.write(JacksonUtils.bean2Json(valueMap));
        bw.newLine();
    }

    /**
     * 关闭资源
     */
    @Override
    public void close() throws IOException {
        bw.close();
    }

}
