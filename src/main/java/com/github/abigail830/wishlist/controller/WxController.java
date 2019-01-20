package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.domain.WxDecryptResponse;
import com.github.abigail830.wishlist.domain.WxLoginResponse;
import com.github.abigail830.wishlist.domainv1.FormIDMappingDTO;
import com.github.abigail830.wishlist.entity.FormIDMapping;
import com.github.abigail830.wishlist.repository.UserDaoImpl;
import com.github.abigail830.wishlist.service.FormIDMappingService;
import com.github.abigail830.wishlist.service.NotificationService;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import com.github.abigail830.wishlist.util.WXBizDataCrypt;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	@Resource
	private FormIDMappingService formIDMappingService;

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
			logger.error("not able to process ws response " + resultData);
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

	@ApiOperation(value = "Get wechat form id mapping - read only",
			response = List.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/formid/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<FormIDMappingDTO> getFormID(
			@ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openID", required = true) String openID) {
		return formIDMappingService.getFormIDs(openID).stream().map(FormIDMappingDTO::new).collect(Collectors.toList());
	}


	@ApiOperation(value = "Take one wechat form id mapping - the taken up will be deleted ",
			response = FormIDMappingDTO.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/formid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FormIDMappingDTO takeFormID(
			@ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openID", required = true) String openID) {
		FormIDMapping formIDMapping = formIDMappingService.takeFormID(openID);
		if (formIDMapping != null) {
			return new FormIDMappingDTO(formIDMapping);
		} else {
			return null;
		}
	}

	@ApiOperation(value = "Get all form id mapping - read only",
			response = List.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/formids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<FormIDMappingDTO> getAllFormID() {
		return formIDMappingService.getAllFormIDs().stream().map(FormIDMappingDTO::new).collect(Collectors.toList());
	}

	@ApiOperation(value = "Delete form ID",
			response = List.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/formids", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void  deleteFormID(
			@ApiParam(example = "formid") @RequestParam(value = "formID", required = true) String formID) {
		formIDMappingService.deleteFormID(formID);
	}

}
