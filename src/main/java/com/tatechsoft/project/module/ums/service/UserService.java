package com.tatechsoft.project.module.ums.service;

import com.tatechsoft.project.common.exception.DataNotFoundException;
import com.tatechsoft.project.common.exception.BadRequestException;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.database.repository.ums.UserRepo;
import com.tatechsoft.project.module.ums.model.UserProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PermissionService permissionService;

    @Transactional
    public User register(User user) throws BadRequestException {

        User isUser = userRepo.findByUsername(user.getUsername());
        if (ObjectUtils.isNotEmpty(isUser)) throw new BadRequestException("EMAIL_EXISTS");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //  encrypt password
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        user.setEmail(user.getUsername());
        return userRepo.save(user);
    }

    public UserProfileDto getUserProfile(String username) {
        User user = userRepo.findByUsername(username);
        return permissionService.getUserProfile(user);
    }

    public UserProfileDto getUserProfile(User user) {
        return permissionService.getUserProfile(user);
    }

    @Transactional
    public void updateUser(User user, List<String> roles) throws BadRequestException {
        user = userRepo.save(user);
        permissionService.addUserRoles(user.getId(), roles);
    }

    public UserProfileDto getUserById(long userId) throws BadRequestException {
        var userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) throw new DataNotFoundException("USER_NOTFOUND");
        User user = userOpt.get();
        if (ObjectUtils.isEmpty(user)) throw new BadRequestException("USER_NOTFOUND");
        return this.getUserProfile(user);
    }

}
