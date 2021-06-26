package com.vbrug.fw4j.core.entity;


import com.vbrug.fw4j.common.util.ObjectUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 结果Bean
 * @author vbrug
 * @since 1.0.0
 */
public class Result implements Serializable {

    private static final long    serialVersionUID = 4932690413041014964L;
    private              Integer status;                                      // 状态码
    private              Integer bCode;                                       // 消息代码
    private              String  bMessage;                                    // 消息内容
    private              Object  data;                                        // 数据

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return success(null, data);
    }

    public static Result success(ResultCode resultCode) {
        return success(resultCode, null);
    }

    public static Result success(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.status = 1;
        result.data = data;
        if (ObjectUtils.isNull(resultCode)) {
            result.bCode = 100;
            result.bMessage = "OK";
        } else
            result.setResultCode(resultCode);
        return result;
    }

    public static Result failure() {
        return failure(null);
    }

    public static Result failure(Object data) {
        return failure(null, data);
    }

    public static Result failure(ResultCode resultCode) {
        return failure(resultCode, null);
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.status = 0;
        result.data = data;
        if (ObjectUtils.isNull(resultCode)) {
            result.bCode = 900;
            result.bMessage = "Exception";
        } else
            result.setResultCode(resultCode);
        return result;
    }

    public void setResultCode(ResultCode resultCode) {
        this.bCode = resultCode.getBCode();
    }

    public Integer getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> clazz) {
        return (T) data;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getData2List(Class<T> clazz) {
        return (List<T>) data;
    }

    public Integer getBCode() {
        return bCode;
    }

    public String getBMessage() {
        return bMessage;
    }
}
