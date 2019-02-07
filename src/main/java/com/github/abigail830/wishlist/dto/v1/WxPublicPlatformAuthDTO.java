package com.github.abigail830.wishlist.dto.v1;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxPublicPlatformAuthDTO {
    private String access_token;

    private String expires_in;

    private String errcode;

    private String errmsg;

    private long validInSecond;

}
