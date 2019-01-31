package com.github.abigail830.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.Wish;
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
@ApiModel("愿望列表前段VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishDomain {

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    @ApiModelProperty(value = "愿望ID", example = "1")
    private Integer wishID;
    @ApiModelProperty(value = "愿望概述", example = "我要一台车！")
    private String description;
    @ApiModelProperty(value = "愿望创建时间", example = "2018-09-01")
    private String createTime;
    @ApiModelProperty(value = "愿望信息最后更新时间", example = "2018-11-01")
    private String lastUpdateTime;
    @ApiModelProperty(value = "愿望最新状态", example = "实现啦！")
    private String wishStatus;
    @ApiModelProperty(value = "愿望承接人", example = "2018-09-01")
    private UserInfo implementor;

    public WishDomain(Wish wish) {
        this.wishID = wish.getId();
        this.description = wish.getDescription();
        this.createTime = f.format(wish.getCreateTime());
        this.lastUpdateTime = f.format(wish.getLastUpdateTime());
        this.wishStatus = wish.getWishStatus();
        if (wish.getImplementorOpenId() != null) {
            this.implementor = new UserInfo(wish.getImplementorOpenId());
        }
    }

}
