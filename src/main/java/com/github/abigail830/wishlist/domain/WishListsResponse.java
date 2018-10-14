package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.WishList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishListsResponse {


    @JsonProperty("wishLists")
    private List<WishList> wishLists;

    private int wishListCount = 0;

    public WishListsResponse(int wishListCount) {
        this.wishListCount = wishListCount;
    }

    public WishListsResponse(List<WishList> wishLists) {
        this.wishLists = wishLists;
        if(!wishLists.isEmpty())
            this.wishListCount = wishLists.size();
    }
}
