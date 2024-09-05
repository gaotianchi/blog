package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.enums.ResponseErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse<T> {
    private int code;
    private String message;
    private T data;

    private static <T> APIResponse<T> response(int code, String msg, T data) {
        APIResponse<T> APIResponse = new APIResponse<>();
        APIResponse.setCode(code);
        APIResponse.setMessage(msg);
        APIResponse.setData(data);

        return APIResponse;
    }

    private static <T> APIResponse<T> response(int code, String msg) {
        APIResponse<T> APIResponse = new APIResponse<>();
        APIResponse.setCode(code);
        APIResponse.setMessage(msg);

        return APIResponse;
    }

    public static <T> APIResponse<T> success() {
        return response(ResponseErrorCode.SUCCESS.getCode(), ResponseErrorCode.SUCCESS.getMessage());
    }

    public static <T> APIResponse<T> success(T data) {
        return response(ResponseErrorCode.SUCCESS.getCode(), ResponseErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> APIResponse<T> fail() {
        return response(ResponseErrorCode.FAIL.getCode(), ResponseErrorCode.FAIL.getMessage());
    }

    public static <T> APIResponse<T> fail(int code, String msg) {
        return response(code, msg);
    }

    public static <T> APIResponse<T> fail(int code, String msg, T data) {
        return response(code, msg, data);
    }
}