package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.domainv1.WxPublicPlatformAuthDTO;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import com.google.common.collect.ImmutableMap;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class WxPublicPlatformService {

    private static final Logger logger = LoggerFactory.getLogger(WxPublicPlatformService.class);

    @Value("${wxservice.appId}")
    private String appId;

    @Value("${wxservice.secret}")
    private String secret;

    public WxPublicPlatformAuthDTO getAccessToken() {
        logger.info("Handle wechat public platform auth");
        String resultData = HttpClientUtil.instance().getData("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appId + "&secret=" + secret);
        logger.info("Wechat public platform auth result: {}", resultData);
        return JsonUtil.toObject(resultData, WxPublicPlatformAuthDTO.class);
    }

    public String registerCoupon() {
        logger.info("Handle wechat public platform create coupon");
        WxPublicPlatformAuthDTO accessToken = getAccessToken();
        logger.info("Got access token {}", accessToken);
        if (StringUtils.isNotBlank(accessToken.getAccess_token())) {
            try {
                Response response = HttpClientUtil.instance().postBody(
                        "https://api.weixin.qq.com/card/qrcode/create?access_token=" + accessToken.getAccess_token(),
                        JsonUtil.toJson(Constants.verificationCoupon),
                        ImmutableMap.of("Content-Type", "application/json"));
                logger.info("Got respond from wechat public platform {}", response);
                logger.info("Got respond body from wechat public platform {}", response.body().string());
                return response.body().string();
            } catch (Exception ex) {
                logger.error("Failed to create coupon. ", ex);
                return "";
            }

        } else {
            logger.error("Failed to get access token for coupon");
            return "";
        }
    }

}
