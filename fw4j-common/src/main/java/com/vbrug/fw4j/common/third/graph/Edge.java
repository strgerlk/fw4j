package com.vbrug.fw4j.common.third.graph;

/**
 * 边
 * @author vbrug
 * @since 1.0.0
 */
public class Edge<E, T> {

    public static final int NORMAL   = 0;
    public static final int MST      = 1;
    public static final int CRITICAL = 2;
    private             int weight;
    private             E   firstVertexId;
    private             E   secondVertexId;
    private             T   attributes;
    private             int type;


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public E getFirstVertexId() {
        return firstVertexId;
    }

    public void setFirstVertexId(E firstVertexId) {
        this.firstVertexId = firstVertexId;
    }

    public E getSecondVertexId() {
        return secondVertexId;
    }

    public void setSecondVertexId(E secondVertexId) {
        this.secondVertexId = secondVertexId;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
