package com.tj.id.common;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Integer SUCCESS = 0;
    private static final Integer FAIL = 1;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
