package com.vbrug.fw4j.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class FileUtilTest {

    public static void main(String[] args) throws Exception {
        List<String> lineList = FileUtil.parseFileByLine(new File("/home/vbrug/Shell/负表达式.tsv"));
        List<String> newLineList = new ArrayList<>();
        for (String s : lineList) {
            if (s.contains("^")){
                newLineList.add(s.replaceAll("\\^", ""));
            } else {
                newLineList.add("");
            }
        }
        FileUtil.produceFile(new File("/home/vbrug/Shell/负表达式.tsv_out"), newLineList);
    }
}
