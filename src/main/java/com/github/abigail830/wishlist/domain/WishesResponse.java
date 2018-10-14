package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("愿望搜索返回结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishesResponse {

    @JsonProperty("wishes")
    private List<Wish> wishes;

    @ApiModelProperty(value = "愿望数量", example = "5")
    @JsonProperty("wishesCount")
    private int wishesCount=0;

    public WishesResponse(int wishesCount) {
        this.wishesCount = wishesCount;
    }

    public WishesResponse(List<Wish> wishes) {
        this.wishes = wishes;
        if(!wishes.isEmpty())
            this.wishesCount = wishes.size();
    }
}
