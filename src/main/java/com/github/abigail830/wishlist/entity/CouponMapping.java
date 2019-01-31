package com.github.abigail830.wishlist.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponMapping {
    private String openId;
    private String coupon;
    private String couponType;
    private String couponStatus;

}
