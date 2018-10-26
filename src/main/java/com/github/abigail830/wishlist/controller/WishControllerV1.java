package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domainv1.WishDashboardDTO;
import com.github.abigail830.wishlist.domainv1.WishListDTO;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/wishes")
public class WishControllerV1 {
    private static final Logger logger = LoggerFactory.getLogger(WishController.class);

    @Autowired
    private WishService wishService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Collect wish list detail by wish list id",
            notes = "根据愿望清单的ID获取清单内所有具体内容",
            response = WishListDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListDTO getWishListDetail(
            @ApiParam(example = "2") @RequestParam(value = "wishListId", required = false) String wishListId) {


        if (StringUtils.isNotBlank(wishListId)) {
            logger.info("User getWishListDetail by wishListId {}", wishListId);
            WishListDTO response = new WishListDTO(
                    wishService.getWishListDetailByWishListID(wishListId));
            logger.info("{}", response);
            return response;
        }else{
            logger.warn("wishListId should not be empty when getWishListDetail.");
            throw new IllegalArgumentException("failed to validate");
        }

    }

    @ApiOperation(value = "Collect wish list by id or open_id",
            notes = "根据ID或者openID搜索愿望清单，ID和openID只需要填一个，ID优先查询，只返回清单概述",
            response = WishDashboardDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishDashboardDTO getWishListsByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId) {


        if (StringUtils.isNotBlank(id)){
            logger.info("User query wishService.getWishListByID: {}", id);
            WishListDetail wishListDetail = wishService.getWishListDetailByWishListID(id);
            if(wishListDetail != null){
                WishDashboardDTO response = new WishDashboardDTO(new WishListDTO(wishListDetail) ,
                        wishService.getMyCompletedWishCount(wishListDetail.getListOpenId()),
                        wishService.getFriendsCompletedWishCountByImplementorID(wishListDetail.getListOpenId()));
                logger.info("{}", response);
                return response;
            }
            else{
                logger.info("No wish list found with ID {}", id);
                return new WishDashboardDTO(Collections.emptyList(), 0, 0);
            }

        }

        if (StringUtils.isNotBlank(openId)){
            logger.info("User[{}] query wishService.getWishListByOpenID: {}", openId,openId);
            return getWishListsByUserOpenID(openId);

        }

        logger.info("Query WishList missing param either wish list Id or openId");
        return new WishDashboardDTO(Collections.EMPTY_LIST, 0, 0);
    }

    private WishDashboardDTO getWishListsByUserOpenID(String openId) {
        int myCompletedWishCount = wishService.getMyCompletedWishCount(openId);
        int myFriendCompletedWishCount = wishService.getFriendsCompletedWishCountByImplementorID(openId);
        List<WishListDTO> wishListDTOs = wishService.getWishListByOpenID(openId)
                .stream().map(WishListDTO::new).collect(Collectors.toList());
        if(!wishListDTOs.isEmpty()){
            WishDashboardDTO response =  new WishDashboardDTO(wishListDTOs,
                    myCompletedWishCount,
                    myFriendCompletedWishCount);
            logger.info("{}", response);
            return response;
        }
        else{
            logger.info("No wish list found with openId {}", openId);
            return new WishDashboardDTO(Collections.EMPTY_LIST, 0, 0);
        }
    }


}
