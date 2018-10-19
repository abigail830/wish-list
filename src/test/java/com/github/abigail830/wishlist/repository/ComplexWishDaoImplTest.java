package com.github.abigail830.wishlist.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplexWishDaoImplTest {

    @Autowired
    ComplexWishDaoImpl complexWishDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFriendsWishCompletedCountByImplementorOpenID() {
        System.out.println(
                complexWishDao.getFriendsWishCompletedCountByImplementorOpenID("oEmJ75YWmBSDgyz4KLi_yGL8MBV4"));
    }

    @Test
    public void getMyWishCompletedCount() {
        System.out.println(
                complexWishDao.getMyWishCompletedCount("oEmJ75YWmBSDgyz4KLi_yGL8MBV4")
        );
    }

    @Test
    public void getWishListDetail() {
        System.out.println(complexWishDao.getWishListDetail("2"));
    }
}