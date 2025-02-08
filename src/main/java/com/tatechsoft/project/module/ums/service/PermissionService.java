package com.tatechsoft.project.module.ums.service;


import com.tatechsoft.project.common.exception.BadRequestException;
import com.tatechsoft.project.common.exception.ProcessException;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import com.tatechsoft.project.component.JwtUtil;
import com.tatechsoft.project.constants.PermissionConstant;
import com.tatechsoft.project.database.entity.Privilege;
import com.tatechsoft.project.database.entity.Role;
import com.tatechsoft.project.database.entity.RolePrivilege;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.database.entity.UserPrivilege;
import com.tatechsoft.project.database.entity.UserRole;
import com.tatechsoft.project.database.repository.ums.PermissionRepo;
import com.tatechsoft.project.database.repository.ums.PrivilegeRepo;
import com.tatechsoft.project.database.repository.ums.RolePrivilegeRepo;
import com.tatechsoft.project.database.repository.ums.RoleRepo;
import com.tatechsoft.project.database.repository.ums.UserPrivilegeRepo;
import com.tatechsoft.project.database.repository.ums.UserRepo;
import com.tatechsoft.project.database.repository.ums.UserRoleRepo;
import com.tatechsoft.project.module.ums.model.PrivilegeGroupDto;
import com.tatechsoft.project.module.ums.model.UserProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionService {
    
    private final RolePrivilegeRepo rolePrivilegeRepo;
    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserPrivilegeRepo userPrivilegeRepo;
    private final PermissionRepo permissionRepo;
    private final JwtUtil jwtUtil;

    public PermissionService(RolePrivilegeRepo rolePrivilegeRepo, PrivilegeRepo privilegeRepo, RoleRepo roleRepo, UserRepo userRepo, UserRoleRepo userRoleRepo, UserPrivilegeRepo userPrivilegeRepo, PermissionRepo permissionRepo, JwtUtil jwtUtil) {
        this.rolePrivilegeRepo = rolePrivilegeRepo;
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userPrivilegeRepo = userPrivilegeRepo;
        this.permissionRepo = permissionRepo;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void initial() throws BadRequestException {

        // ==> 1. Create Role
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        for (int i = 0; i < PermissionConstant.Roles.length; i++) {
            role = new Role(PermissionConstant.Roles[i][0], PermissionConstant.Roles[i][1], PermissionConstant.Roles[i][2], i + 1);
            roleList.add(role);
        }
        roleRepo.saveAll(roleList);

        // ==> 2. Create Privilege (note if insert in the middle will new sort no)
        List<Privilege> privilegeList = new ArrayList<>();
        Privilege privilege = new Privilege();
        for (int i = 0; i < PermissionConstant.Privileges.length; i++) {
            privilege = new Privilege(PermissionConstant.Privileges[i][0], PermissionConstant.Privileges[i][1], PermissionConstant.Privileges[i][2], PermissionConstant.Privileges[i][3], i + 1);
            privilegeList.add(privilege);
        }
        privilegeRepo.saveAll(privilegeList);

        // ==> 3. Create User => superadmin
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var username = "superadmin";
        var password = "superadmin";
        var passwordEncrypt = encoder.encode(password);

        User user = userRepo.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncrypt);
            userRepo.save(user);
        }

        role = roleList.stream().filter(p -> PermissionConstant.SUPER_ADMIN.equals(p.getCode())).findFirst().get();

        // ==> 4. clear user roles
        userRoleRepo.deleteByUser(user);

        // ==> 5. Map users, roles
        userRoleRepo.save(new UserRole(user, role));

        // ==> 6. clear role privileges
        rolePrivilegeRepo.deleteByRole(role);

        // ==> 7. Map roles, privileges
        for (Privilege pvl : privilegeList) {
            rolePrivilegeRepo.save(new RolePrivilege(role, pvl));
        }

    }

    public List<PrivilegeGroupDto> getPrivilegeAll() {
        List<PrivilegeGroupDto> result = new ArrayList<>();

        //  Distinct Privileges group
        List<String> groupPrivileges = new ArrayList<>();
        var privilegeMap = new HashMap<String, String>();

        var privilegesAll = privilegeRepo.findAllByOrderByNoAsc();
        for (Privilege p : privilegesAll) {
            groupPrivileges.add(p.getGroupCode());
            privilegeMap.put(p.getGroupCode(), p.getGroupLabel());
        }
        groupPrivileges = groupPrivileges.stream().distinct().collect(Collectors.toList());

        for (int i = 0; i < groupPrivileges.size(); i++) {

            String group = groupPrivileges.get(i);
            var groupDesc = privilegeMap.get(group);

            //  Set Group
            PrivilegeGroupDto privilegeGroup = new PrivilegeGroupDto();
            privilegeGroup.setGroup(group);
            privilegeGroup.setGroupDesc(groupDesc);

            //  Set Privileges
            privilegeGroup.setPrivileges(new ArrayList<>());
            for (Privilege p : privilegesAll) {
                if (group.equals(p.getGroupCode())) {
                    Privilege privilege = new Privilege();
                    privilege.setGroupCode(p.getGroupCode());
                    privilege.setCode(p.getCode());
                    privilege.setLabel(p.getLabel());
                    privilege.setGroupLabel(p.getGroupLabel());
                    privilege.setNo(p.getNo());
                    privilegeGroup.getPrivileges().add(privilege);
                }
            }

            //  Add privilegeGroup
            result.add(privilegeGroup);
        }
        return result;
    }

    public List<String> findPrivilegesByUser(String username) {
        return permissionRepo.findPrivilegesByUser(username);
    }

    @Transactional
    public void addRolePrivileges(String roleCode, List<String> privileges) throws ProcessException {

        //  Get Role
        Optional<Role> optionalRole = roleRepo.findById(roleCode);
        if (optionalRole.isEmpty()) {
            throw new ProcessException("SYS_P0");
        }
        Role role = optionalRole.get();

        //  Clear privileges all
        rolePrivilegeRepo.deleteByRole(role);

        //  Create new privileges
        List<RolePrivilege> rolePrivilegeList = new ArrayList<>();
        for (String privilegeStr : privileges) {

            Privilege privilege = privilegeRepo.findById(privilegeStr).get();

            RolePrivilege rp = new RolePrivilege();
            rp.setPrivilege(privilege);
            rp.setRole(role);

            rolePrivilegeList.add(rp);
        }
        rolePrivilegeRepo.saveAll(rolePrivilegeList);

    }

    @Transactional
    public void addUserRoles(Long userId, List<String> rolesCode) throws BadRequestException {

        //  Get & Check User
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) throw new BadRequestException("user.notfound");

        User user = optionalUser.get();

        //  Clear userRole
        userRoleRepo.deleteByUser(user);

        //  Loop get role & add list
        List<UserRole> userRoles = new ArrayList<>();
        for (String rc : rolesCode) {
            Optional<Role> roleOptional = roleRepo.findById(rc);
            if (roleOptional.isPresent()) {
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(roleOptional.get());

                //  Add list
                userRoles.add(userRole);
            }
        }

        //  Save userRole list
        userRoleRepo.saveAll(userRoles);
    }

    @Transactional
    public void addUserPrivileges(Long userId, List<String> privileges) throws BadRequestException {

        //  Get & Check User
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) throw new BadRequestException("USER_NOTFOUND");

        User user = optionalUser.get();

        //  Clear userRole
        userPrivilegeRepo.deleteByUser(user);

        //  Loop get role & add list
        List<UserPrivilege> userPrivileges = new ArrayList<>();
        for (String p : privileges) {
            Optional<Privilege> optionalPrivilege = privilegeRepo.findById(p);
            if (optionalPrivilege.isPresent()) {
                UserPrivilege userPrivilege = new UserPrivilege();
                userPrivilege.setUser(user);
                userPrivilege.setPrivilege(optionalPrivilege.get());

                //  Add list
                userPrivileges.add(userPrivilege);
            }
        }

        //  Save userRole list
        userPrivilegeRepo.saveAll(userPrivileges);
    }

    public List<String> getPrivilegesByRole(String code) {
        Role role = roleRepo.findById(code).get();
        List<RolePrivilege> list = rolePrivilegeRepo.findByRole(role);

        List<String> rs = new ArrayList<>();
        for (RolePrivilege rp : list) {
            rs.add(rp.getPrivilege().getCode());
        }
        return rs;
    }

    public List<Role> getRoleAll() {
        var roleList = roleRepo.findAllByOrderByNoAsc();
        return roleList.stream().filter(it -> !PermissionConstant.SUPER_ADMIN.equals(it.getCode())).collect(Collectors.toList());
    }

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public UserRole saveUsreRole(UserRole userRole) {
        return roleRepo.save(userRole);
    }

    public void deleteRole(String code, Role role) throws BadRequestException {
        Optional<Role> roleOptional = roleRepo.findById(code);
        if (roleOptional.isEmpty()) {
            throw new BadRequestException("RD0");
        }
        role.setIsDeleted("Y");
        roleRepo.save(role);
    }

    public boolean isPermission(String privilege, HttpServletRequest httpServletRequest) {
        String username = UserLoginUtils.getUsername();
        List<String> privilegeList = jwtUtil.getPrivilege(httpServletRequest);
        if (ObjectUtils.isEmpty(privilegeList)) {
            log.warn("[username: {}] ==> is not permission", username);
            return false;
        }
        //  Check privileges
        return privilegeList.stream().anyMatch(it -> it.equals(privilege));
    }

    public boolean isPermission(String privilege) {
        log.info("validate permission with database");
        String username = UserLoginUtils.getUsername();
        //  get user profile
        User user = userRepo.findByUsername(username);
        UserProfileDto userProfile = this.getUserProfile(user);

        if (ObjectUtils.isEmpty(userProfile)) {
            log.warn("[username: {}] ==> is not permission", username);
            return false;
        }

        //  Check privileges
        return privilegesIsMatch(privilege, userProfile);
    }

    private boolean privilegesIsMatch(String privilege, UserProfileDto userProfile) {
        List<String> privileges = userProfile.getUser().getPrivileges();
        Optional<String> result = privileges
                .stream().parallel()
                .filter(p -> p.equals(privilege)).findAny();
        return result.isPresent();
    }

    public UserProfileDto getUserProfile(User user) {

        //  Get and save user profile from database
        UserProfileDto userProfileDto = new UserProfileDto();
        List<String> privileges = this.findPrivilegesByUser(user.getUsername());
        List<UserRole> userRoleList = userRoleRepo.findByUser(user);

        List<String> roles = new ArrayList<>();
        for (UserRole ur : userRoleList) {
            roles.add(ur.getRole().getCode());
        }

        //  set roles & privileges
        user.setRoles(roles);
        user.setPrivileges(privileges);

        userProfileDto.setUser(user);

        log.info("get user profile from database");
        return userProfileDto;
    }

    public List<UserRole> findUserRole(User user) {
        return userRoleRepo.findByUser(user);
    }

    public List<UserRole> findUserRole(String username) {
        var user = userRepo.findByUsername(username);
        return userRoleRepo.findByUser(user);
    }
}
