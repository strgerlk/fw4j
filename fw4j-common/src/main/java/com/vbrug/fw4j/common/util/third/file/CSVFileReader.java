package com.vbrug.fw4j.common.util.third.file;

import com.vbrug.fw4j.common.exception.Fw4jException;
import com.vbrug.fw4j.common.util.ArrayUtils;
import com.vbrug.fw4j.common.util.StringUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV文件读取
 * @author vbrug
 * @since 1.0.0
 */
public class CSVFileReader implements FileReader {


    private final LineNumberReader lnr;
    private final CSVFileConfigure configure;
    private final int              lineAmount;


    /**
     * 构造函数，根据文件创建解析类
     * @param file 文件
     * @throws FileNotFoundException 异常信息
     */
    public CSVFileReader(File file, CSVFileConfigure configure) throws Exception {
        this(new FileInputStream(file), configure);
    }

    /**
     * 构造函数，根据文件流创建解析类
     * @param fis       文件流
     * @param configure 配置类
     */
    public CSVFileReader(FileInputStream fis, CSVFileConfigure configure) throws Exception {
        this.configure = configure;
        this.lnr = new LineNumberReader(new InputStreamReader(fis, configure.getFileEncoding()));
        // 获取文件记录数量
        this.lnr.skip(Long.MAX_VALUE);
        if (configure.getFirstRowIsHeader())
            this.lineAmount = this.lnr.getLineNumber();
        else
            this.lineAmount = this.lnr.getLineNumber() + 1;
        this.lnr.reset();
        // 初始化标题
        this.init();
    }


    /**
     * 将一行数据读为map
     * @return Map集合
     */
    @Override
    public Map<String, String> readLineToMap() throws IOException {
        String line = lnr.readLine();
        if (line == null) return null;

        Map<String, String> valueMap = new HashMap<>();
        String[]                 strings  = this.splitLine(line);
        if (ArrayUtils.isEmpty(strings))
            return valueMap;

        if (ArrayUtils.isEmpty(configure.getHeaders())) {
            for (int i = 0; i < strings.length; i++) {
                valueMap.put(String.valueOf(i), strings[i]);
            }
        } else {
            for (int i = 0; i < strings.length && i < configure.getHeaders().length; i++) {
                valueMap.put(configure.getHeaders()[i], strings[i]);
            }
        }
        return valueMap;
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

    /**
     * 资源关闭
     */
    @Override
    public void close() throws IOException {
        lnr.close();
    }

    /**
     * 获取待解析文件总行数
     * @return 结果
     */
    public int getLineAmount() {
        return lineAmount;
    }

    /**
     * 将一行数据切割为字符串数组
     * @param line 待切割文本内容
     * @return 字符串集合
     */
    private String[] splitLine(String line) {
        String separator = configure.getValueSeparator().value;
        if (StringUtils.hasText(configure.getQuotation())) {
            line = StringUtils.trimStr(line, configure.getQuotation());
            separator = configure.getQuotation() + separator + configure.getQuotation();
        }
        return line.split(separator);
    }

    /**
     * 解析标题
     */
    private void init() throws IOException {
        if (configure.getFirstRowIsHeader()) {
            String headerLine = lnr.readLine();
            if (!StringUtils.hasText(headerLine))
                throw new Fw4jException("文件为空，第一行无标题内容");
            String[] headers = this.splitLine(headerLine);
            headers = Arrays.stream(headers).map(x -> {
                if (StringUtils.hasText(configure.getQuotation()))
                    x = StringUtils.trimStr(x, configure.getQuotation());
                if (configure.getIsLineToHump())
                    return StringUtils.lineToHump(x);
                return x;
            }).toArray(String[]::new);
            configure.setHeaders(headers);
        }
    }

}
