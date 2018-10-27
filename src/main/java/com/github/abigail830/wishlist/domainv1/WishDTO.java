package com.github.abigail830.wishlist.domainv1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;

@ApiModel("愿望列表前段VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishDTO {

    @ApiModelProperty(value = "愿望ID",  example = "1")
    private Integer wishID;

    @ApiModelProperty(value = "愿望列表ID",  example = "1")
    private Integer wishListID;

    @ApiModelProperty(value = "愿望概述",  example = "我要一台车！")
    private String description;

    @ApiModelProperty(value = "愿望创建时间",  example = "2018-09-01")
    private String createTime;

    @ApiModelProperty(value = "愿望信息最后更新时间",  example = "2018-11-01")
    private String lastUpdateTime;

    @ApiModelProperty(value = "愿望最新状态",  example = "实现啦！")
    private String wishStatus;

    @ApiModelProperty(value = "愿望承接人",  example = "2018-09-01")
    private UserDTO implementor;

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public WishDTO () {

    }

    public WishDTO(Integer wishID, String description, String createTime, String lastUpdateTime,
                      String wishStatus, UserDTO implementor, Integer wishListID) {
        this.wishID = wishID;
        this.description = description;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.wishStatus = wishStatus;
        this.implementor = implementor;
        this.wishListID = wishListID;
    }

    public WishDTO(Wish wish) {
        this.wishID = wish.getId();
        this.description = wish.getDescription();
        this.createTime = dateFormatter.get().format(wish.getCreateTime());
        this.lastUpdateTime = dateFormatter.get().format(wish.getLastUpdateTime());
        this.wishStatus = wish.getWishStatus();
        this.wishListID = wish.getWishListId();
        if (StringUtils.isNotBlank(wish.getImplementorOpenId())) {
            this.implementor = new UserDTO(wish.getImplementor());
        }
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

    public UserDTO getImplementor() {
        return implementor;
    }

    public void setImplementor(UserDTO implementor) {
        this.implementor = implementor;
    }

    public Integer getWishListID() {
        return wishListID;
    }

    public void setWishListID(Integer wishListID) {
        this.wishListID = wishListID;
    }

    @Override
    public String toString() {
        return "WishDTO{" +
                "wishID=" + wishID +
                ", wishListID='" + wishListID + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", wishStatus='" + wishStatus + '\'' +
                ", implementor=" + implementor +
                '}';
    }

}
