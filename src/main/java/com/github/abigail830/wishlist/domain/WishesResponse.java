package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishesResponse {


    @JsonProperty("wishes")
    private List<Wish> wishes;

    @ApiModelProperty(value = "愿望数量", example = "5")
    @JsonProperty("wishCount")
    private int wishCount;


    public WishesResponse(int wishCount) {
        this.wishCount = wishCount;
    }

    public WishesResponse(List<Wish> wishes) {
        this.wishes = wishes;
        if(!wishes.isEmpty())
            this.wishCount = wishes.size();
    }
}
