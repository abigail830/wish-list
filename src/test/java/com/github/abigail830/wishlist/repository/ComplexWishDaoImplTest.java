package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class ComplexWishDaoImplTest {


    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:ComplexWishDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        Toggle.TEST_MODE.setStatus(true);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        userDao.createUser(new UserInfo("openID1", "M", "nickname1", "city",
                "country", "province", "lang", "imageUrl"));
        userDao.createUser(new UserInfo("openID2", "M", "nickname2", "city",
                "country", "province", "lang", "imageUrl2"));

        WishList wishList = new WishList();
        wishList.setId(1);
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
        assertThat(wishListDetail.getWishes().get(0).getWishStatus(),is(Constants.WISH_STATUS_DONE));
        assertThat(wishListDetail.getWishes().get(0).getImplementorOpenId(),is("openID2"));
    }
}