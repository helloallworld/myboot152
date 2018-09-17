package com.wumin.boot152.common.entity;

import javax.persistence.*;

@Table(name = "user_role_asso")
public class UserRoleAsso extends BaseEntity {


    @Id
    @Column(name = "role_id")
    private Integer roleId;



    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}