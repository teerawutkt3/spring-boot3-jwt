package com.tatechsoft.project.common.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Locale;

public class AppUtils {

    public static String getClientIp(HttpServletRequest httpReq) {
        String remoteAddr = "";
        if (httpReq != null) {
            remoteAddr = httpReq.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpReq.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static boolean hasValue(Object o) {
        if (o == null) return false;
        if (o instanceof String && ((String) o).trim().equals("")) return false;
        if (o.getClass().isArray() && o != null && Array.getLength(o) == 0) return false;
        if (o instanceof Collection && ((Collection) o).isEmpty()) return false;
        return true;
    }

    public static String entityLowerCase(String entityName){
        return entityName.substring(0, 1).toLowerCase(Locale.ROOT) + entityName.substring(1, entityName.length());
    }
}
