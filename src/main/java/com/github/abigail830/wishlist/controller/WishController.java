package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dto.*;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import com.github.abigail830.wishlist.util.Constants;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wish")
public class WishController {

    private static final Logger logger = LoggerFactory.getLogger(WishController.class);

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private WishService wishService;

    @Autowired
    private UserService userService;


    @GetMapping
    public WishListDetailResponse getWishListDetail(
            @ApiParam(example = "2") @RequestParam(value = "wishListId", required = false) String wishListId) {


        if (StringUtils.isNotBlank(wishListId)) {
            logger.info("User getWishListDetail by wishListId {}", wishListId);
            WishListDetailResponse response = new WishListDetailResponse(
                    wishService.getWishListDetailByWishListID(wishListId));
            response.setResultCode(Constants.HTTP_STATUS_SUCCESS);
            logger.info("{}", response);
            return response;
        }else{
            logger.warn("wishListId should not be empty when getWishListDetail.");
            return new WishListDetailResponse(Constants.HTTP_STATUS_VALIDATION_FAIL);
        }

    }

    @PostMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishListDetailResponse postNewWish(@RequestBody Wish wish) {

        logger.info("Add new wish {}", wish);

        if (StringUtils.isNotBlank(wish.getDescription())) {
            return new WishListDetailResponse(Constants.HTTP_STATUS_SUCCESS);
        }else{
            logger.warn("wishListId should not be empty when getWishListDetail.");
            return new WishListDetailResponse(Constants.HTTP_STATUS_VALIDATION_FAIL);
        }

    }


    @PostMapping("/lists")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishListsResponse postNewWishList(
            @RequestBody WishList wishList) {
        logger.info("Add new wish list {}", wishList);

        if (StringUtils.isNotBlank(wishList.getOpenId()) && StringUtils.isNotBlank(wishList.getTitle())) {
            wishService.createWishList(wishList);
            return getWishListsByUserOpenID(wishList.getOpenId());
        } else {
            return new WishListsResponse(false, Constants.HTTP_STATUS_VALIDATION_FAIL);
        }

    }

    @DeleteMapping("/lists")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishListsResponse deleteWishList(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id) {
        List<WishList> wishToBeDeleted = wishService.getWishListByID(id);
        logger.info("Going to delete wish list {}", wishToBeDeleted);

        if (wishToBeDeleted.size() > 0) {
            wishService.deleteWishListByListID(id);
            return getWishListsByUserOpenID(wishToBeDeleted.get(0).getOpenId());
        } else {
            return new WishListsResponse(false, Constants.HTTP_STATUS_VALIDATION_FAIL);
        }

    }


    @GetMapping("/lists")
    public WishListsResponse getWishListsByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId) {


        if (StringUtils.isNotBlank(id)){
            logger.info("User query wishService.getWishListByID: {}", id);
            List<BriefWishList> briefWishLists = wishService.getWishListByID(id)
                    .stream().map(BriefWishList::new).collect(Collectors.toList());
            if(!briefWishLists.isEmpty()){
                WishListsResponse response =  new WishListsResponse(briefWishLists,
                        wishService.getMyCompletedWishCount(briefWishLists.get(0).getOpenId()),
                        wishService.getFriendsCompletedWishCountByImplementorID(briefWishLists.get(0).getOpenId()),
                        Constants.HTTP_STATUS_SUCCESS);
                logger.info("{}", response);
                return response;
            }
            else{
                logger.info("No wish list found with ID {}", id);
                return new WishListsResponse(false, Constants.HTTP_STATUS_SUCCESS);
            }

        }

        if (StringUtils.isNotBlank(openId)){
            logger.info("User[{}] query wishService.getWishListByOpenID: {}", openId,openId);
            return getWishListsByUserOpenID(openId);

        }

        logger.info("Query WishList missing param either wish list Id or openId");
        return new WishListsResponse(false, Constants.HTTP_STATUS_VALIDATION_FAIL);
    }

    private WishListsResponse getWishListsByUserOpenID(String openId) {
        int myCompletedWishCount = wishService.getMyCompletedWishCount(openId);
        int myFriendCompletedWishCount = wishService.getFriendsCompletedWishCountByImplementorID(openId);
        List<BriefWishList> briefWishLists = wishService.getWishListByOpenID(openId)
                .stream().map(BriefWishList::new).collect(Collectors.toList());
        if(!briefWishLists.isEmpty()){
            WishListsResponse response =  new WishListsResponse(briefWishLists,
                    myCompletedWishCount,
                    myFriendCompletedWishCount,
                    Constants.HTTP_STATUS_SUCCESS
                    );
            logger.info("{}", response);
            return response;
        }
        else{
            logger.info("No wish list found with openId {}", openId);
            return new WishListsResponse(false, Constants.HTTP_STATUS_SUCCESS);
        }
    }

    @GetMapping("/details")
    public WishesResponse getWishesByID(
            @ApiParam(example = "1") @RequestParam(value = "wishId", required = false) String id,
            @ApiParam(example = "2") @RequestParam(value = "wishListId", required = false) String wishListID) {

        if (StringUtils.isNotBlank(id)) {
            logger.info("Query wishService.getWishDetailByID: {}", id);
            List<Wish> wishes = wishService.getWishDetailByID(id);

            if(!wishes.isEmpty()){
                return convertWishListToWishesResponse(wishes);
            }else{
                return new WishesResponse(false, Constants.HTTP_STATUS_SUCCESS);
            }

        }
        if(StringUtils.isNotBlank(wishListID)){
            logger.info("Query wishService.getWishDetailByWishListID: {}", wishListID);
            List<Wish> wishes =wishService.getWishDetailByWishListID(wishListID);

            if(!wishes.isEmpty()){
                return convertWishListToWishesResponse(wishes);
            }else{
                return new WishesResponse(false, Constants.HTTP_STATUS_SUCCESS);
            }
        }

        logger.info("Query Wish detail missing param either wish Id or wish list id");
        return new WishesResponse(false, Constants.HTTP_STATUS_VALIDATION_FAIL);
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

        WishesResponse response =  new WishesResponse(wishDomains, Constants.HTTP_STATUS_SUCCESS);
        logger.info("{}", response);
        return response;
    }

    public void setWishService(WishService wishService) {
        this.wishService = wishService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
