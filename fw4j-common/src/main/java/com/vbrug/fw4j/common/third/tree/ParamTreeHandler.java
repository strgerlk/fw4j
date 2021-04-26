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
public class ParamTreeHandler<E, T> extends BaseTreeHandler<E, T> {


    public ParamTreeHandler(List<TreeNode<E, T>> treeNodeList) {super(treeNodeList);}

    /**
     * ck码表处理
     */
    public List<Map<String, Object>> ckClear() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        this.vRoot.bfs(x -> {
            x.bfs(y -> {
                Map<String, Object> map = new HashMap<>();
                map.put("superCode", x.getCode());
                map.put("superName", x.getName());
                map.put("superLevel", x.getLevel());
                map.put("code", y.getCode());
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
            map.put("code", x.getCode());
            map.put("name", x.getName());
            map.put("parentCode", x.getParentCode());
            map.put("level", x.getLevel());
            // 计算全编号
            String         fullCode = "";
            TreeNode<E, T> loopNode = x;
            while (loopNode != null) {
                fullCode = "-" + loopNode.getCode() + fullCode;
                loopNode = get(loopNode.getParentCode());
            }
            map.put("fullCode", fullCode);
            mapList.add(map);
        });
        return mapList;
    }

}
