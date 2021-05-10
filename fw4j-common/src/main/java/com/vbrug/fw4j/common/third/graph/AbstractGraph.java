package com.vbrug.fw4j.common.third.graph;

import java.util.*;

/**
 * @author vbrug
 * @since 1.0.0
 */
public abstract class AbstractGraph<E, T, V> implements Graph<E, T, V> {

    protected final Map<E, Vertex<E, T>> vertexIndex = new HashMap<>();

    protected final Map<E, Map<E, Edge<E, V>>> edgeIndex = new HashMap<>();

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void insert(Vertex<E, T> vertex) {
        vertexIndex.put(vertex.getId(), vertex);
    }


    @Override
    public void remove(Vertex<E, T> vertex) {
        vertexIndex.remove(vertex.getId());
        edgeIndex.remove(edgeIndex.get(vertex.getId()));
    }

    @Override
    public int getVertexNum() {
        return vertexIndex.size();
    }


    @Override
    public Iterator<Vertex<E, T>> getVertex() {
        return vertexIndex.values().iterator();
    }


    @Override
    public Iterator<Vertex<E, T>> adjacentVertex(Vertex<E, T> vertex) {
        return edgeIndex.get(vertex.getId()).keySet().stream().map(vertexIndex::get).iterator();
    }

    @Override
    public Iterator<Vertex<E, T>> dfs(Vertex<E, T> vertex) {
        LinkedList<Vertex<E, T>> list          = new LinkedList<>();
        Stack<Vertex<E, T>>      stack         = new Stack<>();
        Set<E>                   visitedVertex = new HashSet<>();
        stack.push(vertex);
        while (!stack.isEmpty()) {
            Vertex<E, T> popVertex = stack.pop();
            visitedVertex.add(popVertex.getId());
            list.addLast(popVertex);
            Iterator<Vertex<E, T>> iterator = adjacentVertex(popVertex);
            while (iterator.hasNext()) {
                Vertex<E, T> next = iterator.next();
                if (!visitedVertex.contains(next.getId())) stack.push(next);
            }
        }
        return list.iterator();
    }

    @Override
    public Iterator<Vertex<E, T>> bfs(Vertex<E, T> vertex) {
        LinkedList<Vertex<E, T>> list          = new LinkedList<>();
        Deque<Vertex<E, T>>      deque         = new LinkedList<>();
        Set<E>                   visitedVertex = new HashSet<>();
        deque.push(vertex);
        while (!deque.isEmpty()) {
            Vertex<E, T> poll = deque.poll();
            visitedVertex.add(poll.getId());
            list.addLast(poll);
            Iterator<Vertex<E, T>> iterator = adjacentVertex(poll);
            while (iterator.hasNext()) {
                Vertex<E, T> next = iterator.next();
                if (!visitedVertex.contains(next.getId())) {
                    deque.push(next);
                }
            }
        }
        return list.iterator();

    }

    @Override
    public Iterator<Vertex<E, T>> shortestPath(Vertex<E, T> vertex) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public void criticalPath() throws UnsupportedOperationException {

    }

    @Override
    public void generateMST() throws UnsupportedOperationException {

    }
}
