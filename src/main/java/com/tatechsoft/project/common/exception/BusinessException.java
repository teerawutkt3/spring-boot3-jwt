package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private String messageEn;
    private String messageTh;
    private Object data;

    public BusinessException(String message) {
        super(message);

        this.messageEn = message;
        this.messageTh = message;
    }

    public BusinessException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

    public BusinessException(String messageEn, String messageTh, Object data) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
        this.data = data;
    }

}
