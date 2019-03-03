package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.dto.v1.WxQrCodeRequestDTO;
import com.github.abigail830.wishlist.dto.v1.WxToken;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_ENCODING;

@Service
@Slf4j
public class QRCodeService {

    private final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

//    private final static String QR_REQUEST_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    private final static String QR_REQUEST_URL_LIMIT = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";

    String forwardPage = "/pages/shareWish/shareWish?wishListId=LIST_ID&wishimageId=IMAGE_ID&wishimageUrl=URL&nickName=NICKNAME";
    
    @Value("${app.appId}")
    private String appId;
    @Value("${app.appSecret}")
    private String appSecret;

    private WxToken getWxToken() {
        String requestUrl = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        String resultData = HttpClientUtil.instance().getData(requestUrl);
        return JsonUtil.toObject(resultData, WxToken.class);
    }

    public byte[] generate(String wishListID, String wishimageId, String imageUrl, String nickName, String width) {
        String access_token = getWxToken().getAccess_token();
        String url = QR_REQUEST_URL_LIMIT + access_token;

        String targetUrl = forwardPage.replace("LIST_ID", wishListID)
                .replace("IMAGE_ID", wishimageId)
                .replace("URL", imageUrl)
                .replace("NICKNAME", nickName);

        final WxQrCodeRequestDTO wxQrCodeRequestDTO = WxQrCodeRequestDTO.builder()
                .path(targetUrl)
                .width(width)
                .is_hyaline(Boolean.TRUE).build();


        Map<String, String> headers = new HashMap<>();
//        headers.put(CONTENT_TYPE, "application/json");
        headers.put(CONTENT_ENCODING, null);

        try {
            final Response response = HttpClientUtil.instance().postBody(url,
                    JsonUtil.toJson(wxQrCodeRequestDTO), headers);

            if (response.isSuccessful()) {
                return response.body().bytes();

            } else {
                log.warn("fail to get QR code from Wx interface with response {}", response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("get QR Code failure. ", e);
        }

        return new byte[0];
    }

    private Boolean isJsonOfError(String result) {
        try {
            new JSONObject(result);
            log.error(result);
            return Boolean.TRUE;

        } catch (JSONException e) {
            return Boolean.FALSE;
        }
    }

}
