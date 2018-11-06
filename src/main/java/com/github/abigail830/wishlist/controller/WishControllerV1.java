package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.WishListDetailResponse;
import com.github.abigail830.wishlist.domain.WishListsResponse;
import com.github.abigail830.wishlist.domainv1.UserDTO;
import com.github.abigail830.wishlist.domainv1.WishDTO;
import com.github.abigail830.wishlist.domainv1.WishDashboardDTO;
import com.github.abigail830.wishlist.domainv1.WishListDTO;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import com.github.abigail830.wishlist.util.Constants;
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

import java.text.ParseException;
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

    @ApiOperation(value = "Add new Wish List",
            notes = "添加新愿望清单",
            response = WishListDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListDTO postNewWishList(
            @RequestBody WishListDTO wishList) throws ParseException {
        logger.info("Add new wish list {}", wishList);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(wishList.getListDescription())) {
            return wishService.createWishList(wishList);
        } else {
            return wishList;
        }

    }

    @ApiOperation(value = "Delete Wish List",
            notes = "删除新愿望清单",
            response = WishDashboardDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishDashboardDTO deleteNewWishList(
            @RequestBody WishListDTO wishList) throws ParseException {
        logger.info("Delete new wish list {}", wishList);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && wishList.getListId() != null) {
            wishService.deleteWishList(wishList);
            return getWishListsByUserOpenID(wishList.getListOpenId());
        } else {
            return new WishDashboardDTO(Collections.EMPTY_LIST, 0, 0);
        }

    }

    @ApiOperation(value = "Update new Wish List",
            notes = "修改新愿望清单",
            response = WishDashboardDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListDTO updateNewWishList(
            @RequestBody WishListDTO wishList) throws ParseException {
        logger.info("Update new wish list {}", wishList);

        if (wishList.getListId() != null) {
             wishService.updateWishList(wishList);
            return wishList;
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Add new wish to wish list",
            notes = "添加新愿望到愿望清单",
            response = WishDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishDTO addWishToWishList(
            @RequestBody WishDTO wishDTO) throws ParseException {
        logger.info("Add new wish to wish list {}", wishDTO);

        if (wishDTO.getWishListID() != null && wishDTO.getDescription() != null) {
            return wishService.addNewWish(wishDTO);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Delete new wish to wish list",
            notes = "删除愿望",
            response = WishDashboardDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishDTO deleteWish(
            @RequestBody WishDTO wishDTO) throws ParseException {
        logger.info("Delete wish from wish list {}", wishDTO);

        if (wishDTO.getWishID() != null) {
            wishService.deleteWish(wishDTO);
            return wishDTO;
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Update wish",
            notes = "更新愿望信息",
            response = WishDashboardDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishDTO updateWish(
            @RequestBody WishDTO wishDTO) throws ParseException {
        logger.info("Update wish", wishDTO);

        if (wishDTO.getWishID() != null) {
            wishService.updateWish(wishDTO);
            return wishDTO;
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Get Taken up wish",
            notes = "获取认领愿望信息",
            response = WishDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/taken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<WishDTO> getTakenUpWish(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openID) throws ParseException {
        logger.info("Get wish by {}", openID);

        if (StringUtils.isNotBlank(openID)) {
            return wishService.getTakenUpWish(openID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }


    @ApiOperation(value = "Taken up wish",
            notes = "认领愿望",
            response = WishDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/taken", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<WishDTO> takeUpWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String takeUpOpenID) throws ParseException {
        logger.info("Take up {} wish by {}", id,takeUpOpenID);

        if (StringUtils.isNotBlank(takeUpOpenID) && StringUtils.isNotBlank(id)) {
            return wishService.takeupWish(id, takeUpOpenID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Taken up wish",
            notes = "取消认领",
            response = WishDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/taken", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void rollbackTakeUpWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id) throws ParseException {
        logger.info("Delete take up wish id : {}", id);

        if (StringUtils.isNotBlank(id)) {
            wishService.removeTakeUp(id);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Complete wish",
            notes = "完成愿望",
            response = WishDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/completed", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<WishDTO>  completeWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String takeUpOpenID) throws ParseException {
        logger.info("Complete wish {}", id);

        if (StringUtils.isNotBlank(id)) {
            return wishService.completeWish(id, takeUpOpenID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
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

    public void setWishService(WishService wishService) {
        this.wishService = wishService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
