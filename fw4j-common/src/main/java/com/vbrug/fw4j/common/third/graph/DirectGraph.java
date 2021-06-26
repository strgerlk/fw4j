package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class DirectGraph<T, V, E> extends AbstractGraph<T, V, E> {

    @Override
    public void remove(Edge<T, E> edge) {
        Map<T, Edge<T, E>> eEdgeMap = forwardEdgeIndex.get(edge.getFirstVertexId());
        eEdgeMap.remove(edge.getSecondVertexId());
        if (CollectionUtils.isEmpty(eEdgeMap))
            forwardEdgeIndex.remove(eEdgeMap);

    }

    @Override
    public int getEdgeNum() {
        long forwardCount = forwardEdgeIndex.keySet().stream().flatMap(x -> forwardEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).count();
        long reverseCount = reverseEdgeIndex.keySet().stream().flatMap(x -> reverseEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).count();
        return (int) (forwardCount + reverseCount);
    }

    @Override
    public Iterator<Edge<T, E>> getEdge() {
        Collection<Edge<T, E>> forwardCollect = forwardEdgeIndex.keySet().stream()
                .flatMap(x -> forwardEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).collect(Collectors.toList());
        Collection<Edge<T, E>> reverseCollect = reverseEdgeIndex.keySet().stream()
                .flatMap(x -> reverseEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).collect(Collectors.toList());
        forwardCollect.addAll(reverseCollect);
        return forwardCollect.iterator();
    }
}
