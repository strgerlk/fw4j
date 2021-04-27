package com.vbrug.fw4j.common.third;

import com.vbrug.fw4j.common.third.excel.ExcelParserXLSX;
import com.vbrug.fw4j.common.third.excel.ExcelPoiUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ExcelParserTest {
    public static void main(String[] args) throws Exception {
        List<Map<String, String>> maps = new ExcelParserXLSX().parseExcel(new FileInputStream(new File("/home/vbrug/Downloads/南明表达式-0427(1).xlsx")));
        System.out.println(maps.size());
    }
}
