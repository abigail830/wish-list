package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.dtov1.WxPublicPlatformAuthDTO;
import com.github.abigail830.wishlist.dtov1.card.APITicketDTO;
import com.github.abigail830.wishlist.dtov1.card.CardSignatureDTO;
import com.github.abigail830.wishlist.util.HttpClientUtil;
import com.github.abigail830.wishlist.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.UUID;


@Service
public class WxPublicPlatformService {

    private static final Logger logger = LoggerFactory.getLogger(WxPublicPlatformService.class);

    private static final long tolerence = 60;

    @Value("${wxservice.appId}")
    private String appId;

    @Value("${wxservice.secret}")
    private String secret;

    private WxPublicPlatformAuthDTO wxPublicPlatformAuthDTO;

    private APITicketDTO apiTicketDTO;

    public boolean isValidToken(WxPublicPlatformAuthDTO wxPublicPlatformAuthDTO) {
        long currentSecond = System.currentTimeMillis()/1000;
        logger.info("Auth Token current second: {} , valid second {}", currentSecond,wxPublicPlatformAuthDTO.getValidInSecond() - tolerence );
        return currentSecond < (wxPublicPlatformAuthDTO.getValidInSecond() - tolerence);
    }

    public WxPublicPlatformAuthDTO getToken() {
        if (wxPublicPlatformAuthDTO != null && isValidToken(wxPublicPlatformAuthDTO)) {
            return wxPublicPlatformAuthDTO;
        } else {
            wxPublicPlatformAuthDTO = getAccessTokenFromWx();
            return wxPublicPlatformAuthDTO;
        }
    }

    private WxPublicPlatformAuthDTO getAccessTokenFromWx() {

        logger.info("Handle wechat public platform auth");
        String resultData = HttpClientUtil.instance().getData("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appId + "&secret=" + secret);
        logger.info("Wechat public platform auth result: {}", resultData);
        WxPublicPlatformAuthDTO wxPublicPlatformAuthDTO = JsonUtil.toObject(resultData, WxPublicPlatformAuthDTO.class);
        long currentSecond = System.currentTimeMillis()/1000;
        wxPublicPlatformAuthDTO.setValidInSecond(currentSecond + Long.valueOf(wxPublicPlatformAuthDTO.getExpires_in()));
        return wxPublicPlatformAuthDTO;
    }

    public boolean isValidToken(APITicketDTO apiTicketDTO) {
        long currentSecond = System.currentTimeMillis()/1000;
        logger.info("API ticket - current second: {} , valid second {}", currentSecond, apiTicketDTO.getValidInSecond() - tolerence);
        return currentSecond < (apiTicketDTO.getValidInSecond() - tolerence);
    }

    public APITicketDTO getAPITicket() {
        if (apiTicketDTO != null && isValidToken(apiTicketDTO)) {
            return apiTicketDTO;
        } else {
            apiTicketDTO = getAPITicketFromWx(getToken());
            return apiTicketDTO;
        }
    }

    private APITicketDTO getAPITicketFromWx(WxPublicPlatformAuthDTO wxPublicPlatformAuthDTO) {
        logger.info("Handle get api ticket with auth {} ", wxPublicPlatformAuthDTO);

        String resultData = HttpClientUtil.instance().getData("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                + wxPublicPlatformAuthDTO.getAccess_token() + "&type=wx_card");

        logger.info("Wechat api ticket result: {} ", resultData);
        APITicketDTO apiTicketDTO = JsonUtil.toObject(resultData, APITicketDTO.class);
        long currentSecond = System.currentTimeMillis()/1000;
        apiTicketDTO.setValidInSecond(currentSecond + apiTicketDTO.getExpires_in());
        return apiTicketDTO;
    }

    public CardSignatureDTO getCardSign(String cardID) {
        APITicketDTO apiTicket = getAPITicket();
        CardSignatureDTO sign = sign2(apiTicket, cardID);
        logger.info("Completed sign {} for card id {} ", sign, cardID);
        return sign;
    }

    private CardSignatureDTO sign2(APITicketDTO apiTicket, String cardID) {
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();

        String param[] = new String[4];

        param[0] = nonceStr;
        param[1] = timestamp;
        param[2] = apiTicket.getTicket();
        param[3] = cardID;

        Arrays.sort(param);

        StringBuilder stringBuilder = new StringBuilder();
        for(String b : param){
            stringBuilder.append(b);
        }
        String stringToBeSign = stringBuilder.toString();
        String signature;

        try{
            logger.info("Start to sign the sorted string {}", stringToBeSign);
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(stringToBeSign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException ex){
            logger.error("Failed to sign ticket. ", ex);
            throw new RuntimeException("Failed to sign ticket. ", ex);
        }catch (UnsupportedEncodingException ex){
            logger.error("Failed to sign ticket. ", ex);
            throw new RuntimeException("Failed to sign ticket. ", ex);

        }

        return new CardSignatureDTO(nonceStr,cardID,signature,timestamp);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
