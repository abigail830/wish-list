package com.github.abigail830.wishlist.domainv1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WishListTimeline {
    private List<WishListTimelineEntry> wishListTimelineEntryList;


    public WishListTimeline(List<WishListTimelineEntry> wishListTimelineEntryList) {
        this.wishListTimelineEntryList = wishListTimelineEntryList;
    }

    public List<WishListTimelineEntry> getWishListTimelineEntryList() {
        return wishListTimelineEntryList;
    }
}
