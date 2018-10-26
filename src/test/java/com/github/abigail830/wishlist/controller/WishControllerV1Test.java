package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.domain.WishListsResponse;
import com.github.abigail830.wishlist.domainv1.WishDTO;
import com.github.abigail830.wishlist.domainv1.WishDashboardDTO;
import com.github.abigail830.wishlist.domainv1.WishListDTO;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.repository.*;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class WishControllerV1Test {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:WishControllerTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        positionData();
    }

    private static void positionData() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname1", "city",
                "country", "province", "lang", "imageUrl"));
        userDao.createUser(new UserInfo("openID2", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl2"));

    }




    @Test
    public void canCreateAndDeleteWishList() throws ParseException {
        WishControllerV1 wishController = getWishController();

        WishList wishList = new WishList();
        wishList.setOpenId("openID1");
        wishList.setDescription("THIS IS FOR WISE LIST ROUNDTRIP TEST");

        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setListDescription("this is the test list");
        wishListDTO.setListDueTime("2018-10-26");
        wishListDTO.setListOpenId("openID1");

        WishDTO wishDTO = new WishDTO();
        wishDTO.setDescription("wish1");

        WishDTO wishDTO2 = new WishDTO();
        wishDTO2.setDescription("wish2");

        wishListDTO.addWish(wishDTO);
        wishListDTO.addWish(wishDTO2);

        WishDashboardDTO wishDashboardDTO = wishController.postNewWishList(wishListDTO);
        assertThat(wishDashboardDTO.getWishLists().size(), is(1));
        WishDashboardDTO wishListsByID = wishController.getWishListsByID("1", null);
        assertThat(wishListsByID.getWishLists().get(0).getWishes().size(), is(2));
    }


    private WishControllerV1 getWishController() {
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

        WishControllerV1 wishController = new WishControllerV1();
        wishController.setUserService(userService);
        wishController.setWishService(wishService);
        return wishController;
    }
}