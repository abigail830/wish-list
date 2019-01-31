package com.github.abigail830.wishlist.dtov1.card;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardSignatureDTO {
    private String nonceStr;
    private String timestamp;
    private String signature;
    private String cardID;

}
