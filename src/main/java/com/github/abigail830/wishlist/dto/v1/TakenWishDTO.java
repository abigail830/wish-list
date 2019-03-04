package com.github.abigail830.wishlist.dto.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @ApiModelProperty(value = "愿望承接人",  example = "微信用户")
    private List<UserDTO> implementors;

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
        if (wish.getImplementors() != null && wish.getImplementors().size() > 0) {
            implementors = new LinkedList<>();
            for (User user : wish.getImplementors()) {
                implementors.add(new UserDTO(user));
            }
        }
        if (wish.getCreator() != null) {
            this.creator = new UserDTO(wish.getCreator());
        }
    }
}
