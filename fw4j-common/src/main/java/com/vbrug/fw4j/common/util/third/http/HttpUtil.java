package com.vbrug.fw4j.common.util.third.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

/**
 * @author LK
 * @since 1.0
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * @param url
     * @return
     * @throws Exception
     * @MethodName doGetUrl
     * @Description http发送get请求，获取请求结果
     */
    public static String doGet(String url) throws Exception {
        CloseableHttpClient client   = HttpClients.createDefault();
        HttpGet           httpGet  = new HttpGet(url);
        HttpResponse      response = client.execute(httpGet);
        HttpEntity        entity   = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /**
     * @param url
     * @param params
     * @return
     * @throws Exception
     * @MethodName doPostUrl
     * @Description http执行post请求，获取请求结果
     */
    public static String doPost(String url, String params) throws Exception {
        CloseableHttpClient client   = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(params));
        HttpResponse response = client.execute(httpPost);
        HttpEntity   entity   = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /**
     * @param url
     * @param params
     * @return
     * @throws Exception
     * @MethodName doPostUrl
     * @Description http执行post请求，获取请求结果
     */
    public static void doPostNoReturn(String url, String params) throws Exception {
        CloseableHttpClient client   = HttpClients.createDefault();
        HttpPost          httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000).setSocketTimeout(1000).build();
            httpPost.setConfig(config);
            client.execute(httpPost);
            client.close();
        } catch (SocketTimeoutException ex){
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * @param url         请求链接
     * @param postEntity  请求体
     * @param headers 请求头，一个字符串代表一个请求头，格式【key:value】
     * @return
     * @throws Exception
     * @MethodName doPostUrl
     * @Description http执行post请求，获取请求结果
     */
    public static String doPost(String url, String postEntity, String... headers) throws Exception {
        CloseableHttpClient client   = HttpClients.createDefault();
        HttpPost          httpPost = new HttpPost(url);

        // 设置请求头
        if (headers != null && headers.length > 0) {
            for (String header : headers) {
                if (header.contains(":")) {
                    httpPost.addHeader(header.substring(0, header.indexOf(":")),
                            header.substring(header.indexOf(":") + 1));
                }
            }
        }

        // 设置请求参数
        if (postEntity != null) {
            httpPost.setEntity(new StringEntity(postEntity, "UTF-8"));
        }

        // 得到返回结果
        HttpResponse response = client.execute(httpPost);
        HttpEntity   entity   = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }
}
