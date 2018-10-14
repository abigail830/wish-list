package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.WishList;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListsResponse {


    @JsonProperty("wishLists")
    private List<WishList> wishLists;

    @ApiModelProperty(value = "愿望清单数量", example = "5")
    @JsonProperty("wishListCount")
    private int wishListCount;

    @ApiModelProperty(value = "我完成的愿望数量", example = "12")
    @JsonProperty("myCompletedWishCount")
    private int myCompletedWishCount;

    @ApiModelProperty(value = "我帮助朋友完成的愿望数量", example = "18")
    @JsonProperty("myFriendsCompletedWishCount")
    private int myFriendsCompletedWishCount;

    public WishListsResponse(int wishListCount) {
        this.wishListCount = wishListCount;
    }

    public WishListsResponse(List<WishList> wishLists, int myCompletedWishCount, int myFriendsCompletedWishCount) {
        this.wishLists = wishLists;
        if(!wishLists.isEmpty())
            this.wishListCount = wishLists.size();
        this.myCompletedWishCount = myCompletedWishCount;
        this.myFriendsCompletedWishCount = myFriendsCompletedWishCount;
    }
}
