package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.ClassUtils;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class GraphBuilder<T, V, E> {


    public <R extends AbstractGraph<T, V, E>> R build(Class<R> clazz) throws Exception {
        return ClassUtils.newInstance(clazz);
    }

}
