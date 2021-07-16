package com.vbrug.fw4j.common.util.third.file;

import java.io.IOException;
import java.util.Map;

/**
 * 文件读写接口
 * @author vbrug
 * @since 1.0.0
 */
public interface FileWriter {

    /**
     * 写入一行数据
     */
    void writeLine(Map<String, String> valueMap) throws Exception;

    /**
     * 关闭资源
     */
    void close() throws IOException;
}
