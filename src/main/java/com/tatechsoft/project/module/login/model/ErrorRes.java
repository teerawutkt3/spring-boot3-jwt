package com.tatechsoft.project.module.login.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorRes {
    private HttpStatus httpStatus;
    private String message;

    public ErrorRes(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
