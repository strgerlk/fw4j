package com.vbrug.fw4j.core.datastruct.tree;

import java.util.List;

/**
 * 树处理工具
 * @param <T>
 * @param <D>
 */
public class BaseTree<T, D> extends AbstractTree<T, D> {

    public BaseTree(List<TreeNode<T, D>> treeNodeList) {
        super(treeNodeList);
    }
}
