package com.github.abigail830.wishlist.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String openId;
    private String gender;
    private String nickName;
    private String city;
    private String country;
    private String province;
    private String lang;
    private String avatarUrl;

}
