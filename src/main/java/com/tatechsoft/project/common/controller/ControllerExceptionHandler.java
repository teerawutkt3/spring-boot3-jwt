package com.tatechsoft.project.common.controller;

import com.tatechsoft.project.common.exception.BusinessException;
import com.tatechsoft.project.common.exception.DataNotFoundException;
import com.tatechsoft.project.common.exception.BadRequestException;
import com.tatechsoft.project.common.exception.OtherException;
import com.tatechsoft.project.common.exception.PermissionException;
import com.tatechsoft.project.common.model.CommonErrorMessage;
import com.tatechsoft.project.constants.AppConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = PermissionException.class)
    public ResponseEntity<CommonErrorMessage> permissionException(PermissionException e) {
        log.error("error permissionException: {}", e.getMessage());
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.PERMISSION_EXCEPTION);
        message.setMessage(e.getMessage());
        message.setError("Is not permission");
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<CommonErrorMessage> badRequestException(BadRequestException e) {
        log.error("error badRequestException: {}", e.getMessage());
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.BAD_REQUEST);
        message.setMessage(e.getMessage());
        message.setError("Bad request");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<CommonErrorMessage> businessException(BusinessException e) {
        log.error("error businessException: {}", e.getMessage());
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.BUSINESS_EXCEPTION);
        message.setMessage(e.getMessage());
        message.setError("Business exception");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<CommonErrorMessage> dataNotFoundException(DataNotFoundException e) {
        log.error("error dataNotFoundException: {}", e.getMessage());
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.DATA_NOT_FOUND);
        message.setMessage(e.getMessage());
        message.setError("Data not found");
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OtherException.class)
    public ResponseEntity<CommonErrorMessage> otherException(OtherException e) {
        log.error("error otherException: {}", e);
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.OTHER_EXCEPTION);
        message.setMessage(e.getMessage());
        message.setError("Other exception");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonErrorMessage> exception(Exception e) {
        log.error("error exception: {}", e);
        CommonErrorMessage message = new CommonErrorMessage();
        message.setCode(AppConstant.ErrorCode.OTHER_EXCEPTION);
        message.setMessage(e.getMessage());
        message.setError("exception");
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}