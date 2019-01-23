package com.github.abigail830.wishlist.controller;



import com.github.abigail830.wishlist.domainv1.WishListDTO;
import com.github.abigail830.wishlist.domainv1.WxPublicPlatformAuthDTO;
import com.github.abigail830.wishlist.domainv1.card.APITicketDTO;
import com.github.abigail830.wishlist.domainv1.card.CardSignatureDTO;
import com.github.abigail830.wishlist.service.WxPublicPlatformService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return wxPublicPlatformService.getToken();
    }

    @ApiOperation(value = "Wechat public platform api ticket",
            response = APITicketDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @GetMapping(value = "/apiticket", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APITicketDTO getAPITicket() {
        logger.info("Get api ticket for wechat public platform");
        return wxPublicPlatformService.getAPITicket();
    }


    @ApiOperation(value = "Wechat public platform card signature ",
            response = CardSignatureDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/cardsign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CardSignatureDTO getSignCard(
            @ApiParam(example = "pJ9yv0Ru3mgyKeOlmHEdCxFyNOz0") @RequestParam(value = "cardID", required = true) String cardID) {


        if (StringUtils.isNotBlank(cardID)) {
            logger.info("Start to get signature for {}", cardID);
            return wxPublicPlatformService.getCardSign(cardID);
        }else{
            logger.warn("cardID could not be null or emptyª");
            throw new IllegalArgumentException("failed to validate");
        }

    }
}
