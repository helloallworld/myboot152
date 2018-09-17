package com.wumin.boot152.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据交互日志记录
 * Created by yaodong01 on 2018/3/26.
 */
@Document(collection = "DataMongoDBLogger")
public class DataMongoDBLogger implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    private String id;
    /**
     * 说明
     */
    private String description;
    /**
     * 记录数据(主键 或者json格式数据文件)
     */
    private Object data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 用时(毫秒)
     */
    private Long diffTime;

    /**
     * 调用(SAP,JDE)接口0, 被调用(webservice) 1
     */
    private String type;
    /**
     * 接口结果处理
     */
    private Object result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }
    public Long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(Long diffTime) {
        this.diffTime = diffTime;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
