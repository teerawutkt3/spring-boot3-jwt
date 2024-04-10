package com.tatechsoft.project.module.login.service;

import com.tatechsoft.project.component.JwtUtil;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.module.login.model.LoginReq;
import com.tatechsoft.project.module.login.model.LoginRes;
import com.tatechsoft.project.module.ums.model.UserProfileDto;
import com.tatechsoft.project.module.ums.service.CustomUserDetailsService;
import com.tatechsoft.project.module.ums.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginRes login(@RequestBody LoginReq loginReq) {
        try {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginReq.getUsername());
//            Authentication authentication =
//                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
//            String email = authentication.getName();
            UserProfileDto profile = userService.getUserProfile(userDetails.getUsername());
            String token = jwtUtil.createToken(profile);
            LoginRes loginRes = new LoginRes();
            loginRes.setToken(token);

            return loginRes;

        } catch (BadCredentialsException e) {
            log.error("error login bad request e: {}", e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            log.error("error login exception e: {}", e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
