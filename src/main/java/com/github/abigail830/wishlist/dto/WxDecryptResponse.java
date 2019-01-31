package com.github.abigail830.wishlist.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@ApiModel("微信信息解密返回结果")
public class WxDecryptResponse {

    @ApiModelProperty(value = "解密错误代码", example = "ErrorCode::$IllegalIv;")
    private String errorCode;

    @ApiModelProperty(value = "执行代码", example = "0000")
    private String code;

    private String msg;

    private UserInfo userInfo;

}
