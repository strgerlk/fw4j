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
 * @author vbrug
 * @since 1.0.0
 */
public abstract class AbstractTree<T, D> implements Tree<T, D> {

    protected     TreeNode<T, D>         vRoot     = new TreeNode<>();
    private final Map<T, TreeNode<T, D>> treeIndex = new HashMap<>();

    public AbstractTree(List<TreeNode<T, D>> treeNodeList) {
        vRoot.setLevel(-1);
        vRoot.setChildren(new ArrayList<>());
        this.init(treeNodeList);
    }

    /**
     * 从节点中构建树
     * @param treeNodeList 树节点
     */
    public void init(List<TreeNode<T, D>> treeNodeList) {
        // 增加节点
        for (TreeNode<T, D> node : treeNodeList) {
            this.add_(node);
        }
        // 刷新节点Level
        for (TreeNode<T, D> treeNode : vRoot.getChildren()) {
            this.flushLevel(treeNode);
        }
    }

    /**
     * 添加节点，不刷新节点Level
     * @param node 待添加的节点
     */
    public void add(TreeNode<T, D> node) {
        this.add_(node);
        this.flushLevel(node);
    }

    /**
     * 删除节点
     * @param node 待删除的节点
     */
    public void delete(TreeNode<T, D> node) {
        if (node.getLevel() == 1)
            vRoot.getChildren().remove(node);
        else
            treeIndex.get(node.getParentId()).getChildren().remove(node);
    }

    /**
     * 根据id查找节点
     */
    public TreeNode<T, D> get(T id) {
        return treeIndex.get(id);
    }


    /**
     * 刷新树level
     * @param node 当前节点
     */
    private void flushLevel(TreeNode<T, D> node) {
        node.bfs(x -> {
            TreeNode<T, D> parentNode = treeIndex.get(x.getParentId());
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
    private void add_(TreeNode<T, D> node) {
        /* 01-判断当前节点是否重复 */
        Assert.isNull(treeIndex.get(node.getId()), "节点：" + node.getId() + " 已存在");
        if (node.getId().equals(node.getParentId()))
            throw new RuntimeException("节点： " + node.getId() + " 自身编号和父级编号相同");

        /* 02-匹配当前一级是否有当前节点的子节点 */
        List<TreeNode<T, D>> rootChildren = this.vRoot.getChildren();
        List<TreeNode<T, D>> childList    = rootChildren.stream().filter(x -> node.getId().equals(x.getParentId())).collect(Collectors.toList());
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
            if (x.getId().equals(node.getParentId())) {
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
        treeIndex.put(node.getId(), node);
    }
}
