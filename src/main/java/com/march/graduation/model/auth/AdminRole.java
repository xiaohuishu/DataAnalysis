package com.march.graduation.model.auth;

import java.io.Serializable;
import java.util.Date;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class AdminRole implements Serializable {

    private static final long serialVersionUID = -1L;

    private int id;

    private String roleName;

    private String remarks;

    private boolean valid;

    private Date cTime;

    private Date uTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }
}
