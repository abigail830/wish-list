package com.github.abigail830.wishlist.dto.v1;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WxQrCodeRequestDTO {
    String path;
    String width;
    Boolean is_hyaline;

}
