package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.domain.UserInfo;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.UserEvent;
import com.github.abigail830.wishlist.repository.UserDaoImpl;
import com.github.abigail830.wishlist.repository.UserEventImpl;
import com.github.abigail830.wishlist.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserEventImpl userEvent;

    public void createUser(UserInfo userInfo) {
        //Insert user_tbl
        userDao.createUser(userInfo);

        //Insert user_event
        UserEvent event = new UserEvent(userInfo.getOpenId());
        event.setEventType(Constants.EVENT_TYPE_LOGIN);
        userEvent.createUserEvent(event);
    }

    public void updateUser(UserInfo userInfo) {
        //Insert user_tbl
        userDao.updateUserByOpenID(userInfo);

        //Insert user_event
        UserEvent event = new UserEvent(userInfo.getOpenId());
        event.setEventType(Constants.EVENT_TYPE_AUTHORIZE);
        userEvent.createUserEvent(event);
    }

    public User getUserByOpenId(String openId){
        return userDao.getUserByOpenId(openId);
    }

    public User getUserByUnionId(String id) {
        return userDao.getUserById(id);
    }
}
