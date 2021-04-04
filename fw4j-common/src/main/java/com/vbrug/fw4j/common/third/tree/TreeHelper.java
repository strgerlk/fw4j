package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.ObjectUtils;
import com.vbrug.fw4j.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树处理工具
 * @author vbrug
 * @since 1.0.0
 */
public class TreeHelper {


    /**
     * 将节点转换为树节点
     * @param dataList 源数据
     * @return 树节点编号
     */
    public static List<TreeNode<String, Map<String, Object>>> convert2TreeNode(List<Map<String, Object>> dataList) {
        return convert2TreeNode(dataList, null, null, null);
    }

    /**
     * 将节点转换为树节点
     * @param dataList        源数据
     * @param codeField       节点编号属性
     * @param parentCodeField 父节点属性名
     * @param nameField       节点名称属性名
     * @return 树节点编号
     */
    public static List<TreeNode<String, Map<String, Object>>> convert2TreeNode(List<Map<String, Object>> dataList,
                                                                               String codeField, String parentCodeField, String nameField) {
        Assert.isTrue(!CollectionUtils.isEmpty(dataList), "数据不可以为空");

        // 主字段名处理
        codeField = StringUtils.isEmpty(codeField) ? "code" : codeField;
        parentCodeField = StringUtils.isEmpty(parentCodeField) ? "parentCode" : parentCodeField;
        nameField = StringUtils.isEmpty(nameField) ? "name" : nameField;

        // 转换TreeNode
        List<TreeNode<String, Map<String, Object>>> treeNodeList = new ArrayList<>();
        for (Map<String, Object> x : dataList) {
            TreeNode<String, Map<String, Object>> node = new TreeNode<>();
            node.setCode(String.valueOf(x.get(codeField)));
            node.setName(String.valueOf(x.get(nameField)));
            node.setParentCode(String.valueOf(x.get(parentCodeField)));
            x.remove(codeField);
            x.remove(nameField);
            x.remove(parentCodeField);
            node.setAttributes(ObjectUtils.isEmpty(x) ? null : x);
            treeNodeList.add(node);
        }
        return treeNodeList;
    }

}
