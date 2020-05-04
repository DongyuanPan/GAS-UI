package com.gas.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Controller统一的响应对象</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** 状态码，正确返回 0 */
    private Integer code = 200;

    /** 错误信息，正确返回空字符串 */
    private String msg = "";

    /** 返回值对象 */
    private Object data = null;

    /**
     * <h2>正确的空响应</h2>
     * @return
     */
    public static Response success() {
        return new Response();
    }

    /**
     * <h2>正确的响应（含正确消息）</h2>
     * @return
     */
    public static Response success(String msg) {
        return new Response(200, msg, null);
    }

    /**
     * <h2>正确的响应（含 data）</h2>
     * @return
     */
    public static Response success(Object data) {
        return new Response(200, "", data);
    }

    /**
     * <h2>正确的响应（全参）</h2>
     * @return
     */
    public static Response success(String msg, Object data) {
        return new Response(200, msg, data);
    }

    /**
     * <h2>错误的空响应</h2>
     * @return
     */
    public static Response failure() {
        return new Response(-1, "", null);
    }

    /**
     * <h2>错误响应（含错误消息）</h2>
     * @param errorMsg
     * @return
     */
    public static Response failure(String errorMsg) {
        return new Response(-1, errorMsg, null);
    }

    /**
     * <h2>错误响应（含 data）</h2>
     * @param data
     * @return
     */
    public static Response failure(Object data) {
        return new Response(-1, "", data);
    }

    /**
     * <h2>错误响应（全参）</h2>
     * @param errorMsg
     * @return
     */
    public static Response failure(String errorMsg, Object data) {
        return new Response(-1, errorMsg, data);
    }
}
