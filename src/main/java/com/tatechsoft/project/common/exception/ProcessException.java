package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class ProcessException extends RuntimeException {

    private static final long serialVersionUID = -8239042342154004529L;
    private String messageEn;
    private String messageTh;
    private Object data;

    public ProcessException(String message) {
        super(message);

        this.messageEn = message;
        this.messageTh = message;
    }

    public ProcessException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

    public ProcessException(String messageEn, String messageTh, Object data) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
        this.data = data;
    }

}
