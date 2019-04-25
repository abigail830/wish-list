package com.github.abigail830.wishlist.dto.v1;

import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO implements Comparable{
    @ApiModelProperty(value = "用户openID",  example = "troEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String listOpenId;

    @ApiModelProperty(value = "愿望清单的ID",  example = "2")
    private Integer listId;

    @ApiModelProperty(value = "愿望清单标题", example = "新年愿望清单")
    private String listDescription;

    @ApiModelProperty(value = "愿望清单描述", example = "我想要大家送我很多很多礼物")
    private String listDescription2;

    @ApiModelProperty(value = "愿望清单创建日期",  example = "2018-09-01")
    private String listCreateTime;

    @ApiModelProperty(value = "愿望清单目标兑现时间",  example = "2018-10-10")
    private String listDueTime;

    @ApiModelProperty(value = "日期",  example = "01")
    private String dateInMonth;

    @ApiModelProperty(value = "月份",  example = "2018-10")
    private String yearAndMonth;

    @ApiModelProperty(value = "进度",  example = "50")
    private Integer progress;

    @ApiModelProperty(value = "认领限制",  example = "1")
    private Integer implementorsLimit;

    @ApiModelProperty(value = "地址", example = "")
    private String address;

    @ApiModelProperty(value = "是否本人见证", example = "true")
    private Boolean isSelfWitness;


    @ApiModelProperty(value = "愿望列表")
    List<WishDTO> wishes = new ArrayList<>();

    public void addWish(WishDTO wishDTO) {
        wishes.add(wishDTO);
    }

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter;
        }
    };

    public WishListDTO(WishListDetail wishListDetail) {
        this.listOpenId = wishListDetail.getListOpenId();
        this.listId = wishListDetail.getListId();
        this.listDescription = wishListDetail.getListTitle();
        this.listDescription2 = wishListDetail.getListBrief();
        this.listCreateTime = dateFormatter.get().format(wishListDetail.getListCreateTime());
        this.listDueTime = dateFormatter.get().format(wishListDetail.getListDueTime());
        if (wishListDetail.getWishes() != null && wishListDetail.getWishes().size() > 0) {
            this.wishes = wishListDetail.getWishes().stream().filter(item -> item.getDescription() != null).map(WishDTO::new).collect(Collectors.toList());
        }
        if (wishListDetail.getImplementorsLimit() != null) {
            this.implementorsLimit = wishListDetail.getImplementorsLimit();
        } else {
            this.implementorsLimit = 1;
        }
        this.dateInMonth = this.listDueTime.substring(8, 10);
        this.yearAndMonth = this.listDueTime.substring(0, 7);
        this.address = wishListDetail.getAddress();
        this.isSelfWitness = wishListDetail.getIsSelfWitness();
    }


    public WishListDTO(WishList wishList) {
        this.listOpenId = wishList.getOpenId();
        this.listId = wishList.getId();
        this.listDescription = wishList.getTitle();
        this.listDescription2 = wishList.getBrief();
        this.listCreateTime = dateFormatter.get().format(wishList.getCreateTime());
        this.listDueTime = dateFormatter.get().format(wishList.getDueTime());
        this.dateInMonth = this.listDueTime.substring(8, 10);
        this.yearAndMonth = this.listDueTime.substring(0, 7);
        if (wishList.getImplementorsLimit() != null) {
            this.implementorsLimit = wishList.getImplementorsLimit();
        } else {
            this.implementorsLimit = 1;
        }
        this.address = wishList.getAddress();
        this.isSelfWitness = wishList.getIsSelfWitness();
    }


    @Override
    public int compareTo(Object targetDTO) {
        WishListDTO wishListDTOTarget = (WishListDTO) targetDTO;
        if (wishListDTOTarget.getDateInMonth() == null || this.getDateInMonth() == null) {
            return 0;
        } else {
            Integer targetDateInMonth = Integer.valueOf(wishListDTOTarget.getDateInMonth());
            Integer dateInMonth = Integer.valueOf(this.getDateInMonth());
            if (targetDateInMonth.equals(dateInMonth)) {
                return -1;
            } else {
                return targetDateInMonth.compareTo(dateInMonth);
            }
        }

    }
}
