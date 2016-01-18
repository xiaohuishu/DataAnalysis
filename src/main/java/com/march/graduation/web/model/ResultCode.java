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

    public static final int MISSING_ARGUMENT = 9;

    public static final int ARGUMENT_TYPE_ERROR = 10;

    public static final int BLACK_LIST_USER = 11;

    //广告内容
    public static final int AD_CONTENT = 12;

    public static final int ACCESS_RATE_LIMIT = 13;

    /**
     * 绑定手机号
     */
    public static final int MOBILE_REQUIRED = 14;

    /**
     * csrfToken错误
     */
    public static final int CSRF_TOKEN_ERROR = 15;

    public static final int STD_OK = 200;

    public static final int STD_CREATED = 201;

    public static final int STD_UNAUTH = 401;

    public static final int STD_FORBIDDEN = 403;

    public static final int STD_NOT_FOUND = 404;

    public static final int STD_UNSUPPORTED_MEDIA_TYPE = 415;

}