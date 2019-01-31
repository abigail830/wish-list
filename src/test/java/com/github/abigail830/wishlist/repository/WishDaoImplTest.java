package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.dto.UserInfo;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class WishDaoImplTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        prepareDB();
    }

    private static void prepareDB() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl"));

        WishList wishList = new WishList();
        wishList.setId(Integer.valueOf(1));
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR TEST");
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
    public void testCreateWish() {
        WishDaoImpl wishDaoImpl = new WishDaoImpl();
        wishDaoImpl.setJdbcTemplate(jdbcTemplate);
        Wish wish = new Wish();
        wish.setWishListId(Integer.valueOf(1));
        wish.setWishStatus("NEW");
        wish.setDescription("DESC1");
//        wish.setImplementorOpenId("openID1");
        wishDaoImpl.createWish(wish);
        assertThat(wishDaoImpl.getWishByID("1").size(), is(1));
    }

    @Test
    public void testUpdateWish() throws Exception {
        WishDaoImpl wishDaoImpl = new WishDaoImpl();
        wishDaoImpl.setJdbcTemplate(jdbcTemplate);
        Wish wish = new Wish();
        wish.setWishListId(Integer.valueOf(1));
        wish.setWishStatus("TEST2");
        wish.setDescription("DESC2");
        wish.setImplementorOpenId("IID2");
        wishDaoImpl.createWish(wish);

        wish.setDescription("DESC2-UPDATE");
        wish.setId(1);
        wishDaoImpl.updateWish(wish);

        assertThat(wishDaoImpl.getWishByWishListId("1")
                .stream()
                .filter(wishItem -> "DESC2-UPDATE".equals(wishItem.getDescription()))
                .count(),
                is(1L));

    }

}