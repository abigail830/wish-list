package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.dto.UserInfo;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WishListDaoImplTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishListDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);

        Toggle.TEST_MODE.setStatus(true);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl"));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM user_event WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wish_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wishlist_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM user_tbl WHERE ID is not null");
    }


    @Test
    public void testCreateWishList() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(1);
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR TEST");
        wishList.setBrief("THIS IS FOR BRIEF TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
        assertThat(wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR TEST".equals(wl.getTitle())).count(), is(1L));
    }

    @Test
    public void testUpdateWishListByID() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(2);
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR TEST");
        wishList.setBrief("THIS IS FOR BRIEF TEST");
        wishList.setAddress("THIS IS ADDRESS");
        wishList.setIsSelfWitness(false);
        wishList.setImplementorsLimit(99);
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);

        wishList.setTitle("THIS IS FOR TEST2");
        wishList.setImplementorsLimit(1);
        wishList.setIsSelfWitness(true);
        wishList.setAddress("THIS IS NEW ADDRESS");
        wishListDao.updateWishListByID(wishList);
        assertThat(wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR TEST2".equals(wl.getTitle()))
                .filter(wl -> wl.getIsSelfWitness().equals(true))
                .filter(wl -> "THIS IS NEW ADDRESS".equals(wl.getAddress()))
                .filter(wl -> wl.getImplementorsLimit() == 1)
                .count(), is(1L));

    }

    @Test
    public void testDeleteWishListByID() throws Exception {
        WishList wishList = new WishList();
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR DELETE TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);

        List<WishList> wishLists = wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR DELETE TEST".equals(wl.getTitle()))
                .collect(Collectors.toList());

        wishListDao.deleteWishList(wishLists.get(0).getId());
        assertThat(wishListDao.getWishListByOpenId("openID1")
                .stream()
                .filter(wl -> "THIS IS FOR DELETE TEST".equals(wl.getTitle()))
                .count(), is(0L));
    }

    @Test
    public void testGetWishListByOpenId() throws Exception {
        WishList wishList = new WishList();
        wishList.setId(3);
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR TEST 3");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);
        List<WishList> wishLists = wishListDao.getWishListByOpenId("openID1");
        assertThat(wishLists.stream().filter(wl -> "THIS IS FOR TEST 3".equals(wl.getTitle())).count(), is(1L));
    }

}