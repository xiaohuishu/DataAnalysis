package com.march.graduation.model;

import com.march.graduation.view.JsonAndView;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-4 时间: 下午2:59
//********************************************
public enum AnalysisResultCode {

    LOGIN_INFO_NULL(9001, "登录信息为空"),
    INVALIDATE_INFO_NULL(9002, "注销信息为空"),

    LOGIN_FAIL_REPEAT(6001, "登录失败, 请勿重复登录"),

    UNKNOWN_ERROR(1001, "未知错误"),
    ILLEGAL_ERROR(1002, "参数错误"),
    REPREATE_ERROR(1003, "重复错误"),
    NOT_EXIST_ERROR(1004, "不存在"),

    SUCCESS_INFO(0, "成功");

    AnalysisResultCode(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public JsonAndView getJsonAndView() {
        return new JsonAndView(code).setErrmsg(errMsg);
    }

    private int code;
    private String errMsg;
}
