package com.vbrug.fw4j.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 文件工具
 * @since 1.0
 */
public abstract class IOUtils {

    public static String getContent(Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        return br.lines().reduce((x, y) -> x + y).get();
    }


    /**
     * 按行解析文件并指定编码
     * @return 文件内容
     * @throws Exception 异常
     */
    public static List<String> parseFileByLine(InputStream fis, String charset) throws Exception {
        BufferedReader br = null;
        charset = Optional.ofNullable(charset).orElse("utf-8");
        List<String> list = new ArrayList<String>();
        try {
            br = !StringUtils.hasText(charset)
                    ? new BufferedReader(new InputStreamReader(fis))
                    : new BufferedReader(new InputStreamReader(fis, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } finally {
            IOUtils.close(br);
        }
        return list;
    }


    /**
     * 关闭流
     * @param closeables 待关闭的流
     */
    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
