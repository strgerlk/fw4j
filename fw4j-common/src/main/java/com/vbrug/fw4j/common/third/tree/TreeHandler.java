package com.vbrug.fw4j.common.third.tree;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 树结构处理工具
 */
public class TreeHandler {

    protected TreeNode tree;

    protected TreeHandler() {
        this.tree = new TreeNode();
        this.tree.setCode("*");
        this.tree.setName("*");
        this.tree.setLevel(0);
        this.tree.setChildren(new ArrayList<>());
    }

    /**
     * 构建树处理器
     *
     * @param dataList 列表数据
     * @return TreeHandler instance
     */
    public static TreeHandler buildFromList(List<Map<String, Object>> dataList) {
        TreeHandler handler = new TreeHandler();
        dataList.forEach(x -> {
            TreeNode node = new TreeNode();
            node.setCode(String.valueOf(x.get("code")));
            node.setName(String.valueOf(x.get("name")));
            node.setParentCode(String.valueOf(x.get("parentCode")));
            x.remove("code");
            x.remove("name");
            x.remove("parentCode");
            node.setAttributes(ObjectUtils.isEmpty(x) ? null : x);
            handler.add(node);
        });
        return handler;
    }

    /**
     * 添加节点
     *
     * @param node 待添加的节点
     */
    public void add(TreeNode node) {
        // 匹配一级是否有子节点
        List<TreeNode> children = this.tree.getChildren();
        List<TreeNode> childList = children.stream().filter(x -> node.getCode().equals(x.getParentCode())).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(childList)) {
            node.setChildren(new ArrayList<>());
            childList.forEach(x -> {
                node.getChildren().add(x);
                children.remove(x);
            });
        }

        // 遍历查找父节点
        Function<TreeNode, Boolean> func = new Function<TreeNode, Boolean>() {
            @Override
            public Boolean apply(TreeNode treeNode) {
                Assert.isTrue(! node.getCode().equals(treeNode.getCode()), "节点重复");
                if (node.getParentCode().equals(treeNode.getCode())) {
                    if (Objects.isNull(treeNode.getChildren()))
                        treeNode.setChildren(new ArrayList<>());
                    Assert.isTrue(! treeNode.getChildren().stream().anyMatch(x -> node.getCode().equals(x.getCode())), "节点重复");
                    treeNode.getChildren().add(node);
                    node.setLevel(treeNode.getLevel()+1);
                    return true;
                } else if (! ObjectUtils.isEmpty(treeNode.getChildren())) {
                    for (TreeNode child : treeNode.getChildren()) {
                        if (this.apply(child))
                            return true;
                    }
                }
                return false;
            }
        };

        // 若无父节点，将节点时添加为一级根节点
        if (! func.apply(this.tree)) {
            node.setLevel(1);
            this.tree.getChildren().add(node);
        }

        // 当前节点如果子节点需刷新节点级别
        if (! ObjectUtils.isEmpty(node.getChildren()))
            flushLevel(this.tree.getLevel()+1, node);
    }

    /**
     * 根据code查找节点
     *
     * @param code 待查找code
     * @return 树节点
     */
    public TreeNode get(String code){
        return new Function<TreeNode, TreeNode>(){
            @Override
            public TreeNode apply(TreeNode node) {
                if (node.getCode().equals(code))
                    return node;
                if (ObjectUtils.isEmpty(node.getChildren()))
                    return null;
                for (TreeNode child : node.getChildren()) {
                    TreeNode apply = this.apply(child);
                    if (Objects.nonNull(apply))
                        return apply;
                }
                return null;
            }
        }.apply(this.tree);
    }

    /**
     * 树的遍历
     * @param consumer 消费方式
     */
    public void forEach(Consumer<TreeNode> consumer){
        Objects.requireNonNull(consumer);
        Consumer<TreeNode> traverseConsumer = new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode treeNode) {
                consumer.accept(treeNode);
                if (!ObjectUtils.isEmpty(treeNode.getChildren()))
                    treeNode.getChildren().forEach(this);
            }
        };
        this.getTree().forEach(traverseConsumer::accept);
    }



    /**
     * 删除节点
     *
     * @param node 待删除的节点
     */
    public void remove(TreeNode node) {
        this.remove(node.getCode());
    }

    /**
     * 删除节点
     *
     * @param codes 待删除节点编号（数组）
     */
    public void remove(String... codes) {
        new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode treeNode) {
                List<TreeNode> children = treeNode.getChildren();
                if (!ObjectUtils.isEmpty(children)) {
                    List<TreeNode> filterList = children.stream()
                            .filter(x -> Stream.of(codes).anyMatch(y -> x.getCode().equals(y))).collect(Collectors.toList());
                    if (ObjectUtils.isEmpty(filterList)) {
                        children.forEach(this);
                    } else {
                        filterList.forEach(children::remove);
                    }
                }
            }
        }.accept(this.tree);
    }

    public List<TreeNode> getTree() {
        return this.tree.getChildren();
    }

    public List<TreeNode> getTree(int depth) {
        return null;
    }

    List<TreeNode> getLevelTree(int level) {
        return null;
    }

    /**
     * 返回深克隆实例
     *
     * @return 深克隆实例
     */
    public TreeHandler deepClone(){
        TreeHandler newHandler = new TreeHandler();
        newHandler.tree = this.tree.deepClone();
        return newHandler;
    }

    /**
     * 根据节点编号获取子树
     *
     * @param codes 新的节点编号
     * @return 返回新树
     */
    public TreeHandler getTreeByRoot(String... codes) {
        TreeHandler newTreeHandler = new TreeHandler();
        new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode treeNode) {
                if (Stream.of(codes).anyMatch(x->Objects.equals(x, treeNode.getCode()))){
                    newTreeHandler.add(treeNode.deepClone());
                } else if (! ObjectUtils.isEmpty(treeNode.getChildren())) {
                    treeNode.getChildren().forEach(this);
                }
            }
        }.accept(this.tree);

        // 刷新节点level
        flushLevel(0, newTreeHandler.tree);
        return newTreeHandler;
    }

    /**
     * 根据子节点获取树
     * @param childCodes 子节点编号
     * @return 返回新树处理器
     */
    public TreeHandler getTreeByChild(String... childCodes){
        TreeHandler newHandler = this.deepClone();
        // 标识有效节点
        new Function<TreeNode, Boolean>(){
            @Override
            public Boolean apply(TreeNode treeNode) {
                Boolean flag = false;
                if (Stream.of(childCodes).anyMatch(x->Objects.equals(x, treeNode.getCode()))){
                    flag = true;
                }
                if (! ObjectUtils.isEmpty(treeNode.getChildren())) {
                    for (int i = 0; i < treeNode.getChildren().size(); i++) {
                        TreeNode child = treeNode.getChildren().get(i);
                        if (this.apply(child))
                            flag = true;
                        else {
                            treeNode.getChildren().remove(child);
                            i --;
                        }
                    }
                }
                return flag;
            }
        }.apply(newHandler.tree);

        return newHandler;
    }

    /**
     * 生成排序编号
     */
    public TreeHandler procSequence(){
        if (ObjectUtils.isEmpty(this.getTree()))
            return this;
        new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode treeNode) {
                for (int i = 0; i < treeNode.getChildren().size(); i++) {
                    TreeNode node = treeNode.getChildren().get(i);
                    if (Objects.isNull(node.getAttributes()))
                        node.setAttributes(new HashMap<>());
                    node.getAttributes().put("displayOrder", i+1);
                    if (!ObjectUtils.isEmpty(node.getChildren()))
                        this.accept(node);
                }
            }
        }.accept(this.tree);
        return this;
    }


    /**
     * 获取树深度
     *
     * @return 树深度
     */
    public Integer getDepth() {
        return new Function<TreeNode, Integer>() {
            @Override
            public Integer apply(TreeNode treeNode) {
                AtomicReference<Integer> depth = new AtomicReference<>(0);
                if (!ObjectUtils.isEmpty(treeNode.getChildren())){
                    treeNode.getChildren().forEach(x->{
                        Integer apply = this.apply(x);
                        depth.set( depth.get() < apply ? apply : depth.get() );
                    });
                }
                return depth.get() + 1;
            }
        }.apply(this.tree);
    }

    /**
     * 根据节点获取当前树深度
     *
     * @param codes
     * @return
     */
    Integer getDepthByRoot(String... codes) {
        return null;
    }

    /**
     * 刷新树level
     * @param level 当前节点level
     * @param node 当前节点
     */
    private static void flushLevel(int level, TreeNode node){
        node.setLevel(level);
        if (! ObjectUtils.isEmpty(node.getChildren())){
            node.getChildren().forEach(x->flushLevel(level+1, x));
        }
    }
}
