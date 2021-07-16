package com.vbrug.fw4j.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.vbrug.fw4j.common.ValueMap;
import com.vbrug.fw4j.common.exception.Fw4jException;
import com.vbrug.fw4j.core.datastruct.tree.BaseTree;
import com.vbrug.fw4j.core.datastruct.tree.TreeNode;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JSON解析工具
 * @author vbrug
 * @since 1.0.0
 */
public abstract class JacksonUtils {

    /**
     * 映射器
     */
    private static final ObjectMapper mapper = new ObjectMapper();


    static {
        // 转换为格式化的json
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 转换字段名驼峰格式
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //修改日期格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将bean序列化为JSON字符串
     * @param object 待序列化对象
     * @return 返回序列化后的字符串
     */
    public static String bean2Json(Object object) {
        Assert.state(!ObjectUtils.isNull(object), "object参数不可为空");
        StringWriter  sw  = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, object);
        } catch (IOException e) {
            throw new Fw4jException(e, "对象{}转JSON出错", object);
        } finally {
            try {
                Objects.requireNonNull(gen).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sw.toString();
    }

    /**
     * 将json解析为bean对象
     * @param jsonStr 待解析字符串
     * @param <T>     clazz  泛型class
     * @return 解析后的结果
     */
    public static <T> T json2Bean(String jsonStr, Class<T> clazz) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {} 转对象出错", jsonStr);
        }
    }

    /**
     * 解析字符串转为jsonNode
     * @param jsonStr 源字符串
     * @return JsonNode
     */
    public static JsonNode json2Node(String jsonStr) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        try {
            return mapper.readTree(jsonStr);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {}转json对象出错", jsonStr);
        }
    }

    /**
     * 将json解析为Map对象
     * @param jsonStr 待解析字符串
     * @param <K>     keyClass  Map泛型class参数一
     * @param <V>     valueClass  Map泛型class参数二
     * @return 解析后的结果
     */
    public static <K, V> Map<K, V> json2Map(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        try {
            return mapper.readValue(jsonStr, mapType);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {}转Map对象出错", jsonStr);
        }
    }

    /**
     * 将json解析为Map对象
     * @param jsonStr 待解析字符串
     * @param <K>     keyClass  Map泛型class参数一
     * @param <V>     valueClass  Map泛型class参数二
     * @return 解析后的结果
     */
    public static <K, V> ValueMap<K, V> json2ValueMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        MapType mapType = mapper.getTypeFactory().constructMapType(ValueMap.class, keyClass, valueClass);
        try {
            return mapper.readValue(jsonStr, mapType);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {}转Map对象出错", jsonStr);
        }
    }

    /**
     * 将json解析为List集合对象
     * @param jsonStr 待解析字符串
     * @param <T>     clazz  List泛型class参数
     * @return 解析后的结果
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> clazz) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return mapper.readValue(jsonStr, collectionType);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {}转List对象出错", jsonStr);
        }
    }

    /**
     * 将json解析为Map对象
     * @param jsonStr 待解析字符串
     * @param <K>     keyClass  Map泛型class参数一
     * @param <V>     valueClass  Map泛型class参数二
     * @return 解析后的结果
     */
    public static <K, V> List<Map<K, V>> jsonToListMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        MapType        mapType        = mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, mapType);
        try {
            return mapper.readValue(jsonStr, collectionType);
        } catch (IOException e) {
            throw new Fw4jException(e, "JSON {}转List<Map>对象出错", jsonStr);
        }
    }


    /**
     * 将json串转为平铺Map集合
     * @param jsonStr 源json串
     * @return 结果
     */
    public static List<Map<String, String>> json2TileList(String jsonStr, String parsePath) {

        Assert.state(!StringUtils.isEmpty(jsonStr), "json解析字符串不可为空");
        Assert.state(!StringUtils.isEmpty(parsePath), "parsePath为空");

        // 解析json字符串
        JsonNode rootNode = json2Node(jsonStr);
        if (rootNode == null || rootNode.isNull()) {
            return null;
        }

        // 构建解析树
        String[]                              pathArray   = parsePath.replaceAll("(\\\\+|/+)", "/").replaceAll("^/", "").split("/");
        BaseTree<String, Map<String, String>> treeHandler = buildTree(rootNode, pathArray);

        // 查找叶子节点
        List<TreeNode<String, Map<String, String>>> leafList = new ArrayList<>();
        treeHandler.get("root").bfs(x -> {
            if (x.isLeaf()) leafList.add(x);
        });

        // 生成结果
        List<Map<String, String>> dataList = new ArrayList<>();
        leafList.forEach(x -> {
            Map<String, String>                   map            = x.getData();
            TreeNode<String, Map<String, String>> parentTreeNode = treeHandler.get(x.getParentId());
            while (parentTreeNode != null) {
                CollectionUtils.copy(parentTreeNode.getData(), map, false);
                parentTreeNode = treeHandler.get(parentTreeNode.getParentId());
            }
            dataList.add(map);
        });

        return dataList;
    }


    /**
     * 构建解析树
     * @param rootJsonNode 根节点
     * @param pathArray    解析路径
     * @return 返回解析树
     */
    private static BaseTree<String, Map<String, String>> buildTree(JsonNode rootJsonNode, String[] pathArray) {
        List<TreeNode<String, Map<String, String>>> treeNodeList = new ArrayList<>();

        Queue<JNode> jNodeQueue = new LinkedList<>();
        JNode        jNode      = new JNode();
        jNode.setJsonNode(rootJsonNode);
        jNode.setPathLevel(0);
        jNode.setTreeNodeCode("root");
        jNodeQueue.add(jNode);
        while (!jNodeQueue.isEmpty()) {
            // 变量初始化
            JNode                                 pollJNode  = jNodeQueue.poll();
            TreeNode<String, Map<String, String>> treeNode   = new TreeNode<>();
            Map<String, String>                   attributes = new HashMap<>();
            // 处理其他属性
            JsonNode pollJsonNode = pollJNode.getJsonNode();
            if (pollJsonNode.isValueNode()) {
                attributes.put(pathArray[pollJNode.getPathLevel() - 1], pollJsonNode.textValue());
            } else {
                Iterator<String> fieldNames = pollJsonNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String next = fieldNames.next();
                    if (pollJNode.getPathLevel() < pathArray.length && next.equals(pathArray[pollJNode.getPathLevel()]))
                        continue;
                    attributes.put(next, pollJsonNode.get(next).textValue());
                }
            }

            treeNode.setData(attributes);
            // 处理节点编号
            treeNode.setId(pollJNode.getTreeNodeCode());
            if (ObjectUtils.notNull(pollJNode.getParentTreeNode()))
                treeNode.setParentId(pollJNode.getParentTreeNode().getId());

            // 加入List
            treeNodeList.add(treeNode);

            // 处理路径节点
            if (pollJNode.getPathLevel() >= pathArray.length) continue;
            JsonNode pathJsonNode = pollJsonNode.get(pathArray[pollJNode.getPathLevel()]);
            if (pathJsonNode != null) {
                if (pathJsonNode.isArray()) {
                    Iterator<JsonNode> pathNodeArray = pathJsonNode.elements();
                    int                i             = 0;
                    while (pathNodeArray.hasNext()) {
                        JsonNode loopJsonNode = pathNodeArray.next();
                        JNode    todoJNode    = new JNode();
                        todoJNode.setParentTreeNode(treeNode);
                        todoJNode.setPathLevel(pollJNode.getPathLevel() + 1);
                        todoJNode.setTreeNodeCode(treeNode.getId() + "_" + pathArray[pollJNode.getPathLevel()] + "-" + i);
                        todoJNode.setJsonNode(loopJsonNode);
                        jNodeQueue.add(todoJNode);
                        i++;
                    }
                } else {
                    JNode todoJNode = new JNode();
                    todoJNode.setParentTreeNode(treeNode);
                    todoJNode.setPathLevel(pollJNode.getPathLevel() + 1);
                    todoJNode.setTreeNodeCode(treeNode.getId() + "_" + pathArray[pollJNode.getPathLevel()]);
                    todoJNode.setJsonNode(pathJsonNode);
                    jNodeQueue.add(todoJNode);
                }
            }
        }
        return new BaseTree<>(treeNodeList);
    }

    private static class JNode {
        private TreeNode<String, Map<String, String>> parentTreeNode;
        private String                                treeNodeCode;
        private int                                   pathLevel;
        private JsonNode                              jsonNode;

        public TreeNode<String, Map<String, String>> getParentTreeNode() {
            return parentTreeNode;
        }

        public void setParentTreeNode(TreeNode<String, Map<String, String>> parentTreeNode) {
            this.parentTreeNode = parentTreeNode;
        }

        public String getTreeNodeCode() {
            return treeNodeCode;
        }

        public void setTreeNodeCode(String treeNodeCode) {
            this.treeNodeCode = treeNodeCode;
        }

        public int getPathLevel() {
            return pathLevel;
        }

        public void setPathLevel(int pathLevel) {
            this.pathLevel = pathLevel;
        }

        public JsonNode getJsonNode() {
            return jsonNode;
        }

        public void setJsonNode(JsonNode jsonNode) {
            this.jsonNode = jsonNode;
        }
    }
}
