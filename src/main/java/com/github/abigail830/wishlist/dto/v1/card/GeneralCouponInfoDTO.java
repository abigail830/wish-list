package com.github.abigail830.wishlist.dto.v1.card;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GeneralCouponInfoDTO {

    private BaseInfoDTO base_info;

    private AdvancedInfoDTO advanced_info;

    private String default_detail;

}
