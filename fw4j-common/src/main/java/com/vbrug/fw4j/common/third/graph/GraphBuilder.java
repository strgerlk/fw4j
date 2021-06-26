package com.vbrug.fw4j.common.third.graph;

import com.vbrug.fw4j.common.util.ClassUtils;

import java.lang.reflect.ParameterizedType;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class GraphBuilder<T, V, E, G extends AbstractGraph<T, V, E>> {

    @SuppressWarnings("unchecked")
    public G build() throws Exception {
        return ClassUtils.newInstance((Class<G>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
