package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.WishListDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("详细版愿望列表搜索返回")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListDetailResponse {

    @JsonProperty("wishListDetail")
    WishListDetail wishListDetail;

    @ApiModelProperty(value = "查询状态", example = "VALIDATION_FAIL")
    @JsonProperty("resultCode")
    String resultCode;

    public WishListDetailResponse(String resultCode) {
        this.resultCode = resultCode;
    }

    public WishListDetailResponse(WishListDetail wishListDetail) {
        this.wishListDetail = wishListDetail;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public WishListDetail getWishListDetail() {
        return wishListDetail;
    }

    public void setWishListDetail(WishListDetail wishListDetail) {
        this.wishListDetail = wishListDetail;
    }

    @Override
    public String toString() {
        return "WishListDetailResponse{" +
                "wishListDetail=" + wishListDetail +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }
}
