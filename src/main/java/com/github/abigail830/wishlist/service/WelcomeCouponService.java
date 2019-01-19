package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.entity.CouponMapping;
import com.github.abigail830.wishlist.repository.CouponMappingDAOImpl;
import com.github.abigail830.wishlist.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WelcomeCouponService {

    @Autowired
    private CouponMappingDAOImpl couponMappingDAO;

    private boolean isCouponExists(List<CouponMapping> couponMappings) {
        return couponMappings != null && couponMappings.size() > 0;
    }

    private List<CouponMapping> getWelcomeCoupon(String openID) {
        return couponMappingDAO.queryCoupon(openID, Constants.COUPON_TYPE_WELCOME);
    }

    public boolean hasWelcomeCoupon(String openID) {
        List<CouponMapping> couponMappings = getWelcomeCoupon(openID);
        return isCouponExists(couponMappings);
    }

    public boolean takeUpWelcomeCoupon(String openID) {
        List<CouponMapping> welcomeCoupons = getWelcomeCoupon(openID);
        if (isCouponExists(welcomeCoupons)) {
            CouponMapping couponMapping = welcomeCoupons.get(0);
            couponMapping.setCouponStatus(Constants.COUPON_STATUS_TAKEUP);
            return couponMappingDAO.updateCouponMapping(couponMapping);
        } else {
            return false;
        }
    }

    public boolean deliverWelcomeCoupon(String openID) {
        CouponMapping welcomeCoupon = new CouponMapping(openID, "welcomeCoupon", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW);
        return couponMappingDAO.createCouponMapping(welcomeCoupon);
    }

    public void setCouponMappingDAO(CouponMappingDAOImpl couponMappingDAO) {
        this.couponMappingDAO = couponMappingDAO;
    }
}
