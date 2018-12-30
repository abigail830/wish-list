package com.github.abigail830.wishlist.domainv1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;

@ApiModel("愿望列表前段VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TakenWishDTO {


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

    @ApiModelProperty(value = "愿望承接人",  example = "微信用户")
    private UserDTO implementor;

    @ApiModelProperty(value = "愿望拥有人",  example = "微信用户")
    private UserDTO creator;

    @ApiModelProperty(value = "愿望兑现时间",  example = "2018-10-10")
    private String listDueTime;

    @ApiModelProperty(value = "日期",  example = "01")
    private String dateInMonth;

    @ApiModelProperty(value = "月份",  example = "2018-10")
    private String yearAndMonth;

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static final ThreadLocal<SimpleDateFormat> duedateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public TakenWishDTO () {

    }

    public TakenWishDTO(Wish wish) {
        this.wishID = wish.getId();
        this.description = wish.getDescription();
        if (wish.getCreateTime() != null) {
            this.createTime = dateFormatter.get().format(wish.getCreateTime());
        }
        if (wish.getLastUpdateTime() != null) {
            this.lastUpdateTime = dateFormatter.get().format(wish.getLastUpdateTime());
        }
        this.wishStatus = wish.getWishStatus();
        this.wishListID = wish.getWishListId();
        if (wish.getDueTime() != null) {
            this.listDueTime = duedateFormatter.get().format(wish.getDueTime());
            this.dateInMonth = this.listDueTime.substring(8, 10);
            this.yearAndMonth = this.listDueTime.substring(0, 7);
        }
        if (StringUtils.isNotBlank(wish.getImplementorOpenId()) && wish.getImplementor() != null) {
            this.implementor = new UserDTO(wish.getImplementor());
        }
        if (wish.getCreator() != null) {
            this.creator = new UserDTO(wish.getCreator());
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

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public String getDateInMonth() {
        return dateInMonth;
    }

    public void setDateInMonth(String dateInMonth) {
        this.dateInMonth = dateInMonth;
    }

    public String getListDueTime() {
        return listDueTime;
    }

    public void setListDueTime(String listDueTime) {
        this.listDueTime = listDueTime;
    }
}
