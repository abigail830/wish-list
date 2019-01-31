package com.github.abigail830.wishlist.dtov1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("愿望列表搜索返回结果")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishDashboardDTO {

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

    public WishDashboardDTO(List<WishListDTO> wishLists,
                            int myCompletedWishCount, int myFriendsCompletedWishCount) {
        this.wishLists = wishLists;
        if(!wishLists.isEmpty())
            this.hasWishList = true;
        this.myCompletedWishCount = myCompletedWishCount;
        this.myFriendsCompletedWishCount = myFriendsCompletedWishCount;
    }

    public WishDashboardDTO(WishListDTO wishListDTO,
                            int myCompletedWishCount, int myFriendsCompletedWishCount) {
        this.wishLists = Arrays.asList(wishListDTO);
        if(!wishLists.isEmpty())
            this.hasWishList = true;
        this.myCompletedWishCount = myCompletedWishCount;
        this.myFriendsCompletedWishCount = myFriendsCompletedWishCount;
    }

}
