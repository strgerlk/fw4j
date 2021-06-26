package com.vbrug.fw4j.common.third.graph;

/**
 * 边
 * @author vbrug
 * @since 1.0.0
 */
public class Edge<T, D> {

    public static final int NORMAL   = 0;
    public static final int MST      = 1;
    public static final int CRITICAL = 2;
    private             int weight;
    private             T   firstVertexId;
    private             T   secondVertexId;
    private             D   data;
    private             int type;


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public T getFirstVertexId() {
        return firstVertexId;
    }

    public void setFirstVertexId(T firstVertexId) {
        this.firstVertexId = firstVertexId;
    }

    public T getSecondVertexId() {
        return secondVertexId;
    }

    public void setSecondVertexId(T secondVertexId) {
        this.secondVertexId = secondVertexId;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
