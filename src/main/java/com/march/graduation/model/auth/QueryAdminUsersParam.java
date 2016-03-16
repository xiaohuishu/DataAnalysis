package com.march.graduation.model.auth;

import com.march.graduation.model.PagingQueryParam;

import java.io.Serializable;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class QueryAdminUsersParam extends PagingQueryParam implements Serializable {

    private static final long serialVersionUID = -1L;

    private String userName;

    private String loginName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
