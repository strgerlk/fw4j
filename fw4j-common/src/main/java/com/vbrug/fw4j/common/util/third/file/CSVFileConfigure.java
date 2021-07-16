package com.vbrug.fw4j.common.util.third.file;

/**
 * CSV文件解析配置类
 * @author vbrug
 * @since 1.0.0
 */
public class CSVFileConfigure {

    public static final String NULL = "NULL";

    private String        fileEncoding     = "UTF-8";             // 文件编码，包含UTF-8、GBK、GB2312等
    private Boolean       isLineToHump     = false;               // 标题是否下划线转驼峰
    private Boolean       firstRowIsHeader = false;               // 首行是否为标题
    private Boolean       nullWriteBlank   = true;                // 空写入空白
    private SeparatorType valueSeparator;                         // 值分隔符号
    private String        quotation;                              // 引用符号
    private String[]      headers;                                // 文件标题

    public CSVFileConfigure() {}

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public void setLineToHump(Boolean lineToHump) {
        isLineToHump = lineToHump;
    }

    public void setValueSeparator(SeparatorType valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public void setFirstRowIsHeader(Boolean firstRowIsHeader) {
        this.firstRowIsHeader = firstRowIsHeader;
    }

    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    public void setNullWriteBlank(Boolean nullWriteBlank) {
        this.nullWriteBlank = nullWriteBlank;
    }

    public Boolean getNullWriteBlank() {
        return nullWriteBlank;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public Boolean getIsLineToHump() {
        return isLineToHump;
    }

    public Boolean getFirstRowIsHeader() {
        return firstRowIsHeader;
    }

    public SeparatorType getValueSeparator() {
        return valueSeparator;
    }

    public String getQuotation() {
        return quotation;
    }

    public String[] getHeaders() {
        return headers;
    }

    public static enum SeparatorType {

        SEMICOLON(";", "SEMICOLON"), TAB("\t", "TAB"), COMMA(",", "COMMA"), SPACE(" ", "SPACE");
        public final String value;
        public final String name;

        SeparatorType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public static SeparatorType getByName(String name) {
            for (SeparatorType type : values()) {
                if (type.name.equalsIgnoreCase(name))
                    return type;
            }
            return null;
        }
    }
}


