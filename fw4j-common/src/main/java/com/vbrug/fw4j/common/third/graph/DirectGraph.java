package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 有向图
 * @author vbrug
 * @since 1.0.0
 */
public class DirectGraph<T, V, E> extends AbstractGraph<T, V, E> {

    @Override
    public void delete(Edge<T, E> edge) {
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
    public Iterator<Edge<T, E>> getEdges() {
        Collection<Edge<T, E>> forwardCollect = forwardEdgeIndex.keySet().stream()
                .flatMap(x -> forwardEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).collect(Collectors.toList());
        Collection<Edge<T, E>> reverseCollect = reverseEdgeIndex.keySet().stream()
                .flatMap(x -> reverseEdgeIndex.get(x).entrySet().stream()).map(Map.Entry::getValue).collect(Collectors.toList());
        forwardCollect.addAll(reverseCollect);
        return forwardCollect.iterator();
    }

    /**
     * 获取正向邻近节点
     * @param id 当前节点ID
     * @return 邻近节点遍历器
     */
    public Iterator<Vertex<T, V>> forwardAdjacentVertex(T id) {
        return forwardEdgeIndex.get(id).keySet().stream().map(vertexIndex::get).iterator();
    }

    /**
     * 获取反向邻近节点
     * @param id 当前节点ID
     * @return 邻近节点遍历器
     */
    public Iterator<Vertex<T, V>> reverseAdjacentVertex(T id) {
        return reverseEdgeIndex.get(id).keySet().stream().map(vertexIndex::get).iterator();
    }

    /**
     * 反向图广度遍历
     * @param vertex   开始遍历顶点
     * @param function 节点处理函数
     */
    public void reverseBFS(Vertex<T, V> vertex, Function<Vertex<T, V>, Boolean> function) {
        Deque<Vertex<T, V>> deque           = new LinkedList<>();
        Set<T>              visitedVertices = new HashSet<>();
        deque.push(vertex);
        while (!deque.isEmpty()) {
            Vertex<T, V> poll = deque.poll();
            visitedVertices.add(poll.getId());
            if (!function.apply(poll))
                break;
            Iterator<Vertex<T, V>> iterator = reverseAdjacentVertex(poll.getId());
            while (iterator.hasNext()) {
                Vertex<T, V> next = iterator.next();
                if (!visitedVertices.contains(next.getId())) {
                    deque.push(next);
                }
            }
        }
    }
}
