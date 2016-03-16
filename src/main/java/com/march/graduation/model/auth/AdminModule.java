package com.march.graduation.model.auth;


import java.io.Serializable;
import java.util.Date;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class AdminModule implements Serializable {

    private static final long serialVersionUID = 933951422976688451L;

    public static final int ROOT_PARENT_ID = 0;

    private int id;

    private String moduleName;

    private String remarks;

    private int parentId;

    private int ordered;

    private String link;

    private boolean valid;

    private Date cTime;

    private Date uTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
