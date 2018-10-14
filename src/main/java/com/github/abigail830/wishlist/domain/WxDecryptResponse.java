package com.github.abigail830.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("微信信息解密返回结果")
public class WxDecryptResponse {

    @ApiModelProperty(value = "解密错误代码", example = "ErrorCode::$IllegalIv;")
    private String errorCode;

    @ApiModelProperty(value = "执行代码", example = "0000")
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
