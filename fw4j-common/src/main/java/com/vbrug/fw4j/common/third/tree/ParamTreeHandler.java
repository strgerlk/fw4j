package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 码表
 * @author vbrug
 * @since 1.0.0
 */
public class ParamTreeHandler<E, T> extends BaseTreeHandler<E, T> {


    public ParamTreeHandler(List<TreeNode<E, T>> treeNodeList) {super(treeNodeList);}

    /**
     * ck码表处理
     */
    public List<Map<String, Object>> ckClear() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        this.vRoot.bfs(x -> {
            // 虚拟根节点跳过
            if (x.getLevel() == -1)
                return;
            x.bfs(y -> {
                Map<String, Object> map = new HashMap<>();
                map.put("superCode", x.getCode());
                map.put("superName", x.getName());
                map.put("superLevel", x.getLevel());
                map.put("code", y.getCode());
                map.put("name", y.getName());
                map.put("level", y.getLevel());
                mapList.add(map);
            });
        });
        return mapList;
    }

    /**
     * code编码处理
     */
    public List<Map<String, Object>> fullCodeClear() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        this.vRoot.bfs(x -> {
            // 虚拟根节点跳过
            if (x.getLevel() == -1)
                return;
            Map<String, Object> map = new HashMap<>();
            map.put("code", x.getCode());
            map.put("name", x.getName());
            map.put("level", x.getLevel());
            // 计算全编号
            String         fullCode = "";
            TreeNode<E, T> loopNode = x;
            while (loopNode != null) {
                fullCode = "-" + loopNode.getCode() + fullCode;
                loopNode = get(loopNode.getParentCode());
            }
            map.put("fullCode", fullCode);
            mapList.add(map);
        });
        return mapList;
    }

    public static void main(String[] args) throws Exception {
        // fullCodeClear("/home/vbrug/sd_xqdw.tsv");
        ckParamClear("/home/vbrug/sd_xqdw_2.tsv");
    }


    private static void ckParamClear(String filePath) throws Exception {
        // 处理ck吗表
        ParamTreeHandler<String, Map<String, Object>> paramTreeHandler = new ParamTreeHandler<>(readFromFile(filePath));
        List<Map<String, Object>>                     maps             = paramTreeHandler.ckClear();

        // 生成结果文件
        List<String> rsLineList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String line = map.get("superCode") + "\t"
                    + map.get("superName") + "\t"
                    + map.get("superLevel") + "\t"
                    + map.get("code") + "\t"
                    + map.get("name") + "\t"
                    + map.get("level") ;
            rsLineList.add(line);
        }
        FileUtil.produceFile(filePath + "_out", rsLineList);
    }

    private static void fullCodeClear(String filePath) throws Exception {
        // 处理全编号
        ParamTreeHandler<String, Map<String, Object>> paramTreeHandler = new ParamTreeHandler<>(readFromFile(filePath));
        List<Map<String, Object>>                     maps             = paramTreeHandler.fullCodeClear();

        // 生成结果文件
        List<String> rsLineList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String line = map.get("code") + "\t" + map.get("name") + "\t" + map.get("level") + "\t" + map.get("fullCode");
            rsLineList.add(line);
        }
        FileUtil.produceFile(filePath + "_out", rsLineList);
    }

    private static List<TreeNode<String, Map<String, Object>>> readFromFile(String filePath) throws Exception {
        List<String> lineList = FileUtil.parseFileByLine(new File(filePath));
        // 转换treeCodeList
        List<TreeNode<String, Map<String, Object>>> treeNodeList = new ArrayList<>();
        for (String s : lineList) {
            String[] splits = s.split("\t");
            // 转换TreeNode
            TreeNode<String, Map<String, Object>> node = new TreeNode<>();
            node.setCode(splits[0].trim());
            node.setName(splits[1].trim());
            node.setParentCode(splits[2].trim());
            treeNodeList.add(node);
        }
        return treeNodeList;
    }
}
