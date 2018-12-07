package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.util.Constants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class WishServiceTest {


    @Test
    public void testCanCalculateProgress() throws Exception {
        Wish wish1 = new Wish();
        Wish wish2 = new Wish();
        Wish wish3 = new Wish();
        Wish wish4 = new Wish();

        wish1.setDescription("wish1");
        wish1.setWishStatus(Constants.WISH_STATUS_NEW);

        wish2.setDescription("wish2");
        wish2.setWishStatus(Constants.WISH_STATUS_TAKEUP);

        wish3.setDescription("wish3");
        wish3.setWishStatus(Constants.WISH_STATUS_DONE);

        wish4.setDescription("wish4");
        wish4.setWishStatus(Constants.WISH_STATUS_DONE);

        WishService wishService = new WishService();
        int progress = wishService.calculateProgress(Arrays.asList(wish1, wish2, wish3, wish4));

        assertEquals(50, progress);
    }

    @Test
    public void testCanCalculateProgressForEmptyWishList() throws Exception {

        WishService wishService = new WishService();
        int progress = wishService.calculateProgress(new ArrayList<>());

        assertEquals(100, progress);
    }

    @Test
    public void testCanCalculateProgressForNothingDone() throws Exception {

        Wish wish1 = new Wish();
        Wish wish2 = new Wish();
        Wish wish3 = new Wish();
        Wish wish4 = new Wish();

        wish1.setDescription("wish1");
        wish1.setWishStatus(Constants.WISH_STATUS_NEW);

        wish2.setDescription("wish2");
        wish2.setWishStatus(Constants.WISH_STATUS_TAKEUP);

        wish3.setDescription("wish3");
        wish3.setWishStatus(Constants.WISH_STATUS_TAKEUP);

        wish4.setDescription("wish4");
        wish4.setWishStatus(Constants.WISH_STATUS_TAKEUP);

        WishService wishService = new WishService();
        int progress = wishService.calculateProgress(Arrays.asList(wish1, wish2, wish3, wish4));

        assertEquals(100, progress);
    }

    @Test
    public void testCanCalculateProgressForAllDone() throws Exception {

        Wish wish1 = new Wish();
        Wish wish2 = new Wish();
        Wish wish3 = new Wish();
        Wish wish4 = new Wish();

        wish1.setDescription("wish1");
        wish1.setWishStatus(Constants.WISH_STATUS_DONE);

        wish2.setDescription("wish2");
        wish2.setWishStatus(Constants.WISH_STATUS_DONE);

        wish3.setDescription("wish3");
        wish3.setWishStatus(Constants.WISH_STATUS_DONE);

        wish4.setDescription("wish4");
        wish4.setWishStatus(Constants.WISH_STATUS_DONE);

        WishService wishService = new WishService();
        int progress = wishService.calculateProgress(Arrays.asList(wish1, wish2, wish3, wish4));

        assertEquals(0, progress);
    }

}