package com.github.abigail830.wishlist.dtov1.card;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoDTO {

    private String type;

    private int begin_timestamp;

    private int end_timestamp;

    private int fixed_begin_term;

}
