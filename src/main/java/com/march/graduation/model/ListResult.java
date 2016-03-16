package com.march.graduation.model;

import java.io.Serializable;
import java.util.List;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class ListResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    private int total;

    private List<T> resultList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
