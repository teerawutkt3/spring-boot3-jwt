package com.tatechsoft.project.constants;

public class AppConstant {

    public static class ErrorCode {
        public static final String PERMISSION_EXCEPTION = "99";
        public static final String BAD_REQUEST = "99";
        public static final String BUSINESS_EXCEPTION = "99";
        public static final String DATA_NOT_FOUND = "99";
        public static final String OTHER_EXCEPTION = "99";
    }

    public static class Locale {
        public static final String TH = "th";
        public static final String EN = "en";
    }

    public static class Flag {
        public static final String Y = "Y";
        public static final String N = "N";
    }

    public static class Status {
        public static final String APPROVE = "APPROVE";
        public static final String REJECT = "REJECT";
        public static final String PENDING = "PENDING";
    }

    public enum RecStatus {
        ACTIVE,
        INACTIVE,
    }
    
    public static final String ACCESS_MENU = "ACCESS_MENU_";
    public static final String GET = "GET_";
    public static final String CREATE = "CREATE_";
    public static final String EDIT = "EDIT_";
    public static final String DELETE = "DELETE_";
    
    public static final String ACCESS_MENUS = "access menu ";
    public static final String GETS = "Get ";
    public static final String CREATES = "Create ";
    public static final String EDITS = "Edit ";
    public static final String DELETES = "Delete ";
    

}
