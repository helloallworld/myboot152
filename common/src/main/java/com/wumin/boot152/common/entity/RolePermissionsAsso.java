package com.wumin.boot152.common.entity;

import javax.persistence.*;

@Table(name = "role_permissions_asso")
public class RolePermissionsAsso extends BaseEntity {


    @Id
    @Column(name = "permissions_id")
    private Long permissionsId;



    /**
     * @return permissions_id
     */
    public Long getPermissionsId() {
        return permissionsId;
    }

    /**
     * @param permissionsId
     */
    public void setPermissionsId(Long permissionsId) {
        this.permissionsId = permissionsId;
    }
}