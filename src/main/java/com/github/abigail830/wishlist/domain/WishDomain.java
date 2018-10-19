package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@ApiModel("愿望列表前段VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishDomain {

    @ApiModelProperty(value = "愿望ID",  example = "1")
    private Integer wishID;

    @ApiModelProperty(value = "愿望概述",  example = "我要一台车！")
    private String description;

    @ApiModelProperty(value = "愿望创建时间",  example = "2018-09-01")
    private String createTime;

    @ApiModelProperty(value = "愿望信息最后更新时间",  example = "2018-11-01")
    private String lastUpdateTime;

    @ApiModelProperty(value = "愿望最新状态",  example = "实现啦！")
    private String wishStatus;

    @ApiModelProperty(value = "愿望承接人",  example = "2018-09-01")
    private UserInfo implementor;

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

    public WishDomain(Integer wishID, String description, String createTime, String lastUpdateTime, String wishStatus, UserInfo implementor) {
        this.wishID = wishID;
        this.description = description;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.wishStatus = wishStatus;
        this.implementor = implementor;
    }

    public WishDomain(Wish wish) {
        this.wishID = wish.getId();
        this.description = wish.getDescription();
        this.createTime = f.format(wish.getCreateTime());
        this.lastUpdateTime = f.format(wish.getLastUpdateTime());
        this.wishStatus = wish.getWishStatus();
    }

    public Integer getWishID() {
        return wishID;
    }

    public void setWishID(Integer wishID) {
        this.wishID = wishID;
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

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getWishStatus() {
        return wishStatus;
    }

    public void setWishStatus(String wishStatus) {
        this.wishStatus = wishStatus;
    }

    public UserInfo getImplementor() {
        return implementor;
    }

    public void setImplementor(UserInfo implementor) {
        this.implementor = implementor;
    }

    @Override
    public String toString() {
        return "WishDomain{" +
                "wishID=" + wishID +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", wishStatus='" + wishStatus + '\'' +
                ", implementor=" + implementor +
                '}';
    }
}
