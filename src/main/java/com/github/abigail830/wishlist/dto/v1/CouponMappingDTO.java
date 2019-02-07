package com.github.abigail830.wishlist.dto.v1;


import com.github.abigail830.wishlist.entity.CouponMapping;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponMappingDTO {
    private String openId;
    private String coupon;
    private String couponType;
    private String couponStatus;

    public CouponMappingDTO(CouponMapping couponMapping) {
        this(couponMapping.getOpenId(), couponMapping.getCoupon(), couponMapping.getCouponType(), couponMapping.getCouponStatus());
    }

}
