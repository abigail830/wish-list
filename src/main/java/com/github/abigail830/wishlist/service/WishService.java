package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.repository.ComplexWishDaoImpl;
import com.github.abigail830.wishlist.repository.UserEventImpl;
import com.github.abigail830.wishlist.repository.WishDaoImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private static final Logger logger = LoggerFactory.getLogger(WishService.class);

    @Autowired
    private WishListDaoImpl wishListDao;

    @Autowired
    private WishDaoImpl wishDao;

    @Autowired
    private ComplexWishDaoImpl complexWishDao;

    @Autowired
    private UserEventImpl userEventDao;

    public WishListDetail getWishListDetailByWishListID(String wishListId){
        return complexWishDao.getWishListDetail(wishListId);
    }

    public List<WishList> getWishListByID(String id){
        return wishListDao.getWishListById(id);
    }

    public List<WishList> getWishListByOpenID(String openId){
        return wishListDao.getWishListByOpenId(openId);
    }

    public List<Wish> getWishDetailByID(String id){
        return wishDao.getWishByID(id);
    }

    public List<Wish> getWishDetailByWishListID(String wishListID){
        return wishDao.getWishByWishListId(wishListID);
    }

    public int getFriendsCompletedWishCountByImplementorID(String openId){
        return complexWishDao.getFriendsWishCompletedCountByImplementorOpenID(openId);
    }

    public int getMyCompletedWishCount(String openId){
        return complexWishDao.getMyWishCompletedCount(openId);
    }

}
