package com.tatechsoft.project.module.login.controller;

import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.module.login.model.ErrorRes;
import com.tatechsoft.project.module.login.model.LoginReq;
import com.tatechsoft.project.module.login.service.LoginService;
import com.tatechsoft.project.module.ums.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        try {
            return ResponseEntity.ok(loginService.login(loginReq));

        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody LoginReq req) {
        try {
            var user = new User();
            user.setUsername(req.getUsername());
            user.setPassword(req.getPassword());
            userService.register(user);
            return ResponseEntity.ok("register success");

        } catch (Exception e) {
            log.info("error exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
