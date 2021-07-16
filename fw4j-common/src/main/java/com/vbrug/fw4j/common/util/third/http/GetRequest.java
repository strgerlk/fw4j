package com.vbrug.fw4j.common.util.third.http;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Objects;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class GetRequest extends BaseRequest {

    private final HttpGet httpGet;

    GetRequest(String url) {
        this.url = url;
        this.client = HttpClients.createDefault();
        this.httpGet = new HttpGet();
    }

    public GetRequest addHeader(final String name, final String value) {
        Assert.notNull(name, "Header name");
        this.httpGet.addHeader(name, value);
        return this;
    }

    public String execute() throws IOException {
        this.setParam();
        httpGet.setURI(URI.create(url));
        if (Objects.nonNull(configBuilder))
            httpGet.setConfig(configBuilder.build());
        CloseableHttpResponse execute = this.client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        return IOUtils.getContent(new InputStreamReader(entity.getContent()));
    }

    public void close() throws IOException {
        this.client.close();
    }

    private void setParam() {
        if (CollectionUtils.isEmpty(this.paramMap)) return;

        StringBuilder builder = new StringBuilder();

        this.paramMap.keySet().iterator().forEachRemaining(x -> {
            builder.append("&").append(x).append("=").append(this.paramMap.get(x));
        });

        if (this.url.contains("?"))
            this.url += builder.toString();
        else
            this.url += builder.toString().replaceFirst("&", "?");
    }
}
