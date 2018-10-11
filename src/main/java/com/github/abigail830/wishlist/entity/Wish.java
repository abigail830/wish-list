package com.github.abigail830.wishlist.entity;

import java.sql.Timestamp;

public class Wish {
    private Integer id;
    private Integer wishListId;
    private String description;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private String status;
    private String implementorOpenId;

    public Wish(Integer id, Integer wishListId, String description) {
        this.id = id;
        this.wishListId = wishListId;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWishListId() {
        return wishListId;
    }

    public void setWishListId(Integer wishListId) {
        this.wishListId = wishListId;
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

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImplementorOpenId() {
        return implementorOpenId;
    }

    public void setImplementorOpenId(String implementorOpenId) {
        this.implementorOpenId = implementorOpenId;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", wishListId=" + wishListId +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", status='" + status + '\'' +
                ", implementorOpenId='" + implementorOpenId + '\'' +
                '}';
    }
}
