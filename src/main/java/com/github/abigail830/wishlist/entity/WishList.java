package com.github.abigail830.wishlist.entity;

import java.sql.Timestamp;

public class WishList implements Comparable{

    private Integer id;
    private String openId;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
                ", openId ='" + openId + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", dueTime=" + dueTime +
                '}';
    }

    @Override
    public int compareTo(Object obj) {
        WishList compareObj = (WishList) obj;
        if (compareObj.getCreateTime() != null && this.createTime != null) {
            return this.createTime.compareTo(compareObj.getCreateTime());
        } else {
            return 0;
        }
    }
}
