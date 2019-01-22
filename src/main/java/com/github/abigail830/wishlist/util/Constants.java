package com.github.abigail830.wishlist.util;

import com.github.abigail830.wishlist.domainv1.card.*;

public class Constants {

    public static final String WISH_STATUS_NEW = "NEW";
    public static final String WISH_STATUS_TAKEUP = "TAKEUP";
    public static final String WISH_STATUS_DONE = "DONE";

    public static final String EVENT_TYPE_LOGIN = "USER_LOGIN";
    public static final String EVENT_TYPE_AUTHORIZE = "USER_AUTHORIZE";
    public static final String EVENT_TYPE_ADD_WISH_LIST = "ADD_WISH_LIST";
    public static final String EVENT_TYPE_UPDATE_WISH_LIST = "UPDATE_WISH_LIST";
    public static final String EVENT_TYPE_REMOVE_WISH_LIST = "REMOVE_WISH_LIST";
    public static final String EVENT_TYPE_ADD_WISH = "ADD_OWN_WISH";
    public static final String EVENT_TYPE_UPDATE_WISH = "UPDATE_OWN_WISH";
    public static final String EVENT_TYPE_REMOVE_WISH = "REMOVE_OWN_WISH";
    public static final String EVENT_TYPE_TAKEUP_WISH = "TAKEUP_WISH";
    public static final String EVENT_TYPE_GIVEUP_WISH = "GIVEUP_WISH";
    public static final String EVENT_TYPE_DONE_WISH = "DONE_WISH";

    public static final String HTTP_STATUS_SUCCESS = "SUCCESS";
    public static final String HTTP_STATUS_VALIDATION_FAIL = "VALIDATION_FAIL";

    public static final String COUPON_TYPE_WELCOME = "WELCOME_COUPON";

    public static final String COUPON_STATUS_NEW = "NEW";
    public static final String COUPON_STATUS_TAKEUP = "TAKEUP";

    public static final String GENERAL_COUPON = "GENERAL_COUPON";

    public static final CouponDTO verificationCoupon =
            new CouponDTO(
                    new CardDTO(
                            GENERAL_COUPON,
                            new GeneralCouponInfoDTO(
                                    new BaseInfoDTO(
                                            "http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZJkmG8xXhiaHqkKSVMMWeN3hLut7X7hicFNjakmxibMLGWpXrEXB33367o7zHN0CwngnQY7zb7g/0",
                                            "CODE_TYPE_QRCODE",
                                            "友爱契约",
                                            "友爱契约卡券",
                                            "Color090",
                                            "请出示二维码",
                                            "不可与其他优惠同享",
                                            new SkuDTO(10),
                                            new DateInfoDTO(
                                                    "DATE_TYPE_FIX_TIME_RANGE",
                                                    1472724261,
                                                    1572724261,
                                                    0)
                                    ),
                                    null,
                                    "这是测试友爱契约卡券1"
                            )));
}
