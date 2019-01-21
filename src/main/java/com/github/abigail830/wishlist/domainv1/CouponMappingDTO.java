package com.github.abigail830.wishlist.domainv1;


import com.github.abigail830.wishlist.entity.CouponMapping;

public class CouponMappingDTO {
    private String openId;
    private String coupon;
    private String couponType;
    private String couponStatus;

    public CouponMappingDTO() {

    }

    public CouponMappingDTO(CouponMapping couponMapping) {
        this(couponMapping.getOpenId(), couponMapping.getCoupon(), couponMapping.getCouponType(), couponMapping.getCouponStatus());
    }

    public CouponMappingDTO(String openId, String coupon, String couponType, String couponStatus) {
        this.openId = openId;
        this.coupon = coupon;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }
}
