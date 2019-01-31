package com.github.abigail830.wishlist.dto;

import com.github.abigail830.wishlist.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserInfo {

    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String language;
    private String avatarUrl;

    public UserInfo(String openId) {
        this.openId = openId;
        this.gender = "0";
        this.nickName = "";
        this.city = "";
        this.country = "";
        this.province = "";
        this.language = "";
        this.avatarUrl = "";
    }


    public UserInfo(User user) {
        this.openId = user.getOpenId();
        this.gender = user.getGender();
        this.nickName = user.getNickName();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.province = user.getProvince();
        this.language = user.getLang();
        this.avatarUrl = user.getAvatarUrl();
    }

}
