package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.dto.v1.CouponMappingDTO;
import com.github.abigail830.wishlist.entity.CouponMapping;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.repository.CouponMappingDAOImpl;
import com.github.abigail830.wishlist.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WelcomeCouponService {

    private static final String CARD_ID = "pJ9yv0Y3nLo-3oeiI21nG44O93hM";

    public static final String WELCOME_WISH_NAME = "甜蜜发喜";

    @Autowired
    private CouponMappingDAOImpl couponMappingDAO;

    @Autowired
    private NotificationService notificationService;

    private boolean isCouponExists(List<CouponMapping> couponMappings) {
        return couponMappings != null && couponMappings.size() > 0;
    }

    private boolean isCouponOutstanding (List<CouponMapping> couponMappings) {
        return isCouponExists(couponMappings)
                && couponMappings.stream().anyMatch(item -> Constants.COUPON_STATUS_NEW.equals(item.getCouponStatus()));
    }

    public List<CouponMapping> getWelcomeCoupon(String openID) {
        return couponMappingDAO.queryCoupon(openID, Constants.COUPON_TYPE_WELCOME);
    }

    public List<CouponMapping> getWelcomeCoupon() {
        return couponMappingDAO.queryCouponByCouponType(Constants.COUPON_TYPE_WELCOME);
    }

    public boolean hasWelcomeCoupon(String openID) {
        List<CouponMapping> couponMappings = getWelcomeCoupon(openID);
        return isCouponExists(couponMappings);
    }

    public boolean hasWelcomeCouponOutstanding(String openID) {
        List<CouponMapping> couponMappings = getWelcomeCoupon(openID);
        return isCouponOutstanding(couponMappings);
    }

    public CouponMappingDTO getOutstandingWelcomeCoupon(String openID) {
        List<CouponMapping> couponMappings = getWelcomeCoupon(openID);
        if (isCouponExists(couponMappings)) {
            List<CouponMapping> coupons = couponMappings.stream().filter(item -> Constants.COUPON_STATUS_NEW.equals(item.getCouponStatus())).collect(Collectors.toList());
            if (coupons.size() > 0) {
                return new CouponMappingDTO(coupons.get(0));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public CouponMapping takeUpWelcomeCoupon(String openID) {
        List<CouponMapping> welcomeCoupons = getWelcomeCoupon(openID);
        if (isCouponExists(welcomeCoupons)) {
            CouponMapping couponMapping = welcomeCoupons.get(0);
            couponMapping.setCouponStatus(Constants.COUPON_STATUS_TAKEUP);
            boolean result = couponMappingDAO.updateCouponMapping(couponMapping);
            log.info("Update the coupon {} result: {}", couponMapping, result);
            return couponMapping;
        } else {
            return null;
        }
    }

    public boolean doesContainsWish(List<Wish> wishList, String wishName) {
        return wishList.stream().anyMatch(item -> wishName.equals(item.getDescription()));
    }

    public void deliverWelcomeCoupon(List<Wish> wishLists) {
        if (doesContainsWish(wishLists, WELCOME_WISH_NAME)) {
            User owner = wishLists.get(0).getCreator();
            String openId = owner.getOpenId();
            if (!hasWelcomeCoupon(openId)) {
                CouponMapping welcomeCoupon = new CouponMapping(openId, CARD_ID, Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW);
                couponMappingDAO.createCouponMapping(welcomeCoupon);
                notificationService.notifyCoupon(owner);
            } else {
                log.info("The open id {} already has coupon. ", openId);
            }
        } else {
            log.info("The wish list doesn't contains the requires wish");
        }

    }

    public void deliverWelcomeCoupon(String openID) {
        if (!hasWelcomeCoupon(openID)) {
            CouponMapping welcomeCoupon = new CouponMapping(openID, CARD_ID, Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW);
            couponMappingDAO.createCouponMapping(welcomeCoupon);
            User testUser = new User();
            testUser.setOpenId(openID);
            testUser.setNickName("TEST_USER");
            notificationService.notifyCoupon(testUser);
        } else {
            log.info("The open id {} already has coupon. ", openID);
        }

    }

    public void deleteWelcomeCoupon(String openID) {
        couponMappingDAO.deleteCoupon(openID, Constants.COUPON_TYPE_WELCOME);
    }


    public void setCouponMappingDAO(CouponMappingDAOImpl couponMappingDAO) {
        this.couponMappingDAO = couponMappingDAO;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
