package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domainv1.CouponDTO;
import com.github.abigail830.wishlist.domainv1.FormIDMappingDTO;
import com.github.abigail830.wishlist.entity.CouponMapping;
import com.github.abigail830.wishlist.service.WelcomeCouponService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupon")
public class CouponAdminController {

    private static final Logger logger = LoggerFactory.getLogger(CouponAdminController.class);

    @Resource
    private WelcomeCouponService welcomeCouponService;


    @ApiOperation(value = "Get coupon mapping - read only",
            response = List.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<CouponDTO> getWelcomeCoupon(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        return welcomeCouponService.getWelcomeCoupon(openID).stream().map(CouponDTO::new).collect(Collectors.toList());
    }


    @ApiOperation(value = "Make coupon as taken up, so it won't be outstanding anymore",
            response = FormIDMappingDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public CouponDTO takeWelcomeCoupon(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        logger.info("Take up coupon for open ID {}", openID);
        CouponMapping couponMapping = welcomeCouponService.takeUpWelcomeCoupon(openID);
        if (couponMapping != null) {
            return new CouponDTO(couponMapping);
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Provision test data. just for service verification usage. ",
            response = FormIDMappingDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void createWelcomeCouponForVerification(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        if ("oEmJ75T7IHx-3zUjMteodZu5g09A".equals(openID) || "oEmJ75YWmBSDgyz4KLi_yGL8MBV4".equals(openID)) {
            logger.info("Provision test coupon for {}", openID);
            welcomeCouponService.deliverWelcomeCoupon(openID);
        }

    }


    @ApiOperation(value = "Provision test data. just for service verification usage. ",
            response = FormIDMappingDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void deleteWelcomeCouponForVerification(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        if ("oEmJ75T7IHx-3zUjMteodZu5g09A".equals(openID) || "oEmJ75YWmBSDgyz4KLi_yGL8MBV4".equals(openID)) {
            logger.info("Provision test coupon for {}", openID);
            welcomeCouponService.deleteWelcomeCoupon(openID);
        }

    }
}
