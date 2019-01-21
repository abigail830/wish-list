package com.github.abigail830.wishlist.controller;



import com.github.abigail830.wishlist.domainv1.WxPublicPlatformAuthDTO;
import com.github.abigail830.wishlist.service.WxPublicPlatformService;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WxPublicPlatformService wxPublicPlatformService;

    @ApiOperation(value = "Wechat public platform login",
            response = WxPublicPlatformAuthDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WxPublicPlatformAuthDTO getAccessToken() {
        logger.info("Get access token for wechat public platform");
        return wxPublicPlatformService.getAccessToken();
    }

    @ApiOperation(value = "Create conpon from wechat public platform",
            response = WxPublicPlatformAuthDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @GetMapping(value = "/coupon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createCoupon() {
        logger.info("Create coupon from wechat public platform");
        return wxPublicPlatformService.registerCoupon();
    }

}
