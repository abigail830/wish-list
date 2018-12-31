package com.github.abigail830.wishlist.service;



import com.github.abigail830.wishlist.domainv1.WxToken;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Value("${app.appId}")
    private String appId;

    @Value("${app.appSecret}")
    private String appSecret;

    public WxToken getWxToken() {
        String requestUrl = token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
        String resultData = HttpClientUtil.instance().getData(requestUrl);
        return JsonUtil.toObject(resultData, WxToken.class);

    }
}
