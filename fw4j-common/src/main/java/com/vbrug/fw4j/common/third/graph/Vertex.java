package com.vbrug.fw4j.common.third.graph;

import java.util.LinkedList;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Vertex<E, T> {

    private E                      id;
    private String                 name;
    private T                      attributes;
    private int                    graphType;

    public Vertex(Graph graph) {
        graphType = graph.getType();
    }

    private boolean isUnDiGraphNode() {
        return graphType == Graph.UndirectedGraph;
    }

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }


    /**
     * 重置顶点状态
     */
    public void resetStatus() {
    }

}
