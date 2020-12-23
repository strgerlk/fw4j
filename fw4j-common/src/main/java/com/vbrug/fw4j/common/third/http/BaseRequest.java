package com.vbrug.fw4j.common.third.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class BaseRequest {

    protected RequestConfig.Builder configBuilder;
    protected String url;
    protected Map<String, Object> paramMap;
    protected CloseableHttpClient client;

    public RequestConfig.Builder getConfigBuilder() {
        if (configBuilder == null)
            this.configBuilder = RequestConfig.custom();
        return this.configBuilder;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public String getUrl() {
        return url;
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public Map<String, Object> putParam(String name, Object value) {
        this.paramMap.put(name, value);
        return this.paramMap;
    }
}
