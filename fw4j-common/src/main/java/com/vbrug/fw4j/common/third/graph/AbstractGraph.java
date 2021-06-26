package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.*;

/**
 * @author vbrug
 * @since 1.0.0
 */
public abstract class AbstractGraph<T, V, E> implements Graph<T, V, E> {

    protected final Map<T, Vertex<T, V>> vertexIndex = new HashMap<>();

    protected final Map<T, Map<T, Edge<T, E>>> forwardEdgeIndex = new HashMap<>();

    protected final Map<T, Map<T, Edge<T, E>>> reverseEdgeIndex = new HashMap<>();

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void insert(Vertex<T, V> vertex) {
        vertexIndex.put(vertex.getId(), vertex);
    }

    @Override
    public void insert(Edge<T, E> edge) {
        if (CollectionUtils.isEmpty(forwardEdgeIndex.get(edge.getFirstVertexId())))
            forwardEdgeIndex.put(edge.getFirstVertexId(), new HashMap<>());
        forwardEdgeIndex.get(edge.getFirstVertexId()).put(edge.getSecondVertexId(), edge);

        if (CollectionUtils.isEmpty(reverseEdgeIndex.get(edge.getSecondVertexId())))
            reverseEdgeIndex.put(edge.getSecondVertexId(), new HashMap<>());
        reverseEdgeIndex.get(edge.getSecondVertexId()).put(edge.getFirstVertexId(), edge);
    }


    @Override
    public void remove(Vertex<T, V> vertex) {
        vertexIndex.remove(vertex.getId());
        Map<T, Edge<T, E>> removeEdgeMap = forwardEdgeIndex.get(vertex.getId());
        for (T next : removeEdgeMap.keySet()) {
            reverseEdgeIndex.get(next).remove(vertex.getId());
        }
        forwardEdgeIndex.remove(vertex.getId());
    }

    @Override
    public int getVertexNum() {
        return vertexIndex.size();
    }

    @Override
    public Iterator<Vertex<T, V>> getVertex() {
        return vertexIndex.values().iterator();
    }

    @Override
    public Iterator<Vertex<T, V>> adjacentVertex(Vertex<T, V> vertex) {
        return forwardEdgeIndex.get(vertex.getId()).keySet().stream().map(vertexIndex::get).iterator();
    }

    @Override
    public Iterator<Vertex<T, V>> dfs(Vertex<T, V> vertex) {
        LinkedList<Vertex<T, V>> list            = new LinkedList<>();
        Stack<Vertex<T, V>>      stack           = new Stack<>();
        Set<T>                   visitedVertices = new HashSet<>();
        stack.push(vertex);
        while (!stack.isEmpty()) {
            Vertex<T, V> popVertex = stack.pop();
            visitedVertices.add(popVertex.getId());
            list.addLast(popVertex);
            Iterator<Vertex<T, V>> iterator = adjacentVertex(popVertex);
            while (iterator.hasNext()) {
                Vertex<T, V> next = iterator.next();
                if (!visitedVertices.contains(next.getId())) stack.push(next);
            }
        }
        return list.iterator();
    }

    @Override
    public Iterator<Vertex<T, V>> bfs(Vertex<T, V> vertex) {
        LinkedList<Vertex<T, V>> list            = new LinkedList<>();
        Deque<Vertex<T, V>>      deque           = new LinkedList<>();
        Set<T>                   visitedVertices = new HashSet<>();
        deque.push(vertex);
        while (!deque.isEmpty()) {
            Vertex<T, V> poll = deque.poll();
            visitedVertices.add(poll.getId());
            list.addLast(poll);
            Iterator<Vertex<T, V>> iterator = adjacentVertex(poll);
            while (iterator.hasNext()) {
                Vertex<T, V> next = iterator.next();
                if (!visitedVertices.contains(next.getId())) {
                    deque.push(next);
                }
            }
        }
        return list.iterator();
    }

    @Override
    public Iterator<Vertex<T, V>> shortestPath(Vertex<T, V> vertex) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public void criticalPath() throws UnsupportedOperationException {

    }

    @Override
    public void generateMST() throws UnsupportedOperationException {

    }
}
