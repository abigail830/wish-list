package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.repository.UserEventImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WishService {
    private static final Logger logger = LoggerFactory.getLogger(WishService.class);

    @Autowired
    private WishListDaoImpl wishListDao;

    @Autowired
    private UserEventImpl userEventDao;

    public List<WishList> getWishListByID(String id){
        return Arrays.asList(wishListDao.getWishListById(id));
    }

    public List<WishList> getWishListByOpenID(String openId){
        return wishListDao.getWishListByOpenId(openId);
    }

}
