package com.github.abigail830.wishlist.dtov1;


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
