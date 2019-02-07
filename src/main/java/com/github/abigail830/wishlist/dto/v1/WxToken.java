package com.github.abigail830.wishlist.dto.v1;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxToken {

    private String access_token;
    private Integer expires_in;

}
