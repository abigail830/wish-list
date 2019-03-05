package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dto.v1.*;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.service.FormIDMappingService;
import com.github.abigail830.wishlist.service.WishService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/wishes")
@Slf4j
public class WishControllerV1 {

    @Autowired
    private WishService wishService;

    @Autowired
    private FormIDMappingService formIDMappingService;

    @GetMapping
    public WishListDTO getWishListDetail(
            @ApiParam(example = "2") @RequestParam(value = "wishListId", required = false) String wishListId) {


        if (StringUtils.isNotBlank(wishListId)) {
            log.info("User getWishListDetail by wishListId {}", wishListId);
            WishListDTO response = new WishListDTO(
                    wishService.getWishListDetailByWishListID(wishListId));
            log.info("{}", response);
            return response;
        }else{
            log.warn("wishListId should not be empty when getWishListDetail.");
            throw new IllegalArgumentException("failed to validate");
        }

    }

    @GetMapping("/lists")
    public WishDashboardDTO getWishListsByID(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) {
        log.info("User query wishService.getWishListByID: {} , fromID: {} ", id, formId);

        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openId,formId);
        }

        if (StringUtils.isNotBlank(id)){
            WishListDetail wishListDetail = wishService.getWishListDetailByWishListID(id);
            if(wishListDetail != null){
                WishDashboardDTO response = new WishDashboardDTO(new WishListDTO(wishListDetail) ,
                        wishService.getMyCompletedWishCount(wishListDetail.getListOpenId()),
                        wishService.getFriendsCompletedWishCountByImplementorID(wishListDetail.getListOpenId()));
                log.info("{}", response);
                return response;
            }
            else{
                log.info("No wish list found with ID {}", id);
                return new WishDashboardDTO(Collections.emptyList(), 0, 0);
            }

        }

        if (StringUtils.isNotBlank(openId)){
            log.info("User[{}] query wishService.getWishListByOpenID: {}", openId, openId);
            return getWishListsByUserOpenID(openId);

        }

        log.info("Query WishList missing param either wish list Id or openId");
        return new WishDashboardDTO(Collections.EMPTY_LIST, 0, 0);
    }


    @GetMapping("/lists/timeline")
    public WishListTimeline getWishListsTimeLineByOpenID(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openId,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) {
        log.info("Query Timeline by openID {} , formID {}", openId, formId);
        if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openId,formId);
        }
        if (StringUtils.isNotBlank(openId)){
            return wishService.getWishListTimeLine(openId);

        } else {
            throw new IllegalArgumentException("Open ID is null");
        }
    }

    @PostMapping("/lists")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishListDTO postNewWishList(
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Add new wish list {}, formID {} ", wishList, formId);

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(wishList.getListOpenId(),formId);
        }

        if (StringUtils.isNotBlank(wishList.getListOpenId()) && StringUtils.isNotBlank(wishList.getListDescription())) {
            return wishService.createWishList(wishList);
        } else {
            return wishList;
        }

    }

    @DeleteMapping("/lists")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishDashboardDTO deleteNewWishList(
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Delete new wish list {}, formID {}", wishList, formId);

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

    @PutMapping("/lists")
    public WishListDTO updateNewWishList(
            @RequestBody WishListDTO wishList,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Update new wish list {}, formID {}", wishList, formId);

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

    @PostMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishDTO addWishToWishList(
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Add new wish to wish list {}, formID {}", wishDTO, formId);

        if (StringUtils.isNotBlank(formId) && wishDTO.getWishListID() != null) {
            formIDMappingService.contributeFormID(wishDTO, formId);
        }

        if (wishDTO.getWishListID() != null && wishDTO.getDescription() != null) {
            return wishService.addNewWish(wishDTO);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @DeleteMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public WishDTO deleteWish(
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Delete wish from wish list {} formID {}", wishDTO, formId);

        if (StringUtils.isNotBlank(formId) && wishDTO.getWishListID() != null) {
            formIDMappingService.contributeFormID(wishDTO, formId);
        }

        if (wishDTO.getWishID() != null) {
            wishService.deleteWish(wishDTO);
            return wishDTO;
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @PutMapping
    public WishDTO updateWish(
            @RequestBody WishDTO wishDTO,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Update wish {}, formID {}", wishDTO, formId);

        if (StringUtils.isNotBlank(formId) && wishDTO.getWishListID() != null) {
            formIDMappingService.contributeFormID(wishDTO, formId);
        }

        if (wishDTO.getWishID() != null) {
            wishService.updateWish(wishDTO);
            return wishDTO;
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @GetMapping("/taken")
    public List<WishDTO> getTakenUpWish(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Get wish by {}, formID {}", openID, formId);

        if (StringUtils.isNotBlank(openID) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openID,formId);
        }

        if (StringUtils.isNotBlank(openID)) {
            return wishService.getTakenUpWish(openID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @GetMapping("/taken/timeline")
    public TakenWishTimeline getTakenUpWishTimeline(
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String openID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formId) throws ParseException {
        log.info("Get taken wish timeline by {}, formID {}", openID, formId);

        if (StringUtils.isNotBlank(openID) && StringUtils.isNotBlank(formId)) {
            formIDMappingService.contributeFormID(openID,formId);
        }

        if (StringUtils.isNotBlank(openID)) {
            return wishService.getTakenWishTimeline(openID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }


    @PutMapping("/taken")
    public List<WishDTO> takeUpWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = true) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = true) String takeUpOpenID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formID) throws ParseException {
        log.info("V2 Take up {} wish by {} with form ID {}", id, takeUpOpenID, formID);

        if (StringUtils.isNotBlank(takeUpOpenID) && StringUtils.isNotBlank(formID)) {
            formIDMappingService.contributeFormID(takeUpOpenID,formID);
        }

        if (StringUtils.isNotBlank(takeUpOpenID) && StringUtils.isNotBlank(id)) {
            return wishService.takeupWishV2(id, takeUpOpenID);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }


    @DeleteMapping("/taken")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public void rollbackTakeUpWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id) throws ParseException {
        log.info("Delete take up wish id : {}", id);

        if (StringUtils.isNotBlank(id)) {
            wishService.removeTakeUp(id);
        } else {
            throw new IllegalArgumentException("Wish information is invalid");
        }
    }

    @PutMapping("/completed")
    public List<WishDTO>  completeWish(
            @ApiParam(example = "1") @RequestParam(value = "id", required = false) String id,
            @ApiParam(example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4") @RequestParam(value = "openId", required = false) String takeUpOpenID,
            @ApiParam(example = "3bd989440d1d9bb5b7d55a88c5425762") @RequestParam(value = "formId", required = false) String formID) throws ParseException {
        log.info("Complete wish {}", id);

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
            log.info("{}", response);
            return response;
        }
        else{
            log.info("No wish list found with openId {}", openId);
            return new WishDashboardDTO(Collections.EMPTY_LIST, 0, 0);
        }
    }

    public void setWishService(WishService wishService) {
        this.wishService = wishService;
    }


}
