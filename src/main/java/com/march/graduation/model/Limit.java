package com.march.graduation.model;

import java.io.Serializable;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-26 时间: 下午5:39
//********************************************
public class Limit implements Serializable {

    private static final long serialVersionUID = 1L;

    private int offset;

    private int limit;

    public Limit(int offset, int limit) {
        if (offset < 0) offset = 0;
        this.offset = offset;
        this.limit = limit;
    }

    public Limit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return offset + limit;
    }
}
