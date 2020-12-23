package com.vbrug.fw4j.common.third.http;

/**
 * http请求帮助类
 *
 * @author vbrug
 * @since 1.0.0
 */
public class HttpHelp {

    public static GetRequest createGetRequest(String url){
        return new GetRequest(url);
    }

    public static PostRequest createPostRequest(String url){
        return new PostRequest(url);
    }

}
