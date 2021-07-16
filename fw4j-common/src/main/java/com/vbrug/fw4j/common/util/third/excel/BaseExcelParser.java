package com.vbrug.fw4j.common.util.third.excel;

import com.vbrug.fw4j.common.util.ClassUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class BaseExcelParser {

    /*
     * 默认开始读取行数
     */
    protected int startRowIndex = 0;

    /*
     * 默认读取Sheet页
     */
    protected int sheetIndex = 0;

    /*
     * 默认文件读取最大行数
     */
    protected int maxRows = 65000;

    /*
     * 遇到空行停止解析
     */
    protected boolean blankStop = false;



    protected  List<Object> handleField(Class<?> beanClazz, String... fields) {
        List<Object> resultList = new ArrayList<>();
        if (beanClazz == Map.class) {
            resultList.addAll(Collections.singletonList(fields));
        } else {
            for (String field : fields) {
                resultList.add(ClassUtils.getPropertyDescriptor(beanClazz, field));
            }
        }
        return resultList;
    }
}
