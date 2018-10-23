package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WishListDaoImplTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishListDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl"));
    }


    @Test
    public void testCreateWishList() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(1);
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
        assertThat(wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR TEST".equals(wl.getDescription())).count(), is(1L));
    }

    @Test
    public void testUpdateWishListByID() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(2);
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
        wishList.setDescription("THIS IS FOR TEST2");
        wishListDao.updateWishListByID(wishList);
        assertThat(wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR TEST2".equals(wl.getDescription())).count(), is(1L));

    }

    @Test
    public void testGetWishListByOpenId() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(3);
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR TEST 3");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
        List<WishList> wishLists = wishListDao.getWishListByOpenId("openID1");
        assertThat(wishLists.stream().filter(wl -> "THIS IS FOR TEST 3".equals(wl.getDescription())).count(), is(1L));
    }

}