package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.CouponMapping;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CouponMappingDAOImplTest {
    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;


    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:CouponMappingDAOImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);

        Toggle.TEST_MODE.setStatus(true);
    }

    @After
    public void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM coupon_map_tbl");
    }


    @Test
    public void testCanUpdateCouponMapping() throws Exception {
        CouponMappingDAOImpl couponMappingDAO = new CouponMappingDAOImpl();
        couponMappingDAO.setJdbcTemplate(jdbcTemplate);
        assertThat(couponMappingDAO.createCouponMapping(
                new CouponMapping("test-open-id1", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW)), is(true));
        assertThat(couponMappingDAO.updateCouponMapping(
                new CouponMapping("test-open-id1", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_TAKEUP)), is(true));
        List<CouponMapping> couponMappings = couponMappingDAO.queryCoupon("test-open-id1");
        assertThat(couponMappings.size(), is(1));
        assertThat(couponMappings.get(0).getOpenId(), is("test-open-id1"));
        assertThat(couponMappings.get(0).getCouponStatus(), is(Constants.COUPON_STATUS_TAKEUP));
    }

    @Test
    public void testCanCreateCouponMapping() throws Exception {
        CouponMappingDAOImpl couponMappingDAO = new CouponMappingDAOImpl();
        couponMappingDAO.setJdbcTemplate(jdbcTemplate);
        assertThat(couponMappingDAO.createCouponMapping(
                new CouponMapping("test-open-id1", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW)), is(true));
        assertThat(couponMappingDAO.createCouponMapping(
                new CouponMapping("test-open-id2", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW)), is(true));
        assertThat(jdbcTemplate.queryForList("select * from coupon_map_tbl").size(), is(2));
    }

    @Test
    public void testCanQueryCouponByOpenID() throws Exception {
        CouponMappingDAOImpl couponMappingDAO = new CouponMappingDAOImpl();
        couponMappingDAO.setJdbcTemplate(jdbcTemplate);
        assertThat(couponMappingDAO.createCouponMapping(
                new CouponMapping("test-open-id1", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW)), is(true));
        List<CouponMapping> couponMappings = couponMappingDAO.queryCoupon("test-open-id1");
        assertThat(couponMappings.size(), is(1));
        assertThat(couponMappings.get(0).getCouponType(), is(Constants.COUPON_TYPE_WELCOME));
        assertThat(couponMappings.get(0).getCouponStatus(), is(Constants.COUPON_STATUS_NEW));
        assertThat(couponMappings.get(0).getOpenId(), is("test-open-id1"));
        assertThat(couponMappings.get(0).getCoupon(), is("coupon1"));
    }

    @Test
    public void testQueryCouponByOpenIDAndCouponType() throws Exception {
        CouponMappingDAOImpl couponMappingDAO = new CouponMappingDAOImpl();
        couponMappingDAO.setJdbcTemplate(jdbcTemplate);
        assertThat(couponMappingDAO.createCouponMapping(
                new CouponMapping("test-open-id1", "coupon1", Constants.COUPON_TYPE_WELCOME, Constants.COUPON_STATUS_NEW)), is(true));
        List<CouponMapping> couponMappings = couponMappingDAO.queryCoupon("test-open-id1", Constants.COUPON_TYPE_WELCOME);
        assertThat(couponMappings.size(), is(1));
        assertThat(couponMappings.get(0).getCouponType(), is(Constants.COUPON_TYPE_WELCOME));
        assertThat(couponMappings.get(0).getCouponStatus(), is(Constants.COUPON_STATUS_NEW));
        assertThat(couponMappings.get(0).getOpenId(), is("test-open-id1"));
        assertThat(couponMappings.get(0).getCoupon(), is("coupon1"));
    }
}