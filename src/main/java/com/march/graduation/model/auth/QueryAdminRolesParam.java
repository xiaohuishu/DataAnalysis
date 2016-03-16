package com.march.graduation.model.auth;

import java.io.Serializable;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class QueryAdminRolesParam implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final int VALID_ALL = 0;

    public static final int VALID_TRUE = 1;

    public static final int VALID_FALSE = 2;

    private String roleName;

    private int valid;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
