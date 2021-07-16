package com.vbrug.fw4j.core.datastruct.tree;

import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 树工具类
 * @author vbrug
 * @since 1.0.0
 */
public class TreeUtils {


    /**
     * 广度优先遍历
     * @param node 树节点
     */
    public static <E, T> void bfs(TreeNode<E, T> node, Consumer<TreeNode<E, T>> consumer) {
        bfs(node, x -> {
            consumer.accept(x);
            return true;
        });
    }

    /**
     * 广度优先遍历
     * @param node 树节点
     */
    public static <E, T> void bfs(TreeNode<E, T> node, Function<TreeNode<E, T>, Boolean> function) {
        Queue<TreeNode<E, T>> toVisitQueue = new LinkedList<>();
        toVisitQueue.add(node);
        while (!toVisitQueue.isEmpty()) {
            TreeNode<E, T> pollNode = toVisitQueue.poll();
            // level 小于0为虚拟节点，不遍历
            if (pollNode.getLevel() >= 0 && !function.apply(pollNode)) {
                break;
            }
            if (!ObjectUtils.isEmpty(pollNode.getChildren()))
                toVisitQueue.addAll(pollNode.getChildren());
        }
    }

    /**
     * 深度优先遍历
     * @param node 树节点
     */
    public static <E, T> void dfs(TreeNode<E, T> node, Function<TreeNode<E, T>, Boolean> function) {
        Stack<TreeNode<E, T>> toVisitStack = new Stack<>();
        toVisitStack.add(node);
        while (!toVisitStack.isEmpty()) {
            TreeNode<E, T> pollNode = toVisitStack.pop();
            if (!function.apply(pollNode))
                break;
            toVisitStack.addAll(pollNode.getChildren());
        }
    }

}
