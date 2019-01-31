package com.github.abigail830.wishlist.dtov1.card;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class APITicketDTO {

    private String errcode;
    private String errmsg;
    private String ticket;
    private long expires_in;
    private long validInSecond;

}
