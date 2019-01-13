package com.github.abigail830.wishlist.service;


import com.github.abigail830.wishlist.domainv1.WishDTO;
import com.github.abigail830.wishlist.entity.FormIDMapping;
import com.github.abigail830.wishlist.entity.WishList;
import com.github.abigail830.wishlist.repository.FormIDMappingDaoImpl;
import com.github.abigail830.wishlist.repository.WishListDaoImpl;
import org.joda.time.DateTime;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormIDMappingService {

    @Autowired
    private FormIDMappingDaoImpl formIDMappingDao;

    @Autowired
    private WishListDaoImpl wishListDao;

    public List<FormIDMapping> getFormIDs(String openID) {
        return formIDMappingDao.queryByOpenID(openID)
                .stream()
                .filter(FormIDMappingService::isValidFormIDMapping)
                .collect(Collectors.toList());
    }

    public FormIDMapping takeFormID(String openID) {
        List<FormIDMapping> formIDMappings = getFormIDs(openID);
        if (formIDMappings.size() > 0) {
            FormIDMapping formIDMapping = formIDMappings.get(0);
            if (formIDMappingDao.deleteFormIDMapping(formIDMapping.getFormId())) {
                return formIDMapping;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void contributeFormID(String openID, String formID) {
        formIDMappingDao.createFormIDMapping(
                openID,
                formID,
                DateTime.now().toDate(),
                DateTime.now().plusDays(6).toDate());
    }

    public void contributeFormID(WishDTO wishDTO, String formID) {
        List<WishList> wishListById = wishListDao.getWishListById(wishDTO.getWishListID().toString());
        if (wishListById.size() > 0
                && StringUtils.isNotBlank(wishListById.get(0).getOpenId())
                && StringUtils.isNotBlank(formID)) {
            contributeFormID(wishListById.get(0).getOpenId(), formID);
        }

    }

    public static boolean isValidFormIDMapping(FormIDMapping formIDMapping) {
        DateTime dueDateTime = new DateTime(formIDMapping.getDueTime());
        return dueDateTime.isAfterNow();
    }


    public void setFormIDMappingDao(FormIDMappingDaoImpl formIDMappingDao) {
        this.formIDMappingDao = formIDMappingDao;
    }
}