package com.github.abigail830.wishlist.dto.v1.card;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class APITicketDTO {

    private String errcode;
    private String errmsg;
    private String ticket;
    private long expires_in;
    private long validInSecond;

}
