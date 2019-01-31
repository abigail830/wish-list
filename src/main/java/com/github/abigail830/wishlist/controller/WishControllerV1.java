package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dtov1.*;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.service.FormIDMappingService;
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

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/wishes")
public class WishControllerV1 {
    private static final Logger logger = LoggerFactory.getLogger(WishControllerV1.class);

    @Autowired
    private WishService wishService;

    @Autowired
    private FormIDMappingService formIDMappingService;

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
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) {
        logger.info("User query wishService.getWishListByID: {} , fromID: {} ", id, formId);

        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openId,formId);
        }

        if (StringUtils.isNotBlank(id)){
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


    @ApiOperation(value = "Collect wish list by open_id in timeline",
            notes = "根据ID或者openID搜索愿望清单，ID和openID只需要填一个，ID优先查询，只返回清单概述",
            response = WishListTimeline.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists/timeline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListTimeline getWishListsTimeLineByOpenID(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) {
        logger.info("Query Timeline by openID {} , formID {}", openId, formId);
        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openId,formId);
        }
        if (StringUtils.isNotBlank(openId)){
            return wishService.getWishListTimeLine(openId);

        } else {
            throw new IllegalArgumentException("Open ID is null");
        }
    }

    @ApiOperation(value = "Add new Wish List",
            notes = "添加新愿望清单",
            response = WishListDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/lists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public WishListDTO postNewWishList(
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Add new wish list {}, formID {} ", wishList, formId);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(wishList.getListOpenId(),formId);
        }

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
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Delete new wish list {}, formID {}", wishList, formId);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(wishList.getListOpenId(),formId);
        }


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
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Update new wish list {}, formID {}", wishList, formId);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(wishList.getListOpenId(),formId);
        }

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
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Add new wish to wish list {}, formID {}", wishDTO, formId);

        formIDMappingService.contributeFormID(wishDTO,formId);

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
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Delete wish from wish list {} formID {}", wishDTO, formId);

        formIDMappingService.contributeFormID(wishDTO,formId);

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
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Update wish {}, formID {}", wishDTO, formId);

        formIDMappingService.contributeFormID(wishDTO,formId);

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
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Get wish by {}, formID {}", openID, formId);

        if (StringUtils.isNotBlank(openID) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openID,formId);
        }

        if (StringUtils.isNotBlank(openID)) {
            return wishService.getTakenUpWish(openID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @ApiOperation(value = "Get Taken up wish timeline",
            notes = "获取认领愿望信息",
            response = TakenWishTimeline.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/taken/timeline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TakenWishTimeline getTakenUpWishTimeline(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        logger.info("Get taken wish timeline by {}, formID {}", openID, formId);

        if (StringUtils.isNotBlank(openID) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openID,formId);
        }

        if (StringUtils.isNotBlank(openID)) {
            return wishService.getTakenWishTimeline(openID);
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
            @ApiParam(example = "1") @RequestParam(value = "id", required = true) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = true) String takeUpOpenID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formID) throws ParseException {
        logger.info("Take up {} wish by {} with form ID {}", id,takeUpOpenID,formID);

        if (StringUtils.isNotBlank(takeUpOpenID) && StringUtils.isNotBlank(formID)) {
            formIDMappingService.contributeFormID(takeUpOpenID,formID);
        }

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
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String takeUpOpenID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formID) throws ParseException {
        logger.info("Complete wish {}", id);

        if (StringUtils.isNotBlank(takeUpOpenID) && StringUtils.isNotBlank(formID)) {
            formIDMappingService.contributeFormID(takeUpOpenID,formID);
        }

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


}
