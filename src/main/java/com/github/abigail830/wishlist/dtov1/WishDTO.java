package com.github.abigail830.wishlist.dtov1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @ApiModelProperty(value = "愿望承接人",  example = "微信用户")
    private UserDTO implementor;

    @ApiModelProperty(value = "愿望拥有人",  example = "微信用户")
    private UserDTO creator;

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


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
        if (wish.getCreateTime() != null) {
            this.createTime = dateFormatter.get().format(wish.getCreateTime());
        }
        if (wish.getLastUpdateTime() != null) {
            this.lastUpdateTime = dateFormatter.get().format(wish.getLastUpdateTime());
        }
        this.wishStatus = wish.getWishStatus();
        this.wishListID = wish.getWishListId();
        if (StringUtils.isNotBlank(wish.getImplementorOpenId()) && wish.getImplementor() != null) {
            this.implementor = new UserDTO(wish.getImplementor());
        }
        if (wish.getCreator() != null) {
            this.creator = new UserDTO(wish.getCreator());
        }
    }


}
