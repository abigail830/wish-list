package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dto.v1.CouponMappingDTO;
import com.github.abigail830.wishlist.entity.CouponMapping;
import com.github.abigail830.wishlist.service.WelcomeCouponService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponAdminController {

    @Resource
    private WelcomeCouponService welcomeCouponService;


    @GetMapping("/")
    public List<CouponMappingDTO> getWelcomeCoupon(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = false) String openID) {
        if (StringUtils.isNotBlank(openID)) {
            return welcomeCouponService.getWelcomeCoupon(openID).stream().map(CouponMappingDTO::new).collect(Collectors.toList());
        } else {
            return welcomeCouponService.getWelcomeCoupon().stream().map(CouponMappingDTO::new).collect(Collectors.toList());
        }
    }

    @GetMapping("/outstanding")
    public CouponMappingDTO getOutstandingWelcomeCoupon(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        return welcomeCouponService.getOutstandingWelcomeCoupon(openID);
    }


    @PutMapping("/")
    public CouponMappingDTO takeWelcomeCoupon(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        log.info("Take up coupon for open ID {}", openID);
        CouponMapping couponMapping = welcomeCouponService.takeUpWelcomeCoupon(openID);
        if (couponMapping != null) {
            return new CouponMappingDTO(couponMapping);
        } else {
            return null;
        }
    }

    @PostMapping("/")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public void createWelcomeCouponForVerification(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        if ("oEmJ75T7IHx-3zUjMteodZu5g09A".equals(openID) || "oEmJ75YWmBSDgyz4KLi_yGL8MBV4".equals(openID)) {
            log.info("Provision test coupon for {}", openID);
            welcomeCouponService.deliverWelcomeCoupon(openID);
        }

    }

    @DeleteMapping("/")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    public void deleteWelcomeCouponForVerification(
            @ApiParam(example = "oEmJ75T7IHx-3zUjMteodZu5g09A") @RequestParam(value = "openID", required = true) String openID) {
        log.info("Delete coupon for {}", openID);
        welcomeCouponService.deleteWelcomeCoupon(openID);

    }

}
