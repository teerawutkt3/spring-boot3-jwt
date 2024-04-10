package com.tatechsoft.project.constants;

public class PermissionConstant {

    // ==> Role
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ADMIN = "ADMIN";
    public static final String SUB_ADMIN = "SUB_ADMIN";
    public static final String MEMBER = "MEMBER";

    // ==> Code | Label | Description
    public static String[][] Roles = {
            {SUPER_ADMIN, "Super admin", ""},
            {ADMIN, "ผู้ดูแลระบบ", ""},
            {SUB_ADMIN, "ผู่้้จัดการ", ""},
            {MEMBER, "สมาชิก", ""},
    };


    public static final String CAN_ACCESS_MENU_USER = "CAN_ACCESS_MENU_USER";
    public static final String CAN_GET_USER = "CAN_GET_USER";
    public static final String CAN_CREATE_USER = "CAN_CREATE_USER";
    public static final String CAN_EDIT_USER = "CAN_EDIT_USER";
    public static final String CAN_DELETE_USER = "CAN_DELETE_USER";
    public static final String CAN_GET_LOGIN_HISTORY = "CAN_GET_LOGIN_HISTORY";
    public static final String CAN_ACCESS_MENU_BENEFIT = "CAN_ACCESS_MENU_BENEFIT";
    public static final String CAN_GET_BENEFIT = "CAN_GET_BENEFIT";
    public static final String CAN_CREATE_BENEFIT = "CAN_CREATE_BENEFIT";
    public static final String CAN_EDIT_BENEFIT = "CAN_EDIT_BENEFIT";
    public static final String CAN_DELETE_BENEFIT = "CAN_DELETE_BENEFIT";
    public static final String CAN_ACCESS_MENU_S_GEN = "CAN_ACCESS_MENU_S_GEN";
    public static final String CAN_GET_S_GEN = "CAN_GET_S_GEN";
    public static final String CAN_CREATE_S_GEN = "CAN_CREATE_S_GEN";
    public static final String CAN_EDIT_S_GEN = "CAN_EDIT_S_GEN";
    public static final String CAN_DELETE_S_GEN = "CAN_DELETE_S_GEN";
    public static final String CAN_ACCESS_MENU_PROFILE = "CAN_ACCESS_MENU_PROFILE";
    public static final String CAN_GET_PROFILE = "CAN_GET_PROFILE";
    public static final String CAN_CREATE_PROFILE = "CAN_CREATE_PROFILE";
    public static final String CAN_EDIT_PROFILE = "CAN_EDIT_PROFILE";
    public static final String CAN_DELETE_PROFILE = "CAN_DELETE_PROFILE";
    public static final String CAN_EDIT_ROLE_PROFILE = "CAN_EDIT_ROLE_PROFILE";
    public static final String CAN_ACCESS_MENU_PROFILE_BENEFIT = "CAN_ACCESS_MENU_PROFILE_BENEFIT";
    public static final String CAN_GET_PROFILE_BENEFIT = "CAN_GET_PROFILE_BENEFIT";
    public static final String CAN_CREATE_PROFILE_BENEFIT = "CAN_CREATE_PROFILE_BENEFIT";
    public static final String CAN_EDIT_PROFILE_BENEFIT = "CAN_EDIT_PROFILE_BENEFIT";
    public static final String CAN_DELETE_PROFILE_BENEFIT = "CAN_DELETE_PROFILE_BENEFIT";
    public static final String CAN_APPROVE_PROFILE_BENEFIT = "CAN_APPROVE_PROFILE_BENEFIT";
    public static final String CAN_REJECT_PROFILE_BENEFIT = "CAN_REJECT_PROFILE_BENEFIT";

    // ==> GroupCode | Code | Label | GroupLabel
    public static String[][] Privileges = {
            {"USER", CAN_ACCESS_MENU_USER, "เข้าถึงเมนู", "ผู้ใช้งาน"},
            {"USER", CAN_GET_USER, "แสดงข้อมูล", "ผู้ใช้งาน"},
            {"USER", CAN_CREATE_USER, "สร้าง", "ผู้ใช้งาน"},
            {"USER", CAN_EDIT_USER, "แก้ไข", "ผู้ใช้งาน"},
            {"USER", CAN_DELETE_USER, "ลบ", "ผู้ใช้งาน"},

            {"BENEFIT", CAN_ACCESS_MENU_BENEFIT, "เข้าถึงเมนู", "สวัสดิการ"},
            {"BENEFIT", CAN_GET_BENEFIT, "แสดงข้อมูล", "สวัสดิการ"},
            {"BENEFIT", CAN_CREATE_BENEFIT, "สร้าง", "สวัสดิการ"},
            {"BENEFIT", CAN_EDIT_BENEFIT, "แก้ไข", "สวัสดิการ"},
            {"BENEFIT", CAN_DELETE_BENEFIT, "ลบ", "สวัสดิการ"},

            {"STUDENT_GENERATION", CAN_ACCESS_MENU_S_GEN, "เข้าถึงเมนู", "รุ่นนักเรียก"},
            {"STUDENT_GENERATION", CAN_GET_S_GEN, "แสดงข้อมูล", "รุ่นนักเรียก"},
            {"STUDENT_GENERATION", CAN_CREATE_S_GEN, "สร้าง", "รุ่นนักเรียก"},
            {"STUDENT_GENERATION", CAN_EDIT_S_GEN, "แก้ไข", "รุ่นนักเรียก"},
            {"STUDENT_GENERATION", CAN_DELETE_S_GEN, "ลบ", "รุ่นนักเรียก"},

            {"PROFILE", CAN_ACCESS_MENU_PROFILE, "เข้าถึงเมนู", "โปรไฟล์"},
            {"PROFILE", CAN_GET_PROFILE, "แสดงข้อมูล", "โปรไฟล์"},
            {"PROFILE", CAN_CREATE_PROFILE, "สร้าง", "โปรไฟล์"},
            {"PROFILE", CAN_EDIT_PROFILE, "แก้ไข", "โปรไฟล์"},
            {"PROFILE", CAN_DELETE_PROFILE, "ลบ", "โปรไฟล์"},
            {"PROFILE", CAN_EDIT_ROLE_PROFILE, "แก้ไขสิทธิ", "โปรไฟล์"},

            {"PROFILE_BENEFIT", CAN_ACCESS_MENU_PROFILE_BENEFIT, "เข้าถึงเมนู", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_GET_PROFILE_BENEFIT, "แสดงข้อมูล", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_CREATE_PROFILE_BENEFIT, "สร้าง", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_EDIT_PROFILE_BENEFIT, "แก้ไข", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_DELETE_PROFILE_BENEFIT, "ลบ", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_APPROVE_PROFILE_BENEFIT, "อนุมัต", "ร้องขอสวัสดิการ"},
            {"PROFILE_BENEFIT", CAN_REJECT_PROFILE_BENEFIT, "ลบ", "ร้องขอสวัสดิการ"},


            {"LOGIN_HISTORY", CAN_GET_LOGIN_HISTORY, "แสดงข้อมูล", "ประวัติการเข้าสู่ระบบ"},
    };

}
