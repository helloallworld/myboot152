package com.wumin.boot152.common.entity;

import java.util.Date;
import javax.persistence.*;

public class Permissions extends BaseEntity {


    private String name;

    private String code;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 父类,0为父类,其余为id值
     */
    @Column(name = "parent_id")
    private Long parentId;




    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取父类,0为父类,其余为id值
     *
     * @return parent_id - 父类,0为父类,其余为id值
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父类,0为父类,其余为id值
     *
     * @param parentId 父类,0为父类,其余为id值
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}