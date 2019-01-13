package com.github.abigail830.wishlist.entity;


import java.sql.Timestamp;

public class FormIDMapping {

    private String openId;
    private String formId;
    private Timestamp createTime;
    private Timestamp dueTime;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return "FormIDMapping{" +
                "openId='" + openId + '\'' +
                ", formId='" + formId + '\'' +
                ", createTime=" + createTime +
                ", dueTime=" + dueTime +
                '}';
    }
}
