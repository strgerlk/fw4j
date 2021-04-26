package com.vbrug.fw4j.common.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

/**
 * @author 文件工具
 * @since 1.0
 */
public abstract class IOUtils {

    public static String getContent(Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        return br.lines().reduce((x, y) -> x+y).get();
    }


    /**
     * 关闭流
     *
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
