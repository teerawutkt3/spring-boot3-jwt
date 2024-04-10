package com.tatechsoft.project.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {
    private String code = "0";
    private String message;
    private T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public CommonResponse(String message) {
        this.message = message;
    }
}
