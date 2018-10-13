package com.github.abigail830.wishlist.domain;

public class WxDecryptResponse {

    private String errorCode;

    private String code;
    private String msg;
    private UserInfo userInfo;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "WxDecryptResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
