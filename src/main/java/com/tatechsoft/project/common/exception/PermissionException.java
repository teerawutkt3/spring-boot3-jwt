package com.tatechsoft.project.common.exception;

import lombok.Data;

@Data
public class PermissionException extends RuntimeException {

    private String messageEn;
    private String messageTh;

    public PermissionException(String message) {
        super(message);
        this.messageEn = message;
        this.messageTh = message;
    }

    public PermissionException(String messageEn, String messageTh) {
        super(messageEn);

        this.messageEn = messageEn;
        this.messageTh = messageTh;
    }

}
