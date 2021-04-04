package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 树处理工具
 * @param <E>
 * @param <T>
 */
public class BaseTreeHandler<E, T> {

    protected     TreeNode<E, T>         vRoot     = new TreeNode<>();
    private final Map<E, TreeNode<E, T>> treeIndex = new HashMap<>();

    public BaseTreeHandler(List<TreeNode<E, T>> treeNodeList) {
        vRoot.setLevel(-1);
        vRoot.setChildren(new ArrayList<>());
        this.init(treeNodeList);
    }

    /**
     * 从节点中构建树
     * @param treeNodeList 树节点
     */
    public void init(List<TreeNode<E, T>> treeNodeList) {
        // 增加节点
        for (TreeNode<E, T> node : treeNodeList) {
            this.add_(node);
        }
        // 刷新节点Level
        for (TreeNode<E, T> treeNode : vRoot.getChildren()) {
            this.flushLevel(treeNode);
        }
    }

    /**
     * 添加节点，不刷新节点Level
     * @param node 待添加的节点
     */
    public void add(TreeNode<E, T> node) {
        this.add_(node);
        this.flushLevel(node);
    }

    /**
     * 删除节点
     * @param node 待删除的节点
     */
    public void delete(TreeNode<E, T> node) {
        if (node.getLevel() == 1)
            vRoot.getChildren().remove(node);
        else
            treeIndex.get(node.getParentCode()).getChildren().remove(node);
    }

    /**
     * 根据code查找节点
     */
    public TreeNode<E, T> get(E code) {
        return treeIndex.get(code);
    }


    /**
     * 刷新树level
     * @param level 当前节点level
     * @param node  当前节点
     */
    private void flushLevel(TreeNode<E, T> node) {
        node.bfs(x -> {
            TreeNode<E, T> parentNode = treeIndex.get(x.getParentCode());
            if (parentNode == null)
                node.setLevel(1);
            else
                x.setLevel(parentNode.getLevel() + 1);
        });
    }

    /**
     * 添加节点，不刷新节点Level
     * @param node 待添加的节点
     */
    private void add_(TreeNode<E, T> node) {
        /* 01-判断当前节点是否重复 */
        Assert.isNull(treeIndex.get(node.getCode()), "节点：" + node.getCode() + " 已存在");

        /* 02-匹配当前一级是否有当前节点的子节点 */
        List<TreeNode<E, T>> rootChildren = this.vRoot.getChildren();
        List<TreeNode<E, T>> childList    = rootChildren.stream().filter(x -> node.getCode().equals(x.getParentCode())).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(childList)) {
            node.setChildren(new ArrayList<>());
            childList.forEach(x -> {
                node.getChildren().add(x);
                rootChildren.remove(x);
            });
        }

        /* 03-遍历查找父节点 */
        AtomicBoolean hasParent = new AtomicBoolean(false);
        this.vRoot.bfs(x -> {
            // 虚拟根节点跳过
            if (x.getLevel() == -1)
                return true;
            if (x.getCode().equals(node.getParentCode())) {
                if (x.getChildren() == null)
                    x.setChildren(new ArrayList<>());
                x.getChildren().add(node);
                hasParent.set(true);
                return false;
            }
            return true;
        });

        /* 04-判断处理结果 */
        // 若无父节点，将节点时添加为一级根节点
        if (!hasParent.get()) {
            this.vRoot.getChildren().add(node);
        }

        /* 05-加入索引 */
        treeIndex.put(node.getCode(), node);
    }
}
