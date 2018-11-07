package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.domain.WishListDetailResponse;
import com.github.abigail830.wishlist.domain.WishListsResponse;
import com.github.abigail830.wishlist.domain.WishesResponse;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.repository.*;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class WishControllerTest {


    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishControllerTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        positionData();

    }

    @AfterClass
    public static void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM user_event WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wish_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wishlist_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM user_tbl WHERE ID is not null");
    }


    public static void positionData() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname1", "city",
                "country", "province", "lang", "imageUrl"));
        userDao.createUser(new UserInfo("openID2", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl2"));

        WishList wishList = new WishList();
        wishList.setId(Integer.valueOf(1));
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR TEST");
        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);
        wishListDao.createWishList(wishList);

        WishDaoImpl wishDaoImpl = new WishDaoImpl();
        wishDaoImpl.setJdbcTemplate(jdbcTemplate);
        Wish wish = new Wish();
        wish.setWishListId(Integer.valueOf(1));
        wish.setWishStatus(Constants.WISH_STATUS_DONE);
        wish.setDescription("DESC1");
        wish.setImplementorOpenId("openID2");
        wishDaoImpl.createWish(wish);
    }



    @Test
    public void canCreateAndDeleteWishList() {
        WishController wishController = getWishController();

        WishList wishList = new WishList();
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR WISE LIST ROUNDTRIP TEST");

        WishListsResponse wishListsResponse = wishController.postNewWishList(wishList);
        assertThat(wishListsResponse.getResultCode(), is(Constants.HTTP_STATUS_SUCCESS));
        assertThat(wishListsResponse.getWishLists()
                .stream()
                .filter(item -> "THIS IS FOR WISE LIST ROUNDTRIP TEST".equals(item.getDescription()))
                .count(), is(1L));

        String wishListID = wishListsResponse.getWishLists()
                .stream()
                .filter(item -> "THIS IS FOR WISE LIST ROUNDTRIP TEST".equals(item.getDescription()))
                .collect(Collectors.toList())
                .get(0)
                .getWishListID().toString();

        wishListsResponse = wishController.deleteWishList(wishListID);
        assertThat(wishListsResponse.getResultCode(), is(Constants.HTTP_STATUS_SUCCESS));
        assertThat(wishListsResponse.getWishLists()
                .stream()
                .filter(item -> "THIS IS FOR WISE LIST ROUNDTRIP TEST".equals(item.getDescription()))
                .count(), is(0L));

    }


    @Test
    public void testGetWishListDetail() throws Exception {
        WishController wishController = getWishController();

        WishListDetailResponse response = wishController.getWishListDetail("1");
        assertThat(response.getResultCode(), is(Constants.HTTP_STATUS_SUCCESS));
        assertThat(response.getListOpenId(), is("openID1"));
        assertThat(response.getListDescription(), is("THIS IS FOR TEST"));
        assertThat(response.getWishes().size(), is(1));
        assertThat(response.getWishes().get(0).getImplementor().getOpenId(), is("openID2"));
        assertThat(response.getWishes().get(0).getWishStatus(), is(Constants.WISH_STATUS_DONE));
        assertThat(response.getWishes().get(0).getDescription(), is("DESC1"));
    }


    @Test
    public void testGetWishListsByID() throws Exception {
        WishController wishController = getWishController();

        WishListsResponse response = wishController.getWishListsByID(null, "openID1");
        assertThat(response.getResultCode(), is(Constants.HTTP_STATUS_SUCCESS));
        assertThat(response.isHasWishList(), is(true));
        assertThat(response.getMyCompletedWishCount(), is(1));
        assertThat(response.getMyFriendsCompletedWishCount(), is(0));
        assertThat(response.getWishLists().size(), is(1));
    }

    @Test
    public void testGetWishesByID() throws Exception {
        WishController wishController = getWishController();
        WishesResponse response = wishController.getWishesByID("1", null);


        assertThat(response.getResultCode(), is(Constants.HTTP_STATUS_SUCCESS));
        assertThat(response.isHasWish(), is(true));
        assertThat(response.getWishes().size(), is(1));

    }


    private WishController getWishController() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);

        WishListDaoImpl wishListDao = new WishListDaoImpl();
        wishListDao.setJdbcTemplate(jdbcTemplate);

        WishDaoImpl wishDao = new WishDaoImpl();
        wishDao.setJdbcTemplate(jdbcTemplate);

        ComplexWishDaoImpl complexWishDao = new ComplexWishDaoImpl();
        complexWishDao.setJdbcTemplate(jdbcTemplate);

        UserEventImpl userEvent = new UserEventImpl();
        userEvent.setJdbcTemplate(jdbcTemplate);

        WishService wishService = new WishService();
        wishService.setComplexWishDao(complexWishDao);
        wishService.setWishDao(wishDao);
        wishService.setWishListDao(wishListDao);
        wishService.setUserEventDao(userEvent);

        UserService userService = new UserService();
        userService.setUserEventDao(userEvent);
        userService.setUserDao(userDao);

        WishController wishController = new WishController();
        wishController.setUserService(userService);
        wishController.setWishService(wishService);
        return wishController;
    }
}