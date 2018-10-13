package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.domain.WxDecryptResponse;
import com.github.abigail830.wishlist.domain.WxLoginResponse;
import com.github.abigail830.wishlist.repository.UserDaoImpl;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import com.github.abigail830.wishlist.util.WXBizDataCrypt;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 小程序相关登录和解密接口
 */
@RestController
@RequestMapping("/common")
public class WxController {

	private static final Logger logger = LoggerFactory.getLogger(WxController.class);

	@Value("${app.appId}")
	private String appId;

	@Value("${app.appSecret}")
	private String appSecret;

    @Resource
    private UserService userService;

	@ApiOperation(value = "Handle wechat login",
			notes = "小程序用户登陆处理",
			response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@GetMapping(value = "/wxLogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String login(HttpServletRequest request) {
		String code = request.getHeader("X-WX-Code");
		String resultData =  HttpClientUtil.instance().getData(
				"https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
						"&secret=" + appSecret +
						"&grant_type=authorization_code" +
						"&js_code=" + code);

        WxLoginResponse wxLoginResponse = JsonUtil.toObject(resultData, WxLoginResponse.class);
        if(wxLoginResponse.getOpenid() != null){
            userService.createUser(new UserInfo(wxLoginResponse.getOpenid()));
        }else {
            logger.error("Fail to get openID from wechat API");
        }

		return resultData;
	}

	@ApiOperation(value = "Handle wechat info decryption",
			notes = "小程序用户消息解密",
			response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@GetMapping(value = "/decrypt", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String decrypt(HttpServletRequest request) {
		String skey = request.getHeader("skey");
		String encryptedData = request.getHeader("encryptedData");
		String iv = request.getHeader("iv");
		WXBizDataCrypt biz = new WXBizDataCrypt(appId, skey);

		String resultDate = biz.decryptData(encryptedData, iv);
        WxDecryptResponse wxDecryptResponse = JsonUtil.toObject(resultDate, WxDecryptResponse.class);
		logger.info("wxDecryptResponse: {}", wxDecryptResponse);
		if(wxDecryptResponse.getErrorCode() == null){
            userService.updateUser(wxDecryptResponse.getUserInfo());
            logger.info("Updated user info for user {}", wxDecryptResponse.getUserInfo().getOpenId());
        }else{
		    logger.error("Error occur during decrypt wechat message with error code: {}", wxDecryptResponse.getErrorCode());
        }

		return resultDate;
	}
}