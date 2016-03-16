package com.march.graduation.model.auth;

import java.io.Serializable;
import java.util.List;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class AuthNode implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final int TYPE_MODULE = 0;

    public static final int TYPE_RESOURCE = 1;

    private int id;

    private int parentId;

    // 节点类型 : 0 - 模块节点, 1 - 资源节点
    private int type;

    private String name;

    private boolean authorize;

    private List<AuthNode> childrenNodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public List<AuthNode> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(List<AuthNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }
}
