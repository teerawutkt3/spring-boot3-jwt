package com.tatechsoft.project.common.controller;

import com.tatechsoft.project.common.exception.BadRequestException;
import com.tatechsoft.project.common.exception.PermissionException;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import com.tatechsoft.project.module.ums.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Log4j2
public class BaseController extends AbstractController {

    @Autowired
    private PermissionService permissionService;

    public String getBaseUrl(HttpServletRequest request) {
        return String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
    }

    public String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @Override
    public void isPermission(String privilegeCode) throws PermissionException {
        if (!permissionService.isPermission(privilegeCode)) {
            String username = UserLoginUtils.getUsername();
            log.error("username: {} is not has permission privilegeCode: {}", username, privilegeCode);
            throw new PermissionException("USER_UNAUTHORIZED");
        }
    }

    @Override
    public void isPermission(String privilegeCode1, String privilegeCode2) throws PermissionException {
        if (!permissionService.isPermission(privilegeCode1) && !permissionService.isPermission(privilegeCode2)) {
            String username = UserLoginUtils.getUsername();
            log.error("username: {} is not has permission privilegeCode1: {} privilegeCode2: {}", username, privilegeCode1, privilegeCode2);
            throw new PermissionException("USER_UNAUTHORIZED");
        }
    }

    @Override
    public void isPermission(String privilegeCode, HttpServletRequest httpServletRequest) throws PermissionException {
        if (!permissionService.isPermission(privilegeCode, httpServletRequest)) {
            String username = UserLoginUtils.getUsername();
            log.error("username: {} is not has permission privilegeCode: {}", username, privilegeCode);
            throw new PermissionException("USER_UNAUTHORIZED");
        }
    }

    @Override
    public void isPermission(String privilegeCode1, String privilegeCode2, HttpServletRequest httpServletRequest) throws PermissionException {
        if (!permissionService.isPermission(privilegeCode1, httpServletRequest) && !permissionService.isPermission(privilegeCode2, httpServletRequest)) {
            String username = UserLoginUtils.getUsername();
            log.error("username: {} is not has permission privilegeCode1: {} privilegeCode2: {}", username, privilegeCode1, privilegeCode2);
            throw new PermissionException("USER_UNAUTHORIZED");
        }
    }

    @Override
    public ResponseEntity<?> processException(Exception e) {
        log.error("processException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("PROCESS FAIL");
    }

    @Override
    public ResponseEntity<?> invalidException(String message) {
        log.error("invalidException message {}", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Override
    public ResponseEntity<?> invalidException(BadRequestException e) {
        log.error("invalidException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @Override
    public ResponseEntity<?> permissionException(String message) {
        log.error("permissionException message: {}", message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @Override
    public ResponseEntity<?> permissionException(Exception e) {
        log.error("permissionException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("USER_UNAUTHORIZED");
    }

    @Override
    public ResponseEntity<?> dataNotFoundException(Exception e) {
        log.error("dataNotFoundException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }
}
