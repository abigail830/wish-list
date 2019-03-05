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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class QRCodeService {

    private final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

//    private final static String QR_REQUEST_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    private final static String QR_REQUEST_URL_LIMIT = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";

    
    @Value("${app.appId}")
    private String appId;
    @Value("${app.appSecret}")
    private String appSecret;

    private WxToken getWxToken() {
        String requestUrl = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        String resultData = HttpClientUtil.instance().getData(requestUrl);
        return JsonUtil.toObject(resultData, WxToken.class);
    }

    public static String decode(String url) {
        try {
            String prevURL = "";
            String decodeURL = url;
            while (!prevURL.equals(decodeURL)) {
                prevURL = decodeURL;
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }

    public byte[] generate(String page) {
        String access_token = getWxToken().getAccess_token();
        String url = QR_REQUEST_URL_LIMIT + access_token;

        final String decodeUrl = decode(page);

        final WxQrCodeRequestDTO wxQrCodeRequestDTO = WxQrCodeRequestDTO.builder()
                .path(decodeUrl)
                .width("250")
                .is_hyaline(Boolean.TRUE).build();

        try {

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=utf-8");
//            headers.put(CONTENT_ENCODING, null);

            final Response response = HttpClientUtil.instance().postBody(url,
                    JsonUtil.toJsonWithoutEncode(wxQrCodeRequestDTO), headers);

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
