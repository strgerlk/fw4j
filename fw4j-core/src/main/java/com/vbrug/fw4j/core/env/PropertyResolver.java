package com.vbrug.fw4j.core.env;

import java.util.Properties;

/**
 *
 * @author vbrug
 * @since 1.0.0
 */
public class PropertyResolver {

    public PropertyResolver(Properties properties) {

    }

    public <T> T getProperty(String key, Class<T> targetValueType) {
        return getProperty(key, targetValueType, true);
    }

    protected String getPropertyAsRawString(String key) {
        return getProperty(key, String.class, false);
    }

    protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        return null;
    }
}

