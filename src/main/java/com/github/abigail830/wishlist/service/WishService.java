package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.domain.BriefWishList;
import com.github.abigail830.wishlist.domainv1.WishDTO;
import com.github.abigail830.wishlist.domainv1.WishListDTO;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.repository.ComplexWishDaoImpl;
import com.github.abigail830.wishlist.repository.UserEventImpl;
import com.github.abigail830.wishlist.repository.WishDaoImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import com.github.abigail830.wishlist.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public WishListDetail getWishListDetailByWishListID(String wishListId){
        return complexWishDao.getWishListDetail(wishListId);
    }

    public void deleteWishListByListID(String wishListId) {
        wishListDao.deleteWishList(Integer.valueOf(wishListId));
    }

    public void createWishList(WishList wishList) {
        wishListDao.createWishList(wishList);
    }

    public void insertNewWishItem(WishDTO wishDTO, Integer wishListID) {
        Wish wish = new Wish();
        wish.setDescription(wishDTO.getDescription());
        wish.setWishStatus(Constants.WISH_STATUS_NEW);
        wish.setWishListId(wishListID);
        wishDao.createWish(wish);
    }

    public WishListDTO createWishList(WishListDTO wishListDTO) throws ParseException {
        WishList wishList = convertWishListFromDTOToEntity(wishListDTO);
        wishListDao.createWishList(wishList);
        WishList wishListInDB = wishListDao.getWishListByOpenId(wishListDTO.getListOpenId())
                .stream()
                .filter(item -> wishListDTO.getListDescription().equals(item.getDescription()))
                .max(((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()))).get();
        wishListDTO.getWishes().forEach(wishItem -> insertNewWishItem(wishItem, wishListInDB.getId()));
        return new WishListDTO(wishListInDB);
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


    public void setWishListDao(WishListDaoImpl wishListDao) {
        this.wishListDao = wishListDao;
    }

    public void setWishDao(WishDaoImpl wishDao) {
        this.wishDao = wishDao;
    }

    public void setComplexWishDao (ComplexWishDaoImpl complexWishDao) {
        this.complexWishDao = complexWishDao;
    }

    public void setUserEventDao(UserEventImpl userEventDao) {
        this.userEventDao = userEventDao;
    }

    public void deleteWishList(WishListDTO wishListDTO) {
        List<WishList> wishLists = wishListDao.getWishListById(wishListDTO.getListId().toString());
        if (wishLists.size() > 0 ) {
            WishList wishListToBeDeleted = wishLists.get(0);
            wishDao.deleteByWishListID(wishListToBeDeleted.getId());
            wishListDao.deleteWishList(wishListToBeDeleted.getId());
        }

    }

    public void updateWishList(WishListDTO wishListDTO) throws ParseException {
        WishList wishList = convertWishListFromDTOToEntity(wishListDTO);
        wishListDao.updateWishListByID(wishList);
    }

    private WishList convertWishListFromDTOToEntity(WishListDTO wishListDTO) throws ParseException {
        WishList wishList = new WishList();
        wishList.setId(wishListDTO.getListId());
        wishList.setDescription(wishListDTO.getListDescription());
        wishList.setOpenId(wishListDTO.getListOpenId());
        wishList.setDueTime(new java.sql.Timestamp(dateFormatter.get().parse(wishListDTO.getListDueTime()).getTime()));
        return wishList;
    }

    public WishDTO addNewWish(WishDTO wishDTO) {

        Wish wish = new Wish();
        wish.setDescription(wishDTO.getDescription());
        wish.setWishStatus(Constants.WISH_STATUS_NEW);
        wish.setWishListId(wishDTO.getWishListID());
        wishDao.createWish(wish);
        Wish newWishInDB = wishDao.getWishByWishListId(wishDTO.getWishListID().toString())
                .stream()
                .filter(item -> wishDTO.getDescription().equals(item.getDescription()))
                .max(((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()))).get();

       return new WishDTO(newWishInDB);

    }

    public void deleteWish(WishDTO wishDTO) {
        wishDao.deleteByWishID(wishDTO.getWishID());
    }

    public void updateWish(WishDTO wishDTO) {
        Wish wish = new Wish();
        wish.setDescription(wishDTO.getDescription());
        wish.setWishStatus(wishDTO.getWishStatus());
        wish.setWishListId(wishDTO.getWishListID());
        wish.setId(wishDTO.getWishID());
        if (wishDTO.getImplementor() != null  && StringUtils.isNotBlank(wishDTO.getImplementor().getOpenId())) {
            wish.setImplementorOpenId(wishDTO.getImplementor().getOpenId());
        }
        wishDao.updateWish(wish);
    }

    public List<WishDTO> getTakenUpWish(String openId) {
        return wishDao.getWishByTakenupUserID(openId).stream().map(WishDTO::new).collect(Collectors.toList());
    }

    public void takeupWish(String id, String takeUpOpenID) {
        wishDao.takeupWish(id, takeUpOpenID);
    }

    public void completeWish(String id) {
        wishDao.completeWish(id);
    }

    public void removeTakeUp(String id) {
        wishDao.removeTakenupWish(id);
    }
}
