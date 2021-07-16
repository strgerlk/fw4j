package com.vbrug.fw4j.common.util.third.file;

/**
 * JSON文件解析配置类
 * @author vbrug
 * @since 1.0.0
 */
public class JSONFileConfigure {

    private String  fileEncoding = "UTF-8";             // 文件编码，包含UTF-8、GBK、GB2312等
    private Boolean isLineToHump = false;               // 标题是否下划线转驼峰
    private Boolean oneLineContainMulti;                // 一行是否包含多条数据
    private String  targetPath;                         // 目标结果路径

    public JSONFileConfigure() {}

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public void setIsLineToHump(Boolean lineToHump) {
        isLineToHump = lineToHump;
    }

    public void setOneLineContainMulti(Boolean oneLineContainMulti) {
        this.oneLineContainMulti = oneLineContainMulti;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public Boolean getLineToHump() {
        return isLineToHump;
    }

    public Boolean getOneLineContainMulti() {
        return oneLineContainMulti;
    }

    public String getTargetPath() {
        return targetPath;
    }
}
