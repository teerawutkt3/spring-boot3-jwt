package com.tatechsoft.project.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserLoginUtils {

    private final static Logger logger = LoggerFactory.getLogger(UserLoginUtils.class);

    public static Authentication getUserLogin() {
        Authentication auth = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            auth = SecurityContextHolder.getContext().getAuthentication();
        }
        return auth;
    }

    public static String getUsername() {
        if (getUserLogin() != null) return getUserLogin().getName();
        else return "SYSTEM";
    }

    public static List<String> getGrantedAuthorityList() {
        List<String> authorityList = new ArrayList<>();
        if (getUserLogin() != null) {
            Collection<? extends GrantedAuthority> grantedAuthorityList = getUserLogin().getAuthorities();
            authorityList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
                authorityList.add(grantedAuthority.getAuthority());
            }
        }
        logger.info("Method getGrantedAuthorityList :  {} success", authorityList);
        return authorityList;
    }

}
