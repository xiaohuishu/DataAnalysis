package com.march.graduation.model;

import java.io.Serializable;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-28 时间: 下午3:41
//********************************************
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;

    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return !(username != null ? !username.equals(user.username) : user.username != null) && !(password != null ?
                !password.equals(user.password) :
                user.password != null);

    }

    @Override public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
