package com.vbrug.fw4j.common.util.third.file;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件解析接口
 * @author vbrug
 * @since 1.0.0
 */
public interface FileReader {

    /**
     * 将一行数据读为map
     * @return Map集合
     */
    Map<String, String> readLineToMap() throws IOException;

    /**
     * 将所有数据读为map集合
     * @return Map集合
     */
    default List<Map<String, String>> readAllLineToMapList() throws IOException {
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
     * @return T
     */
    <T> T readLineToBean(Class<T> tClass);

    /**
     * 将所有数据读转换为bean类集合
     * @return T
     */
    <T> List<T> readAllLineToBeanList(Class<T> tClass);

    /**
     * 资源关闭
     */
    void close() throws IOException;

}
