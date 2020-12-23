package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树节点
 */
public class TreeNode implements Cloneable {
    private String         code;
    private String         name;
    private List<TreeNode> children;
    private String         parentCode;
    private Integer        level;
    private Map<String, Object> attributes;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(
            List<TreeNode> children) {
        this.children = children;
    }

    public Boolean isLeaf() {
        return this.children == null || this.children.size() == 0;
    }

    /**
     * 浅克隆，克隆基本信息，不克隆引用对象
     *
     * @return  克隆实例
     */
    public TreeNode clone() {
        TreeNode clone = null;
        try {
            clone = (TreeNode) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clone.setChildren(null);
        clone.setAttributes(this.getAttributes());
        return clone;
    }

    /**
     * 深度克隆
     *
     * @return 深克隆实例
     */
    public TreeNode deepClone() {
        TreeNode clone = this.clone();
        if (!ObjectUtils.isEmpty(this.getChildren())){
            List<TreeNode> children = new ArrayList<>();
            this.getChildren().forEach(x -> {
                children.add(x.deepClone());
            });
            clone.setChildren(children);
        }
        return clone;
    }
}
