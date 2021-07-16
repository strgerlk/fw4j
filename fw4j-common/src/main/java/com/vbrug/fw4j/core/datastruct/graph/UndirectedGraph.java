package com.vbrug.fw4j.core.datastruct.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 无向图
 * @author vbrug
 * @since 1.0.0
 */
public class UndirectedGraph<T, V, E> extends AbstractGraph<T, V, E> {


    @Override
    public void delete(Edge<T, E> edge) {
        // 处理正向边
        Map<T, Edge<T, E>> forwardEdgeMap = forwardEdgeIndex.get(edge.getFirstVertexId());
        forwardEdgeMap.remove(edge.getSecondVertexId());
        if (CollectionUtils.isEmpty(forwardEdgeMap))
            forwardEdgeIndex.remove(forwardEdgeMap);

        // 处理逆向边
        Map<T, Edge<T, E>> reverseEdgeMap = reverseEdgeIndex.get(edge.getSecondVertexId());
        reverseEdgeMap.remove(edge.getFirstVertexId());
        if (CollectionUtils.isEmpty(reverseEdgeMap))
            reverseEdgeIndex.remove(reverseEdgeMap);
    }

    @Override
    public int getEdgeNum() {
        return (int) forwardEdgeIndex.keySet().stream().flatMap(x -> forwardEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).distinct().count();
    }

    @Override
    public Iterator<Edge<T, E>> getEdges() {
        return forwardEdgeIndex.keySet().stream().flatMap(x -> forwardEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).distinct().iterator();
    }
}
