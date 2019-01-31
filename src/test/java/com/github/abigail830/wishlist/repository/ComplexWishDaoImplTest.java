package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.dto.UserInfo;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class ComplexWishDaoImplTest {


    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:ComplexWishDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname1", "city",
                "country", "province", "lang", "imageUrl"));
        userDao.createUser(new UserInfo("openID2", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl2"));
        userDao.createUser(new UserInfo("openID3", "M", "nickname3", "city",
                "country", "province", "lang", "imageUrl3"));

        WishList wishList = new WishList();
        wishList.setId(1);
        wishList.setOpenId("openID1");
        wishList.setTitle("THIS IS FOR TEST");
        wishList.setBrief("THIS IS BRIEF TEST");
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
        Wish wish2 = new Wish();
        wish2.setWishListId(Integer.valueOf(1));
        wish2.setWishStatus(Constants.WISH_STATUS_NEW);
        wish2.setDescription("DESC2");
        wishDaoImpl.createWish(wish2);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM user_event WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wish_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wishlist_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM user_tbl WHERE ID is not null");
    }


    @Test
    public void getFriendsWishCompletedCountByImplementorOpenID() {
        ComplexWishDaoImpl complexWishDao = new ComplexWishDaoImpl();
        complexWishDao.setJdbcTemplate(jdbcTemplate);
        assertThat(complexWishDao.getFriendsWishCompletedCountByImplementorOpenID("openID2"), is(1));
    }

    @Test
    public void getMyWishCompletedCount() {
        ComplexWishDaoImpl complexWishDao = new ComplexWishDaoImpl();
        complexWishDao.setJdbcTemplate(jdbcTemplate);
        assertThat(complexWishDao.getMyWishCompletedCount("openID1"), is(1));
    }

    @Test
    public void getWishListDetail() {
        ComplexWishDaoImpl complexWishDao = new ComplexWishDaoImpl();
        complexWishDao.setJdbcTemplate(jdbcTemplate);
        WishListDetail wishListDetail = complexWishDao.getWishListDetail("1");
        assertThat(wishListDetail.getListOpenId(), is("openID1"));
        assertThat(wishListDetail.getWishes().size(), is(2));
        assertThat(wishListDetail.getListTitle(), is("THIS IS FOR TEST"));
        assertThat(wishListDetail.getListBrief(), is("THIS IS BRIEF TEST"));
        Wish doneWish = wishListDetail.getWishes().stream()
                .filter(item -> Constants.WISH_STATUS_DONE.equals(item.getWishStatus()))
                .collect(Collectors.toList())
                .get(0);
        assertThat(doneWish.getDescription(), is("DESC1"));
        assertThat(doneWish.getImplementorOpenId(), is("openID2"));
        assertThat(doneWish.getImplementor().getNickName(), is("nickname2"));
        Wish openWish = wishListDetail.getWishes().stream()
                .filter(item -> Constants.WISH_STATUS_NEW.equals(item.getWishStatus()))
                .collect(Collectors.toList())
                .get(0);
        assertThat(openWish.getDescription(), is("DESC2"));
        assertThat(openWish.getImplementor(), is(nullValue()));

    }
}