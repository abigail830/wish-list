package com.github.abigail830.wishlist.controller;


import com.github.abigail830.wishlist.dto.v1.WxPublicPlatformAuthDTO;
import com.github.abigail830.wishlist.dto.v1.card.APITicketDTO;
import com.github.abigail830.wishlist.dto.v1.card.CardSignatureDTO;
import com.github.abigail830.wishlist.service.WxPublicPlatformService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/platform")
@Slf4j
public class WxPublicPlatformController {

    @Autowired
    private WxPublicPlatformService wxPublicPlatformService;

    @GetMapping("/auth")
    public WxPublicPlatformAuthDTO getAccessToken() {
        log.info("Get access token for wechat public platform");
        return wxPublicPlatformService.getToken();
    }

    @GetMapping("/apiticket")
    public APITicketDTO getAPITicket() {
        log.info("Get api ticket for wechat public platform");
        return wxPublicPlatformService.getAPITicket();
    }


    @GetMapping("/cardsign")
    public CardSignatureDTO getSignCard(
            @ApiParam(example = "pJ9yv0Ru3mgyKeOlmHEdCxFyNOz0") @RequestParam(value = "cardID", required = true) String cardID) {


        if (StringUtils.isNotBlank(cardID)) {
            log.info("Start to get signature for {}", cardID);
            return wxPublicPlatformService.getCardSign(cardID);
        }else{
            log.warn("cardID could not be null or emptyª");
            throw new IllegalArgumentException("failed to validate");
        }

    }
}
