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
public class TreeNode<E, T> implements Cloneable {
    private E                    code;
    private E                    parentCode;
    private String               name;
    private List<TreeNode<E, T>> children;
    private int                  level = 0;
    private T                    attributes;
    private boolean              isLeaf;

    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    public E getCode() {
        return code;
    }

    public void setCode(E code) {
        this.code = code;
    }

    public E getParentCode() {
        return parentCode;
    }

    public void setParentCode(E parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode<E, T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<E, T>> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }

    /**
     * 浅克隆，克隆基本信息，不克隆引用对象
     * @return 克隆实例
     */
    public TreeNode<E, T> clone() {
        TreeNode<E, T> clone = null;
        try {
            clone = (TreeNode<E, T>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(clone).setChildren(null);
        clone.setAttributes(this.getAttributes());
        return clone;
    }

    /**
     * 深度克隆
     * @return 深克隆实例
     */
    public TreeNode<E, T> deepClone() {
        TreeNode<E, T> clone = this.clone();
        if (!ObjectUtils.isEmpty(this.getChildren())) {
            List<TreeNode<E, T>> children = new ArrayList<>();
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
    public void bfs(Consumer<TreeNode<E, T>> consumer) {
        TreeUtils.bfs(this, consumer);
    }

    /**
     * 广度遍历
     * @param consumer 消费方式
     */
    public void bfs(Function<TreeNode<E, T>, Boolean> function) {
        TreeUtils.bfs(this, function);
    }
}
