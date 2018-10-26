package com.github.abigail830.wishlist.domainv1;

import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class WishListDTO {
    @ApiModelProperty(value = "用户openID",  example = "troEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String listOpenId;

    @ApiModelProperty(value = "愿望清单的ID",  example = "2")
    private Integer listId;

    @ApiModelProperty(value = "愿望清单概述",  example = "新年愿望清单")
    private String listDescription;

    @ApiModelProperty(value = "愿望清单创建日期",  example = "2018-09-01")
    private String listCreateTime;

    @ApiModelProperty(value = "愿望清单目标兑现时间",  example = "2018-10-10")
    private String listDueTime;

    @ApiModelProperty(value = "愿望列表")
    List<WishDTO> wishes = new ArrayList<>();


    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public WishListDTO(WishListDetail wishListDetail) {
        this.listOpenId = wishListDetail.getListOpenId();
        this.listId = wishListDetail.getListId();
        this.listDescription = wishListDetail.getListDescription();
        this.listCreateTime = dateFormatter.get().format(wishListDetail.getListCreateTime());
        this.listDueTime = dateFormatter.get().format(wishListDetail.getListDueTime());
        this.wishes = wishListDetail.getWishes().stream().map(WishDTO::new).collect(Collectors.toList());
    }


    public WishListDTO(WishList wishList) {
        this.listOpenId = wishList.getOpenId();
        this.listId = wishList.getId();
        this.listDescription = wishList.getDescription();
        this.listCreateTime = dateFormatter.get().format(wishList.getCreateTime());
        this.listDueTime = dateFormatter.get().format(wishList.getDueTime());
    }


    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListOpenId() {
        return listOpenId;
    }

    public void setListOpenId(String listOpenId) {
        this.listOpenId = listOpenId;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public String getListCreateTime() {
        return listCreateTime;
    }

    public void setListCreateTime(String listCreateTime) {
        this.listCreateTime = listCreateTime;
    }

    public String getListDueTime() {
        return listDueTime;
    }

    public void setListDueTime(String listDueTime) {
        this.listDueTime = listDueTime;
    }

    public List<WishDTO> getWishes() {
        return wishes;
    }

    public void setWishes(List<WishDTO> wishes) {
        this.wishes = wishes;
    }

    public void addWish(WishDTO wish){
        this.wishes.add(wish);
    }

}
