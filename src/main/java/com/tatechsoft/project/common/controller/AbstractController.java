package com.tatechsoft.project.common.controller;

import com.tatechsoft.project.common.exception.BadRequestException;
import com.tatechsoft.project.common.exception.PermissionException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    public abstract void isPermission(String privilegeCode) throws PermissionException;

    public abstract void isPermission(String privilegeCode1, String privilegeCode2) throws PermissionException;

    public abstract void isPermission(String privilegeCode, HttpServletRequest httpServletRequest) throws PermissionException;

    public abstract void isPermission(String privilegeCode1, String privilegeCode2, HttpServletRequest httpServletRequest) throws PermissionException;

    public abstract ResponseEntity<?> processException(Exception e);

    public abstract ResponseEntity<?> invalidException(String msgCode);

    public abstract ResponseEntity<?> invalidException(BadRequestException e);

    public abstract ResponseEntity<?> permissionException(String msgCode);

    public abstract ResponseEntity<?> permissionException(Exception e);

    public abstract ResponseEntity<?> dataNotFoundException(Exception e);
}
