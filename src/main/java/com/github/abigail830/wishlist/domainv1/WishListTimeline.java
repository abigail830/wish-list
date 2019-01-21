package com.github.abigail830.wishlist.domainv1;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class WishListTimeline {
    private List<WishListTimelineEntry> wishListTimelineEntryList;

    @JsonProperty("wishLists")
    private List<WishListDTO> wishLists;

    @ApiModelProperty(value = "是否存在愿望清单", notes = "当搜索无结果时该数量为false", example = "true")
    @JsonProperty("hasWishList")
    private boolean hasWishList = false;

    @ApiModelProperty(value = "我完成的愿望数量", example = "12")
    @JsonProperty("myCompletedWishCount")
    private int myCompletedWishCount;

    @ApiModelProperty(value = "我帮助朋友完成的愿望数量", example = "18")
    @JsonProperty("myFriendsCompletedWishCount")
    private int myFriendsCompletedWishCount;

    @JsonProperty("outstandingCoupon")
    private CouponMappingDTO outstandingCoupon;


    public WishListTimeline(List<WishListTimelineEntry> wishListTimelineEntryList,
                            List<WishListDTO> wishLists,
                            int myCompletedWishCount,
                            int myFriendsCompletedWishCount,
                            CouponMappingDTO outstandingCoupon) {
        this.wishListTimelineEntryList = wishListTimelineEntryList;
        this.wishLists = wishLists;
        this.hasWishList = !wishLists.isEmpty();
        this.myCompletedWishCount = myCompletedWishCount;
        this.myFriendsCompletedWishCount = myFriendsCompletedWishCount;
        this.outstandingCoupon = outstandingCoupon;
    }



    public List<WishListTimelineEntry> getWishListTimelineEntryList() {
        return wishListTimelineEntryList;
    }

    public List<WishListDTO> getWishLists() {
        return wishLists;
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

    public CouponMappingDTO getOutstandingCoupon() {
        return outstandingCoupon;
    }

    public void setOutstandingCoupon(CouponMappingDTO outstandingCoupon) {
        this.outstandingCoupon = outstandingCoupon;
    }
}
