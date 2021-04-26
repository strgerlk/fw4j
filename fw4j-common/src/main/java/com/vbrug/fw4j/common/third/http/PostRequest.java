package com.vbrug.fw4j.common.third.http;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.IOUtils;
import com.vbrug.fw4j.common.util.JacksonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class PostRequest extends BaseRequest {

    private final HttpPost httpPost;

    PostRequest(String url) {
        this.client = HttpClients.createDefault();
        this.httpPost = new HttpPost(url);
        this.paramMap = new HashMap<>();
    }

    public PostRequest addHeader(final String name, final String value) {
        Assert.notNull(name, "Header name");
        this.httpPost.addHeader(name, value);
        return this;
    }

    public String execute() throws IOException {
        this.setEntity();
        if (Objects.nonNull(configBuilder))
            httpPost.setConfig(configBuilder.build());
        CloseableHttpResponse response = this.client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = IOUtils.getContent(new InputStreamReader(response.getEntity().getContent()));
        if (response.getStatusLine().getStatusCode() != 200)
            throw new RuntimeException("请求异常, "+content);
        return content;
    }

    public void close() throws IOException {
        this.client.close();
    }

    private void setEntity() throws IOException {
        if (!CollectionUtils.isEmpty(this.paramMap))
            httpPost.setEntity(new StringEntity(JacksonUtils.bean2Json(this.paramMap), "utf-8"));
    }


}
