package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.WishList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;

@ApiModel("愿望列表前段")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BriefWishList {

    @ApiModelProperty(value = "愿望清单的ID",  example = "1")
    private Integer wishListID;

    @ApiModelProperty(value = "用户openID",  example = "troEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String openId;

    @ApiModelProperty(value = "愿望清单概述",  example = "新年愿望清单")
    private String description;

    @ApiModelProperty(value = "愿望清单创建日期",  example = "2018-09-01")
    private String createTime;

    @ApiModelProperty(value = "愿望清单目标兑现时间",  example = "2018-10-10")
    private String dueTime;

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

    public BriefWishList(Integer wishListID, String openId, String description, String createTime, String dueTime) {
        this.wishListID = wishListID;
        this.openId = openId;
        this.description = description;
        this.createTime = createTime;
        this.dueTime = dueTime;
    }

    public BriefWishList(WishList wishList) {
        this.wishListID = wishList.getId();
        this.openId = wishList.getOpenId();
        this.description = wishList.getTitle();
        this.createTime = f.format(wishList.getCreateTime());
        this.dueTime = f.format(wishList.getDueTime());
    }

    public Integer getWishListID() {
        return wishListID;
    }

    public void setWishListID(Integer wishListID) {
        this.wishListID = wishListID;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return "BriefWishList{" +
                "wishListID=" + wishListID +
                ", openId='" + openId + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", dueTime='" + dueTime + '\'' +
                '}';
    }
}
