package com.shopping.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponse<T> {
    private String code;
    private T data;
    private String message;

    public ResultResponse() {
        this.code = "0";
    }

    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> response = new ResultResponse<T>();
        response.setData(data);
        return response;
    }
}
