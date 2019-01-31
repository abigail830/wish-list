package com.github.abigail830.wishlist.dtov1.card;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfoDTO {
    private String logo_url;
    private String code_type;
    private String brand_name;
    private String title;
    private String color;
    private String notice;
    private String description;
    private SkuDTO sku;
    private DateInfoDTO date_info;

}
