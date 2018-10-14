package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.WishListsResponse;
import com.github.abigail830.wishlist.domain.WishesResponse;
import com.github.abigail830.wishlist.entity.UserEvent;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
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

@RestController
@RequestMapping("/wish")
public class WishController {

    private static final Logger logger = LoggerFactory.getLogger(WishController.class);

    @Resource
    private WishService wishService;

    @ApiOperation(value = "Collect wish list by id or open_id",
            notes = "根据ID或者openID搜索愿望清单",
            response = WishListsResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListsResponse getWishListByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId) {


        if (StringUtils.isNotBlank(id))
            return new WishListsResponse(wishService.getWishListByID(id),
                    0, 0);

        if(StringUtils.isNotBlank(openId))
            return new WishListsResponse(wishService.getWishListByOpenID(id),
                    0, 0);

        return new WishListsResponse(0);
    }

    @ApiOperation(value = "Collect wish item by id or wishlist_id",
            notes = "根据ID或者openID搜索愿望",
            response = WishesResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishesResponse getWishesByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "2") @RequestParam(value = "wishListID", required = false) String wishListID) {

        if (StringUtils.isNotBlank(id))
            return new WishesResponse(wishService.getWishByID(id));

        if(StringUtils.isNotBlank(wishListID))
            return new WishesResponse(wishService.getwishByWishListID(wishListID));

        return new WishesResponse(0);
    }



}
