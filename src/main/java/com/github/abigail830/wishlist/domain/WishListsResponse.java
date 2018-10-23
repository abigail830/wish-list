package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("愿望列表搜索返回结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListsResponse {

    @JsonProperty("wishLists")
    private List<BriefWishList> wishLists;

    @ApiModelProperty(value = "是否存在愿望清单", notes = "当搜索无结果时该数量为false", example = "true")
    @JsonProperty("hasWishList")
    private boolean hasWishList = false;

    @ApiModelProperty(value = "我完成的愿望数量", example = "12")
    @JsonProperty("myCompletedWishCount")
    private int myCompletedWishCount;

    @ApiModelProperty(value = "我帮助朋友完成的愿望数量", example = "18")
    @JsonProperty("myFriendsCompletedWishCount")
    private int myFriendsCompletedWishCount;

    @ApiModelProperty(value = "查询状态", example = "VALIDATION_FAIL")
    @JsonProperty("resultCode")
    String resultCode;

    public WishListsResponse(boolean hasWishList, String resultCode) {
        this.hasWishList = hasWishList;
        this.resultCode = resultCode;
    }

    public WishListsResponse(List<BriefWishList> wishLists,
                             int myCompletedWishCount, int myFriendsCompletedWishCount,
                             String resultCode) {
        this.wishLists = wishLists;
        if(!wishLists.isEmpty())
            this.hasWishList = true;
        this.myCompletedWishCount = myCompletedWishCount;
        this.myFriendsCompletedWishCount = myFriendsCompletedWishCount;
        this.resultCode = resultCode;
    }


    public boolean isHasWishList() {
        return hasWishList;
    }

    public int getMyCompletedWishCount() {
        return myCompletedWishCount;
    }

    public int getMyFriendsCompletedWishCount() {
        return myFriendsCompletedWishCount;
    }

    public String getResultCode() {
        return resultCode;
    }

    public List<BriefWishList> getWishLists() {
        return wishLists;
    }

    @Override
    public String toString() {
        return "WishListsResponse{" +
                "wishLists=" + wishLists +
                ", hasWishList=" + hasWishList +
                ", myCompletedWishCount=" + myCompletedWishCount +
                ", myFriendsCompletedWishCount=" + myFriendsCompletedWishCount +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }
}
