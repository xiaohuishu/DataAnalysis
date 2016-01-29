package com.march.graduation.execute;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-26 时间: 下午3:25
//********************************************
public class FutureBlockException extends Exception {

    private String message;

    public FutureBlockException(String message) {
        super(message);
        this.message = message;
    }

    @Override public String getMessage() {
        return message;
    }
}
