package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class DirectGraph<E, T, V> extends AbstractGraph<E, T, V> {

    @Override
    public void insert(Edge<E, V> edge) {
        if (CollectionUtils.isEmpty(edgeIndex.get(edge.getFirstVertexId())))
            edgeIndex.put(edge.getFirstVertexId(), new HashMap<>());
        edgeIndex.get(edge.getFirstVertexId()).put(edge.getSecondVertexId(), edge);
    }

    @Override
    public void remove(Edge<E, V> edge) {
        Map<E, Edge<E, V>> eEdgeMap = edgeIndex.get(edge.getFirstVertexId());
        eEdgeMap.remove(edge.getSecondVertexId());
        if (CollectionUtils.isEmpty(eEdgeMap))
            edgeIndex.remove(eEdgeMap);
    }

    @Override
    public int getEdgeNum() {
        return (int) edgeIndex.keySet().stream().flatMap(x -> edgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).count();
    }

    @Override
    public Iterator<Edge<E, V>> getEdge() {
        return edgeIndex.keySet().stream().flatMap(x -> edgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).iterator();
    }
}
