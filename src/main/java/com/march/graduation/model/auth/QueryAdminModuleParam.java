package com.march.graduation.model.auth;

import java.io.Serializable;
import java.util.List;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class QueryAdminModuleParam implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final int VALID_ALL = 0;

    public static final int VALID_TRUE = 1;

    public static final int VALID_FALSE = 2;

    private String moduleName;

    private List<Integer> modelIdList;

    private int valid;

    private int parentId = -1;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Integer> getModelIdList() {
        return modelIdList;
    }

    public void setModelIdList(List<Integer> modelIdList) {
        this.modelIdList = modelIdList;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}