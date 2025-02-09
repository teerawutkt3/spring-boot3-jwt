package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class DataNotFoundException extends RuntimeException {

    private String messageEn;
    private String messageTh;

    public DataNotFoundException(String message) {
        super(message);
        this.messageEn = message;
        this.messageTh = message;
    }

    public DataNotFoundException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

}
