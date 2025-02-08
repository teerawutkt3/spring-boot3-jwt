package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {

    private String messageEn;
    private String messageTh;

    public BadRequestException(String message) {
        super(message);
        this.messageEn = message;
        this.messageTh = message;
    }

    public BadRequestException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

}
