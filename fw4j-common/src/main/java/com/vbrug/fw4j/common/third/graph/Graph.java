package com.vbrug.fw4j.common.third.graph;

import java.util.Iterator;

/**
 * @author vbrug
 * @since 1.0.0
 */
public interface Graph<E, T, V> {
    /*
     * 无向图
     */
    static final int UndirectedGraph = 0;

    /*
     * 有向图
     */
    static final int DirectedGraph = 0;


    /**
     * 获取图数据类型
     * @return 图类型
     */
    int getType();

    /**
     * 插入顶点
     * @param vertex 待插入的顶点
     */
    void insert(Vertex<E, T> vertex);

    /**
     * 插入边
     * @param edge 待插入的边
     */
    void insert(Edge<E, V> edge);

    /**
     * 删除顶点
     * @param vertex 待删除顶点
     */
    void remove(Vertex<E, T> vertex);

    /**
     * 删除边
     * @param edge 待删除边
     */
    void remove(Edge<E, V> edge);

    /**
     * 获取顶点数量
     * @return 顶点数量
     */
    int getVertexNum();

    /**
     * 获取边数量
     * @return 边数量
     */
    int getEdgeNum();

    /**
     * 获取所有顶点
     * @return 顶点
     */
    Iterator<Vertex<E, T>> getVertex();

    /**
     * 获取所有边
     * @return 边
     */
    Iterator<Edge<E, V>> getEdge();

    /**
     * 获取顶点的邻接点
     * @return 邻接点
     */
    Iterator<Vertex<E, T>> adjacentVertex(Vertex<E, T> vertex);

    /**
     * 深度遍历
     * @param vertex 遍历起点
     * @return 迭代器
     */
    Iterator<Vertex<E, T>> dfs(Vertex<E, T> vertex);

    /**
     * 广度遍历
     * @param vertex 遍历起点
     * @return 迭代器
     */
    Iterator<Vertex<E, T>> bfs(Vertex<E, T> vertex);

    /**
     * 获取最短路径
     * @param vertex 起点
     * @return 迭代器
     * @throws UnsupportedOperationException 异常
     */
    Iterator<Vertex<E, T>> shortestPath(Vertex<E, T> vertex) throws UnsupportedOperationException;

    /**
     * 获取关键路径
     * @throws UnsupportedOperationException 异常
     */
    void criticalPath() throws UnsupportedOperationException;

    /**
     * 求有向图的拓扑序列
     * @throws UnsupportedOperationException 异常
     */
    void generateMST() throws UnsupportedOperationException;
}
