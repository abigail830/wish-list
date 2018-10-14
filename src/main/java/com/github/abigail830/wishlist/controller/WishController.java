package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.WishListsResponse;
import com.github.abigail830.wishlist.entity.UserEvent;
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
            notes = "愿望清单根据ID或者openID搜索",
            response = WishListsResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/wishlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListsResponse getFoodListByName(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "abc") @RequestParam(value = "openId", required = false) String openId) {


        if (StringUtils.isNotBlank(id))
            return new WishListsResponse(wishService.getWishListByID(id));

        if(StringUtils.isNotBlank(openId))
            return new WishListsResponse(wishService.getWishListByID(id));

        return new WishListsResponse(0);
    }



}
