package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class OtherException extends RuntimeException {
    private String messageEn;
    private String messageTh;
    private Object data;

    public OtherException() {
        super();
    }
    public OtherException(String message) {
        super(message);

        this.messageEn = message;
        this.messageTh = message;
    }

    public OtherException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

    public OtherException(String messageEn, String messageTh, Object data) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
        this.data = data;
    }

}
