package com.vbrug.fw4j.common.third.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 码表
 * @author vbrug
 * @since 1.0.0
 */
public class ParamTree<T, D> extends AbstractTree<T, D> {

    public ParamTree(List<TreeNode<T, D>> treeNodeList) {super(treeNodeList);}

    /**
     * ck码表处理
     */
    public List<Map<String, Object>> ckClear() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        this.vRoot.bfs(x -> {
            x.bfs(y -> {
                Map<String, Object> map = new HashMap<>();
                map.put("superCode", x.getId());
                map.put("superName", x.getName());
                map.put("superLevel", x.getLevel());
                map.put("code", y.getId());
                map.put("name", y.getName());
                map.put("level", y.getLevel());
                mapList.add(map);
            });
        });
        return mapList;
    }

    /**
     * code编码处理
     */
    public List<Map<String, Object>> fullCodeClear() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        this.vRoot.bfs(x -> {
            Map<String, Object> map = new HashMap<>();
            map.put("code", x.getId());
            map.put("name", x.getName());
            map.put("parentCode", x.getParentId());
            map.put("level", x.getLevel());
            // 计算全编号
            String         fullCode = "";
            TreeNode<T, D> loopNode = x;
            while (loopNode != null) {
                fullCode = "-" + loopNode.getId() + fullCode;
                loopNode = get(loopNode.getParentId());
            }
            map.put("fullCode", fullCode);
            mapList.add(map);
        });
        return mapList;
    }

}
