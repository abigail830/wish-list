package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.abigail830.wishlist.entity.Wish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("愿望搜索返回结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishesResponse {

    @JsonProperty("wishes")
    private List<Wish> wishes;

    @ApiModelProperty(value = "是否存在愿望", example = "true")
    @JsonProperty("hasWish")
    private boolean hasWish=false;

    public WishesResponse(boolean hasWish) {
        this.hasWish = hasWish;
    }

    public WishesResponse(List<Wish> wishes) {
        this.wishes = wishes;
        if(!wishes.isEmpty())
            this.hasWish = true;
    }

    @Override
    public String toString() {
        return "WishesResponse{" +
                "wishes=" + wishes +
                ", hasWish=" + hasWish +
                '}';
    }
}
