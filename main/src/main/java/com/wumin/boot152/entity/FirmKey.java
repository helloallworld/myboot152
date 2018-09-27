package com.wumin.boot152.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 保存id 和key对应关系的mongo实体
 */
@Document(collection = "FirmKey")
public class FirmKey implements Serializable {
    private static final long serialVersionUID = -1l;
    @Id
    private String id;

    /**
     * 公司名字
     */
    private String name;

    /**
     * 秘钥
     */
    private String key;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 描述
     */
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
