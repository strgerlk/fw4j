package com.vbrug.fw4j.common.third.graph;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Vertex<T, D> {

    private T      id;
    private String name;
    private D      data;
    private int    graphType;

    public Vertex() {}

    public Vertex(Graph graph) {
        graphType = graph.getType();
    }

    private boolean isUnDiGraphNode() {
        return graphType == Graph.UndirectedGraph;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }


    /**
     * 重置顶点状态
     */
    public void resetStatus() {
    }

}
