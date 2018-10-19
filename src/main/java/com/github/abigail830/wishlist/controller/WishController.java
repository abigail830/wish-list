package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.*;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wish")
public class WishController {

    private static final Logger logger = LoggerFactory.getLogger(WishController.class);

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private WishService wishService;

    @Resource
    private UserService userService;

    @ApiOperation(value = "Collect wish list by id or open_id",
            notes = "根据ID或者openID搜索愿望清单，ID和openID只需要填一个，ID优先查询",
            response = WishListsResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListsResponse getWishListsByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId) {


        if (StringUtils.isNotBlank(id)){
            logger.info("User query wishService.getWishListByID: {}", id);
            List<WishListDomain> wishListDomains = wishService.getWishListByID(id)
                    .stream().map(WishListDomain::new).collect(Collectors.toList());
            if(!wishListDomains.isEmpty()){
                WishListsResponse response =  new WishListsResponse(wishListDomains,
                        wishService.getMyCompletedWishCount(wishListDomains.get(0).getOpenId()),
                        wishService.getFriendsCompletedWishCountByImplementorID(wishListDomains.get(0).getOpenId()));
                logger.info("{}", response);
                return response;
            }
            else{
                logger.info("No wish list found with ID {}", id);
                return new WishListsResponse(false);
            }

        }

        if (StringUtils.isNotBlank(openId)){
            logger.info("User[{}] query wishService.getWishListByOpenID: {}", openId,openId);
            int myCompletedWishCount = wishService.getMyCompletedWishCount(openId);
            int myFriendCompletedWishCount = wishService.getFriendsCompletedWishCountByImplementorID(openId);
            List<WishListDomain> wishListDomains = wishService.getWishListByOpenID(openId)
                    .stream().map(WishListDomain::new).collect(Collectors.toList());
            if(!wishListDomains.isEmpty()){
                WishListsResponse response =  new WishListsResponse(wishListDomains,
                        myCompletedWishCount,
                        myFriendCompletedWishCount
                        );
                logger.info("{}", response);
                return response;
            }
            else{
                logger.info("No wish list found with openId {}", openId);
                return new WishListsResponse(false);
            }

        }

        logger.info("Query WishList missing param either wish list Id or openId");
        return new WishListsResponse(false);
    }

    @ApiOperation(value = "Collect wish item by id or wishlist_id",
            notes = "根据ID或者openID搜索愿望,ID和openID只需要填一个，ID优先查询",
            response = WishesResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishesResponse getWishesByID(
            @ApiParam(example = "1") @RequestParam(value = "wishId", required = false) String id,
            @ApiParam(example = "2") @RequestParam(value = "wishListId", required = false) String wishListID) {

        if (StringUtils.isNotBlank(id)) {
            logger.info("Query wishService.getWishDetailByID: {}", id);
            List<Wish> wishes = wishService.getWishDetailByID(id);

            if(!wishes.isEmpty()){
                return convertWishListToWishesResponse(wishes);
            }else{
                return new WishesResponse(false);
            }

        }
        if(StringUtils.isNotBlank(wishListID)){
            logger.info("Query wishService.getWishDetailByWishListID: {}", wishListID);
            List<Wish> wishes =wishService.getWishDetailByWishListID(wishListID);

            if(!wishes.isEmpty()){
                return convertWishListToWishesResponse(wishes);
            }else{
                return new WishesResponse(false);
            }
        }

        logger.info("Query Wish detail missing param either wish Id or wish list id");
        return new WishesResponse(false);
    }

    private WishesResponse convertWishListToWishesResponse(List<Wish> wishes){

        List<WishDomain> wishDomains =  wishes.stream().map(wish -> {
            WishDomain domain = new WishDomain(wish);
            if(!wish.getImplementorOpenId().isEmpty()) {
                UserInfo userInfo = new UserInfo(userService.getUserByOpenId(wish.getImplementorOpenId()));
                domain.setImplementor(userInfo);
            }
            return domain;
        }).collect(Collectors.toList());

        WishesResponse response =  new WishesResponse(wishDomains);
        logger.info("{}", response);
        return response;
    }


}
