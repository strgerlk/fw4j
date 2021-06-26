package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class TreeBuilder<T, D> {

    private final List<TreeNode<T, D>> treeNodeList = new ArrayList<>();

    /**
     * 将节点转换为树节点
     * @param nodeList 节点
     * @return 树节点编号
     */
    public TreeBuilder<T, D> setTreeNode(List<TreeNode<T, D>> nodeList) {
        treeNodeList.addAll(nodeList);
        return this;
    }

    /**
     * 将节点转换为树节点
     * @param dataList 源数据
     * @return 树节点编号
     */
    public TreeBuilder<T, D> setTreeNodeFromData(List<Map<String, Object>> dataList) {
        return this.setTreeNodeFromData(dataList, null, null, null);
    }

    /**
     * 将节点转换为树节点
     * @param dataList        源数据
     * @param codeField       节点编号属性
     * @param parentCodeField 父节点属性名
     * @param nameField       节点名称属性名
     * @return 树节点编号
     */
    @SuppressWarnings({"unchecked", "MismatchedQueryAndUpdateOfCollection"})
    public TreeBuilder<T, D> setTreeNodeFromData(List<Map<String, Object>> dataList, String codeField, String parentCodeField, String nameField) {
        Assert.isTrue(!CollectionUtils.isEmpty(dataList), "数据不可以为空");

        // 主字段名处理
        codeField = StringUtils.isEmpty(codeField) ? "code" : codeField;
        parentCodeField = StringUtils.isEmpty(parentCodeField) ? "parentCode" : parentCodeField;
        nameField = StringUtils.isEmpty(nameField) ? "name" : nameField;

        // 转换TreeNode
        for (Map<String, Object> x : dataList) {
            TreeNode<T, D> node = new TreeNode<>();
            node.setId((T) x.get(codeField));
            node.setName(String.valueOf(x.get(nameField)));
            node.setParentId((T) x.get(parentCodeField));
            x.remove(codeField);
            x.remove(nameField);
            x.remove(parentCodeField);
            node.setData(ObjectUtils.isEmpty(x) ? null : (D) x);
            treeNodeList.add(node);
        }
        return this;
    }

    public <R extends AbstractTree<T, D>> R build(Class<R> clazz) throws Exception {
        return ClassUtils.newInstance(clazz, treeNodeList);
    }

}
