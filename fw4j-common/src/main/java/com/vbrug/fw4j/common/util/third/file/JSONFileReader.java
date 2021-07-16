package com.vbrug.fw4j.common.util.third.file;

import com.vbrug.fw4j.common.exception.Fw4jException;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.JacksonUtils;
import com.vbrug.fw4j.common.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class JSONFileReader implements FileReader {

    private final LineNumberReader  lnr;
    private final JSONFileConfigure configure;
    private final int               lineAmount;


    /**
     * 构造函数，根据文件创建解析类
     * @param file         文件
     * @param configure 配置，默认“UTF-8”
     * @throws FileNotFoundException 异常信息
     */
    public JSONFileReader(File file, JSONFileConfigure configure) throws Exception {
        this(new FileInputStream(file), configure);
    }

    /**
     * 构造函数，根据文件流创建解析类
     * @param fis 文件流
     */
    public JSONFileReader(FileInputStream fis, JSONFileConfigure configure) throws Exception {
        this.configure = configure;
        this.lnr = new LineNumberReader(new InputStreamReader(fis, configure.getFileEncoding()));
        // 获取文件记录数量
        this.lnr.skip(Long.MAX_VALUE);
        this.lineAmount = this.lnr.getLineNumber() + 1;
        this.lnr.reset();
    }

    /**
     * 将一行数据读为map
     * @return Map集合
     */
    @Override
    public Map<String, String> readLineToMap() throws IOException {
        String line = lnr.readLine();
        if (line == null) return null;

        if (!StringUtils.hasText(line))
            return new HashMap<>();

        Map<String, String> map = JacksonUtils.json2Map(line, String.class, String.class);
        return this.convertMapToDataMap(map);
    }

    /**
     * 将集合转换为结果集合
     * @param map 解析值集合
     * @return 结果集合
     */
    private Map<String, String> convertMapToDataMap(Map<String, String> map) {
        Map<String, String> dataMap = new HashMap<>();
        if (configure.getLineToHump()) {
            map.keySet().forEach(x -> {
                dataMap.put(StringUtils.lineToHump(x), map.get(x));
            });
        } else
            dataMap.putAll(map);
        return dataMap;
    }

    /**
     * 一行内容包含多条记录时，读取一行返回多条记录
     * @return 结果
     */
    public List<Map<String, String>> readLineToMapList() throws IOException {
        if (!configure.getOneLineContainMulti())
            throw new Fw4jException("一行内容未包含多条记录");

        String line = lnr.readLine();
        if (line == null) return null;

        List<Map<String, String>> resultList = new ArrayList<>();
        if (!StringUtils.hasText(line))
            return resultList;

        List<Map<String, String>> mapList = JacksonUtils.json2TileList(line, configure.getTargetPath());
        if (!CollectionUtils.isEmpty(mapList))
            mapList.forEach(x -> resultList.add(this.convertMapToDataMap(x)));
        return resultList;
    }


    @Override
    public List<Map<String, String>> readAllLineToMapList() throws IOException {
        Map<String, String>       loopMap;
        List<Map<String, String>> resultList = new ArrayList<>();
        while ((loopMap = this.readLineToMap()) != null)
            resultList.add(loopMap);
        if (CollectionUtils.isEmpty(resultList))
            return null;
        return resultList;
    }

    /**
     * 将一行数据读为bean类
     * @param tClass
     * @return T
     */
    @Override
    public <T> T readLineToBean(Class<T> tClass) {
        return null;
    }

    /**
     * 将所有数据读转换为bean类集合
     * @param tClass
     * @return T
     */
    @Override
    public <T> List<T> readAllLineToBeanList(Class<T> tClass) {
        return null;
    }

    public JSONFileConfigure getConfigure() {
        return configure;
    }

    /**
     * 资源关闭
     */
    @Override
    public void close() throws IOException {
        lnr.close();
    }

    /**
     * 判断一行内容是否包含多条记录
     * @return 结果
     */
    public Boolean isOneLineContainMulti() {
        return configure.getOneLineContainMulti();
    }

    /**
     * 获取解析文件总行数
     * @return 结果
     */
    public int getLineAmount() {
        return lineAmount;
    }
}
