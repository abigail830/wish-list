package com.github.abigail830.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@ApiModel("愿望搜索返回结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishesResponse {

    @JsonProperty("wishes")
    private List<WishDomain> wishes;

    @ApiModelProperty(value = "是否存在愿望", example = "true")
    @JsonProperty("hasWish")
    private boolean hasWish=false;

    @ApiModelProperty(value = "查询状态", example = "VALIDATION_FAIL")
    @JsonProperty("resultCode")
    String resultCode;

    public WishesResponse(boolean hasWish, String resultCode) {
        this.hasWish = hasWish;
        this.resultCode = resultCode;
    }

    public WishesResponse(List<WishDomain> wishes,String resultCode) {
        this.resultCode = resultCode;
        this.wishes = wishes;
        if(!wishes.isEmpty())
            this.hasWish = true;
    }

}
