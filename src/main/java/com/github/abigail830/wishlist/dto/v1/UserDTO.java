package com.github.abigail830.wishlist.dto.v1;


import com.github.abigail830.wishlist.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String language;
    private String avatarUrl;

    public UserDTO(String openId) {
        this.openId = openId;
        this.gender = "0";
        this.nickName = "";
        this.city = "";
        this.country = "";
        this.province = "";
        this.language = "";
        this.avatarUrl = "";
    }

    public UserDTO(User user) {
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
