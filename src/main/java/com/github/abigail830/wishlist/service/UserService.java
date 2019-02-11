package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.dto.UserInfo;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.UserEvent;
import com.github.abigail830.wishlist.repository.UserDaoImpl;
import com.github.abigail830.wishlist.repository.UserEventImpl;
import com.github.abigail830.wishlist.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserEventImpl userEventDao;

    public void createUser(UserInfo userInfo) {

        log.debug("Record UserInfo into DB for user [{}].", userInfo.getOpenId());
        userDao.createUser(userInfo);

        //Insert user_event
        log.debug("Record Login event into DB for user [{}].", userInfo.getOpenId());
        UserEvent event = new UserEvent(userInfo.getOpenId());
        event.setEventType(Constants.EVENT_TYPE_LOGIN);
        userEventDao.createUserEvent(event);
    }

    public void updateUser(UserInfo userInfo) {
        //Insert user_tbl
        log.debug("Update UserInfo into DB for user [{}].", userInfo.getOpenId());
        userDao.updateUserByOpenID(userInfo);

        //Insert user_event
        log.debug("Record authorize event into DB for user [{}].", userInfo.getOpenId());
        UserEvent event = new UserEvent(userInfo.getOpenId());
        event.setEventType(Constants.EVENT_TYPE_AUTHORIZE);
        userEventDao.createUserEvent(event);
    }

    public User getUserByOpenId(String openId){
        return userDao.getUserByOpenId(openId);
    }

    public User getUserByUnionId(String id) {
        return userDao.getUserById(id);
    }

    public void setUserDao (UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public void setUserEventDao (UserEventImpl userEventDao) {
        this.userEventDao = userEventDao;
    }
}
