package com.wumin.boot152.common.entity;

import javax.persistence.*;

public class User extends BaseEntity {


    /**
     * 用户名
     */
    private String username;

    /**
     * password
     */
    private String password;

    private String salt;

    /**
     * 真名
     */
    @Column(name = "realName")
    private String realname;



    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取password
     *
     * @return password - password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取真名
     *
     * @return realName - 真名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真名
     *
     * @param realname 真名
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }
}