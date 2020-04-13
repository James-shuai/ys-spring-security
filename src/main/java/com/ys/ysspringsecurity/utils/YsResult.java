package com.ys.ysspringsecurity.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 */
@Data
public class YsResult {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

	public YsResult() {
    }
	public YsResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public YsResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public YsResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static YsResult ok() {
        return new YsResult(null);
    }
    public static YsResult ok(String message) {
        return new YsResult(message, null);
    }
    public static YsResult ok(Object data) {
        return new YsResult(data);
    }
    public static YsResult ok(String message, Object data) {
        return new YsResult(message, data);
    }

    public static YsResult build(Integer code, String message) {
        return new YsResult(code, message, null);
    }

    public static YsResult build(Integer code, String message, Object data) {
        return new YsResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 YsResult 对象
     * @param json
     * @return
     */
    public static YsResult format(String json) {
        try {
            return JSON.parseObject(json, YsResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
