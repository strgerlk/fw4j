package com.vbrug.fw4j.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * JSON解析工具
 *
 * @author vbrug
 * @since 1.0.0
 */
public abstract class JacksonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();


    public static String bean2Json(Object object) {
        StringWriter sw = new StringWriter();
        try {
            JsonGenerator gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, object);
            gen.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    public static <T> T json2Bean(String jsonStr, Class<T> clazz) {
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map json2Map(String jsonStr) {
        return JacksonUtils.json2Bean(jsonStr, Map.class);
    }

    /**
     * 将json串转为平铺Map集合
     *
     * @param jsonStr 源json串
     * @return 结果
     */
    public static List<Map<String, Object>> jsonToTileList(String jsonStr) {

        // 解析Map
        Map parseMap = json2Map(jsonStr);
        if (parseMap == null) return null;

        // 遍历
        Iterator keyIterator = parseMap.keySet().iterator();
        Map<String, Object> rootMap = new HashMap<>();
        List<Map<String, Object>> childList = new ArrayList<>();
        while (keyIterator.hasNext()) {
            Object key = keyIterator.next();
            Object value = parseMap.get(key);
            if (value instanceof List) {
                List valueList = (List) value;
                valueList.forEach(x -> {
                    if (x instanceof Map) {
                        Map<String, Object> dataMap = (Map<String, Object>) x;
                        Map.Entry<String, Object> entry = dataMap.entrySet().iterator().next();
                        dataMap.put("featureCode", entry.getKey());
                        dataMap.put("featureValue", entry.getValue());
                        childList.add(dataMap);
                    } else {
                        childList.add(CollectionUtils.createValueMap().add(String.valueOf(key), x).build());
                    }
                });
            } else {
                rootMap.put(String.valueOf(key), value);
            }
        }

        // 合并集合
        if (!CollectionUtils.isEmpty(childList)) {
            childList.forEach(x -> CollectionUtils.copy(rootMap, x));
        }
        return childList;
    }

    /**
     * 解析字符串转为jsonNode
     *
     * @param jsonStr 源字符串
     * @return JsonNode
     */
    public static JsonNode json2Node(String jsonStr) {
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }

    public static void main(String[] args) {
//        String content = "{'caseNo': '201901150752051212174389', 'featureList': [{'carinfo': '苏EW65K2'}, {'carinfo': '浙JA992G'}, {'carinfo': '沪BME033'}]}";
        String content = "{'caseNo': '201901150752051212174389', 'featureList': []}";


//        content = "{\"centerCaseNo\":\"201901010716545472234586\", \"caseNo\":['201812311654104552789604', '201812311719105852127089', '201812311949377182271351', '201812312114500032206703', '201812312135359132366156', '201901010020184242247439', '201901010449357512334851', '201901010514469552497236', '201901011124020952693385', '201901011344085492929174']}";

        List<Map<String, Object>> maps = JacksonUtils.jsonToTileList(content.replaceAll("'", "\""));
        System.out.println(maps.size());

        String tsv = "01901101545097672385451        9999";
        String[] split = tsv.split("\t");
        System.out.println(tsv.split("\\s+").length);

        System.out.println(String.valueOf(null));
    }

}
