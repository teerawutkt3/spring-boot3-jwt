package com.tatechsoft.project.module.ums.controller;

import com.tatechsoft.project.common.controller.BaseController;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import com.tatechsoft.project.constants.AppConstant;
import com.tatechsoft.project.constants.PermissionConstant;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.database.repository.ums.UserRepo;
import com.tatechsoft.project.module.ums.criteria.UserCriteria;
import com.tatechsoft.project.module.ums.model.GetProfileRes;
import com.tatechsoft.project.module.ums.model.UserProfileDto;
import com.tatechsoft.project.module.ums.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private final UserRepo userRepo;
    private final UserService userService;

    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        try {
            String username = UserLoginUtils.getUsername();
            var userProfileDto = userService.getUserProfile(username);
            userProfileDto.getUser().setPassword(null);
            GetProfileRes response = new GetProfileRes();
            response.setUser(userProfileDto.getUser());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return processException(e);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchUser(@RequestBody UserCriteria criteria, HttpServletRequest http) {
        isPermission(PermissionConstant.CAN_GET_USER, http);
        return ResponseEntity.ok(userRepo.searchUser(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id, HttpServletRequest http) {
        isPermission(PermissionConstant.CAN_GET_USER, http);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletRequest http) {
        isPermission(PermissionConstant.CAN_CREATE_USER, http);
        // Check has username
        if (ObjectUtils.isEmpty(user.getId())) {
            if (ObjectUtils.isNotEmpty(userRepo.findByUsername(user.getUsername())))
                return invalidException("username.exists");
        }
        user = userRepo.save(user);
        log.info("Method saveUser success data: {}", user.toString());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileDto dto, HttpServletRequest http) {
        isPermission(PermissionConstant.CAN_EDIT_USER, http);
        log.info("Method updateUser params => {}", dto.toString());
        // Check has username
        if (ObjectUtils.isEmpty(dto.getUser().getId())) {
            if (ObjectUtils.isNotEmpty(userRepo.findByUsername(dto.getUser().getUsername())))
                return invalidException("USERNAME_EXISTS");
        }

        userService.updateUser(dto.getUser(), dto.getRoles());
        log.info("Method updateUser success");
        return ResponseEntity.ok(dto.getUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id, HttpServletRequest http) {
        isPermission(PermissionConstant.CAN_DELETE_USER, http);
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) return invalidException("USER_NOTFOUND");

        //  Set rec status = DELETED & Save
        User user = optionalUser.get();
        user.setIsDeleted(AppConstant.Flag.Y);
        userRepo.save(user);

        log.info("Method deleteUser {} success", id);
        return ResponseEntity.ok("delete success");
    }
}
