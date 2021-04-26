package com.vbrug.fw4j.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文件读取工具类
 * @author vbrug
 * @since 1.0
 */
public class FileUtil {

    /**
     * 按行解析文件
     * @param file 待解析文件
     * @return 文件内容
     * @throws Exception 异常
     */
    public static List<String> parseFileByLine(File file) throws Exception {
        return parseFileByLine(file, null);
    }

    /**
     * 按行解析文件并指定编码
     * @param file    待读取的文件
     * @param charset 文件字符集, 空时默认为utf-8
     * @return 文件内容
     * @throws Exception 异常
     */
    public static List<String> parseFileByLine(File file, String charset) throws Exception {
        Assert.state(ObjectUtils.notNull(file), "文件为空");

        BufferedReader br = null;
        charset = Optional.ofNullable(charset).orElse("utf-8");
        List<String> list = new ArrayList<String>();
        try {
            br = !StringUtils.hasText(charset)
                    ? new BufferedReader(new InputStreamReader(new FileInputStream(file)))
                    : new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
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
     * 生成文件
     * @param file     待生成文件
     * @param lineList 内容
     * @throws Exception 异常
     */
    public static void produceFile(File file, List<String> lineList) throws Exception {
        produceFile(file, lineList, null);
    }

    /**
     * 生成文件
     * @param file     待生成文件
     * @param lineList 内容
     * @param charset  文件字符集, 空时默认为utf-8
     * @throws Exception 异常
     */
    public static void produceFile(File file, List<String> lineList, String charset) throws Exception {
        Assert.state(ObjectUtils.notNull(file), "文件为空");

        charset = Optional.ofNullable(charset).orElse("utf-8");
        // 若文件已存在，覆盖，将历史文件删除
        if (file.exists()) file.delete();

        // 若目录不存在，创建文件目录
        mkdir(file);

        // 写入文件
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            for (String line : lineList) {
                bw.write(line + System.lineSeparator());
            }
            bw.flush();
        } finally {
            IOUtils.close(bw);
        }
    }

    /**
     * 删除文件或目录
     * @param file 待删除文件或目录
     * @return 是否删除成功
     */
    public static boolean delete(File file) {
        Assert.state(ObjectUtils.notNull(file), "文件或目录为空");
        return recursiveDelete(file);
    }

    /**
     * 递归删除文件或目录
     * @param file 待删除文件或目录
     */
    private static boolean recursiveDelete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File loopFile : files) {
                    recursiveDelete(loopFile);
                }
            }
        }
        return file.delete();
    }

    /**
     * 创建文件的目录
     * @param file 待创建目录文件
     */
    public static boolean mkdir(File file) {
        String path       = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/"));
        File   folderFile = new File(path);
        if (!folderFile.exists())
            return folderFile.mkdirs();
        return true;
    }

    private static String pathClear(String path) {
        Assert.state(!StringUtils.isEmpty(path), "filePath is null");
        return path.replaceAll("(\\\\+|/+)", "/");
    }
}
