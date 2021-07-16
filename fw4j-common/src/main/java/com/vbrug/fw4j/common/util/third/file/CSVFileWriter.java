package com.vbrug.fw4j.common.util.third.file;

import com.vbrug.fw4j.common.util.ArrayUtils;
import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.StringUtils;

import java.io.*;
import java.util.Map;

/**
 * CSV文件写入
 * @author vbrug
 * @since 1.0.0
 */
public class CSVFileWriter implements FileWriter {

    private final BufferedWriter   bw;
    private final CSVFileConfigure configure;

    public CSVFileWriter(String filePath, CSVFileConfigure configure) throws Exception {
        this(new File(filePath), configure);
    }

    public CSVFileWriter(File file, CSVFileConfigure configure) throws Exception {
        Assert.state(ArrayUtils.isNotEmpty(configure.getHeaders()), "标题不可以为空");
        this.bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), configure.getFileEncoding()));
        this.configure = configure;
    }

    /**
     * 写入一行数据
     */
    @Override
    public void writeLine(Map<String, String> valueMap) throws IOException {
        if (CollectionUtils.isEmpty(valueMap))
            bw.newLine();
        StringBuilder sb = new StringBuilder();
        for (String header : configure.getHeaders()) {
            sb.append(configure.getValueSeparator());
            String value = valueMap.get(header);
            if (value == null) {
                if (configure.getNullWriteBlank() && StringUtils.hasText(configure.getQuotation())) {
                    value = configure.getQuotation() + configure.getQuotation();
                } else {
                    value = CSVFileConfigure.NULL;
                }
            } else {
                if (StringUtils.hasText(configure.getQuotation())) {
                    value = configure.getQuotation() + value + configure.getQuotation();
                }
            }
        }
        bw.write(sb.substring(1).toString());
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
