package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.dto.v1.*;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.repository.ComplexWishDaoImpl;
import com.github.abigail830.wishlist.repository.WishDaoImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import com.github.abigail830.wishlist.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WishService {

    @Autowired
    private WelcomeCouponService welcomeCouponService;

    @Autowired
    private WishListDaoImpl wishListDao;

    @Autowired
    private WishDaoImpl wishDao;

    @Autowired
    private ComplexWishDaoImpl complexWishDao;


    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override protected SimpleDateFormat initialValue() {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> legacyDateFormatter = new ThreadLocal<SimpleDateFormat>() {
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
                .filter(item -> wishListDTO.getListDescription().equals(item.getTitle()))
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

    public WelcomeCouponService getWelcomeCouponService() {
        return welcomeCouponService;
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
        wishList.setTitle(wishListDTO.getListDescription());
        wishList.setBrief(wishListDTO.getListDescription2());
        wishList.setOpenId(wishListDTO.getListOpenId());
        wishList.setAddress(wishListDTO.getAddress());
        wishList.setIsSelfWitness(wishListDTO.getIsSelfWitness());
        wishList.setImplementorsLimit(wishListDTO.getImplementorsLimit() != null? wishListDTO.getImplementorsLimit():1);
        if (wishListDTO.getListDueTime().length() > 10) {
            wishList.setDueTime(new java.sql.Timestamp(dateFormatter.get().parse(wishListDTO.getListDueTime()).getTime()));
        } else {
            wishList.setDueTime(new java.sql.Timestamp(legacyDateFormatter.get().parse(wishListDTO.getListDueTime()).getTime()));
        }
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

    public List<WishDTO> takeupWish(String id, String takeUpOpenID) {
        List<Wish> wishByID = wishDao.getWishByID(id);
        if (wishByID.size() > 0) {
            wishDao.takeupWish(id, takeUpOpenID);
            deliverCoupon(wishByID);
            return wishDao.getWishByWishListId(wishByID.get(0).getWishListId().toString())
                    .stream()
                    .map(WishDTO::new).collect(Collectors.toList()) ;
        } else {
            throw new IllegalArgumentException("wish not found by ID");
        }
    }

    public List<WishDTO> takeupWishV2 (String id, String takeUpOpenID) {
        List<Wish> wishByID = wishDao.getWishByID(id);
        if (wishByID.size() > 0) {
            takeUp(wishByID.get(0), takeUpOpenID);
            deliverCoupon(wishByID);
            return wishDao.getWishByWishListId(wishByID.get(0).getWishListId().toString())
                    .stream()
                    .map(WishDTO::new).collect(Collectors.toList()) ;
        } else {
            throw new IllegalArgumentException("wish not found by ID");
        }
    }

    private void takeUp(Wish takeupWish, String takeUpOpenID) {
        int implementorLimit = takeupWish.getImplementorsLimit() != null? takeupWish.getImplementorsLimit() : 1;
        int currentImplementorSequence = wishDao.getCurrentImplementorSequence(takeupWish.getId().toString());
        if (currentImplementorSequence < (implementorLimit - 1)) {
            log.info("Use the sequence {} for open id {} take up {} ", (currentImplementorSequence + 1), takeUpOpenID, takeupWish.getId());
            wishDao.takeupWish(takeupWish.getId().toString(), takeUpOpenID, (currentImplementorSequence + 1));
            wishDao.updateTakeupStatus(takeupWish.getId().toString());
        } else {
            log.error("Take up user volume is over the limit " + implementorLimit + " current sequence " + currentImplementorSequence);
            throw new RuntimeException("Take up user volume is over the limit " + implementorLimit);
        }
    }

    private void deliverCoupon(List<Wish> wishByID) {
        List<Wish> wishList = wishDao.getWishDetailByWishListID(wishByID.get(0).getWishListId().toString());
        welcomeCouponService.deliverWelcomeCoupon(wishList);
    }

    public List<Wish> getWishByID(String id) {
        return wishDao.getWishByID(id);
    }

    public List<WishDTO>  completeWish(String id, String takeUpOpenID) {
        wishDao.completeWish(id);
        return wishDao.getWishByTakenupUserID(takeUpOpenID).stream().map(WishDTO::new).collect(Collectors.toList());
    }

    public void removeTakeUp(String id) {
        wishDao.removeTakenupWish(id);
    }

    public List<Wish> getWishes(WishListDTO wishListDTO) {
        return wishDao.getWishByWishListId(wishListDTO.getListId().toString())
                .stream().filter(item -> item.getDescription() != null).collect(Collectors.toList());
    }

    public int calculateProgress(List<Wish> wishes) {
        if (wishes == null || wishes.size() == 0) {
            return 100;
        } else {
            double doneCount = wishes.stream().filter(item -> Constants.WISH_STATUS_DONE.equals(item.getWishStatus())).count();
            BigDecimal total = BigDecimal.valueOf(wishes.size());
            return total.subtract(BigDecimal.valueOf(doneCount)).divide(total, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).intValue();
        }
    }

    public void enrichWishListProgress (List<WishListDTO> wishListDTOs) {
        for (WishListDTO wishListDTO : wishListDTOs) {
            int progress = calculateProgress(getWishes(wishListDTO));
            wishListDTO.setProgress(progress);

        }
    }

    public WishListTimeline getWishListTimeLine(String openId) {
        int myCompletedWishCount = getMyCompletedWishCount(openId);
        int myFriendCompletedWishCount = getFriendsCompletedWishCountByImplementorID(openId);
        List<WishListDTO> wishListDTOs = getWishListByOpenID(openId)
                .stream().map(WishListDTO::new).collect(Collectors.toList());
        enrichWishListProgress(wishListDTOs);
        TreeMap<String, WishListTimelineEntry> wishListTimelineEntryMap = new TreeMap<String, WishListTimelineEntry>();

        for (WishListDTO wishListDTO : wishListDTOs) {
            String month = wishListDTO.getYearAndMonth();
            WishListTimelineEntry wishListTimelineEntry = wishListTimelineEntryMap.get(month);
            if (wishListTimelineEntry == null) {
                String[] yearAndMonth = month.split("-");
                String dateToPresent = yearAndMonth[0] + "年" + yearAndMonth[1] + "月";
                ArrayList<WishListDTO> wishListDTOList = new ArrayList<>();
                wishListDTOList.add(wishListDTO);
                WishListTimelineEntry newWishListTimelineEntry = new WishListTimelineEntry(dateToPresent, wishListDTOList);
                wishListTimelineEntryMap.put(month, newWishListTimelineEntry);
            } else {
                wishListTimelineEntry.getWishListDTOList().add(wishListDTO);
            }

        }
        List<WishListTimelineEntry> resultList = new ArrayList<WishListTimelineEntry>(wishListTimelineEntryMap.values());
        Collections.reverse(resultList);

        CouponMappingDTO outstandingWelcomeCoupon = welcomeCouponService.getOutstandingWelcomeCoupon(openId);

        return new WishListTimeline(resultList,
                wishListDTOs,
                myCompletedWishCount,
                myFriendCompletedWishCount,
                outstandingWelcomeCoupon);
    }

    public TakenWishTimeline getTakenWishTimeline(String openId) {
        List<TakenWishDTO> takenWishDTOs = wishDao.getWishByTakenupUserID(openId).stream().map(TakenWishDTO::new).collect(Collectors.toList());

        TreeMap<String, TakenWishTimelineEntry> takenWishTimelineEntryMap = new TreeMap<String, TakenWishTimelineEntry>();
        for (TakenWishDTO takenWishDTO : takenWishDTOs) {
            String month = takenWishDTO.getYearAndMonth();
            TakenWishTimelineEntry takenWishTimelineEntry = takenWishTimelineEntryMap.get(month);
            if (takenWishTimelineEntry == null) {
                String[] yearAndMonth = month.split("-");
                String dateToPresent = yearAndMonth[0] + "年" + yearAndMonth[1] + "月";
                ArrayList<TakenWishDTO> takenWishDTOList = new ArrayList<>();
                takenWishDTOList.add(takenWishDTO);
                TakenWishTimelineEntry newTakenWishTimelineEntry = new TakenWishTimelineEntry(dateToPresent, takenWishDTOList);
                takenWishTimelineEntryMap.put(month, newTakenWishTimelineEntry);
            } else {
                takenWishTimelineEntry.getTakenWishDTOList().add(takenWishDTO);
            }

        }
        List<TakenWishTimelineEntry> resultList = new ArrayList<TakenWishTimelineEntry>(takenWishTimelineEntryMap.values());
        Collections.reverse(resultList);
        return new TakenWishTimeline(resultList);
    }

    public WishList getWishList(Wish wish) {
        List<WishList> wishListById = wishListDao.getWishListById(wish.getWishListId().toString());
        if (wishListById.size() == 0) {
            throw new IllegalArgumentException("Can not find the wish list with wish id " + wish.getWishListId());
        } else {
            return wishListById.get(0);
        }
    }

    public Wish getWish(String wishID) {
        List<Wish> wishByID = wishDao.getWishByID(wishID);
        if (wishByID.size() == 0) {
            throw new IllegalArgumentException("Can not find the wish with wish id " + wishID);
        } else {
            return wishByID.get(0);
        }
    }

    public List<WishDTO> completeWishWithWitnessCheck(String id, String takeUpOpenID) {
        Wish wish = getWish(id);
        WishList wishList = getWishList(wish);
        if (wishList.getIsSelfWitness() != null && wishList.getIsSelfWitness() == true) {
            if (takeUpOpenID.equals(wishList.getOpenId())) {
                return completeWish(id, takeUpOpenID);
            } else {
                throw new IllegalArgumentException("Self Witness wish could only be completed by owner");
            }
        } else {
            List<User> users = wishDao.queryImplementors(id);
            if (users.stream().anyMatch(user -> takeUpOpenID.equals(user.getOpenId())) || takeUpOpenID.equals(wish.getImplementorOpenId())) {
                return completeWish(id, takeUpOpenID);
            } else {
                throw new IllegalArgumentException("The open ID is not in the implementors list " + takeUpOpenID );
            }

        }
    }
}
