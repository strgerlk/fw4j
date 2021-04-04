package com.vbrug.fw4j.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author vbrug
 * @since 1.0
 */
public class FileUtil {

    /**
     * 按行解析文件并指定编码
     *
     * @param file
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static List<String> parseFileByLine(File file, String charsetName) throws Exception {
        BufferedReader br = null;
        charsetName = Optional.ofNullable(charsetName).orElse("utf-8");
        try {
            List<String> list = new ArrayList<String>();
            br = !StringUtils.hasText(charsetName)
                    ? new BufferedReader(new InputStreamReader(new FileInputStream(file)))
                    : new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        } finally {
            IOUtils.close(br);
        }
    }

    /**
     * 按行解析文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static List<String> parseFileByLine(File file) throws Exception {
        return parseFileByLine(file, null);
    }

    /**
     * 生成文件
     *
     * @param fileName
     * @param list
     * @throws Exception
     */
    public static void produceFile(String fileName, List<String> list) throws Exception {

        // 判断文件如果存在，删除
        File file = new File(fileName);
        if (file.exists())
            file.delete();

        // 判断目录是否存在
        File folder = new File(fileName.substring(0, fileName.lastIndexOf("/")));
        if (!folder.exists())
            folder.mkdirs();

        // 写入文件
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            for (String line : list) {
                if (StringUtils.hasText(line))
                    bw.write(line + System.lineSeparator());
            }
            bw.flush();
        } finally {
            IOUtils.close(bw);
        }
    }

    /**
     * 删除文件或目录
     *
     * @param path
     */
    public static boolean delete(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File loopFile : files) {
                    delete(loopFile.getAbsolutePath());
                }
            }
        }
        return file.delete();
    }

    public static void mkdir(String path) {
        path = path.substring(0, path.lastIndexOf("/"));
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }
}
