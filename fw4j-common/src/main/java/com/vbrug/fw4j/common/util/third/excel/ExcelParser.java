package com.vbrug.fw4j.common.util.third.excel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel解析工具
 */
public interface ExcelParser {

    /**
     * 解析Excel，
     * @param is 输入流
     * @return 解析结果
     * @throws Exception 解析异常
     */
    List<Map<String, String>> parseExcel(InputStream is) throws Exception;

    /**
     * 解析Excel，
     * @param is 输入流
     * @return 解析结果
     * @throws Exception 解析异常
     */
    List<Map<String, String>> parseExcel(InputStream is, String... fields) throws Exception;

    /**
     * 解析Excel，
     * @param is 输入流
     * @return 解析结果
     * @throws Exception 解析异常
     */
    List<Map<String, String>> parseExcel(InputStream is, int sheetIndex, int startRowIndex, int maxRows, String... fields) throws Exception;

    /**
     * 解析Excel，
     * @param is    输入流
     * @param clazz 对应BEAN类
     * @return 解析结果
     * @throws Exception 解析异常
     */
    <T> List<T> parseExcel(InputStream is, Class<T> clazz) throws Exception;

    /**
     * 解析Excel，
     * @param is     输入流
     * @param clazz  对应BEAN类
     * @param fields excel对应BEAN字段
     * @return 解析结果
     * @throws Exception 解析异常
     */
    <T> List<T> parseExcel(InputStream is, Class<T> clazz, String... fields) throws Exception;


    /**
     * 解析Excel，
     * @param is            输入流
     * @param fields        excel对应BEAN字段
     * @param clazz         对应BEAN类
     * @param startRowIndex 从第几行开始读数据
     * @param maxRows       导入最大行数限制
     * @return 解析结果
     * @throws Exception 解析异常
     */
    <T> List<T> parseExcel(InputStream is, Class<T> clazz, int sheetIndex, int startRowIndex, int maxRows, String... fields) throws Exception;

}
