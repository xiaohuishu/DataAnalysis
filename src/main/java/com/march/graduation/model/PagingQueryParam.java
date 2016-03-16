package com.march.graduation.model;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------

import java.io.Serializable;

public class PagingQueryParam implements Serializable {

    private int page = 1;

    private int pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPagingOffset() {
        return (this.page - 1) * pageSize;
    }
}