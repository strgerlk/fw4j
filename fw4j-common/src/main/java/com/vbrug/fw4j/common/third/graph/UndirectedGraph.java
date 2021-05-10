package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class UndirectedGraph<E, T, V> extends AbstractGraph<E, T, V> {

    @Override
    public void insert(Edge<E, V> edge) {
        // 处理边
        if (CollectionUtils.isEmpty(edgeIndex.get(edge.getFirstVertexId())))
            edgeIndex.put(edge.getFirstVertexId(), new HashMap<>());
        edgeIndex.get(edge.getFirstVertexId()).put(edge.getSecondVertexId(), edge);
        if (CollectionUtils.isEmpty(edgeIndex.get(edge.getSecondVertexId())))
            edgeIndex.put(edge.getSecondVertexId(), new HashMap<>());
        edgeIndex.get(edge.getSecondVertexId()).put(edge.getFirstVertexId(), edge);
    }

    @Override
    public void remove(Edge<E, V> edge) {
        // 处理边
        Map<E, Edge<E, V>> firstEdgeMap = edgeIndex.get(edge.getFirstVertexId());
        firstEdgeMap.remove(edge.getSecondVertexId());
        if (CollectionUtils.isEmpty(firstEdgeMap))
            edgeIndex.remove(firstEdgeMap);
        Map<E, Edge<E, V>> secondEdgeMap = edgeIndex.get(edge.getSecondVertexId());
        firstEdgeMap.remove(edge.getFirstVertexId());
        if (CollectionUtils.isEmpty(secondEdgeMap))
            edgeIndex.remove(secondEdgeMap);
    }

    @Override
    public int getEdgeNum() {
        return (int) edgeIndex.keySet().stream().flatMap(x -> edgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).distinct().count();
    }

    @Override
    public Iterator<Edge<E, V>> getEdge() {
        return edgeIndex.keySet().stream().flatMap(x -> edgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).distinct().iterator();
    }
}
