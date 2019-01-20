package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.CouponMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponMappingDAOImpl {
    private static final Logger logger = LoggerFactory.getLogger(CouponMappingDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<CouponMapping> rowMapper = new BeanPropertyRowMapper<>(CouponMapping.class);

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean updateCouponMapping(CouponMapping couponMapping) {
        logger.info("Going to update coupon mapping: {}",
                couponMapping);
        try {
            int result = jdbcTemplate.update(
                    "UPDATE coupon_map_tbl SET coupon_status = ? WHERE open_id=? AND coupon=? AND coupon_type=?",
                    couponMapping.getCouponStatus(),
                    couponMapping.getOpenId(),
                    couponMapping.getCoupon(),
                    couponMapping.getCouponType());
            return result == 1;
        } catch (Exception ex) {
            logger.error("Failed to insert coupon mapping. ", ex);
            return false;
        }
    }

    public boolean createCouponMapping(CouponMapping couponMapping) {
        logger.info("Going to create coupon mapping: {}",
                couponMapping);
        try {
            int result = jdbcTemplate.update(
                    "INSERT INTO coupon_map_tbl (open_id, coupon, coupon_type, coupon_status) VALUES (?,?,?,?)",
                    couponMapping.getOpenId(),
                    couponMapping.getCoupon(),
                    couponMapping.getCouponType(),
                    couponMapping.getCouponStatus());
            return result == 1;
        } catch (Exception ex) {
            logger.error("Failed to insert coupon mapping. ", ex);
            return false;
        }
    }

    public List<CouponMapping> queryCoupon(String openId) {
        logger.info("Going to query coupon mapping by open id: openID={}", openId);
        return jdbcTemplate.query("SELECT * FROM coupon_map_tbl WHERE open_id=?", rowMapper, openId);
    }

    public List<CouponMapping> queryCoupon(String openId, String couponType) {
        logger.info("Going to query coupon mapping by open id: openID={}, couponType={}", openId, couponType);
        return jdbcTemplate.query("SELECT * FROM coupon_map_tbl WHERE open_id=? AND coupon_type=?", rowMapper, openId, couponType);
    }


    public boolean deleteCoupon(String openID, String couponType) {
        try {
            logger.info("Delete coupon record for openID {} couponType {}", openID, couponType);
            int result = jdbcTemplate.update(
                    "DELETE FROM coupon_map_tbl WHERE open_id=? AND coupon_type=?", openID, couponType);
            return result == 1;
        } catch (Exception ex) {
            logger.error("Failed to delete coupon mapping. ", ex);
            return false;
        }
    }
}
