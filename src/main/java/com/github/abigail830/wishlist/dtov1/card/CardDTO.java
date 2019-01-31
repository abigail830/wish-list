package com.github.abigail830.wishlist.dtov1.card;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String card_type;

    private GeneralCouponInfoDTO general_coupon;

}
