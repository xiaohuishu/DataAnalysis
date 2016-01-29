package com.march.graduation.web.model;

public class ResultCode {

    public static final int OK = 0;

    public static final int UPDATE_FAIL = 1;

    public static final int DELETE_FAIL = 2;

    public static final int CREATE_FAIL = 3;

    public static final int FIND_FAIL = 4;

    public static final int ILLEGAL_ARGUMENT = 5;

    public static final int FORBIDDEN_CONTENT = 6;

    public static final int LOGIN_REQUIRED = 7;

    public static final int UNKNOWN_ERROR = 8;

    public static final int STD_OK = 200;

    public static final int STD_CREATED = 201;

    public static final int STD_UNAUTH = 401;

    public static final int STD_FORBIDDEN = 403;

    public static final int STD_NOT_FOUND = 404;

    public static final int STD_UNSUPPORTED_MEDIA_TYPE = 415;
}