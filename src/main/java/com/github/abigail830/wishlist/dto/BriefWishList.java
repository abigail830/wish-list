package com.github.abigail830.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.WishList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;

@Getter
@Setter
@ToString
@AllArgsConstructor
@ApiModel("愿望列表前段")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BriefWishList {

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    @ApiModelProperty(value = "愿望清单的ID", example = "1")
    private Integer wishListID;
    @ApiModelProperty(value = "用户openID", example = "troEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String openId;
    @ApiModelProperty(value = "愿望清单概述", example = "新年愿望清单")
    private String description;
    @ApiModelProperty(value = "愿望清单创建日期", example = "2018-09-01")
    private String createTime;
    @ApiModelProperty(value = "愿望清单目标兑现时间", example = "2018-10-10")
    private String dueTime;

    public BriefWishList(WishList wishList) {
        this.wishListID = wishList.getId();
        this.openId = wishList.getOpenId();
        this.description = wishList.getTitle();
        this.createTime = f.format(wishList.getCreateTime());
        this.dueTime = f.format(wishList.getDueTime());
    }
}
