package com.github.abigail830.wishlist.service;


import com.github.abigail830.wishlist.entity.FormIDMapping;
import com.github.abigail830.wishlist.repository.FormIDMappingDaoImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormIDMappingService {

    @Autowired
    private FormIDMappingDaoImpl formIDMappingDao;

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

    public static boolean isValidFormIDMapping(FormIDMapping formIDMapping) {
        DateTime dueDateTime = new DateTime(formIDMapping.getDueTime());
        return dueDateTime.isAfterNow();
    }


    public void setFormIDMappingDao(FormIDMappingDaoImpl formIDMappingDao) {
        this.formIDMappingDao = formIDMappingDao;
    }
}
