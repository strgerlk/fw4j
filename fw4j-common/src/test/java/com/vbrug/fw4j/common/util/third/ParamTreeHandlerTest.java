package com.vbrug.fw4j.common.util.third;

import com.vbrug.fw4j.core.datastruct.tree.ParamTree;
import com.vbrug.fw4j.core.datastruct.tree.TreeBuilder;
import com.vbrug.fw4j.core.datastruct.tree.TreeNode;
import com.vbrug.fw4j.common.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ParamTreeHandlerTest {

    public static void main(String[] args) throws Exception {
        String filePath = "/home/vbrug/wj_xq.tsv";

        ParamTree<String, Map<String, Object>> paramTree = new TreeBuilder<String, Map<String, Object>>()
                .setTreeNode(readFromFile(filePath)).build(ParamTree.class);
        System.out.println(paramTree.ckClear());


        fullCodeClear(filePath);
        ckParamClear(filePath);
    }


    private static void ckParamClear(String filePath) throws Exception {
        // 处理ck码表
        ParamTree<String, Map<String, Object>> paramTree = new ParamTree<>(readFromFile(filePath));
        List<Map<String, Object>>              maps      = paramTree.ckClear();

        // 生成结果文件
        List<String> rsLineList = new ArrayList<>();

        // 文件头
        String lineTitle = "id\tsuper_code\tsuper_name\tsuper_level\tcode\tname\tlevel";
        rsLineList.add(lineTitle);
        // 内容
        for (int i = 0, mapsSize = maps.size(); i < mapsSize; i++) {
            Map<String, Object> map = maps.get(i);
            String line = (i + 1) + "\t"
                    + map.get("superCode") + "\t"
                    + map.get("superName") + "\t"
                    + map.get("superLevel") + "\t"
                    + map.get("code") + "\t"
                    + map.get("name") + "\t"
                    + map.get("level");
            rsLineList.add(line);
        }
        FileUtil.produceFile(new File(filePath + "_out_ck"), rsLineList);
    }

    private static void fullCodeClear(String filePath) throws Exception {
        // 处理全编号
        ParamTree<String, Map<String, Object>> paramTree = new ParamTree<>(readFromFile(filePath));
        List<Map<String, Object>>              maps      = paramTree.fullCodeClear();

        // 生成结果文件
        List<String> rsLineList = new ArrayList<>();
        // 文件头
        String lineTitle = "id\txqdw_code\txqdw_name\tlevel\tparent_code\tallCode";
        rsLineList.add(lineTitle);
        // 内容
        for (int i = 0, mapsSize = maps.size(); i < mapsSize; i++) {
            Map<String, Object> map = maps.get(i);
            String line = (i + 1) + "\t"
                    + map.get("code") + "\t"
                    + map.get("name") + "\t"
                    + map.get("level") + "\t"
                    + map.get("parentCode") + "\t"
                    + map.get("fullCode");
            rsLineList.add(line);
        }
        FileUtil.produceFile(new File(filePath + "_out_mq"), rsLineList);
    }

    private static List<TreeNode<String, Map<String, Object>>> readFromFile(String filePath) throws Exception {
        List<String> lineList = FileUtil.parseFileByLine(new File(filePath));
        // 转换treeCodeList
        List<TreeNode<String, Map<String, Object>>> treeNodeList = new ArrayList<>();
        for (String s : lineList) {
            String[] splits = s.split("\t");
            // 转换TreeNode
            TreeNode<String, Map<String, Object>> node = new TreeNode<>();
            node.setId(splits[0].trim());
            node.setName(splits[1].trim());
            node.setParentId(splits[2].trim());
            treeNodeList.add(node);
        }
        return treeNodeList;
    }
}
