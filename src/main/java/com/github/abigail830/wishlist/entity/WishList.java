package com.github.abigail830.wishlist.entity;

import java.sql.Timestamp;

public class WishList {

    private Integer id;
    private String openID;
    private String description;
    private Timestamp createTime;
    private Timestamp dueTime;

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "WishList{" +
                "id=" + id +
                ", openID='" + openID + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", dueTime=" + dueTime +
                '}';
    }
}
