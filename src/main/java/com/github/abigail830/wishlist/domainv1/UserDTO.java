package com.github.abigail830.wishlist.domainv1;


import com.github.abigail830.wishlist.entity.User;

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
        this.avatarUrl="";
    }

    public UserDTO(String openId, String gender, String nickName, String city, String country, String province,
                    String language, String avatarUrl) {
        this.openId = openId;
        this.gender = gender;
        this.nickName = nickName;
        this.city = city;
        this.country = country;
        this.province = province;
        this.language = language;
        this.avatarUrl = avatarUrl;
    }

    public UserDTO(User user){
        this.openId = user.getOpenId();
        this.gender = user.getGender();
        this.nickName = user.getNickName();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.province = user.getProvince();
        this.language = user.getLang();
        this.avatarUrl = user.getAvatarUrl();
    }



    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", openId='" + openId + '\'' +
                ", gender=" + gender +
                ", nickName='" + nickName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
