package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.abigail830.wishlist.entity.WishListDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("详细版愿望列表搜索返回")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListDetailResponse {

    @ApiModelProperty(value = "用户openID",  example = "troEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String listOpenId;

    @ApiModelProperty(value = "愿望清单概述",  example = "新年愿望清单")
    private String listDescription;

    @ApiModelProperty(value = "愿望清单创建日期",  example = "2018-09-01")
    private String listCreateTime;

    @ApiModelProperty(value = "愿望清单目标兑现时间",  example = "2018-10-10")
    private String listDueTime;

    @ApiModelProperty(value = "愿望列表")
    List<WishDomain> wishes = new ArrayList<>();

    @ApiModelProperty(value = "查询状态", example = "VALIDATION_FAIL")
    String resultCode;

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

    public WishListDetailResponse(String resultCode) {
        this.resultCode = resultCode;
    }

    public WishListDetailResponse(WishListDetail wishListDetail) {
        this.listOpenId = wishListDetail.getListOpenId();
        this.listDescription = wishListDetail.getListTitle();
        this.listCreateTime = f.format(wishListDetail.getListCreateTime());
        this.listDueTime = f.format(wishListDetail.getListDueTime());
        this.wishes = wishListDetail.getWishes().stream().map(WishDomain::new).collect(Collectors.toList());
    }


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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

    public List<WishDomain> getWishes() {
        return wishes;
    }

    public void setWishes(List<WishDomain> wishes) {
        this.wishes = wishes;
    }

    public void addWish(WishDomain wish){
        this.wishes.add(wish);
    }
}
