package com.vbrug.fw4j.core.datastruct.tree;

/**
 * @author vbrug
 * @since 1.0.0
 */
interface Tree<T, D> {
    /**
     * 添加节点，不刷新节点Level
     * @param node 待添加的节点
     */
    void add(TreeNode<T, D> node);

    /**
     * 删除节点
     * @param node 待删除的节点
     */
    void delete(TreeNode<T, D> node);

    /**
     * 根据id查找节点
     */
    TreeNode<T, D> get(T id);
}
