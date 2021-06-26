package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 树节点
 */
public class TreeNode<T, D> implements Cloneable {
    private T                    id;
    private T                    parentId;
    private String               name;
    private List<TreeNode<T, D>> children;
    private int                  level = 0;
    private D                    data;
    private boolean              isLeaf;

    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getParentId() {
        return parentId;
    }

    public void setParentId(T parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode<T, D>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T, D>> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    /**
     * 浅克隆，克隆基本信息，不克隆引用对象
     * @return 克隆实例
     */
    public TreeNode<T, D> clone() {
        TreeNode<T, D> clone = null;
        try {
            clone = (TreeNode<T, D>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(clone).setChildren(null);
        clone.setData(this.getData());
        return clone;
    }

    /**
     * 深度克隆
     * @return 深克隆实例
     */
    public TreeNode<T, D> deepClone() {
        TreeNode<T, D> clone = this.clone();
        if (!ObjectUtils.isEmpty(this.getChildren())) {
            List<TreeNode<T, D>> children = new ArrayList<>();
            this.getChildren().forEach(x -> {
                children.add(x.deepClone());
            });
            clone.setChildren(children);
        }
        return clone;
    }


    /**
     * 广度遍历
     * @param consumer 消费方式
     */
    public void bfs(Consumer<TreeNode<T, D>> consumer) {
        TreeUtils.bfs(this, consumer);
    }

    /**
     * 广度遍历
     * @param consumer 消费方式
     */
    public void bfs(Function<TreeNode<T, D>, Boolean> function) {
        TreeUtils.bfs(this, function);
    }
}
