package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.repository.ComplexWishDaoImpl;
import com.github.abigail830.wishlist.repository.UserDaoImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;

public class WishServiceTest {

    @Autowired
    private static WishService service;

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishServiceTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        prepareDB();

        service = new WishService();
        ComplexWishDaoImpl complexWishDao = new ComplexWishDaoImpl();
        complexWishDao.setJdbcTemplate(jdbcTemplate);
        service.setComplexWishDao(complexWishDao);
    }

    private static void prepareDB() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl"));

        WishList wishList = new WishList();
        wishList.setId(Integer.valueOf(1));
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM user_event WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wish_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wishlist_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM user_tbl WHERE ID is not null");
    }

    @Test
    public void getWishListDetailByWishListID() {
        assertEquals("openID1", service.getWishListDetailByWishListID("1").getListOpenId());
    }
}