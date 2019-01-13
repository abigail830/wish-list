package com.github.abigail830.wishlist.domainv1;


import com.github.abigail830.wishlist.entity.FormIDMapping;

import java.util.Date;

public class FormIDMappingDTO {
    private String openID;
    private String formID;
    private Date createTime;
    private Date dueTime;

    public FormIDMappingDTO() {
    }

    public FormIDMappingDTO(FormIDMapping formIDMapping) {
        this.openID = formIDMapping.getOpenId();
        this.formID = formIDMapping.getFormId();
        this.createTime = formIDMapping.getCreateTime();
        this.dueTime = formIDMapping.getDueTime();
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
}
