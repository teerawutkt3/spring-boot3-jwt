package com.tatechsoft.project.module.ums.controller;

import com.tatechsoft.project.common.controller.BaseController;
import com.tatechsoft.project.database.entity.Role;
import com.tatechsoft.project.module.ums.model.RolePrivilegeReqDto;
import com.tatechsoft.project.module.ums.model.UserPrivilegeReqDto;
import com.tatechsoft.project.module.ums.model.UserRoleReqDto;
import com.tatechsoft.project.module.ums.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends BaseController {


    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        super();
        this.permissionService = permissionService;
    }

    @GetMapping("/initial")
    public ResponseEntity<?> initial() {
        permissionService.initial();
        log.info("Initial Permission success");
        return ResponseEntity.ok("Initial Permission success");
    }

    @GetMapping("/get-privilege-all")
    public ResponseEntity<?> getPrivilegeAll() {
        return ResponseEntity.ok(permissionService.getPrivilegeAll());
    }

    @GetMapping("/get-privilege-by-role")
    public ResponseEntity<?> getPrivilegesByRole(@RequestParam String code) {
        return ResponseEntity.ok(permissionService.getPrivilegesByRole(code));
    }

    @PostMapping("/add-role-privileges")
    public ResponseEntity<?> addRolePrivileges(@RequestBody RolePrivilegeReqDto dto, HttpServletRequest http) {
        isPermission("EDIT_PRIVILEGE", http);
        permissionService.addRolePrivileges(dto.getRoleCode(), dto.getPrivileges());
        return ResponseEntity.ok("Add Role & Privileges success.");
    }

    @PostMapping("/add-user-roles")
    public ResponseEntity<?> addUserRole(@RequestBody UserRoleReqDto dto, HttpServletRequest http) {
        isPermission("EDIT_PRIVILEGE", http);
        permissionService.addUserRoles(dto.getUserId(), dto.getRolesCode());
        return ResponseEntity.ok("Add User & Roles success.");
    }

    @PostMapping("/add-user-privileges")
    public ResponseEntity<?> addUserPrivileges(@RequestBody UserPrivilegeReqDto dto, HttpServletRequest http) {
        isPermission("EDIT_PRIVILEGE", http);
        permissionService.addUserPrivileges(dto.getUserId(), dto.getPrivileges());
        return ResponseEntity.ok("Add User & Privileges success.");
    }

    @GetMapping("/get-role-all")
    public ResponseEntity<?> getRoleAll() {
        return ResponseEntity.ok(permissionService.getRoleAll());
    }

    @PostMapping("/save-role")
    public ResponseEntity<?> saveRole(@RequestBody Role role, HttpServletRequest http) {
        isPermission("EDIT_ROLE", "CREATE_ROLE", http);
        return ResponseEntity.ok(permissionService.saveRole(role));
    }

    @PostMapping("/delete-role/{code}")
    public ResponseEntity<?> deleteRole(@PathVariable String code, @RequestBody Role role, HttpServletRequest http) {
        isPermission("DELETE_ROLE", http);
        permissionService.deleteRole(code, role);
        return ResponseEntity.ok("delete success");
    }
}
