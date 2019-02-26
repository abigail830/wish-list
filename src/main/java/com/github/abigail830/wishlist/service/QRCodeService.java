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
import sun.misc.BASE64Decoder;

import java.io.IOException;

@Service
@Slf4j
public class QRCodeService {

    private final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private final static String QR_REQUEST_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    @Value("${app.appId}")
    private String appId;
    @Value("${app.appSecret}")
    private String appSecret;

    private WxToken getWxToken() {
        String requestUrl = TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
        String resultData = HttpClientUtil.instance().getData(requestUrl);
        return JsonUtil.toObject(resultData, WxToken.class);
    }

    public byte[] generate(String page, String scene, String width) {
        String access_token = getWxToken().getAccess_token();
        String url = QR_REQUEST_URL + access_token;

        final WxQrCodeRequestDTO wxQrCodeRequestDTO = WxQrCodeRequestDTO.builder()
                .page(page).scene(scene).width(width)
                .is_hyaline(Boolean.TRUE).build();

        try {
            final Response response = HttpClientUtil.instance().postBody(url,
                    JsonUtil.toJson(wxQrCodeRequestDTO), null);

            if (response.isSuccessful()) {
                final String result = response.body().string();
                if (!isJsonOfError(result)) {
                    final String base64String = result;
                    log.debug(base64String);
                    return decoder.decodeBuffer(base64String);
                }
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
