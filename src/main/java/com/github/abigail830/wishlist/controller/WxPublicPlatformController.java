package com.github.abigail830.wishlist.controller;



import com.github.abigail830.wishlist.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/platform")
public class WxPublicPlatformController {

    private static final Logger logger = LoggerFactory.getLogger(WxPublicPlatformController.class);

    @Value("${wxservice.appId}")
    private String appId;

    @Value("${wxservice.secret}")
    private String secret;


    @ApiOperation(value = "Wechat public platform login",
            response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAccessToken() {
        String resultData = HttpClientUtil.instance().getData("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appId + "&secret=" + secret);
        return resultData;
    }

}
