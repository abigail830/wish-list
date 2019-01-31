package com.github.abigail830.wishlist.service;


import com.github.abigail830.wishlist.dtov1.WxToken;
import com.github.abigail830.wishlist.entity.FormIDMapping;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);


    public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public final static String NOTIFICATION_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    public final static String TEMPLATE_ID = "iOYn0MAVCf5w9bdy5V3aA_jA_-f2xXt9uTRE5_pggt4";

    public final static String MESSAGE_TEMPLATE = "您的【listName】-【wishName】被朋友领取啦！";

    public final static String COUPON_MESSAGE_TEMPLATE = "您有新的卡券,请登陆小程序领取！";

    @Autowired
    FormIDMappingService formIDMappingService;

    @Value("${app.appId}")
    private String appId;

    @Value("${app.appSecret}")
    private String appSecret;

    public WxToken getWxToken() {
        String requestUrl = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        String resultData = HttpClientUtil.instance().getData(requestUrl);
        return JsonUtil.toObject(resultData, WxToken.class);

    }

    public void notifyUser(String userOpenID, List<String> params, String formID) {
        String access_token = getWxToken().getAccess_token();

        try {
            URL url = new URL(NOTIFICATION_URL + access_token);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type", "utf-8");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();

            obj.put("access_token", access_token);
            obj.put("touser", userOpenID);
            obj.put("template_id", TEMPLATE_ID);
            obj.put("form_id", formID);
            obj.put("page", "pages/index_new/index_new");

            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < params.size(); i++) {
                JSONObject dataInfo = new JSONObject();
                dataInfo.put("value", params.get(i));
                jsonObject.put("keyword" + (i + 1), dataInfo);
            }

            obj.put("data", jsonObject);
            out.write(obj.toString().getBytes());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            String reply = sb.toString();
            logger.info("notification result:  " + reply);
            reader.close();

            connection.disconnect();
        } catch (Exception e) {
            logger.error("notification failure. ", e);
        }
    }

    public void notifyUser(User takeupUser, WishList wishList, Wish wish, String formID) {
        List<String> params = Arrays.asList(takeupUser.getNickName(),
                MESSAGE_TEMPLATE.replace("listName", wishList.getTitle()).replace("wishName", wish.getDescription()));
        notifyUser(wishList.getOpenId(), params, formID);

    }


    public void notifyCoupon(User user) {
        FormIDMapping formIDMapping =
                formIDMappingService.takeFormID(user.getOpenId());
        if (formIDMapping != null) {
            logger.info("This form ID {} is used for notify user: {}", formIDMapping, user);
            List<String> params = Arrays.asList(user.getNickName(),
                    COUPON_MESSAGE_TEMPLATE);
            notifyUser(user.getOpenId(), params, formIDMapping.getFormId());
        } else {
            logger.info("There is no form id for this user: {} ", user);
        }

    }
}
